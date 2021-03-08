
/**
 * Title:      		Harjoitus 12
 * Description:		Laadi ohjelma joka kysyy lukuja ja tulostaa jokaisesta 
 *					annetusta kokonaisluvusta seuraavat tiedot:
 *
 *					a) Onko luku suurempi kuin 5 
 *					b) Onko luku jaollinen kymmenellä 
 *					c) Onko luku suurempi kuin edellinen luku 
 *					d) Lukujen keskiarvo tarkasti 				
 * Copyright:    	Copyright (c) 2003
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
 public class harjoitus12{
 	public static void main(String []args){
 		int luku=0;// Tähän talletetaan kysyttävä luku
 		int tulos=0;//Tämän avulla lasketaan lukujen keskiarvo
 		int edellinen=0;//Muuttuja johon edellisen kierroksen luku talletetaan
 		//luetaan viisilukua, joista annetaan tietoa käyttäjälle
 		for(int i=1;i<=5;i++){
 			System.out.println("Anna jokin luku");
 			edellinen=luku;
 			luku=Lue.kluku();
 			tulos=tulos+luku;
 			//if - else rakenteella toteutettuja vertailuja 
 			if(luku<5)
 				System.out.println(luku +" on pienempi kuin 5");
 			else
 				System.out.println(luku +" on suurempi tai yhtasuuri kuin 5");
 			
 			if(luku%10==0)
 				System.out.println(luku +" on jaollinen kymmenellä");
 			else
 				System.out.println(luku +" ei ole jaollinen kymmenellä");
 			
 			if(luku<edellinen)
 				System.out.println(luku +" on pienempi kuin edellinen luku " +edellinen);
 			else
 				System.out.println(luku +" on suurempi kuin edellinen luku " +edellinen);
 			
 			
 			
 		}
 		//(double) -määrittelyllä muutetaan int muuttuja double muuttujaksi
		System.out.print("Antamasi lukujen keskiarvo on: " +((double) tulos)/5);
 		
 	}
 }