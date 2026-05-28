fp_add:
# check the exponents and make sure they are the same
bgt $s1, $s4, firstGT:
bgt $s4, $s1, secondGT:
j addStep

firstGT:
sub $s6, $s1, $s4		# difference in exponents
sllv $s5, $s5, $s6		# shift the smaller one left
move $s4, $s1			# make exponents the same
j addStep

secondGT:
sub $s6, $s1, $s4		# difference in exponents
sllv $s2, $s2, $s6		# shift the smaller one left
move $s1, $s4			# make exponents the same
j addStep

addStep:
add $s7, $s2, $s5		# add the mantissas together