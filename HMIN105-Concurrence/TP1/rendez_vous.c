#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include "calcul.h"

pthread_mutex_t lock;

struct thread_data
{
  int thread_id;
  int total;
  int *arrive_compteur;
};

void * arrive(void * args){
	struct thread_data *data;
	data = (struct thread_data *) args;
	printf("Thread %d : je commence mon travail\n", data->thread_id);
	calcul(2);
	
	pthread_mutex_lock(&lock);
	++(*data->arrive_compteur);
	pthread_mutex_unlock(&lock);

	printf("Thread %d : je suis au point de synchronisation\n", data->thread_id);
	//acces à la structure avec ->
	if(*data->arrive_compteur == data->total){
		printf("Tout le monde est arrivé !\n");
	}
	else {
		printf("Thread %d : je suis arrive, j'attends\n", data->thread_id);
		while(*data->arrive_compteur != data->total){
		}
		
	}
	
	calcul(2);
	pthread_exit(NULL);
}

int main(int argc, char ** argv){
	if(argc != 2){
		perror("Erreur d'arguments\n");
		perror("./prog nombre_de_personne\n");
		return -1;
	}

	int nbre_pers = atof(argv[1]);
	int cpt = 0;
	pthread_t threads[nbre_pers];
	struct thread_data td[nbre_pers];


	if (pthread_mutex_init(&lock, NULL) != 0)
    {
        printf("\n mutex init failed\n");
        return 1;
    }
	
	for(int i=0; i < nbre_pers; i++){
		//acces au tableau avec .
		td[i].thread_id = i;
		td[i].total = nbre_pers;
		td[i].arrive_compteur = &cpt;
		if(pthread_create(&threads[i], NULL,
                          arrive, (void *)&td[i]) != 0){
			perror("Erreur de création \n");
			return -2;
		}
	}
	
	for(int i=0; i<nbre_pers; i++){
		pthread_join(threads[i], NULL);
	}
	pthread_mutex_destroy(&lock);
	return 0;
}