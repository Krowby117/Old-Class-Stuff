.data
    prompt1: .asciiz "Enter the first integer: "
    prompt2: .asciiz "Enter the second integer: "
    resultMsg: .asciiz "Result (first - second) = "

.text
.globl main

main:
    # Prompt and read first integer
    li $v0, 4              # syscall for print string
    la $a0, prompt1
    syscall

    li $v0, 5              # syscall for read int
    syscall
    move $t0, $v0          # store first int in $t0

    # Prompt and read second integer
    li $v0, 4
    la $a0, prompt2
    syscall

    li $v0, 5
    syscall
    move $t1, $v0          # store second int in $t1

    # Subtract second from first
    add $t2, $t0, $t1      # $t2 = $t0 - $t1

    # Print result message
    li $v0, 4
    la $a0, resultMsg
    syscall

    # Print result value
    li $v0, 1
    move $a0, $t2
    syscall

    # Exit
    li $v0, 10
    syscall
