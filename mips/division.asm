# Gavin Dominique, Michael Tannehill, Michael Kenny, Tim Farley
# CSC 364 Lab 3
# 3/27/25
# division.asm-- A program that computes and prints the quotient
# and remainder of two numbers specified at runtime by the user.
# Registers used:
# $t0 - used to hold the divisor.
# $t1 - used to hold the dividend.
# $t2 - used to hold the quotient
# $t3 - used to hold the remainder
# $t4 - used to hold the tempDivisor
# $t5 - used to hold the multiple
# $t6 - used to hold the loop check value

.data
newline: .asciiz "\n"		# new line character

.text

main:
	li $v0, 5
	syscall
	add $t0, $zero, $v0 	# diviosr
              
	li $v0, 5
	syscall
	add $t1, $zero, $v0	# dividend
    
	li $t2, 0		# quotient / result
	li $t3, -1		# remainder
	
beqz $t1, bail
add $t3, $zero, $t0

outerloop:
blt $t3, $t1, bail
	add $t4, $zero, $t1		# tempDivisor
	li $t5, 1			# multiple
	
	innerloop:
	sll $t6, $t4, 1			# check value for loop
	bgt $t6, $t3, rest
	sll $t4, $t4, 1
	sll $t5, $t5, 1
	j innerloop
	
rest:
subu $t3, $t3, $t4
add $t2, $t2, $t5
j outerloop

bail:
li $v0, 1              
move $a0, $t2
syscall

li $v0, 4              
la $a0, newline
syscall

li $v0, 1              
move $a0, $t3
syscall

li $v0, 10
syscall