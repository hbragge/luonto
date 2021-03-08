
/**
 * Title:      		Harjoitus 23
 * Description:		Tarkista henkil�tunnuksen oikeellisuus. Tulosta my�s oikea tarkistusmerkki.
			Vihje: Sy�tt� esimerkiksi 120456-195P.
			Tarkistusmerkki m��r�ytyy jakoj��nn�ksen (120456195%31) 
			mukaan seuraavasta merkkilistasta:
			0123456789ABCDEFHJKLMNPRSTUVWXY
 * Copyright:    	Copyright (c) 2003
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
 public class harjoitus23{
 	public static void main(String []args){
 		//Luodaan tarkistusmerkkitaulu
 		char tarkistus[]={'0','1','2','3','4','5','6','7','8','9',
 						'A','B','C','D','E','F','H','J','K','L',
 						'M','N','P','R','S','T','U','V','W','X','Y'};
 		String tunnus;
 		int luku2=0;
 		//j on apuna kun char:ia muutetaan int:ksi
 		int j=100000000;
 		System.out.println("Anna Henkil�tunnus:");
 		tunnus=Lue.rivi();
 		for(int i=0;i<10;i++){
 			if(i!=6){ 
 				//Character-luokasta getNumericValue-metodi jolla char->int muunnos tehd��n. 
 				//Onko muita tapoja?				
 				luku2=luku2+((java.lang.Character.getNumericValue(tunnus.charAt(i)))*j);
 				j=j/10;
 			}
 		}
 		//tarkistus jakoj��nn�ksen avulla. HUOM. huomioi vain isot kirjaimet.
 		if(tunnus.charAt(10)==tarkistus[luku2%31])
 			System.out.println("Oikein!");
 		System.out.println("Tarkistusmerkkisi on: " +tarkistus[luku2%31]);
 	}
 }
 			
 			