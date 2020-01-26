#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>

//gcc -Wall thread.c -o thread -lpthread

void * f(void * param){
	printf("f\n");
	exit(0);
	return NULL;
}

void * g(void * param){
	printf("g\n");
	return NULL;
}


int main(){
	pthread_t t1;
	if(pthread_create(&t1, NULL,f, NULL) != 0)
		printf("erreur de creation!\n");
	for(int i=0; i<100;i++){
		printf("g\n");
	}	
	return 0;

}