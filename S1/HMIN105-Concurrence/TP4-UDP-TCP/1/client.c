
// Client side implementation of UDP client-server model 
#include <stdio.h> 
#include <stdlib.h> 
#include <unistd.h> 
#include <string.h> 
#include <sys/types.h> 
#include <sys/socket.h> 
#include <arpa/inet.h> 
#include <netinet/in.h> 

#define MAXLINE 1024 
  
// Driver code 
int main(int argc, char ** argv) { 
    //ip locale : 127.0.0.1 
    //port serveur définit : 8080
    if(argc < 3){
    	printf("Usage : %s <adresse_ip> <num_de_port> \n", argv[0]);
    	return 0;
  	}

    int sockfd; 
    char buffer[MAXLINE]; 
    char msg[256]; 
    struct sockaddr_in     servaddr; 
  
    // Creating socket file descriptor 
    if ( (sockfd = socket(AF_INET, SOCK_DGRAM, 0)) < 0 ) { 
        perror("socket creation failed"); 
        exit(EXIT_FAILURE); 
    } 
  
    memset(&servaddr, 0, sizeof(servaddr)); 
      
    // Filling server information 
    inet_pton(AF_INET, argv[1], &(servaddr.sin_addr));
    //servaddr.sin_addr.s_addr = inet_addr(argv[1]); 
    servaddr.sin_family = AF_INET; 
    servaddr.sin_port = htons(atoi(argv[2])); 
    socklen_t len;  
    int n; 

    printf("Saisissez le message : ");
    scanf("%s", msg);
    sendto(sockfd, (const char *)msg, strlen(msg), 
        MSG_CONFIRM, (const struct sockaddr *) &servaddr,  
            sizeof(servaddr));
    printf("message envoyé.\n"); 
          
    n = recvfrom(sockfd, (char *)buffer, MAXLINE,  
                MSG_WAITALL, (struct sockaddr *) &servaddr, 
                &len); 
    buffer[n] = '\0'; 
    printf("Server répondu : %s\n", buffer); 
    
    close(sockfd); 
    return 0; 
} 
