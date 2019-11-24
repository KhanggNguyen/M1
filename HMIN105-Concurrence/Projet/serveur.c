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
	int num_client;
	char nom[256];
};

const char * fichier_partage = "fichier_partage.txt";
const char * fichier_notif = "fichier_notif.txt";
const char * fichier_maj = "ficheir_maj.txt";

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

void init_donnees_client(struct Donnees_Client* donnees_client, int* tab_socket_client, int position, int sh_id, int num_client){
	donnees_client->tab_socket_client = tab_socket_client;
	donnees_client->position = position; 
	donnees_client->sh_id = sh_id;
	donnees_client->num_client = num_client;
}

int recherche_position_libre(int* tab_socket_client){
	for(int i = 0; i < sizeof(tab_socket_client); i++){
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
	segment_partage = (struct Segment_partage*) shmat(donnees_client->sh_id, NULL, 0);
	if(segment_partage == NULL){
		perror("Erreur de la liaison à la mémoire partagée");
		exit(EXIT_FAILURE);
	}

	/* ------------------- initialisation de la mémoire partagé ------------------- */
    key_t notif, fichier, maj;
    int sh_id;

    printf("Creation de la clé d'acces IPC...\n");
    /* ------------------- création de la cle d'accès IPC du fichier donnees ------------------- */
    if ((fichier = ftok(fichier_partage, 42)) == -1) {
        perror("Erreur de l'assignation de la clé\n");
        exit(EXIT_FAILURE);
    }

    /* -------------------création de la cle d'accès IPC pour notifier la maj ------------------- */
    if ((notif = ftok(fichier_notif, 42)) == -1) {
        perror("Erreur de l'assignation de la clé\n");
        exit(EXIT_FAILURE);
    }

    /* -------------------création de la cle d'accès IPC pour la maj ------------------- */
    if ((maj = ftok(fichier_maj, 42)) == -1) {
        perror("Erreur de l'assignation de la clé\n");
        exit(EXIT_FAILURE);
    }

	printf("Création des sémaphores d'associe à la clé pour l'écriture");
    /* ------------------- semaphore d'associe à la cle pour la fichier partage ------------------- */
    int sem_id_partage = semget(fichier, 0, IPC_CREAT | 0666);
    if (sem_id_partage == -1) {
        perror("Erreur création sémaphore");
    }

    /* ------------------- semaphore d'associe à la cle pour la notif ------------------- */
    int sem_id = semget(notif, position, IPC_CREAT | 0666);
    if (sem_id == -1) {
        perror("Erreur création sémaphore \n");
        exit(EXIT_FAILURE);
    }

    /* ------------------- semaphore d'associe à la cle pour la maj ------------------- */
    int sem_id_maj = semget(maj, MAX_CLIENT, IPC_CREAT | 0666);
    if (sem_id_maj == -1) {
        perror("Erreur création sémaphore\n");
        exit(EXIT_FAILURE);
    }

	if(envoi(tab_socket_client[position], segment_partage->fichier, sizeof(segment_partage->fichier)) != 0){
		perror("Erreur d'envoi");
		exit(EXIT_FAILURE);
	}

	struct sembuf sop;

	//semaphore
	sop.sem_num = 0;
	sop.sem_op = -1;
	sop.sem_flg = 0;
    if(semop(sem_id_partage, &sop, 1) < 0){
		perror("Erreur de semop");
		exit(EXIT_FAILURE);
	}

	int flag_autre = 2 ;
	for (int i = 0; i < sizeof(tab_socket_client); ++i)
	{
		if(i != position && segment_partage->tab_socket_client[i] != -1){
			if(envoi(segment_partage->tab_socket_client[i], &flag_autre, sizeof(int)) != 0){
				perror("Erreur envoi flag_autre 2");
				exit(EXIT_FAILURE);
			}
		}
	}

	sop.sem_num = 0;
	sop.sem_op = 1;
	sop.sem_flg = 0;
    if(semop(sem_id_partage, &sop, 1) < 0){
		perror("Erreur de semop");
		exit(EXIT_FAILURE);
	}

	int verif = 0;
	if(reception(tab_socket_client[position], &verif, sizeof(verif)) != 0){
		perror("Erreur reception");
		exit(EXIT_FAILURE);
	}

	if(verif == 0){
		perror("verification echoué.");
		exit(EXIT_FAILURE);
	}

	int flag;
	do{
		if(reception(tab_socket_client[position], &flag, sizeof(int)) != 0){
			perror("Erreur reception");
			flag=0;
		}
		/* cas deconnexion */
		if(flag == 0){
			printf("Client déconnecté !\n");
			sop.sem_num = 0;
			sop.sem_op = -1;
			sop.sem_flg = 0;
            if(semop(sem_id_partage, &sop, 1) < 0){
                perror("Erreur de semop");
                exit(EXIT_FAILURE);
		    }

			segment_partage->nb_Clients--;			
			close(segment_partage->tab_socket_client[position]);
			segment_partage->tab_socket_client[position] = -1;

			sop.sem_num=0;
			sop.sem_op=1;
			sop.sem_flg=0;
            if(semop(sem_id_partage, &sop, 1) < 0){
                perror("Erreur de semop");
                exit(EXIT_FAILURE);
		    }

			for (int i = 0; i < sizeof(tab_socket_client); ++i) //on update les pseudos
			{
				if(segment_partage->tab_socket_client[i]!=-1 && i!=position){
					sop.sem_num = i;
					sop.sem_op = 1;
					sop.sem_flg = 0;
                    if(semop(sem_id_partage, &sop, 1) < 0){
                        perror("Erreur de semop");
                        exit(EXIT_FAILURE);
		            }
				}
			}
		}else{
			char fichier[9999];
            if(reception(tab_socket_client[position], fichier, sizeof(fichier)) != 0){
				perror("Erreur reception");
				exit(EXIT_FAILURE);
			}

            sop.sem_num = 0;
			sop.sem_op = -1;
			sop.sem_flg = 0;
            if(semop(sem_id_partage, &sop, 1) < 0){
                perror("Erreur de semop");
                exit(EXIT_FAILURE);
		    }

            strcpy(segment_partage->fichier, fichier);

            sop.sem_num = 0;
			sop.sem_op = 1;
			sop.sem_flg = 0;
			if(semop(sem_id_partage, &sop, 1) < 0){
                perror("Erreur de semop");
                exit(EXIT_FAILURE);
		    }
		
			for (int i = 0; i < sizeof(tab_socket_client); ++i) //on update le fichier à tous les clients
			{
				if(segment_partage->tab_socket_client[i] != -1 && i != position){
					sop.sem_num = i;
					sop.sem_op = 1;
					sop.sem_flg = 0;
					if(semop(sem_id_partage, &sop, 1) < 0){
                        perror("Erreur de semop");
                        exit(EXIT_FAILURE);
                    }
				}
			}
		}
	}while(flag == 1);
}

void* maj_fichier_utilisateur(void * tmp){
	struct Donnees_Client* donnees_client = tmp;

    int position = donnees_client->position;
	int* tab_socket_client = donnees_client->tab_socket_client;

    struct Segment_partage* segment_partage = NULL;
	segment_partage = (struct Segment_partage*) shmat(donnees_client->sh_id, NULL, 0);
	if(segment_partage == NULL){
		perror("Erreur de la liaison à la mémoire partagée");
		exit(EXIT_FAILURE);
	}

    key_t file, maj;
    struct sembuf sop;

	if((maj = ftok("./fichier_maj.txt", 42)) == -1){
		perror("Erreur  de l'assignation de la clé maj");
		exit(EXIT_FAILURE);
	}

    if((file = ftok("./fichier_partage.txt", 42)) == -1){
		perror("Erreur de l'assignation de la clé key");
		exit(EXIT_FAILURE);
	}

    int sem_id_partage = semget(file, 0, 0666);
	if(sem_id_partage==-1){
        perror("Erreur semaphore \n"); 
        exit(EXIT_FAILURE);
    }

    int sem_id_maj = semget(maj, sizeof(tab_socket_client), 0666);
	if(sem_id_maj == -1){
        perror("Erreur sémaphore");
        exit(EXIT_FAILURE);
    }

    do{
		sop.sem_num = position;
		sop.sem_op = -1;
		sop.sem_flg = 0;
        if(semop(sem_id_maj, &sop, 1) < 0){
            perror("Erreur de semop");
            exit(EXIT_FAILURE);
        }

		sop.sem_num = 0;
		sop.sem_op = -1;
		sop.sem_flg = 0;
        if(semop(sem_id_partage, &sop, 1) < 0){
            perror("Erreur de semop");
            exit(EXIT_FAILURE);
        }

		int flag = 1;
		if(envoi(tab_socket_client[position], &flag, sizeof(int)) == -1){
			perror("Erreur envoie");
		}

		if(envoi(tab_socket_client[position], segment_partage->fichier,sizeof(segment_partage->fichier)) != 0){
			perror("Erreur reception");
		}

		sop.sem_num = 0;
		sop.sem_op = 1;
		sop.sem_flg = 0;
		if(semop(sem_id_partage, &sop, 1) < 0){
            perror("Erreur de semop");
            exit(EXIT_FAILURE);
        }
	}while(1);
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
    printf("Creation du fichier partagée...\n");
    if (open(fichier_partage, O_RDWR | O_CREAT, 0666) < 0) {
        perror("Erreur d'ouverture de fichier");
        exit(EXIT_FAILURE);
    }

    printf("Creation du fichier notif...\n");
    if (open(fichier_notif, O_RDWR | O_CREAT, 0666) < 0) {
        perror("Erreur d'ouverture de fichier");
        exit(EXIT_FAILURE);
    }

    /*
    printf("Creation du fichier maj...\n");
    if (open(fichier_maj, O_RDWR | O_CREAT, 0666) < 0) {
        perror("Erreur d'ouverture de fichier");
        exit(EXIT_FAILURE);
    }
    */

    /* ------------------- initialisation de la mémoire partagé ------------------- */
    key_t notif, fichier, maj;
    int sh_id;

    printf("Creation de la clé d'acces IPC...\n");
    /* ------------------- création de la cle d'accès IPC du fichier donnees ------------------- */
    if ((fichier = ftok(fichier_partage, 42)) == -1) {
        perror("Erreur de l'assignation de la clé\n");
        exit(EXIT_FAILURE);
    }

    /* -------------------création de la cle d'accès IPC pour notifier la maj ------------------- */
    if ((notif = ftok(fichier_notif, 42)) == -1) {
        perror("Erreur de l'assignation de la clé\n");
        exit(EXIT_FAILURE);
    }

    /* -------------------création de la cle d'accès IPC pour la maj ------------------- */
    /*
    if ((maj = ftok(fichier_maj, 42)) == -1) {
        perror("Erreur de l'assignation de la clé\n");
        exit(EXIT_FAILURE);
    }*/

    printf("Creation du segment de mémoire partagée...\n");
    /* ------------------- création d'un nouveau segment de mémoire avec l'identifiant en retour ------------------- */
    if ((sh_id = shmget(fichier, sizeof(struct Segment_partage), IPC_CREAT | 0666)) == -1) {
    	perror("Erreur lors de la création de la mémoire.");
        exit(EXIT_FAILURE);
    }

    printf("Initialisation de la variable partagée...\n");
    /* ------------------- Initialisation de la variable partagé ------------------- */
    struct Segment_partage * segment_partage = NULL;
    segment_partage = (struct Segment_partage * ) shmat(sh_id, NULL, 0);
    if (segment_partage == NULL) {
        perror("Erreur de la liaison à la mémoire partagée");
        exit(EXIT_FAILURE);
    }
    segment_partage-> nb_Clients = 0;
    segment_partage-> max_clients = max_clients;

    printf("Création de la sémaphore d'associe à la clé pour l'écriture\n");
    union semun egCtrl;
    /* ------------------- semaphore d'associe à la cle pour la fichier partage ------------------- */
    int sem_id_partage = semget(fichier, 1, IPC_CREAT | 0666);
    if (sem_id_partage == -1) {
        perror("Erreur création sémaphore");
    }

    egCtrl.val = 1; //nb de personne ayant la ressource en même temps
    if (semctl(sem_id_partage, 0, SETVAL, egCtrl) == -1) {
        perror("Erreur d'initialisation");
    }

    /* ------------------- semaphore d'associe à la cle pour la notif ------------------- */
    int sem_id = semget(notif, max_clients, IPC_CREAT | 0666);
    if (sem_id == -1) {
        perror("Erreur création sémaphore \n");
        exit(EXIT_FAILURE);
    }

    egCtrl.val = 0;
    for (int i = 0; i < max_clients; ++i) //
    {
        if (semctl(sem_id, i, SETVAL, egCtrl) == -1) {
            perror("Erreur d'initialisation");
        }
    }

    /* ------------------- semaphore d'associe à la cle pour la maj ------------------- */
    int sem_id_maj = semget(maj, max_clients, IPC_CREAT | 0666);
    if (sem_id_maj == -1) {
        perror("Erreur création sémaphore\n");
        exit(EXIT_FAILURE);
    }

    egCtrl.val = 0;
    for (int i = 0; i < max_clients; ++i) //
    {
        if (semctl(sem_id_maj, i, SETVAL, egCtrl) == -1) {
            perror("Erreur d'initialisation\n");
            exit(EXIT_FAILURE);
        }
    }

    /* ------------------- Initialisation du socket ------------------- */
    struct sockaddr_in servaddr;
    servaddr.sin_family = AF_INET; // IPv4 
    servaddr.sin_addr.s_addr = htonl(INADDR_ANY); //ip
    servaddr.sin_port = htons(port); //port

    int socket_serveur;
    if ((socket_serveur = socket(AF_INET, SOCK_DGRAM, 0)) < 0) {
        perror("Erreur de création du socket");
        exit(EXIT_FAILURE);
    }
    printf("Socket créé succès!\n");

    if (bind(socket_serveur, (const struct sockaddr * ) &servaddr, sizeof(servaddr)) < 0) {
        perror("Erreur de nommage");
        exit(EXIT_FAILURE);
    }
    printf("Nommage succès\n");

    int nb_Clients = 0;
    int * tab_socket_client = malloc(max_clients * sizeof(int));

    for (int i = 0; i < max_clients; ++i) {
        tab_socket_client[i] = -1;
        segment_partage->tab_socket_client[i] = -1;
    }

    pthread_t * threads_clients = malloc(max_clients * sizeof(pthread_t));
    //pthread_t * threads_clients_maj = malloc(max_clients * sizeof(pthread_t));
    pthread_t * threads_notif_maj = malloc(max_clients * sizeof(pthread_t));
    pid_t pid;

    if (listen(socket_serveur, max_clients) != 0) {
        perror("Erreur de positionnement");
        exit(EXIT_FAILURE);
    }
    printf("Information du serveur : <%s> <%i>\n",inet_ntoa((struct in_addr)cliaddr.sin_addr), port);
    printf("En mode écoute....\n");

    struct sembuf sop;

    /* ------------------- Gestion des clients ------------------- */
	struct Donnees_Client* donnees_client;
    int fils = 0;
    while (fils != 1) {
        int socket_client;
        struct sockaddr_in cliaddr;
		int flag;
        socklen_t lgA_client = sizeof(struct sockaddr_in);
		/* ------------------- Connecter un client au socket -------------------*/
        if (socket_client = accept(socket_serveur, (struct sockaddr * ) &cliaddr, &lgA_client) < 0) {
            perror("Erreur de connexion");
            exit(EXIT_FAILURE);
        }

		sop.sem_num = 0;	/* Agir sur le sémaphore 0 */
		sop.sem_op = -1;	/* Attendre que la valeur soit égale à 0 */
		sop.sem_flg = 0;
		if(semop(sem_id_partage, &sop, 1) < 0){
			perror("Erreur de semop");
			exit(EXIT_FAILURE);
		}

        if (segment_partage->nb_Clients >= max_clients) {
            sop.sem_num = 0;
            sop.sem_op = 1;
            sop.sem_flg = 0;
            if((semop(sem_id_partage, &sop, 1)) < 0 ){
				perror("Erreur de semop");
				exit(EXIT_FAILURE);
			}

            flag = 1; //trop de client connecté
            if (envoi(socket_client, &flag, sizeof(int)) != 0) {
                perror("Erreur d'envoi");
                exit(EXIT_FAILURE);
            }
         }else {
            flag = 0;
            if (envoi(socket_client, &flag, sizeof(int)) != 0) {
                perror("Erreur d'envoi");
                exit(EXIT_FAILURE);
            }

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

			segment_partage->tab_socket_client[position] = tab_socket_client[position];
			for(int i = 0; i < max_clients; i++){
				tab_socket_client[i] = segment_partage->tab_socket_client[i];
			}

			segment_partage->nb_Clients++;
			nb_Clients = segment_partage->nb_Clients++;

			sop.sem_num = 0;
			sop.sem_op = 1;
			sop.sem_flg = 0;
			if(semop(sem_id_partage, &sop, 1) < 0){
				perror("Erreur de semop");
				exit(EXIT_FAILURE);
			}

			printf("Nouvelle connection : %s\n", inet_ntoa((struct in_addr)cliaddr.sin_addr));
			pid = fork();
			switch(pid){
				case -1 :
				perror("Erreur de fork");
				exit(EXIT_FAILURE);
				
                case 0 :
				fils = 1;
				/* ------------------- Creation du client ------------------- */
				donnees_client = malloc(sizeof(struct Donnees_Client));
				init_donnees_client(donnees_client, tab_socket_client, position, sh_id, nb_Clients);
					
                if(pthread_create(&threads_clients[position], NULL, gestion_client, donnees_client) != 0){
                    printf("Erreur pthread gestion_client! \n");
                    exit(EXIT_FAILURE);
                }

                if(pthread_create(&threads_notif_maj[position], NULL, maj_fichier_utilisateur, donnees_client) != 0){
		      		printf("Erreur pthread maj fichier utilisateur\n");
		      		exit(EXIT_FAILURE);
		      	}

                if(pthread_join(threads_clients[position],NULL) != 0){
                    printf("Erreur join pthreads! \n");
                    exit(EXIT_FAILURE);
		      	}
			}
        }
    }
	if (pid != 0 && pid != -1) {
                if (shmctl(sh_id, IPC_RMID, NULL) == -1) {
                    perror("Erreur du détachement de la mémoire partagée.");
                    exit(EXIT_FAILURE);
                }
                close(socket_serveur);
                free(threads_clients);
                //free(threads_clients_maj);
                free(threads_notif_maj);
				printf("Fermeture du serveur\n");
            }   
	return 0;
}