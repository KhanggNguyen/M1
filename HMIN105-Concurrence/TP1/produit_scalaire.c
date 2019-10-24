#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

/* 
	
algo
argument : x1,y1,x2,y2
debut 

vecteur v1,v2;
pthread idt1, idt2;
créer thread qui execute la fonction multiplication de vecteur et qui renvoie le résultat en parametre;
addition de vecteur; 
afficher le resultat;

fin
*/
// x1 * y1 + x2 * y2 
/*
gcc -Wall tp1.c -lm -o tp1 -lpthread
*/

void * calcul_produit(void * args){
	float * s = (float *) args;
	//il est nécessaire d'allouer sinon ça disparait une fois sortie de la fonction
	float * res;
	res = malloc(sizeof(float));
	// *res == res[0]
	*res = (s[0] * s[1]);
	pthread_exit(res);
	free(res);
}

int main(int argc, char **argv){
	if(argc != 5){
		return 0;
	}
	float t1[2] = {atof(argv[1]), atof(argv[2])};
	float t2[2] = {atof(argv[3]), atof(argv[4])};
	pthread_t pt1,pt2;
	void * rv1;
	void * rv2;
	float somme = 0;
	
	//pas besoin de passer l'adresse puisque t1 est déjà un tableau
	if(pthread_create(&pt1, NULL, &calcul_produit, t1) != 0)
		 printf("erreur de creation!\n");
	if(pthread_create(&pt2, NULL, &calcul_produit, t2) != 0)
		 printf("erreur de creation!\n");
	
	//rv1 on donne adresse de variable de retourne
	pthread_join(pt1, &rv1);
	pthread_join(pt2, &rv2);
	somme = *(float*)rv1 + *(float*)rv2;

	//ici il faut utilise le cast (float*) après le * et pas rv1[0]
	printf("%lf\n", *(float*)rv1);
	printf("%lf\n", *(float*)rv2);
	printf("%lf\n", somme);
	return 0;
}
