#include <stdio.h> 
#include <stdlib.h> 
#include <unistd.h> 
#include <string.h> 
#include <sys/types.h> 
#include <sys/socket.h> 
#include <arpa/inet.h> 
#include <netinet/in.h> 
  
#define PORT     8080 
#define MAXLINE 1024 

int main(int argc, char ** argv) { 

    int sockfd; 
    char buffer[MAXLINE]; 
    char buf_ip[INET_ADDRSTRLEN];
    
    char msg[256];
    struct sockaddr_in servaddr, cliaddr; 

    // Creating socket file descriptor 
    if ( (sockfd = socket(AF_INET, SOCK_DGRAM, 0)) < 0 ) { 
        perror("socket creation failed"); 
        exit(EXIT_FAILURE); 
    } 

    memset(&servaddr, 0, sizeof(servaddr)); 
    memset(&cliaddr, 0, sizeof(cliaddr)); 
    
    // Filling server information 
    servaddr.sin_family    = AF_INET; // IPv4 
    servaddr.sin_addr.s_addr = htonl(INADDR_ANY); 
    servaddr.sin_port = htons(PORT); 
    
    // Bind the socket with the server address 
    if (bind(sockfd, (const struct sockaddr *)&servaddr,  
            sizeof(servaddr)) < 0 ) 
    { 
        perror("bind failed"); 
        exit(EXIT_FAILURE); 
    } 
    socklen_t len;
    int n; 
    n = recvfrom(sockfd, (char *)buffer, MAXLINE,  
                MSG_WAITALL, ( struct sockaddr *) &cliaddr, 
                &len);

    inet_ntop(AF_INET, &(cliaddr.sin_addr), buf_ip, INET_ADDRSTRLEN);//met ip du client dans buf_ip
    printf("IP du client : %s\n", buf_ip);
    printf("Port du client : %d\n", cliaddr.sin_port); 
    buffer[n] = '\0'; 
    printf("Client envoyé : %s\n", buffer);

    printf("Saissisez le message à envoyer au client : ");
    scanf("%s", msg); 
    sendto(sockfd, (const char *)msg, strlen(msg),  
        MSG_CONFIRM, (const struct sockaddr *) &cliaddr, 
            len); 
    printf("message répondu.\n");  
      
    return 0; 
} 