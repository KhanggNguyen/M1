#include <stdio.h> 
#include <stdlib.h> 
#include <unistd.h> 
#include <string.h> 
#include <sys/types.h> 
#include <sys/socket.h> 
#include <arpa/inet.h> 
#include <netinet/in.h>
#include <fcntl.h>

#include <pthread.h>
#include <sys/sem.h>
#include <sys/shm.h>

#define MEM_SEG_KEY 12345

const char* msg_data_file_path = "msg_data.txt";

void initialisation(){
    printf("Début initialisation\n");

    printf("Création du fichier partagée...\n");
    int fd = open(msg_data_file_path, O_RDWR|O_CREAT, 0666);
    if(fd < 0){
        perror("Erreur d'ouverture de fichier");
        return 5;
    }

    printf("Creation de la clé d'acces IPC...\n");
    key_t key = ftok(msg_data_file_path, 1); 
    if(key < 0){
        perrr("Erreur de création de la clé");
    }

}

int main(int argc, char ** argv) { 
    if(argc < 3){
        printf("Usage %s <PORT> <NB_MAX_CLIENT>\n", argv[0]);
        exit(EXIT_FAILURE);
    }; 

    if(argv[2] < 1){
        printf("NB_MAX_CLIENT doit etre strictement superieur a 1!\n");
        exit(EXIT_FAILURE);
    }
    const int nb_max_client = argv[2];
    const char* port = argv[1];
    struct sockaddr_in servaddr; 

    //Ajouter infos sur ip, port
    servaddr.sin_family    = AF_INET; // IPv4 
    servaddr.sin_addr.s_addr = htonl(INADDR_ANY);//ip
    servaddr.sin_port = htons(port);//port

    //Créer socket 
    int socket_serveur;//descripteur serveur
    if ((socket_serveur = socket(AF_INET, SOCK_DGRAM, 0)) < 0 ) { 
        perror("Erreur de création du socket"); 
        return 1;
    }
    printf("Socket créé succès!\n");

    // Bind the socket with the server address 
    if (bind(socket_serveur, (const struct sockaddr *)&servaddr,  
            sizeof(servaddr)) < 0 ) 
    { 
        perror("Erreur de nommage"); 
        return 2;
    }
    printf("Nommage succès\n");

    if(listen(socket_serveur, nb_max_client) != 0){
        perror("Erreur de positionnement");
        return 3;
    }
    printf("En mode écoute!\n");

    //client
    int socket_client;//descripteur client
    socklen_t lgA_client = sizeof(struct sockaddr_in);
    struct sockaddr_in cliaddr;
    
    while(1){
        if(socket_client = accept(socket_serveur, (struct sockaddr*) &cliaddr, lgA_client) < 0){
            perror("Accept echoue");
            close(socket_client);
            close(socket_serveur);
            return 4;
        }

        switch(fock()){
            case -1: 
                perror("Problème du serveur");
                close(socket_client);
                close(socket_serveur);
                return 5;
            case 0://fils
                close(socket_serveur);
                traiter(socket_client);
                close(socket_client);
                exit(0);
            default:
                
        }
    }
    printf("Arrete du serveur" );
    close(socket_client);
    close(socket_serveur);
    return 0;
}
