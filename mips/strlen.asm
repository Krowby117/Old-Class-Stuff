# Gavin Dominique & Gunter
# strlen.asm —- A program that determine’s a string’s length
# Registers used:
# $t0 – holds a counter
# $a0 – holds the address of the string
# $v0 - syscall parameter and sometimes return value
.data
str: .asciiz "Do you like Huey Lewis and the News?"

.text
# Load the string’s address into a0
la $a0, str

strlen:
li $t0, 0 # initialize the count to zero

loop:
lb $t1, 0($a0) # load the next character into t1
beqz $t1, exit # check for the null character

#increment the counter and jump
addi $t0, $t0, 1
addi $a0, $a0, 1
j loop

#wrap it up
exit:
li $v0, 1
move $a0, $t0
syscall

li $v0, 10
syscall