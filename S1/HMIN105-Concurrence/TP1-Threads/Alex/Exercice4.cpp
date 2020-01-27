#include <iostream>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include "calcul.h"

/* Exercice 4 question 2 
Pour compiler :
-g++ -c Exercice4.cpp
-g++ calculCC.o Exercice4.o -o exercice4 -lpthread

Pour éxécuter : 
./exercice4 <Nombre de zone à traiter>
*/
using namespace std;

struct donnee{

  int nbrzone;
  int d[3];
  
  pthread_mutex_t lock;
  pthread_cond_t cond;
  
  
};


void *traitement1 (void* par){
  int num = pthread_self();
  printf("T1 Thread %d : Je démarre. \n",num );
  
  struct donnee* b = ((donnee*)par);

  for(int i = 0; i < (b->nbrzone); i++){
    printf("T1 Thread %d : Je commence le traitement de la zone %d \n",num,i+1);
    calcul(2);
    printf("T1 Thread %d : J'ai terminé le traitement de la zone %d \n",num,i+1);
    
    if(pthread_mutex_lock(&b->lock) < 0){
      perror("traitement 1 lock : ");
      exit(0);
    }

    b->d[0] =  b->d[0]+1;
    
    
    if(pthread_cond_broadcast(&(b->cond)) < 0){
      perror("traitement 1 broadcast : ");
      exit(0);
    }
        if( (pthread_mutex_unlock(&b->lock)) < 0){
      perror("traitement 1 unlock : ");
      exit(0);
    }
  }
	   
    calcul(2);
    printf("T1 Thread %d : Mission accomplie, je termine. \n",num);
    
    pthread_exit(NULL);
    
}


void *traitement2 (void* par){
  int num = pthread_self();
  printf("T2 Thread %d : Je démarre. \n",num);
  struct donnee* b = ((donnee*)par);
  
  for(int i = 0;i < (b->nbrzone)  ; i++){
    if(pthread_mutex_lock(&b->lock) < 0){
	perror("traitement 2 lock : ");
	exit(0);
      }
    while((b->d[0])<=i){
      printf("T2 Thread %d : Je suis en attente du Traitement 1 sur la zone %d \n",num,i+1); 
      if(pthread_cond_wait(&(b->cond), &(b->lock)) < 0){
	perror("traitement 2 wait : ");
	exit(0);
      }
    }
    printf("T2 Thread %d : Je commence le traitement de la zone %d \n",num,i+1);
    calcul(2);
    b->d[1] = b->d[1]+1;
    if(pthread_cond_broadcast(&(b->cond)) < 0){
      perror("traitement 2 broadcast : ");
      exit(0);
    }
    if( (pthread_mutex_unlock(&b->lock)) < 0){
      perror("traitement 2 unlock : ");
      exit(0);
    }
    printf("T2 Thread %d : J'ai terminé le traitement de la zone %d \n",num,i+1);
  }
  calcul(2);
  printf("T2 Thread %d : Mission accomplie, je termine. \n",num);
  
  pthread_exit(NULL);
}

void *traitement3 (void* par){
  int num = pthread_self();
  printf("T3 Thread %d : Je démarre. \n",num);
  struct donnee* b = ((donnee*)par);
  
  for(int i = 0;i < (b->nbrzone)  ; i++){
    if(pthread_mutex_lock(&b->lock) < 0){
	perror("traitement 3 lock : ");
	exit(0);
      }
    while((b->d[1])<=i){
      printf("T3 Thread %d : Je suis en attente du Traitement 2 sur la zone %d \n",num,i+1); 
      if(pthread_cond_wait(&(b->cond), &(b->lock)) < 0){
	perror("traitement 3 wait : ");
	exit(0);
      }
    }
    printf("T3 Thread %d : Je commence le traitement de la zone %d \n",num,i+1);
    calcul(2);
    b->d[2] = b->d[2]+1;
    if(pthread_cond_broadcast(&(b->cond)) < 0){
      perror("traitement 2 broadcast : ");
      exit(0);
    }
    if( (pthread_mutex_unlock(&b->lock)) < 0){
      perror("traitement 3 unlock : ");
      exit(0);
    }
    printf("T3 Thread %d : J'ai terminé le traitement de la zone %d \n",num,i+1);
  }
  calcul(2);
  printf("T3 Thread %d : Mission accomplie, je termine. \n",num);
  
  pthread_exit(NULL);
}

int main(int argc, char* argv[]){
  if(argc <= 1){
    perror("Argument manquant ou invalide");
    exit(0);
  }

  else if(atoi(argv[1]) <= 0){
    perror("requis un nombre > 0");
    exit(0);
  }

  int nbrzone = atoi(argv[1]);
  pthread_t thread[3];
  printf("Thread Principal : Début du thread principal pour %d zones \n", nbrzone);
  struct donnee b;
  for(int i=0; i<3; i++){
    b.d[i]=0;
  }

  if(pthread_mutex_init(&(b.lock), NULL) < 0){
    perror("Erreur création mutex : ");
    exit(0);
  }

  if(pthread_cond_init(&(b.cond), NULL) < 0){
    perror("Erreur création mutex : ");
    exit(0);
  }

  b.nbrzone = nbrzone;

  if(pthread_create(&thread[0], NULL, traitement1, &b) < 0){
    perror("Erreur création mutex : ");
    exit(0);     
  }

   if(pthread_create(&thread[1], NULL, traitement2, &b) < 0){
     perror("Erreur création mutex : ");
     exit(0);     
   }

   if(pthread_create(&thread[2], NULL, traitement3, &b) < 0){
     perror("Erreur création mutex : ");
     exit(0);     
   }
  
  for(int i = 0; i < 3; i++){
    if(pthread_join(thread[i],NULL) != 0){
      cout << "Erreur pthread_join " << endl;
    }
  }

  printf("Thread Principal : Toute les opérations ont été effectués. \n ");


  return 0;

}
