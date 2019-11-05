#include <stdio.h> 
#include <stdlib.h> 
#include <pthread.h> 
#include <semaphore.h> 
#include <sys/sem.h>
#include <ipc.h>
#include <unistd.h> 

sem_t semaphore;

typedef struct parking {
    int nbPlaces;
}

struct sembuf op[] = {
    {(u_short) 0, (short) -1, SEM_UNDO},
    {(u_short) 0, (short) +1, SEM_UNDO}
};

int main(int agrc, char** argv){
    pthread_t th1, th2, th3;



    return 0;
}