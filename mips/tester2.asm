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
    
    
    