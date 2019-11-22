#include <stdio.h> 
#include <stdlib.h> 
#include <unistd.h> 
#include <string.h> 
#include <sys/types.h> 
#include <sys/socket.h> 
#include <arpa/inet.h> 
#include <netinet/in.h>

#define MEM_SEG_KEY 12345

struct shared_segment {
    int socket_fds_size = MAX_CLIENT;
    int client_current_num = 0;
    int socket_fds[MAX_CLIENT] = {-1};  
};

const char* msg_data_file_path = "msg_data.txt";

int main(int argc, char ** argv) { 
    if(argc < 3){
        printf("Usage %s <PORT> <NB_MAX_CLIENT>\n", argv[0]);
        exit(EXIT_FAILURE);
    })

    const char* port = argv[1];

    struct sockaddr_in servaddr, cliaddr; 

    servaddr.sin_family    = AF_INET; // IPv4 
    servaddr.sin_addr.s_addr = htonl(INADDR_ANY); 
    servaddr.sin_port = htons(port);
}
