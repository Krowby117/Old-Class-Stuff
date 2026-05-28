# Gavin Dominique & Gunter
# embiggen.asm -- A program that converts lowercase to uppercase
# Registers used:
# $t0 – holds the string address
# $t2 - stores the current byte

.data
string: .asciiz "pUlL thE LEvEr KROnk!" # embiggen this
newline: .asciiz "\n" #useful

.text
main:
la $t0, string # load the string’s address

embiggen:
lb $t2, 0($t0) # get a byte
beqz $t2, end # if there’s nothing there, end
j lowerBoundCheck

lowerBoundCheck:
# if (character >= 'a'
bge $t2, 97, upperBoundCheck
j continue

upperBoundCheck:
# && character <= 'z')
ble $t2, 122, isLowerCase

continue:
# Continue the iteration
addi $t0, $t0, 1 # Increment the address
j embiggen

isLowerCase:
# sub 32
subi $t2, $t2, 32

sb $t2, 0($t0) # store the result
j continue # continue iterating
#wrap things up

end:
li $v0, 4 # Print the string
la $a0, string
syscall

li $v0, 4 # A nice newline
la $a0, newline
syscall

# We are done, exit the program
li $v0, 10
syscall