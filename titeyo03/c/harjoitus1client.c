/*

7.11.2005
Henri Bragge 189696

Harjoitustyö 1: Client

Toteutimme yksinkertaisen C-kielisen asiakas-palvelin -sovelluksen,
mikä käyttää TCP-protokollaa tiedonsiirrossa. Palvelinkoneen
IP-osoite annetaan asiakassovelluksen komentoriviparametrina
joko host-nimen muodossa tai pistedesimaalimuodossa.
Palvelinsovellus palauttaa verkko-osoitteensa host-osan.
*/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <netdb.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <sys/socket.h>

#define PORT 3390 // serverin portti mihin client ottaa yhteyden 

#define MAXDATASIZE 100 // maksimi määrä bittejä mitä voi siirtää kerralla 

int main(int argc, char *argv[])
{
    int sockfd, numbytes;  
    char buf[MAXDATASIZE];
    struct hostent *he;
    struct sockaddr_in their_addr; // koneiden osoitteet 

    if (argc != 2) {
        fprintf(stderr,"käyttö: palvelinsovelluksen IP-osoite host-nimen muodossa tai pistedesimaalimuodossa\n");
        exit(1);
    }

    if ((he=gethostbyname(argv[1])) == NULL) {  // haetaan serverin host-nimi 
        herror("gethostbyname");
        exit(1);
    }

    if ((sockfd = socket(AF_INET, SOCK_STREAM, 0)) == -1) {  // avataan soketti
        perror("socket");
        exit(1);
    }

    their_addr.sin_family = AF_INET;    // host byte order 
    their_addr.sin_port = htons(PORT);  // short, network byte order 
    their_addr.sin_addr = *((struct in_addr *)he->h_addr);
    memset(&(their_addr.sin_zero), '\0', 8);  // asetetaan loppuosassa nolliksi

    if (connect(sockfd, (struct sockaddr *)&their_addr,             // otetaan yhteys serveriin ja tarkistetaan samalla onnistuiko se
                                          sizeof(struct sockaddr)) == -1) {
        perror("connect");
        exit(1);
    }

    if ((numbytes=recv(sockfd, buf, MAXDATASIZE-1, 0)) == -1) {   // vastaanotetaan sokettiin tuleva data ja annetaan virhe jos validia dataa ei saada
        perror("recv");
        exit(1);
    }

    buf[numbytes] = '\0';

    printf("Vastaanotettiin: %s\n",buf);   // tulostetaan buf-muuttujan sisältö

    close(sockfd);    // suljetaan soketti

    return 0;
} 
