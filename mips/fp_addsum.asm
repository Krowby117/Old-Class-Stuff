# Gavin Dominique & Gunter Tannehill
# fp_addsum.asm -- A program that does floating point addition and multiplication
# $s0 = sign1
# $s1 = exp1
# $s2 = mant1
# $s3 = sign2
# $s4 = exp2
# $s5 = mant2
# $s6 = difference in exponents
# $s7 = final sum mantisssa
# $t9 = tempSign used in recombine after addition

.data
prompt1: .asciiz "Enter first float: "
prompt2: .asciiz "Enter second float: "
debug1: .asciiz "first negative"
debug2: .asciiz "second negative"
newline: .asciiz "\n"

.text
.globl main
main:
    #### Read first float ####
    li $v0, 4
    la $a0, prompt1
    syscall

    li $v0, 6              # syscall: read float
    syscall
    mfc1 $t0, $f0          # move float bits to integer register

    #### Read second float ####
    li $v0, 4
    la $a0, prompt2
    syscall

    li $v0, 6
    syscall
    mfc1 $t1, $f0

    #### Extract sign, exponent, mantissa for float 1 ####
    srl $s0, $t0, 31        # sign1 = bit 31
    srl $s1, $t0, 23
    andi $s1, $s1, 0xFF     # exponent1 = bits 30-23
    andi $s2, $t0, 0x7FFFFF # mantissa1 = bits 22-0
    ori $s2, $s2, 0x800000  # add implicit leading 1

    #### Extract sign, exponent, mantissa for float 2 ####
    srl $s3, $t1, 31        # sign2
    srl $s4, $t1, 23
    andi $s4, $s4, 0xFF     # exponent2
    andi $s5, $t1, 0x7FFFFF # mantissa2
    ori $s5, $s5, 0x800000  # add leading 1
    
li $t9, 0 # temp sign

### do addition
jal fp_add

li $v0, 2            # Print floating-point number
mov.s $f12, $f0      # Move result into $f12
syscall

jal fp_mul

### Exit program
li $v0, 10
syscall

### floating-point addition function
fp_add:
    # Check if the exponents are equal
    bgt $s1, $s4, firstGT
    bgt $s4, $s1, secondGT
    j addStep

firstGT:
    sub $s6, $s1, $s4           # difference in exponents
    srlv $s5, $s5, $s6          # shift mantissa2 to the right
    move $s1, $s4               # make exponents the same
    j addStep

secondGT:
    sub $s6, $s4, $s1           # difference in exponents
    srlv $s2, $s2, $s6          # shift mantissa1 to the right
    move $s4, $s1               # make exponents the same
    j addStep

addStep:
    # If the signs are different, subtract mantissas
    beq $s0, $s3, addMantissas    # If signs are the same, add mantissas
    bgt $s0, $s3, firstNegative   # If sign1 > sign2, negate mantissa1
    bgt $s3, $s0, secondNegative  # If sign2 > sign1, negate mantissa2

firstNegative:
    sub $s2, $zero, $s2          # Negate mantissa1
    j addMantissas

secondNegative:
    sub $s5, $zero, $s5          # Negate mantissa2
    j addMantissas

addMantissas:
    add $s7, $s2, $s5            # Add the mantissas
    bgez $s7, pass               # If the result is positive, pass
    sub $s7, $zero, $s7           # Make the result positive if it's negative
    addi $t9, $t9, 1
    j pass

pass:
    blt $s7, 0x800000, normalize_result   # Check if mantissa is less than 1
    srl $s7, $s7, 1               # Shift mantissa right (dividing by 2)
    addi $s1, $s1, 1              # Increment exponent by 1
    j finalize

normalize_result:
    bltz $s7, normalize_mantissa
    sll $s7, $s7, 1               # Shift mantissa left (multiplying by 2)
    addi $s1, $s1, -1             # Decrement exponent by 1
    j finalize

normalize_mantissa:
    sll $s7, $s7, 1               # Shift mantissa left
    addi $s1, $s1, -1             # Decrement exponent by 1

finalize:
    # Reassemble the floating-point number
    sll $s1, $s1, 23              # Move exponent to the correct position
    or $s7, $s7, $s1              # Combine mantissa and exponent
    #or $t9, $s0, $s3
    bnez $t9, apply_sign          # If sign is negative, apply the sign bit
    j done

apply_sign:
    li $t8, 0x80000000            # Set the sign bit (bit 31)
    or $s7, $s7, $t8              # Apply the sign bit

done:
    mtc1 $s7, $f0                 # Move the final result to floating-point register
    jr $ra                        # Return from the function

fp_mul:
    srl $s0, $t0, 31        # sign1 = bit 31
    srl $s1, $t0, 23
    andi $s1, $s1, 0xFF     # exponent1 = bits 30-23
    andi $s2, $t0, 0x7FFFFF # mantissa1 = bits 22-0


    #### Extract sign, exponent, mantissa for float 2 ####
    srl $s3, $t1, 31        # sign2
    srl $s4, $t1, 23
    andi $s4, $s4, 0xFF     # exponent2
    andi $s5, $t1, 0x7FFFFF # mantissa2

    # $s0 = sign1, $s1 = exp1, $s2 = mant1
    # $s3 = sign2, $s4 = exp2, $s5 = mant2

    xor $s0, $s0, $s3        # comparing signs

    add $s1, $s1, $s4        # add exponent

    mul $s7, $s2, $s5         # add mantissa

    sll $s0, $s0, 31
    sll $s1, $s1, 23

    or $t0, $s0, $s1
    or $t0, $t0, $s7

    mtc1 $t0, $f0

    jr $ra