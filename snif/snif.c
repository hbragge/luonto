#include <sys/socket.h>
#include <linux/if_ether.h>
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>

void main(void)
{
	int sock, i;
	struct ethhdr *eh;

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
		id = ntohs(eh->h_proto);
		printf("LEN: %i, ID: 0x%X\n", len, id);
		printf("SRC:");
		for (i = 0; i < ETH_ALEN; i++)
			printf(" %02X", eh->h_source[i]);
		printf(" || DST:");
		for (i = 0; i < ETH_ALEN; i++)
			printf(" %02X", eh->h_dest[i]);

		if (id != 0x800) {
			printf("Unrecognized frame ID\n");
		}

		getchar();
	}
}
