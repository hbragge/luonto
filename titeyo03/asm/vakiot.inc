;	Tyo 3
;
;
; Yleisiä vakioita (esim. muistiosotteita)
;
;
;


SPEED_OF_SOUND 	.set	344 ; m/s
FS				.set	44100 ; näytettä/s -> 1m = 44100 / 344 = 128 näytettä
BUFFERSIZE  	.set	2*(FS/SPEED_OF_SOUND); 2m = 256 näytettä
BURST_LENGTH	.set	8 ;9 näytettä --> 1 sinijakso
INTERVAL		.set	20; Sinipurskeiden toiston väli, yksikkö on 2^20 kelloa = n. 5,2 ms
PERIODI			.set	28000
RAJA			.set	4000

CPLD	.set	0x300000

;---- I/O osoitteet TIMER0
TIM0	.set	0x1000
PRD0	.set	0x1001
TCR0	.set	0x1002
PRSC0	.set	0x1003
;----

;---- I/O osoitteet TIMER1
TIM1	.set	0x2400
PRD1	.set	0x2401
TCR1	.set	0x2402
PRSC1	.set	0x2403
;----

;---- I/O osoitteet MCBSP1
SPCR21	.set 	0x2C04
SPCR11	.set 	0x2C05
PCR1	.set 	0x2C12
XCR11	.set	0x2C09
XCR21	.set	0x2C08
DXR11	.set 	0x2C03
SRGR11	.set    0x2C0B
SRGR21	.set 	0x2C0A
;----

;---- I/O osoitteet MCBSP2
SPCR22	.set 	0x3004
SPCR12	.set 	0x3005
PCR2	.set 	0x3012
XCR12	.set	0x3009
XCR22	.set	0x3008
DXR12	.set 	0x3003
DRR12	.set 	0x3001
SRGR12	.set    0x300B
SRGR22	.set 	0x300A
RCR12	.set 	0x3007
RCR22	.set 	0x3006
;----

;---- I/O osoitteet DMA GLOBAL
DMA_GCR		.set 	0x0E00
DMA_GSRC 	.set 	0x0E02
DMA_GTCR 	.set 	0x0E03
;----

;---- I/O osoitteet DMA #0 (kirjoitus koodekille)
DMA_CCR0 	.set 	0x0C01
DMA_CICR0	.set 	0x0C02
DMA_CSR0	.set 	0x0C03
DMA_CSDP0	.set 	0x0C00
DMA_CSSA_U0 .set 	0x0C05
DMA_CSSA_L0	.set 	0x0C04
DMA_CDSA_U0 .set 	0x0C07
DMA_CDSA_L0	.set 	0x0C06
DMA_CFN0	.set 	0x0C09
DMA_CEN0	.set 	0x0C08
DMA_CSEI0	.set	0x0C0B
DMA_CSFI0	.set	0x0C0A
;----

;---- I/O osoitteet DMA #1 (luku koodekilta)
DMA_CCR1 	.set 	0x0C21
DMA_CICR1	.set 	0x0C22
DMA_CSR1	.set 	0x0C23
DMA_CSDP1	.set 	0x0C20
DMA_CSSA_U1 .set 	0x0C25
DMA_CSSA_L1	.set 	0x0C24
DMA_CDSA_U1 .set 	0x0C27
DMA_CDSA_L1	.set 	0x0C26
DMA_CFN1	.set 	0x0C29
DMA_CEN1	.set 	0x0C28
DMA_CDEI1	.set	0x0C2B
DMA_CDFI1	.set	0x0C2A
;----

