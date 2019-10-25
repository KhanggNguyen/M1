#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>
#include <errno.h>

struct sDonneesPartagees{
  int n; // nombre de lignes de la matrice. Valeur donnée en paramètre du programme.
  int m; // nombre de colonnes de la matrice. Valeur donnée en paramètre du programme.
  int ** matrice; // matrice n x m qui sera initialisée avec des valeurs aléatoires.
  int ligneCourante; // indice de la prochaine ligne à traiter. Initialisée à 0.
  int colonneCourante; // indice de la prochaine colonne à traiter. Initialisée à 0.
  
  int compteur; // compteur dont l'utilisation / sémantique est à trouver.
  pthread_mutex_t mut; // pour protéger les variables partagées en lecture et écriture. A vous de savoir quand l'utiliser.
  pthread_cond_t condition; // utilisation à trouver. 
};

typedef struct  sDonneesPartagees tDonnees;

struct sParams {
  int indice;
  tDonnees * donnees;
};

typedef struct sParams params;


//La fonction à appeler pour traiter une ligne de la matrice "matrice"
void traiterUneLigne(int ** matrice, int indiceLigneATraiter, int nbColonnesMatrice, int indiceThreadAppelant);

//La fonction à appeler pour traiter une colonne de la matrice "matrice"
void traiterUneColonne(int ** matrice, int indiceColonneATraiter, int nbLignesMatrice, int indiceThreadAppelant);

// la fonction appelée par les threads participants au calcul effectué sur la matrice. Il s'agit de la fonction à compléter.
void * travailler(void * p);

  
// Les appels des deux fonctions suivantes, effectués dans la fonction travailler(...) sont à garder. Ils participent à la génération de traces pour tester votre code. Vous n'avez pas besoin de les utiliser en dehors des lignes déjà présentes dans le squelette.
void afficheDebutFinThread(int debutOufin, int indiceThread);
void testEtapeLignes(pthread_mutex_t * mut);
