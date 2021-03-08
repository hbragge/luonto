#include "dsk5510.h"
#include "dsk5510_aic23.h"
#define n 20000
#define h 3

void main(void)
{
	Int16 data;
	static Int16 puskuri[n];
	static Int16 y=5550;
	static Int16 x=0;
	static Int16 z=0;
	
	static DSK5510_AIC23_Config cfg = { 
	    0x0017,  /* 0 DSK5510_AIC23_LEFTINVOL  Left line input channel volume */   
	    0x0017,  /* 1 DSK5510_AIC23_RIGHTINVOL Right line input channel volume */
	    0x01f9,  /* 2 DSK5510_AIC23_LEFTHPVOL  Left channel headphone volume */
	    0x01f9,  /* 3 DSK5510_AIC23_RIGHTHPVOL Right channel headphone volume */  
	    0x0015,  /* 4 DSK5510_AIC23_ANAPATH    Analog audio path control */  
	    0x0000,  /* 5 DSK5510_AIC23_DIGPATH    Digital audio path control */  
	    0x0000,  /* 6 DSK5510_AIC23_POWERDOWN  Power down control */  
    	0x0043,  /* 7 DSK5510_AIC23_DIGIF      Digital audio interface format */  
    	0x0023,  /* 8 DSK5510_AIC23_SAMPLERATE Sample rate control */  
	    0x0001   /* 9 DSK5510_AIC23_DIGACT     Digital interface activation */  
	};
	
	DSK5510_AIC23_CodecHandle hndl;

	DSK5510_init(); 

	hndl = DSK5510_AIC23_openCodec(0,	&cfg); 

	
	
	while(1){
	
	while(!DSK5510_AIC23_read16(hndl, &data));
	while(!DSK5510_AIC23_read16(hndl, &data));
	puskuri[x]=data;
	puskuri[x+1]=data;
	x = (x+2)%n;
	
	z = (z+h)%n;		// toistetaan ‰‰ni muuttujan h m‰‰r‰‰m‰ll‰ korkeuden s‰‰dˆll‰
	y = (y+h)%n;		// toistetaan kaiku vakion y m‰‰r‰‰m‰ll‰ viiveell‰
	while(!DSK5510_AIC23_write16(hndl, puskuri[z]));	// kirjoitetaan puskurin arvo kohdassa z AIC:hen handlerin avaaman yhteyden kautta
	while(!DSK5510_AIC23_write16(hndl, puskuri[y]));	// kirjoitetaan puskurin arvo kohdassa y AIC:hen handlerin avaaman yhteyden kautta
	
	
}
}
