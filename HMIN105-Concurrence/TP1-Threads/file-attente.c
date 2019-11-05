#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <sys/types.h>
#include <unistd.h>
#include <time.h> 
#include "calcul.h"

//compiler 	gcc -c file-attente.c 
//compiler 	gcc calculC.o file-attente.c -o file-attente -lpthread
//executer	./file-attente


typedef struct {
    pthread_mutex_t verrou;
    pthread_cond_t cond;
    int nb_max_traitement;
    int nb_threads_en_cours;
} salles;

typedef struct {
    int n; // nb de salles à traiter
    int i; // numéro du thread courant
    salles** sTab;
} traitement_struct;

// LE VERROU SUIVANT EST UTILISE UNIQUEMENT POUR LA SORTIE STANDARD. 
// NE PAS LE REUTILISER POUR AUTRE FONCTION QUE L'AFFICHAGE. VOIR UTILISATION PLUS LOIN.
pthread_mutex_t vStdOut= PTHREAD_MUTEX_INITIALIZER;


void *traitement(void* args){

    traitement_struct *param = args;

    int indice = param->i+1;
    int nbsalles = param->n;

    for(int i=0; i<nbsalles; i++){
        // Zone courante param - > sTab[i]

        pthread_mutex_lock(&param->sTab[i]->verrou);

        while(param->sTab[i]->nb_threads_en_cours >= param->sTab[i]->nb_max_traitement){
            pthread_mutex_lock(&vStdOut);printf("personne n°%d : en attente d'accès à la salle n°%d\n", indice, i+1); pthread_mutex_unlock(&vStdOut);
            pthread_cond_wait(&param->sTab[i]->cond, &param->sTab[i]->verrou);
        }

        param->sTab[i]->nb_threads_en_cours++; // Accès à la zone

        pthread_mutex_unlock(&param->sTab[i]->verrou);

        pthread_mutex_lock(&vStdOut); printf("personne n°%d : rentre dans la salle n°%d et suit le cours \n", indice, i+1); pthread_mutex_unlock(&vStdOut);
        int arg_calcul = rand() % 5 + 1; // aléatoire entre 1 et 5
        calcul(arg_calcul);

        pthread_mutex_lock(&vStdOut);printf("personne n°%d : a terminé le cours et sort de la salle %d \n", indice, i+1); pthread_mutex_unlock(&vStdOut);
            pthread_mutex_unlock(&param->sTab[i]->verrou);

        pthread_mutex_lock(&param->sTab[i]->verrou);
        param->sTab[i]->nb_threads_en_cours--;
        //v->nb_thread_inside--;
        pthread_cond_signal(&param->sTab[i]->cond);
  
        pthread_mutex_unlock(&param->sTab[i]->verrou);

    }

    printf("personne n°%d : a fini les cours, il rentre chez lui jouer à Fortnite\n", indice);

    free(param);
    pthread_exit(NULL);
}

int main(){
    srand (time (NULL));
    int n = 0;
    printf("Nombre de salles : ");
    scanf("%d",&n);

    int p = 0;
    printf("Nombre de personnes : ");
    scanf("%d",&p);

    int k = 0;
    printf("Nombre de personnes max par salle : ");
    scanf("%d",&k);

    // Initialisation des salles
    salles **sTab = (salles**)malloc(n * sizeof(salles));

    for(int i=0; i<n; i++){
        sTab[i] = (salles*)malloc(sizeof(salles));;
        sTab[i]->nb_max_traitement = k;
        sTab[i]->nb_threads_en_cours = 0;
        pthread_cond_init(&sTab[i]->cond, NULL);
        pthread_mutex_init(&sTab[i]->verrou, NULL);
    }
    
    pthread_t threads[p];

    // Création de p threads

    for(int i=0; i<p; ++i){
        traitement_struct *args = malloc(sizeof *args);
        args->n = n;
        args->i = i;
        args->sTab = sTab;

        if (pthread_create(&threads[i], NULL, traitement, args) != 0){
            perror("Impossible de créer le(s) thread(s)\n");
            exit(EXIT_FAILURE);
        }
    }

    // Le main reprend la main sur tous les threads

    for(int i=0; i<2; ++i){
        int join = pthread_join(threads[i], NULL);
    }  

    printf("thread principal : reprend la main sur tous les threads secondaires\n");

    free(sTab);
    return 0;
}
