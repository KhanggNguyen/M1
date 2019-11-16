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

#include "warn.h"

struct sembuf op[] = {
    { 0, -1, SEM_UNDO }, //lock
    { 0, 1, SEM_UNDO } //unlock
};  




int main() {

    const char* filepath = "parking_shared.txt";

    printf("Creation de la clé d'acces IPC...\n");

    key_t key = ftok(filepath, 1); 
    WARN_ERROR(key);

    printf("Accès au segment de mémoire partagée...\n");

    int sh_id = shmget(key, 0, 0666);
    WARN_ERROR(sh_id);

    int sem_id = semget(key, 1, IPC_CREAT|0666);
    WARN_ERROR(sem_id);

    int error;

    while (1) {

        sleep(1);

        error = semop(sem_id, op, 1);
        WARN_ERROR(error);  

        int* nb_places = (int*)shmat(sh_id, NULL, SHM_W);
        WARN_IF(*nb_places == -1);

        if (*nb_places < 20) {
            printf("Demande accepté\n");
            (*nb_places)++;
            //printf("Impression ticket\n");
            printf("Nombre de place restante : %d\n", (*nb_places));
        } else {
            printf("Plus de voiture");
        }

        error = semop(sem_id, op+1, 1);
        WARN_ERROR(error);  

    }

    return 0;
}