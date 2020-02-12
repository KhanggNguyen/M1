import sys

fichier_dictionnaire = open("./dictionnaire.txt", "r")
fichier_grammaire = open("./grammaire.txt", "r")
dictionnaire = []
grammaire = [] 

#init tableau dictionnaire
line = fichier_dictionnaire.readline()
while line :
	line = line.strip('\n')
	dictionnaire.append(line.split(":"))
	line = fichier_dictionnaire.readline()
fichier_dictionnaire.close()

#init tableau grammaire 
line = fichier_grammaire.readline()
while line : 
	line = line.strip('\n')
	grammaire.append(line.split(":"))
	line = fichier_grammaire.readline()
fichier_grammaire.close()

def get_etiquette_par_mot(mot, mot_suivant, mot_precedent):
	for d in dictionnaire :
		if(d[0] == mot_precedent + mot + mot_suivant) : 
			return d[2]

#if(len(sys.argv) > 1) :
#	fichier_phrase = open(sys.argv[1], "r")
#phrase = fichier_phrase.readline().split(" ")

phrase = "le bien qu'il fait, il le fait bien."
phrase = phrase.split(" ")
length = len(phrase)

m_phrase = [[0 for x in range(length)] for y in range(length)]
for i in range(0, length) :
	m_phrase[0][i] = phrase[i]
	m_phrase[i][0] = phrase[i]
	print("{},{} : {}".format(0,i,m_phrase[0][i]))
