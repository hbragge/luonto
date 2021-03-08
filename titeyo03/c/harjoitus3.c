/*

7.11.2005
Henri Bragge 189696

Harjoitustyö 3
Tehtävänä on tehdä yksinkertainen c-kielinen raakaan sokettiin (Raw Socket) perustuva sovellus. 
Ideana olisi, että sovellus lukisi kyseisestä soketista tietoa (esim. ICMP- tai TCP-sanoman) 
ja tulostaisi näytölle tiedon, jotta voidaan varmistua vastaanoton onnistumisesta. 
Tulostettava tieto voi olla esimerkiksi jokin protokollaotsakkeen kentistä. 
Ei siis ihan yksinkertainen tehtävä, mutta katsotaan miten onnistuu.    */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <netdb.h>

#include <sys/types.h>
#include <sys/stat.h>
#include <sys/socket.h>

#include <netinet/in_systm.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <netinet/udp.h>
#include <netinet/ip_icmp.h>
#include <netinet/tcp.h>

#include <arpa/inet.h> 

#define P 25	


int 
main (void)
{ 
  char buffer[8192];    			// alustetaan puskurin koko 8192 tavuun, koska paketit eivät normaalisti tämän isompia            
  int s = socket (PF_INET, SOCK_RAW, IPPROTO_ICMP);	// avaa raw socket

  struct ip *iph = (struct ip *) buffer;                  	// buffer-muuttujan alkupään sisältö rakennetaan ip-tietueen muotoon
  struct icmp *icmph = (struct icmp *) buffer + sizeof(*iph);  // buffer-muuttujan ip-headerin jälkeinen sisältö rakennetaan icmp-tietueen muotoon
  struct sockaddr_in sin;

  sin.sin_family = AF_INET;
  sin.sin_port = htons (P);                               // asetetaan portti network byte order - muotoon
  sin.sin_addr.s_addr = inet_addr ("127.0.0.1");          // kuunnellaan omaa ip:tä



	while(read(s, buffer, 8192)>0){                 // luetaan vastaanottopuskurin sisältö muuttujaan s
		
		printf("ip-otsakkeen pituus:%u\n", iph->ip_len);   // tulostetaan ip-otsakkeen pituus osoittamalla ip-headerin kenttään ip_len
		printf("ip-ttl:%u\n", iph->ip_ttl);					// tulostetaan ip-ttl eli ip-paketin elinaika
		printf("icmp-type: %u\n", icmph->icmp_type);}      // tulostetaan icmp-type soittamalla icmp-headerin kenttään icmp_type


  return 0;
}
