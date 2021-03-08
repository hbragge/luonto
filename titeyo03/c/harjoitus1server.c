/*

7.11.2005
Henri Bragge 189696

Harjoitustyˆ 1: Server

Toteutimme yksinkertaisen C-kielisen asiakas-palvelin -sovelluksen,
mik‰ k‰ytt‰‰ TCP-protokollaa tiedonsiirrossa. Palvelinkoneen
IP-osoite annetaan asiakassovelluksen komentoriviparametrina
joko host-nimen muodossa tai pistedesimaalimuodossa.
Palvelinsovellus palauttaa verkko-osoitteensa host-osan.
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <sys/wait.h>
#include <signal.h>

#define MYPORT 3390    // portti mihin clientit ottavat yhteyden

#define BACKLOG 10     // kerralla avoimien yhteyksien m‰‰r‰

void sigchld_handler(int s)
{
    while(waitpid(-1, NULL, WNOHANG) > 0);
}

int main(void)
{
	char h[50];
    int sockfd, new_fd;  // luodaan sockfd ja new_fd muuttujat
    struct sockaddr_in my_addr;    // serverin osoite
    struct sockaddr_in their_addr; // clienttien osoitteet
    socklen_t sin_size;
    struct sigaction sa;
    int yes=1;

    if ((sockfd = socket(AF_INET, SOCK_STREAM, 0)) == -1) {     // avaa soketti muuttujaan sockfd
        perror("socket");
        exit(1);
    }

    if (setsockopt(sockfd,SOL_SOCKET,SO_REUSEADDR,&yes,sizeof(int)) == -1) {    // asetetaan soketille ominaisuuksia
        perror("setsockopt");
        exit(1);
    }
    
    my_addr.sin_family = AF_INET;         // host byte order
    my_addr.sin_port = htons(MYPORT);     // short, network byte order
    my_addr.sin_addr.s_addr = INADDR_ANY; // t‰ytet‰‰n automaattisesti omalla IP:ll‰
    memset(&(my_addr.sin_zero), '\0', 8); // nollataan loput tietueesta

    if (bind(sockfd, (struct sockaddr *)&my_addr, sizeof(struct sockaddr))
                                                                   == -1) {
        perror("bind");
        exit(1);
    }

    if (listen(sockfd, BACKLOG) == -1) {        // kuuntele muuttujan sockfd sokettia
        perror("listen");
        exit(1);
    }

    sa.sa_handler = sigchld_handler; // poistetaan kaikki kuolleet prosessit
    sigemptyset(&sa.sa_mask);
    sa.sa_flags = SA_RESTART;
    if (sigaction(SIGCHLD, &sa, NULL) == -1) {
        perror("sigaction");
        exit(1);
    }

    while(1) {  
        sin_size = sizeof(struct sockaddr_in);
        if ((new_fd = accept(sockfd, (struct sockaddr *)&their_addr,  // hyv‰ksy yhteys sokettiin ja tallenna se new_fd-muuttujaan
                                                       &sin_size)) == -1) {
            perror("accept");
            continue;
        }
        printf("server: yhteys osoitteesta %s\n",
                                           inet_ntoa(their_addr.sin_addr));
        
	gethostname(h,strlen(h));
        if (!fork()) { // lapsiprosessi
            close(sockfd); //lapsiprosessi ei tarvitse listeneri‰
            if (send(new_fd, h, 50, 0) == -1)       // l‰hetet‰‰n vastaus yhteyden ottaneelle k‰ytt‰j‰lle eli new_fd
                perror("send");
		
            close(new_fd);
            exit(0);
        }
        close(new_fd);  // p‰‰prosessi ei tarvitse
    }

    return 0;
} 
