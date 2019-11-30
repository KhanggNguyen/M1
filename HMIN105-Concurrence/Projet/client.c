#include <stdio.h>
#include <stdlib.h>
#include <unistd.h> 
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/msg.h>
#include <netdb.h>
#include <time.h>

#include <pthread.h>

#include <arpa/inet.h>
#include <netinet/in.h>
#include <sys/socket.h>

#include <sys/ipc.h>
#include <sys/sem.h>
#include <sys/shm.h>

/* ------------------- definition structure et variable constantes------------------- */
typedef struct Donnees_Fichier Donnees_Fichier;
struct Donnees_Fichier{
    int socket;
    char* fichier;
};

struct sembuf sop[] ={
       {0, -1, SEM_UNDO},//lock
       {0, 1, SEM_UNDO}  //unlock
    };

pthread_mutex_t lock;

/* ------------------- definition des fonctions utiles ------------------- */
int reception(int dest, void * msg, int taille_msg) {
    int nb_octet_recu = 0;
    int resultat;
    while (nb_octet_recu != taille_msg) {
        resultat = recv(dest, msg + nb_octet_recu, taille_msg - nb_octet_recu, 0);
        if (resultat < 0) {
            printf("Problème de reception\n");
            return 1;
        } else if (resultat == 0) {
            printf("Socket Ferme\n");
            return 2;
        }
        nb_octet_recu += resultat;
    }
    return 0;
}

int envoi(int dest, void * msg, int taille_msg) {
    int nb_octet_envoi = 0;
    int resultat;
    while (nb_octet_envoi != taille_msg) {
        resultat = send(dest, msg + nb_octet_envoi, taille_msg - nb_octet_envoi, 0);
        if (resultat < 0) {
            printf("Problème d'envoi\n");
            return 1;
        } else if (resultat == 0) {
            printf("Socket fermé\n");
            return 2;
        }
        nb_octet_envoi += resultat;
    }
    return 0;
}

void* gestion_fichier(void* arg){
    struct Donnees_Fichier* donnees_fichier = arg;
    int n;
    int flag_connexion_serveur;
    int flag_connexion_client = 1;
    /* Reception du buffer*/
    char fichier[9999];
	if(reception(donnees_fichier->socket, fichier, sizeof(fichier))!=0){
		perror("Erreur reception du fichier");
		exit(EXIT_FAILURE);
	}
    printf("Contenu du fichier : %s\n", fichier);

    do{
        
        if(envoi(donnees_fichier->socket ,&flag_connexion_client,sizeof(int)) != 0){
            perror("Erreur reception");
            exit(EXIT_FAILURE);
        }
        //printf("Envoyé flag de connexion\n");

        //pthread_mutex_lock(&lock);
        if((n = reception(donnees_fichier->socket, &flag_connexion_serveur, sizeof(int))) < 0){
            perror("Erreur de reception");
            exit(EXIT_FAILURE);
        }
        //printf("Reçu flag de connexion du serveur : %d\n", flag_connexion_serveur);
        //pthread_mutex_unlock(&lock);

        if(flag_connexion_serveur == 2){
            printf("Fermeture du serveur\n");
            exit(EXIT_FAILURE);
        }

        if(flag_connexion_serveur == 1){
            char buf[9999];
            printf("Saississez votre contenu :  ");
            scanf("%s", buf);
            if(envoi(donnees_fichier->socket, &buf, sizeof(buf)) != 0){
                perror("Erreur reception");
                exit(EXIT_FAILURE);
            }
            printf("Contenu Envoyé\n");
            if(reception(donnees_fichier->socket, fichier, sizeof(fichier))!=0){
                perror("Erreur reception du fichier");
                exit(EXIT_FAILURE);
            }
            printf("Contenu du fichier : %s\n", fichier);
        }
        
    }while(flag_connexion_serveur == 1);
    free(donnees_fichier);
    pthread_exit(NULL);
}

void* reception_modification(void* arg){
    struct Donnees_Fichier* donnees_fichier = arg;

    do{
        char fichier[9999];
        if(reception(donnees_fichier->socket, fichier, sizeof(fichier)) > 0){
            printf("Contenu du fichier : %s\n", fichier);
        }
    }while(1);

    free(donnees_fichier);
    pthread_exit(NULL);
} 

int main(int argc, char ** argv){

    if(argc < 3){
        printf("Usage %s <IP> <PORT>\n", argv[0]);
        exit(EXIT_FAILURE);
    }
    
    char ip[16];
    strcpy(ip, argv[1]);
    const int port = atoi(argv[2]);

    /* ------------------- Initialisation du socket ------------------- */
    int socket_client;
    if ((socket_client = socket(PF_INET, SOCK_STREAM, 0)) < 0) {
        perror("Erreur de création du socket");
        exit(EXIT_FAILURE);
    }
    printf("Socket créé succès!\n");

    struct sockaddr_in servaddr;
    servaddr.sin_family = AF_INET; // IPv4
    servaddr.sin_port = htons(port);
    servaddr.sin_addr.s_addr = inet_addr(ip);
    /*
    if(inet_pton(AF_INET, ip, &(servaddr.sin_addr)) < 0 ){
        perror("Erreur d'initialisation de l'adresse ip");
        exit(EXIT_FAILURE);
    }*/

	if (connect(socket_client,(struct sockaddr*)&servaddr, sizeof(servaddr)) == -1)
	{
		perror("Erreur de la connexion");
		exit(EXIT_FAILURE);
	}
	printf("Connecté.\n");

    if(pthread_mutex_init(&lock, NULL) != 0){
        perror("Erreur d'initialisation mutex");
        exit(EXIT_FAILURE);
    }
    
	pthread_t* threads_clients = malloc (2 * sizeof(pthread_t));

    struct Donnees_Fichier* donnees_fichier = NULL;
    donnees_fichier = malloc(sizeof(struct Donnees_Fichier));
    donnees_fichier->socket = socket_client;
    //donnees_fichier->fichier = fichier;

	if(pthread_create(&threads_clients[0], NULL, gestion_fichier, donnees_fichier) != 0){
  		printf("Erreur thread gestion_fichier! \n");
  		exit(EXIT_FAILURE);
  	}
    /*
    if(pthread_create(&threads_clients[1], NULL, reception_modification, donnees_fichier) != 0){
        printf("Erreur thread reception modif\n");
        exit(EXIT_FAILURE);
    } */

    pthread_join(threads_clients[0], NULL);
    //pthread_join(threads_clients[1], NULL);
    free(threads_clients);
    close(socket_client);
    printf("Fin processus\n");

    return 0;
}