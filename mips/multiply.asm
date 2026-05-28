# Gavin Dominique, Michael Tannehill, Michael Kenny, Tim Farley
# CSC 364 Lab 3
# 3/27/25
# multiply.asm-- A program that computes and prints the product
# of two numbers specified at runtime by the user.
# Registers used:
# $t0 - used to hold the first value.
# $t1 - used to hold the second value.
# $t2 - used to hold the result
# $t3 - last bit of second value

main:
    li $v0, 5
    syscall
    add $t0, $zero, $v0
              
    li $v0, 5
    syscall
    add $t1, $zero, $v0
    
    li $t2, 0
    
outerLoop:   # if $s0 value is zero, close loop go to bail
beq $t1, $zero, bail
	andi $t3, $t1, 1
	beq $t3, $zero, shift # if zero then shift 
		add $t2, $t2, $t0
		j shift
	shift:
	sll $t0, $t0, 1
	srl $t1, $t1, 1

j outerLoop      # call loop procedure
  
bail:
li $v0, 1              
move $a0, $t2
syscall

li $v0, 10
syscall