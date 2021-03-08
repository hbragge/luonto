

#include <90s2313.h>
#include "exb2313.h"
#include <delay.h>
#include <stdio.h>

#asm
   .equ __lcd_port=0x18;PORTB  
#endasm
#include <lcd.h>

char buff[16];   
int set, laskuriss, laskurids,i; 

/* nollausfunktio nollaa asetuksen sek‰ laskurin (sekuntit ja desimaalit)
 */

void nollaa(){         
	set = 0; 
    	laskuriss = 0;
  	laskurids = 0;
  	lcd_gotoxy(0,0);
    	sprintf(buff, "%03i:", laskuriss);
    	lcd_puts(buff);
    	lcd_gotoxy(4,0);
    	sprintf(buff, "%i ", laskurids);
    	lcd_puts(buff);
  	}  

/* pys‰ytysfunktio tulostaa n‰ytˆlle "Stopped", ja j‰‰ ikuiseen looppiin,
 * kunnes S3:sta painetaan (uudestaan). 
 * Jos S4 on painettu alas, niin kutsutaan nollaa() -funktiota.
 */

void pysaa(){ 
 	          
	lcd_gotoxy(6,1);
    	sprintf(buff, "Stopped");     
    	lcd_puts(buff);
      	while(1){ 
      		delay_ms(500); 
      		if (!S4) {
      			nollaa();
      	       		}
        	if (!S3){ 
        	lcd_gotoxy(6,1);
        	sprintf(buff, "       ");
        	lcd_puts(buff);  
        	break;
        	}   
      	} 
} 	

// main -metodi

void main(void)
{
  
  
  PORTB=0xFF;        // Portin B alustus
  DDRB=0xFF;

  PORTD=0x7C;        // Portin D alustus
  DDRD=0x40;
  
  ACSR=0x80;         // Analoginen vertailija pois p‰‰lt‰

  lcd_init(16);      // LCD-modulin alustus
 
  
  lcd_clear(); 
  
  set = 0;           // alkuarvot muuttujille  
  laskuriss = 0;
  laskurids = 0;
  
  while (1)
  { 
    laskuriss = laskuriss%9999;	       // modulon avulla huolehditaan laskurin nollaus
    				      // kun se ylitt‰‰ maksimin
    laskuriss++;                             
    lcd_gotoxy(0,0);
    sprintf(buff, "%03i:", laskuriss);
    
    lcd_puts(buff);                    // lcd:lle tulostus

    lcd_gotoxy(6,0);
    sprintf(buff, "Set : %03i", set);
    lcd_puts(buff);

               
    
    if (!S1)          // n‰pp‰imill‰ voidaan lis‰t‰
      set--;         // tai v‰hent‰‰ as1:n ja as2:n
    if (!S2)        // arvoja
      set++;
    if (!S3){
    	pysaa();
      	}
    if (!S4){
    	nollaa();
  	}
  	  
    for(i = 0; i<10;i++){
    	delay_ms(100);              // 100ms viive sekunnin kymmenesosien tulostusta varten
    	laskurids = laskurids%9; 
    	
    	
    	lcd_gotoxy(4,0);
    	sprintf(buff, "%i ", laskurids);
    	lcd_puts(buff);
    	laskurids++;
    	}            
    }
}


