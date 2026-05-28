.data
prompt1: .asciiz "Enter an int: "

.text
.main
# Prompt for integer
li $v0, 4          # syscall for printing string
la $a0, prompt1
syscall

li $v0, 5          # syscall for reading integer
syscall
move $s0, $v0      # store first integer in $s0

blt $s0, $zero, its_neg
j end                   

its_neg:
li $t1, -1 
mul $s0, $t1, $s0
j end

end:
## print out the return value
move $a0, $s0      # Move return value to argument for printing
li $v0, 1          # syscall for printing integer
syscall

# Exit
li $v0, 10
syscall