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

#define MEM_SEG_KEY 12345

/* definition structure */
typedef struct Segment_partage segment_partage;

struct segment_partage {
    int nb_Clients;
    int max_clients;
    int tab_socket_client[max_clients];
    char fichier[5000];
};

union semun {
    int val;
    struct semid * buf;
    unsigned short * tab;
    struct seminfo * _buf;
}

const char * fichier_partage = "fichier_partage.txt";
const char * fichier_notif = "fichier_notif.txt";
const char * fichier_maj = "ficheir_maj.txt";

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
            return 1
        } else if (resultat == 0) {
            printf("Socket fermé\n")
            return 2;
        }
        nb_octet_envoi += resultat;
    }
    return 0;
}

int main(int argc, char ** argv) {
    if (argc < 3) {
        printf("Usage %s <PORT> <NB_MAX_CLIENT> ( nb_max_client doit etre > 0 )\n", argv[0]);
        exit(EXIT_FAILURE);
    }
    const char * port = argv[1];

    if (argv[2] < 1) {
        printf("NB_MAX_CLIENT doit etre strictement superieur a 1!\n");
        exit(EXIT_FAILURE);
    }
    const int max_clients = argv[2];

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

    printf("Creation du fichier maj...\n");
    if (open(fichier_maj, O_RDWR | O_CREAT, 0666) < 0) {
        perror("Erreur d'ouverture de fichier");
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

    printf("Creation du segment de mémoire partagée...\n");
    /* ------------------- création d'un nouveau segment de mémoire avec l'identifiant en retour ------------------- */
    if ((sh_id = shmget(fichier, sizeof(struct segment_partage), IPC_CREAT | 0666)) == -1) {
    	perror("Erreur lors de la création de la mémoire.");
        exit(EXIT_FAILURE);
    }

    printf("Initialisation de la variable partagée...\n");
    /* ------------------- Initialisation de la variable partagé ------------------- */
    struct Segment_partage * segment_partage = NULL;
    segment_partage = (Segment_partage * ) shmat(sh_id, NULL, 0);
    if (segment_partage == NULL) {
        perror("Erreur de la liaison à la mémoire partagée");
        exit(EXIT_FAILURE);
    }
    segment_partage-> nb_Clients = 0;
    segment_partage-> max_clients = max_clients;

    printf("Création de la sémaphore d'associe à la clé pour l'écriture");
    union semun egCtrl;
    /* ------------------- semaphore d'associe à la cle pour la fichier partage ------------------- */
    int sem_id_partage = semget(fichier_partage, 1, IPC_CREAT | 0666);
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

    int cpt_clients = 0;
    int * tab_socket_Client = malloc(max_clients * sizeof(int));

    for (int i = 0; i < max_clients; ++i) {
        tab_socket_Client[i] = -1;
        segment_partage->tab_socket_Client[i] = -1;
    }

    pthread_t * threads_clients = malloc(max_clients * sizeof(pthread_t));
    pthread_t * threads_clients_maj = malloc(max_clients * sizeof(pthread_t));
    pthread_t * threads_notif_maj = malloc(max_clients * sizeof(pthread_t));
    pid_t pid;

    if (listen(socket_serveur, nb_max_client) != 0) {
        perror("Erreur de positionnement");
        exit(EXIT_FAILURE);
    }
    printf("En mode écoute!\n");

    struct sembuf op;

    /* ------------------- Gestion de reception et envoi ------------------- */
    int fils = 0;
    while (fils != 1) {
        int socket_client;
        struct sockaddr_in cliaddr;
		int flag;
        socklen_t lgA_client = sizeof(struct sockaddr_in);
        if (socket_client = accept(socket_serveur, (struct sockaddr * ) &cliaddr, &lgA_client) < 0) {
            perror("Erreur de connexion");
            exit(EXIT_FAILURE);
        }

		op.sem_num=0;
		op.sem_op=-1;
		op.sem_flg=0;
		semop(sem_id_partage,&op,1);

        if (segment_partage->nbClients >= max_clients) {
            op.sem_num = 0;
            op.sem_op = 1;
            op.sem_flg = 0;
            semop(sem_id_partage, &op, 1);

            flag = 1; //trop de client connecté
            if (envoi(socket_client, &flag, sizeof(int)) != 0) {
                perror("Erreur envoi flag connection");
                exit(EXIT_FAILURE);
            }
         }else {
            flag = 0;
            if (envoi(socket_client, &flag, sizeof(int)) != 0) {
                perror("Erreur envoi flag connection");
                exit(EXIT_FAILURE);
            }

            int position = recuperer_position(tab_socket_Client);
            tab_socket_Client[position] = dSclient;
            if (tab_socket_Client[position] == -1) {
                perror("Erreur de connexion");
                exit(EXIT_FAILURE);
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
                free(threads_clients_maj);
                free(threads_notif_maj);
				printf("Fermeture du serveur\n");
            }   
	return 0;
}