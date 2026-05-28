.data
prompt1: .asciiz "Enter first float: "
prompt2: .asciiz "Enter second float: "
resultMsg: .asciiz "Mantissa sum: "

.text
.globl main

main:
    # Prompt for first float
    li $v0, 4
    la $a0, prompt1
    syscall

    li $v0, 6              # read float
    syscall
    mfc1 $t0, $f0          # move raw float bits into $t0

    # Prompt for second float
    li $v0, 4
    la $a0, prompt2
    syscall

    li $v0, 6
    syscall
    mfc1 $t1, $f0          # move raw float bits into $t1

    # Extract sign, exponent, and mantissa for float 1
    srl $s0, $t0, 31              # sign1
    srl $s1, $t0, 23
    andi $s1, $s1, 0xFF           # exponent1
    andi $s2, $t0, 0x7FFFFF       # mantissa1

    # Extract sign, exponent, and mantissa for float 2
    srl $s3, $t1, 31              # sign2
    srl $s4, $t1, 23
    andi $s4, $s4, 0xFF           # exponent2
    andi $s5, $t1, 0x7FFFFF       # mantissa2

    # Add mantissas via function
    jal fp_add

    # Print result
    li $v0, 4
    la $a0, resultMsg
    syscall

    li $v0, 1
    move $a0, $s7
    syscall

    # Exit
    li $v0, 10
    syscall

# === Addition Function ===
fp_add:
    # Align exponents
    bgt $s1, $s4, firstGT
    bgt $s4, $s1, secondGT
    j addStep

firstGT:
    sub $s6, $s1, $s4
    srlv $s5, $s5, $s6      # shift mantissa2 right
    move $s4, $s1
    j addStep

secondGT:
    sub $s6, $s4, $s1
    srlv $s2, $s2, $s6      # shift mantissa1 right
    move $s1, $s4
    j addStep

addStep:
    add $s7, $s2, $s5       # add mantissas
    jr $ra
