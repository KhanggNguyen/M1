Pour charger les fonctions de compilateur : (load "compilateur")
Pour charger les fonctions de la machine virtuel  : (load "vm")

Pour compiler un fonction : (compil-fichier "nomFichier.lisp" "nomFichierDestination.lisp")

Pour créer la machine virtuel : (make-vm 'vm taille)

Pour charger la fonction dans la vm : (lecture_code 'vm "nomFichierCompile.lisp")

Pour exécuter la fonction dans la mémoire : (expr_exec 'vm)
