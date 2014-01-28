#include <sys/socket.h>
#include <linux/if_ether.h>
#include <linux/ip.h>
#include <arpa/inet.h>
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>

char *ip_proto_label(unsigned char proto)
{
	switch (proto) {
	case 1:
		return "ICMP";
	case 2:
		return "IGMP";
	case 6:
		return "TCP";
	case 17:
		return "UDP";
	default:
		return "Unknown";
	}
}

int main(void)
{
	int sock, i;
	struct ethhdr *eh;
	struct iphdr *iph;

	if ((sock = socket(AF_PACKET, SOCK_RAW, htons(ETH_P_ALL))) < 0) {
		printf("socket() failed %i\n", errno);
	}

	void *buf = (void*)malloc(ETH_FRAME_LEN);

	while (1) {
		int len = recvfrom(sock, buf, ETH_FRAME_LEN, 0, NULL, NULL);
		unsigned int id;

		if (len <= 0 || buf == NULL) {
			printf("Frame null\n");
			continue;
		}

		eh = (struct ethhdr *)buf;
		iph = (struct iphdr *)(buf  + sizeof(struct ethhdr));

		id = ntohs(eh->h_proto);
		printf("LEN: %i, ID: 0x%X\n", len, id);
		if (id == 0x800)
			printf("IP VER: %i, PROTO: %i (%s)\n", iph->version, iph->protocol,
				ip_proto_label(iph->protocol));
		printf("SRC:");
		for (i = 0; i < ETH_ALEN; i++)
			printf(" %02X", eh->h_source[i]);
		printf(" || DST:");
		for (i = 0; i < ETH_ALEN; i++)
			printf(" %02X", eh->h_dest[i]);

		printf("\n");

		getchar();
	}
}
