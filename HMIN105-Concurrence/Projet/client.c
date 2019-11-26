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
    int flag;
    do{
        if((n = reception(donnees_fichier->socket, &flag, sizeof(int))) < 0){
            perror("Erreur de reception");
            exit(EXIT_FAILURE);
        }

        if(n == 2){
            printf("Fermeture du serveur\n");
            exit(EXIT_FAILURE);
        }

        if(flag == 1){
            char fichier[9999];
            if(reception(donnees_fichier->socket, fichier, sizeof(fichier)) < 0){
                perror("Erreur de reception");
            }
            strcpy(donnees_fichier->fichier, fichier);
        }
    }while(flag != 0);
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
    if(inet_pton(AF_INET, ip, &(servaddr.sin_addr)) < 0 ){
        perror("Erreur d'initialisation de l'adresse ip");
        exit(EXIT_FAILURE);
    }

	if (connect(socket_client,(struct sockaddr*)&servaddr, sizeof(servaddr)) == -1)
	{
		perror("Erreur de la connexion");
		exit(EXIT_FAILURE);
	}
	printf("Connecté.\n");

    char flag_connexion;
	if(reception(socket_client,&flag_connexion,sizeof(int)) != 0){
		perror("Erreur reception");
		exit(EXIT_FAILURE);
	}

    if(flag_connexion != 0){
        printf("Erreur de connexion : plein");
        exit(EXIT_FAILURE);
    }

    /* Reception du buffer*/
    char fichier[9999];
	if(reception(socket_client, fichier, sizeof(fichier))!=0){
		perror("Erreur reception du fichier");
		exit(EXIT_FAILURE);
	}

    int verif = 1;
    if(send(socket_client, &verif, sizeof(verif), 0) ==-1){
		perror("Erreur envoi verification");
		exit(EXIT_FAILURE);
	}

	pthread_t* threads_clients = malloc (2 * sizeof(pthread_t));

    struct Donnees_Fichier* donnees_fichier = NULL;
    donnees_fichier = malloc(sizeof(struct Donnees_Fichier));
    donnees_fichier->socket = socket_client;
    donnees_fichier->fichier = fichier;

	if(pthread_create(&threads_clients[0], NULL, gestion_fichier, donnees_fichier) != 0){
  		printf("Erreur thread gestion_fichier! \n");
  		exit(EXIT_FAILURE);
  	}

    free(threads_clients);
    close(socket_client);
    printf("Fin processus\n");

    return 0;
}