#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <netdb.h>
#include <fcntl.h>
#include <pthread.h>
#include <sys/sem.h>
#include <sys/shm.h>

#define MEM_SEG_KEY 9999
#define MAX_CLIENT 99




/* ------------------- definition structure et variable constantes------------------- */
typedef struct Segment_partage Segment_partage;

struct Segment_partage {
    int nb_Clients;
    int max_clients;
    int tab_socket_client[MAX_CLIENT];
    char fichier[9999];
};

pthread_mutex_t lock;

union semun {
    int val;
    struct semid * buf;
    unsigned short * tab;
    struct seminfo * _buf;
};

typedef struct Donnees_Client Donnees_Client;
struct Donnees_Client{
	int *tab_socket_client;
	int position;
	int sh_id;
    key_t fichier;
    int sem_id;
 	int num_client;
	char nom[256];
};

struct sembuf sop[] ={
       {0, -1, SEM_UNDO},//lock
       {0, 1, SEM_UNDO}  //unlock
    };

const char * fichier_partage = "fichier_partage.txt";

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

void init_donnees_client(struct Donnees_Client* donnees_client, int* tab_socket_client, int position, int sh_id, int num_client, key_t fichier, int sem_id){
	donnees_client->tab_socket_client = tab_socket_client;
	donnees_client->position = position; 
	donnees_client->sh_id = sh_id;
	donnees_client->num_client = num_client;
    donnees_client->fichier = fichier;
    donnees_client->sem_id = sem_id;
}

int recherche_position_libre(int* tab_socket_client){
	for(int i = 0; i < MAX_CLIENT; i++){
		if(tab_socket_client[i] == -1){
			return i;
		}
	}
	return -1;
}

void* gestion_client(void* arg){
	struct Donnees_Client* donnees_client = arg;
	printf("Gestion du client numéro %d\n", donnees_client->num_client);

	int position = donnees_client->position;
	int* tab_socket_client = donnees_client->tab_socket_client; 

	struct Segment_partage* segment_partage = NULL;
	segment_partage = (struct Segment_partage *) shmat(donnees_client->sh_id, NULL, 0);
	if(segment_partage == NULL){
		perror("Erreur de la liaison à la mémoire partagée");
		exit(EXIT_FAILURE);
	}

    segment_partage->tab_socket_client[position] = tab_socket_client[position];
    for(int i = 0; i < segment_partage->max_clients; i++){
        tab_socket_client[i] = segment_partage->tab_socket_client[i];
    }

    //envoi la contenue du fichier dès la connexion du client
	if(envoi(tab_socket_client[position], segment_partage->fichier, sizeof(segment_partage->fichier)) != 0){
		perror("Erreur d'envoi");
		exit(EXIT_FAILURE);
	}
    printf("Envoyé contenu du fichier au client numéro %d\n", donnees_client->num_client);

	int flag_connexion_client;
    int flag_connexion_serveur = 1;
	do{
        //pthread_mutex_lock(&lock);
		if(reception(tab_socket_client[position], &flag_connexion_client, sizeof(int)) != 0){
			printf("Erreur reception\n ");
			flag_connexion_client=0;
		}
        //printf("Reçu flag de vérification du client numéro %d : %d\n", donnees_client->num_client, flag_connexion_client);
        
        if (envoi(tab_socket_client[position], &flag_connexion_serveur, sizeof(int)) != 0) {
            perror("Erreur d'envoi\n ");
        }
        //printf("Envoyé flag de connexion au client numéro %d\n", donnees_client->num_client);
        //pthread_mutex_unlock(&lock);

		/* cas deconnexion */
		if(flag_connexion_client == 0){
			printf("Client déconnecté !\n");

            if(semop(donnees_client->sem_id, sop, 1) < 0){
                perror("Erreur de semop");
                exit(EXIT_FAILURE);
		    }

			segment_partage->nb_Clients--;			
			close(segment_partage->tab_socket_client[position]);
			segment_partage->tab_socket_client[position] = -1;

            if(semop(donnees_client->sem_id, sop+1, 1) < 0){
                perror("Erreur de semop");
                exit(EXIT_FAILURE);
		    }
		}else{
			char buffer[9999];

            if(reception(tab_socket_client[position], buffer, sizeof(buffer)) != 0){
				printf("Erreur reception\n");
                flag_connexion_client=0;
			}
            printf("Contenu reçu du client numéro %d : %s\n ", donnees_client->num_client, buffer);

            if(semop(donnees_client->sem_id, sop, 1) < 0){
                perror("Erreur de semop");
                exit(EXIT_FAILURE);
		    }

            strcpy(segment_partage->fichier, buffer);

			if(semop(donnees_client->sem_id, sop+1, 1) < 0){
                perror("Erreur de semop");
                exit(EXIT_FAILURE);
		    }

            for(int i=0; i< segment_partage->max_clients; i++){
                if(segment_partage->tab_socket_client[i] != -1){
                    if(envoi(tab_socket_client[i], segment_partage->fichier, sizeof(segment_partage->fichier)) != 0){
                        printf("Erreur d'envoi");
                    }
                } 
                
            } 
		}
	}while(flag_connexion_client == 1);
    free(donnees_client);
    pthread_exit(NULL);

}

/* ------------------- exécution ------------------- */
int main(int argc, char ** argv) {
    if (argc < 3) {
        printf("Usage %s <PORT> <NB_MAX_CLIENT> ( nb_max_client doit etre > 0 )\n", argv[0]);
        exit(EXIT_FAILURE);
    }
    const int port = atoi(argv[1]);

    if (atoi(argv[2]) < 1) {
        printf("NB_MAX_CLIENT doit etre strictement superieur a 1!\n");
        exit(EXIT_FAILURE);
    }
    const int max_clients = atoi(argv[2]);

    /* ------------------- ouverture des fichiers ------------------- */
    FILE* fd_fichier;
    printf("Creation des fichier. . .\n");
    fd_fichier = fopen(fichier_partage, "a+");
    if(fd_fichier == NULL){
        perror("Erreur d'ouverture fichier\n");
        exit(EXIT_FAILURE);
    }

    /* ------------------- initialisation de la mémoire partagé ------------------- */
    key_t fichier; 
    int sh_id;

    printf("Creation de la clé d'acces IPC. . .\n");
    /* ------------------- création de la cle d'accès IPC du fichier donnees ------------------- */
    if ((fichier = ftok(fichier_partage, 1)) == -1) {
        perror("Erreur de l'assignation de la clé\n");
        exit(EXIT_FAILURE);
    }

    printf("Création de la sémaphore d'associe à la clé pour l'écriture. . .\n");
    union semun egCtrl;
    /* ------------------- semaphore d'associe à la cle pour la fichier partage ------------------- */
    int sem_id = semget(fichier, 1, IPC_CREAT | 0666);
    if (sem_id == -1) {
        perror("Erreur création sémaphore");
    }

    egCtrl.val = 1; //nb de personne ayant la ressource en même temps
    if (semctl(sem_id, 0, SETVAL, egCtrl) == -1) {
        perror("Erreur d'initialisation");
    }

    printf("Creation du segment de mémoire partagée. . .\n");
    /* ------------------- création d'un nouveau segment de mémoire avec l'identifiant en retour ------------------- */
    if ((sh_id = shmget(fichier, sizeof(struct Segment_partage), IPC_CREAT | 0666)) == -1) {
    	perror("Erreur lors de la création de la mémoire.");
        exit(EXIT_FAILURE);
    }

    


    printf("Initialisation de la variable partagée. . .\n");
    /* ------------------- Initialisation de la variable partagé ------------------- */
    struct Segment_partage * segment_partage = NULL;
    segment_partage = (struct Segment_partage * ) shmat(sh_id, NULL, 0);
    if (segment_partage == NULL) {
        perror("Erreur de la liaison à la mémoire partagée");
        exit(EXIT_FAILURE);
    }
    segment_partage-> nb_Clients = 0;
    segment_partage-> max_clients = max_clients;
    fseek(fd_fichier, 0, SEEK_END);
    long length = ftell(fd_fichier);
    fseek(fd_fichier, 0, SEEK_SET);
    fread(segment_partage->fichier, 1, length, fd_fichier);
    printf("Contenu du fichier : %s", segment_partage->fichier);

    /* ------------------- Initialisation du socket ------------------- */
    int socket_serveur;
    if ((socket_serveur = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        perror("Erreur de création du socket");
        exit(EXIT_FAILURE);
    }
    printf("Socket créé succès!\n");

    struct sockaddr_in servaddr;
    servaddr.sin_family = AF_INET; // IPv4 
    servaddr.sin_addr.s_addr = htonl(INADDR_ANY); //ip
    servaddr.sin_port = htons(port); //port
    if (bind(socket_serveur, (const struct sockaddr * ) &servaddr, sizeof(servaddr)) < 0) {
        perror("Erreur de nommage");
        exit(EXIT_FAILURE);
    }
    printf("Nommage succès!\n");

    if (listen(socket_serveur, 2) == -1) {
        perror("Erreur de positionnement");
        exit(EXIT_FAILURE);
    }
    printf("Information du serveur : <%s> <%i>\n",inet_ntoa((struct in_addr)servaddr.sin_addr), port);
    printf("En mode écoute. . .\n");

    int * tab_socket_client = malloc(max_clients * sizeof(int));

    for (int i = 0; i < max_clients; ++i) {
        tab_socket_client[i] = -1;
        segment_partage->tab_socket_client[i] = -1;
    }

    pthread_t * threads_clients = malloc(max_clients * sizeof(pthread_t));

    /* ------------------- Gestion des clients ------------------- */
	struct Donnees_Client* donnees_client;

    if(pthread_mutex_init(&lock, NULL) != 0){
        perror("Erreur d'initialisation mutex");
        exit(EXIT_FAILURE);
    }

    while (1) {
        int socket_client;
        struct sockaddr_in cliaddr;
        socklen_t lgA_client = sizeof(struct sockaddr_in);
		/* ------------------- Connecter un client au socket -------------------*/
        if ((socket_client = accept(socket_serveur, (struct sockaddr * ) &cliaddr, &lgA_client)) < 0) {
            perror("Erreur de connexion");
            exit(EXIT_FAILURE);
        }

        printf("Nouvelle connection : %s\n", inet_ntoa((struct in_addr)cliaddr.sin_addr));

        int position = recherche_position_libre(tab_socket_client);
        if(position == -1){
            perror("Plus de place libre\n");
            exit(EXIT_FAILURE);
        } 
        tab_socket_client[position] = socket_client;
        if (tab_socket_client[position] == -1) {
            perror("Erreur de connexion");
            exit(EXIT_FAILURE);
        }

        segment_partage->nb_Clients++;

        /* ------------------- Creation du client ------------------- */
        donnees_client = malloc(sizeof(struct Donnees_Client));
        init_donnees_client(donnees_client, tab_socket_client, position, sh_id, segment_partage->nb_Clients, fichier, sem_id);

        if(pthread_create(&threads_clients[position], NULL, gestion_client, donnees_client) != 0){
            printf("Erreur pthread gestion_client! \n");
            exit(EXIT_FAILURE);
        }

    } 
    if (shmdt(segment_partage) == -1) {
        perror("Erreur du détachement de la mémoire partagée.");
        exit(EXIT_FAILURE);
    }
    pthread_mutex_destroy(&lock);
    fclose(fd_fichier);
    close(socket_serveur);
    free(threads_clients);
	printf("Fermeture du serveur\n"); 
	return 0;
}