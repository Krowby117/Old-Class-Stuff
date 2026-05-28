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

    #### You now have:
    # $s0 = sign1, $s1 = exp1, $s2 = mant1
    # $s3 = sign2, $s4 = exp2, $s5 = mant2
    
li $t2, 0 # temp sign

### do addition
jal fp_add

li $v0, 1
move $a0, $s7
syscall

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
    li $v0, 4
    la $a0, debug1
    syscall
    sub $s2, $zero, $s2          # Negate mantissa1
    j addMantissas

secondNegative:
    li $v0, 4
    la $a0, debug2
    syscall
    sub $s5, $zero, $s5          # Negate mantissa2
    j addMantissas

addMantissas:
    add $s7, $s2, $s5            # Add the mantissas
    bgez $s7, pass               # If the result is positive, pass
    sub $s7, $zero, $s7           # Make the result positive if it's negative
    j pass

pass:
    jr $ra                       # Return from function
