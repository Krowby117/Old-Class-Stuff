# Gavin Dominique & Gunter
# charCounter.asm -- A program that converts lowercase to uppercase
# Registers used:
# $t0 – holds the string address
# $t1 - stores the counter for d
# $t2 - stores the counter for o
# $t3 - stores the counter for g
# $t4 - stores the counter for s
# $t5 - stores the byte being checked

.data
d: .asciiz "d: "
o: .asciiz "o: "
g: .asciiz "g: "
s: .asciiz "s: "
newline: .asciiz "\n" #useful
strbuff: .space 100          # Reserve 100 bytes for the input string

.text
main:
li $v0, 8
la $a0, strbuff              # $a0 = address of buffer
li $a1, 100                 # $a1 = maximum number of characters
syscall

move $t0, $a0

li $t1, 0	# counter for d char
li $t2, 0	# counter for o char
li $t3, 0	# counter for g char
li $t4, 0	# counter for s char

charCounting:
lb $t5, 0($t0) # get a byte
beqz $t5, end # if there’s nothing there, end
j d_check

d_check:
bne $t5, 100, o_check
addi $t1, $t1, 1
j continue

o_check:
bne $t5, 111, g_check
addi $t2, $t2, 1
j continue

g_check:
bne $t5, 103, s_check
addi $t3, $t3, 1
j continue

s_check:
bne $t5, 115, continue
addi $t4, $t4, 1
j continue

continue:
addi $t0, $t0, 1 # Increment the address
j charCounting

end:
li $v0, 4 # Print the string
la $a0, d
syscall
li $v0, 1
move $a0, $t1
syscall
li $v0, 4 # Print the string
la $a0, newline
syscall

li $v0, 4 # Print the string
la $a0, o
syscall
li $v0, 1
move $a0, $t2
syscall
li $v0, 4 # Print the string
la $a0, newline
syscall

li $v0, 4 # Print the string
la $a0, g
syscall
li $v0, 1
move $a0, $t3
syscall
li $v0, 4 # Print the string
la $a0, newline
syscall

li $v0, 4 # Print the string
la $a0, s
syscall
li $v0, 1
move $a0, $t4
syscall
li $v0, 4 # Print the string
la $a0, newline
syscall

# We are done, exit the program
li $v0, 10
syscall