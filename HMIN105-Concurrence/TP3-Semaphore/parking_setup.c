#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include <unistd.h>
#include <sys/sem.h>
#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/shm.h>
#include <pthread.h>
#include <fcntl.h>

#include "warn.h"

union semun{
  int val ;/* cmd = SETVAL */
  struct semidds *buf ;/* cmd = IPCSTAT ou IPCSET */
  unsigned short *array ;    /* cmd = GETALL ou SETALL */
  struct seminfo *__buf ;/* cmd = IPCINFO (sous Linux) */
};

// creation du segment de mémoire partagée.
int parking_init(const char* filepath, int places){

    printf("[PARKING_INIT] : STARTED\n");

    printf("Creation du fichier partagée...\n");

    int fd = open(filepath, O_RDWR|O_CREAT, 0666);
    WARN_ERROR(fd);

    printf("Creation de la clé d'acces IPC...\n");

    key_t key = ftok(filepath, 1); 
    WARN_ERROR(key);

	// Création d'une sémaphore associée a la clée cleSem 
  	int idSem = semget(key, 1, IPC_CREAT|0666);

	//nb de personne ayant l'accès au ressource en meme temps
	union semun egCtrl;
	egCtrl.val=1;

	if(semctl(idSem, 0, SETVAL, egCtrl) == -1){
    	perror("Probleme init");//suite
	}
    
    printf("Creation du segment de mémoire partagée...\n");
    //création d'un nouveau segments de mémoire avec l'identifiant en retour
    int sh_id = shmget(key, sizeof(int), IPC_CREAT|0666);
    WARN_ERROR(sh_id);

    printf("Initialisation de la variable partagée...\n");
    int* nb_places = (int*)shmat(sh_id, NULL, 0);
    if (*nb_places == -1){
		perror("Erreur d'initialisation de la variable partagé\n");
	}
    *nb_places = places;
    printf("Places disponible : %d \n", *nb_places);

    printf("Detachement du segment de mémoire partagée...\n");

    int error = shmdt((void*)nb_places);
    WARN_ERROR(error);

    printf("[PARKING_INIT] : ENDED\n");

    close(fd);

    return sh_id;
}

// destruction du segment de mémoire partagée.
int parking_destroy(int sh_id){
    return shmctl(sh_id, IPC_RMID, NULL);
}

int main(int argc, char const *argv[]) {

    PRINT_USAGE_IF(argc < 2, "Usage %s <NB_PLACES>", argv[0]);

    parking_init("parking_shared.txt", atoi(argv[1]));

    return 0;
}
