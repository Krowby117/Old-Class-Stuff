.data
prompt1: .asciiz "Enter first int: "
prompt2: .asciiz "Enter second int: "

.text
.main
# Prompt for first integer
li $v0, 4          # syscall for printing string
la $a0, prompt1
syscall

li $v0, 5          # syscall for reading integer
syscall
move $s0, $v0      # store first integer in $s0

# Prompt for second integer
li $v0, 4
la $a0, prompt2
syscall

li $v0, 5
syscall
move $s1, $v0      # store second integer in $s1

## move values into function
move $a0, $s0
move $a1, $s1
jal min

## print out the return value
move $a0, $v0      # Move return value to argument for printing
li $v0, 1          # syscall for printing integer
syscall

# Exit
li $v0, 10
syscall

## the minimum function
min:
bgt $a0, $a1, next
move $v0, $a0
jr $ra
next:
move $v0, $a1
jr $ra
