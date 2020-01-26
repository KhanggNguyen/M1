#entier_positive.asm

.data
positive:  .asciiz  "positive \n"
negative:  .asciiz "negative \n"

.text
main:
	li $v0, 5
	syscall  #syscall pour la saisie de valeur au $v0
	li $t0, 0 #t0 = 0
	blt $v0, $t0, neg #si negative on saut au neg:
	move $a0, $v0 #on passe la valeur saisie $a0 à $v0
	li $v0, 1 #$v0, 1 pour print integer
	syscall
	j end
neg :
	neg $a0, $v0 #prend l'opposé de $a0 et passe en $v0
	li $v0, 1 #prepare print integer
	syscall
end :