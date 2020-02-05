Pour charger les fonctions de compilateur : (load "fonction-compilateur")

Pour charger les fonctions de la machine virtuel  : (load "fonction-vm")

Pour compiler un fonction : (compil-fichier "nomFichier.lisp" "nomFichierDestination.lisp") //ex : nomFichier = fact.lisp

Pour créer la machine virtuel : (make-vm 'vm taille) //taille pourrait être 200000

Pour charger la fonction dans la vm : (charger_code 'vm "nomFichierCompile.lisp")

Pour exécuter la fonction dans la mémoire : (exec 'vm)
