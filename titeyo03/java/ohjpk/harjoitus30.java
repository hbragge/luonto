/**
 * Title:      		Harjoitus 30
 * Description:		Tee er‰p‰iv‰n laskeva metodi, jolla parametreina kuluvan vuoden
 					p‰iv‰m‰‰r‰‰ kuvaava merkkijono(esim 11.7.) ja maksuaika p‰iviss‰.
 					Ohjelma palauttaa er‰p‰iv‰n merkkijonona. Vuosilukua p‰iv‰m‰‰riss‰ ei
 					tarvi huomioida, mutta joulukuun j‰lkeen tulee tietenkin tammikuu.
 					Karkausvuosia ei tarvi huomioida.
 * Copyright:    	Copyright (c) 2003
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
 public class harjoitus30{
 	//Tarvitaan tietoa, ett‰ montako p‰iv‰‰ kuussa on
 	public static int paivat[]={31,28,31,30,31,30,31,31,30,31,30,31};
 	//Main-metodi jolla testataan ohjelmaa
 	public static void main(String []args){
 		System.out.println("Anna laskutus pvm:");
 		String nyt=Lue.rivi();
 		System.out.println("Anna maksuaika");
 		int aika=Lue.kluku();
 		System.out.println("Er‰p‰iv‰ on " +eraPaiva(nyt, aika ));
 	}
 	
 	//T‰ss‰ eraPaivametodi jota teht‰v‰nannossa pyydettiin
 	public static String eraPaiva(String nyt, int aika){
 		//Erotetaan paivat
 		int paiva=Integer.parseInt(nyt.substring(0,2));
 		//Erotetaan kuukausi
 		int kuu=(Integer.parseInt(nyt.substring(3,5)))-1;		
 		int erapaiva=0;
 		erapaiva=paiva+aika;
 		//Lasketaan kuukausia eteenp‰in kunnes p‰iv‰m‰‰r‰ on pienempi tai
 		//yht‰suuri kuin kuukauden viimeinen p‰iv‰
 		while(paivat[kuu]<=erapaiva){
 			erapaiva-=paivat[kuu];
 			if(kuu<11)
 				kuu++;
 			else
 				kuu=0;
 		}
 		//Palauttaa p‰iv‰m‰‰r‰n, mutta ei osaa lis‰t‰ tarvittaessa nollaa numeron eteen
 		return (erapaiva +"." +(kuu+1));
 	}
 }