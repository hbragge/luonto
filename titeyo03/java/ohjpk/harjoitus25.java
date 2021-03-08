/**
 * Title:      		Harjoitus 25
 * Description:		Tuntilaskuri. Tee laskin joka ilmoittaa Java harjoituksiin 
 					k‰ytt‰m‰si ajan p‰ivin‰, tunteina ja minuutteina viikon aikana.
 					Ohjelma kysyy k‰ytetyn ajan joka viikonp‰iv‰lt‰. 
 					Ja ottaa vastaan ajan tunteina ja minuutteina esim. 2.45.
 * Copyright:    	Copyright (c) 2003
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
 public class harjoitus25{
 	public static void main(String []args){
 		int min=0;
 		int h=0;
 		int d=0;
 		String aika;
 		String viikonPaivat[]={"Maanantai",
 								"Tiistai",
 								"Keskiviikko",
 								"Torstai",
 								"Perjantai",
 								"Lauantai",
 								"Sunnuntai"};
 		//For-silmukalla k‰yd‰‰n l‰pi jokainen viikonp‰iv‰
 		for(int i=0;i<7;i++){
	 		try{ //Virheen korjausta, mik‰li esim lyˆt tyhj‰n enterin
	 			System.out.println("Anna " +viikonPaivat[i] 
	 								+"na java harjoituksiin k‰ytt‰m‰si aika muodossa hh:mm ");
	 			aika=Lue.rivi();
	 			//String muutetaan int:ksi
				h+=Integer.parseInt(aika.substring(0, 2));
	 			min+=Integer.parseInt(aika.substring(3, 5));
	 		}catch (Exception e) {//Virhe ilmoitus tyhj‰n enterin lyˆnnist‰
	 			System.out.println("T‰t‰ syˆttˆ‰ ei lasketa");
	 		}//Virhe loppuu
 		}
 		//Lasketaan sopivat minuuttiarvot 
 		if(min>60){
 			h+=min/60;
 			min=min%60;
 		}
 		//Lasketaan sopivat tunti ja p‰iv‰-arvot
 		if(h>24){
 			d=h/24;
 			h=h%24;
 		} 			
 		System.out.println("Olet tyˆskennellyt viikon aikana java harjoitusten parissa "
 							+d +" p‰iv‰‰ " +h +" tuntia " +min +" minuuttia");
 		if(d>1)System.out.println(" Onnittelen!");
 	}
 }