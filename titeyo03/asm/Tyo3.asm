;	Tyo 3
;
;-----------------------------------
;
; Kaikuluotaukseen perustuva etäisyyden mittaaja
; 
;-----------------------------------
;
;
	.include "vakiot.inc"

	.ref	initMCBSP2
	.ref	initMCBSP1
	.ref	readMcBSP2
	.ref	writeMcBSP2
	.ref	waitXRDY1
	.ref	waitXRDY2
	.ref	waitRRDY2
	.ref	initTIMER0
	.ref	initTIMER1
	.ref	startTIMER0
	.ref	startTIMER1
	.ref	initDMA0
	.ref	initDMA1
	.ref 	waitDMA0
	.ref 	waitDMA1
	.ref 	destinationAddressDMA1
	
	
	.def begin
	.data
	.def outptr
	.def inptr


SINE_BURST	.word	0, 21062, 32269, 28377, 11207, -11207, -28377, -32269, -21062	;4410 Hz
SINE_BURST_SIZE .set 9

;------- KÄYTETYT PUSKURIT
	.bss 	outptr,BUFFERSIZE,1
	.bss	emptyroom,BUFFERSIZE,1
	.bss 	inptr,BUFFERSIZE,1 
	.bss 	time1,1,1
;------------------------	

	.text
;--- PÄÄOHJELMA
begin:

	CALL	initAIC23 ;konfiguroidaan koodekki ja sarjaportit
	CALL	initDMA0;
	CALL	initDMA1;
	CALL	fillzeros; Tyhjentää out-puskurin
	CALL	sine_burst; Kirjoittaa out-puskuriin sinipurskeen

	MOV		#0,T0
	CALL 	writeMcBSP2 
	CALL	readMcBSP2

	MOV		#INTERVAL, *(timer0div)
	CALL	initTIMER0; alustaa ajastimen 0
	CALL 	initTIMER1; alustaa ajastimen 1
	CALL	startTIMER0; käynnistää ajastimen 0


main_loop: ;jää silmukkaan odottamaan ajastimen 0 laukeamista
	B 		main_loop
;----



	.text
	
searchEcho:
	MOV		#inptr, AR0			;ensimmäinen muistiosoite AR0:aan
	MOV		#BUFFERSIZE, AR1	;puskurin koko AR1:een
	MOV		*AR0+, AC0
	MOV		#RAJA, T1 			;raja-arvo
	
luuppi:
	ABS		AC0, AC0			;otetaan tarkasteltavan arvon itseisarvo
	sub		T1, AC0				;vähennetään siitä raja-arvo
	sub		#1, AR1				;vähennetään puskurin kokoa yhdellä
	BCC hyppy, AC0<=#0 			;jos arvo - maksimiarvo > 0, se...
	MOV		*AR0, T1			;...tallennetaan
	MOV		AR0, AC3			;ja siirretään AC3:een
hyppy:
	MOV		*AR0+, AC0			;kasvatetaan AR0:n arvoa
	BCC luuppi, AR1!=0			;loopataan, kunnes AR1=0
	sub		#inptr, AC3			;vähennetään ensimmäinen muistiosoite, jolloin saadaan etäisyys
	
	
	;led0
	MOV AC3,AC2					;asetetaan etäisyys AC2:een, jotta alkuperäinen pysyy tallessa
	SUB #150,AC2				;vähennetään 150 näytettä
	BCC jump0, AC2>#0			;verrataan onko AC2 suurempi kuin nolla
	MOV		#0,AC1				;jos ei ole, niin...
	BSET	AC1,*(#CPLD)		;..sytytetään led0 asettamalla CPLD-rekisteriin 0
jump0:	
	BCC jump02, AC2<=#0			;jos on, niin...
	MOV		#0,AC1	
	BCLR	AC1,*(#CPLD)		;..sammutetaan led0
jump02:

	;led1
	;MOV		#1,AC1
	;BSET	AC1,*(#CPLD)
	MOV AC3,AC2
	SUB #100,AC2				;vastaava kuin edellinen vaihe, mutta pienennetään vähennettäävä arvoa
	BCC jump1, AC2>#0			;koska tällöin myös AC2:n pitää olla pienempi, jotta jump1:stä ei suoriteta
	MOV		#1,AC1				;ja led sytytetään
	BSET	AC1,*(#CPLD)
jump1:	
	BCC jump12, AC2<=#0
	MOV		#1,AC1
	BCLR	AC1,*(#CPLD)
jump12:

	;led2
	;MOV		#2,AC1
	;BSET	AC1,*(#CPLD)
	MOV AC3,AC2
	SUB #60,AC2
	BCC jump2, AC2>#0
	MOV		#2,AC1
	BSET	AC1,*(#CPLD)
jump2:	
	BCC jump22, AC2<=#0
	MOV		#2,AC1
	BCLR	AC1,*(#CPLD)
jump22:

	;led3
	;MOV		#3,AC1
	;BSET	AC1,*(#CPLD)
	MOV AC3,AC2
	SUB #30,AC2
	BCC jump3, AC2>#0
	MOV		#3,AC1
	BSET	AC1,*(#CPLD)
jump3:	
	BCC jump32, AC2<=#0
	MOV		#3,AC1
	BCLR	AC1,*(#CPLD)
jump32:

;	Mikistä luettu kaiku + muut äänet huoneessa
;   löytyy inptr:n osoittamasta muistialueesta.
;   Tästä puskurista tulee etsiä esimerkiksi
;   voimakkain piikki, joka on oletettavasti
;   suora kaiku lähimmästä kohteesta. 

;   Puskurin pituus on BUFFERSIZE.
;
;   Kaiun aiheuttaneen esineen etäisyyden perusteella
;   sytytetään tai sammutetaan kortin led-valoja.
;   esim: 
;   MOV		#0,AC1
;	BSET	AC1,*(#CPLD)
;	Sytyttää ledin numero 0.
	RET
	
	
	.ref timer0div
	.def start_write_and_process

start_write_and_process: ;toistaa purskeen ja prosessoi aiemmin
						 ;luetun datan inptr:ssä (kutsumalla searchEchoa).

	MOV		#0011100011001010b, port(#DMA_CCR0); kirjoitus AIC:lle
	CALL 	startTIMER1; käynnistää ajastimen 1
	CALL 	searchEcho; käsittelee inptr:ssä olevan datan ja sytyttää ledit
	RET

	.def start_read
start_read: ;lukee inptr:n osoittaman puskurin täyteen näytteitä mikistä
	MOV		#inptr, T0
	CALL 	destinationAddressDMA1
	MOV		#1100100011001001b, port(#DMA_CCR1); luku AIC:lta
	RET

;--- Sini purske - 	
	.def sine_burst
sine_burst:
	MOV 	#SINE_BURST_SIZE, BK03
	BSET 	AR0LC;	Rengasosoitteistus
	AMOV 	#0x000000, XAR0
	MOV  	#SINE_BURST, BSA01; 
	AMOV	#outptr, XAR2

	RPT		#BURST_LENGTH
			MOV		*AR0+,*AR2+

	BCLR	AR0LC; Lineaariosoitteistus
	RET
;---

fillzeros:
	AMOV	#outptr, XAR2
	RPT		#BUFFERSIZE
			MOV		#0,*AR2+
	RET

;---- Alustaa koodekin ja tarvittavat sarjaportit (McBSP 1 & 2)
initAIC23:
	CALL initMCBSP1; konfiguroidaan kontrolliportti
	CALL initMCBSP2; konfiguroidaan dataportti

	CALL	waitXRDY1; Odotetaan kunnes McBSP1 valmis siirtämään
	MOV		#0001111000000000b, port(#DXR11); Kirjoitetaan
	;---- IN VOLUME
	CALL	waitXRDY1
	MOV		#0000000000011111b, port(#DXR11); vasen
	CALL	waitXRDY1
	MOV		#0000001000011111b, port(#DXR11); oikea
	;---- HEADPHONE VOLUME 
	CALL	waitXRDY1
	MOV		#0000010111111001b, port(#DXR11); vasen
	CALL	waitXRDY1
	MOV		#0000011111111001b, port(#DXR11); oikea
	;---- ANALOG AUDIO PATH
	CALL	waitXRDY1
	MOV		#0000100000010101b, port(#DXR11); mikkisisääntulo
	;---- DIGITAL AUDIO PATH
	CALL	waitXRDY1
	MOV		#0000101000000000b, port(#DXR11) 
	;---- POWERDOWN 
	CALL	waitXRDY1
	MOV		#0000110100000000b, port(#DXR11)
	;---- DIGITAL AUDIO INTERFACE
	CALL	waitXRDY1
	MOV		#0000111001000011b, port(#DXR11)
	;---- SAMPLE RATE
	CALL	waitXRDY1
	MOV		#0001000000100011b, port(#DXR11); 44.1kHz näytteetottotaajuus
	;---- INTERFACE ACTIVATION
	CALL	waitXRDY1
	MOV		#0001001000000001b, port(#DXR11); aktivoidaan AIC23
	RET
;----------


