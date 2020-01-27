#include "definitions.h"


// Fonction exécutée par chaque thread de traitement.
// /!\ NE PAS MODIFIER LES LIGNES DE CODE INCLUES
void * travailler(void * p){

  params * param = (params*)p;
  afficheDebutFinThread(1, param->indice); // conserver cet appel à cet emplacement. 

  // Début partie à compléter -----

  // En boucle, prendre une ligne à traiter pour la traiter. Sortir de
  // la boucle quand toutes les lignes ont été prises en compte
  // (traitées ou en cours de traitement). Le traitement d'une ligne
  // est indépendant du traitement d'une autre ligne, donc, les
  // traitements sur différentes lignes, effectués par différents
  // threads, doivent pouvoir s'exécuter en parallèle.
  
  for(int i=0; i<param->donnees->n; i++){
    while(param->donnees->ligneCourante < param->donnees->n){
      if(pthread_mutex_lock(&(param->donnees)->mut) < 0){
        perror("Problème de lock");
        free(param);
        exit(0);
      }
      traiterUneLigne(param->donnees->matrice, param->donnees->ligneCourante, param->donnees->m, param->indice);
      if(pthread_mutex_unlock(&(param->donnees)->mut) <0){
        perror("Problème de unlock");
        free(param);
        exit(0);
      }
      if(pthread_mutex_lock(&(param->donnees)->mut) < 0){
        perror("Problème de lock");
        free(param);
        exit(0);
      }
      param->donnees->ligneCourante++;
      if(pthread_mutex_unlock(&(param->donnees)->mut) <0){
        perror("Problème de unlock");
        free(param);
        exit(0);
      }
    }
  }
  

  // Ensuite, attendre la fin des traitements de ligne en cours avant
  // de passer au traitement des colonnes.
  
  // Fin partie à compléter -----
  
  // L'appel suivant doit être conservé avant de passer au traitement
  // des colonnes et ne doit pas apparaitre dans une section critique.
  testEtapeLignes(&(param->donnees->mut));
  
  // Début partie à compléter -----
  
  // En boucle, prendre une colonne à traiter pour la
  // traiter. Sortir de la boucle quand toutes les colonnes ont été
  // prises en compte (traitées ou en cours de traitement). Le
  // traitement d'une colonne est indépendant du traitement d'une
  // autre colonne, donc, les traitements sur différentes colonnes,
  // effectués par différents threads, doivent pouvoir s'exécuter en
  // parallèle.

  if(pthread_mutex_lock(&(param->donnees)->mut) <0){
    perror("Problème de lock");
    free(param);
    exit(0);
  }
    pthread_cond_wait(&param->donnees->condition, &param->donnees->mut);

  for(int i = param->donnees->ligneCourante; i < param->donnees->n; i++){
    for(int j = param->donnees->colonneCourante; j < param->donnees->m; j++){
      traiterUneColonne(param->donnees->matrice, j, param->donnees->n, param->indice);
    }
  }

  pthread_cond_signal(&param->donnees->condition);

  if(pthread_mutex_unlock(&(param->donnees)->mut) <0){
    perror("Problème de unlock");
    free(param);
    exit(0);
  }
  // Fin partie à compléter -----
  // le code suivant (jusqu'à la fin de la fonction), est à conserver.
  afficheDebutFinThread(0, param->indice);
  free(param);
  pthread_exit(NULL);
}
