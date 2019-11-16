#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include <unistd.h>

#include <sys/types.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <sys/sem.h>
#include <pthread.h>
#include <fcntl.h>
#include <signal.h>

#include "warn.h"

struct sembuf op[] = {
    { 0, -1, SEM_UNDO }, //lock
    { 0, 1, SEM_UNDO } //unlock
};  


int main(){
    const char* filepath = "parking_shared.txt";

    printf("Creation de la clé d'acces IPC...\n");

    key_t key = ftok(filepath, 1); 
    WARN_ERROR(key);

    printf("Accès au segment de mémoire partagée...\n");

    //demande d'acces en lecture seul -> taille = 0
    int sh_id = shmget(key, 0, 0666);
    WARN_ERROR(sh_id);

    //ecriture
    int sem_id = semget(key, 1, IPC_CREAT|0666);
    WARN_ERROR(sem_id);

    int error;

    while (1) {

        error = semop(sem_id, op, 1); WARN_ERROR(error);

        int* nb_places = (int*)shmat(sh_id, NULL, 0);
        WARN_IF(*nb_places == -1);

        if (*nb_places > 0) {
            printf("Demande accepté\n");
            (*nb_places)--;
            printf("Impression ticket\n");
            printf("Nombre de place restante : %d", (*nb_places));
        } else {
            printf("Pas de place");
        }

        error = semop(sem_id, op+1, 1); WARN_ERROR(error);

        sleep(2);
    }

    return 0;
}
