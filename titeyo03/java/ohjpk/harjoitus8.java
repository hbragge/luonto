/**
 * Title:      		Harjoitus 8
 * Description:		Laadi ohjelma, joka laskee syötetylle palkkatiedolle annetun 
 					prosentin suuruisen palkankorotuksen.
 					Tulostaa alkuperäisen palkan, uuden palkan ja palkkojen välisen eron euroissa.
 					Tämän jälkeen kysytään seuraava palkkatieto ja lasketaan sekä tulostetaan kuten edellä.
 * Copyright:    	Copyright (c) 2002
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
 public class harjoitus8{
 	public static void main(String []args){
 		double palkka=1;
 		double prosentti=0;
 		System.out.println("Anna palkka! ");
 		palkka=Lue.dluku();
 		//while silmukka pyörii kunnes palkaksi annetaan 0 
 		while(palkka!=0){
 			System.out.println("Anna korotus %! ");
 			prosentti=Lue.dluku();
 			System.out.println("Vanha palkkasi on " +palkka);
 			System.out.println("Uusi palkkasi on " +(palkka+(palkka*(prosentti/100))));
			System.out.println("joka on " +((palkka+(palkka*(prosentti/100)))-palkka) 
								+" euroa suurempi kuin vanha palkkasi");
			System.out.println("(lopetus 0)");
			System.out.println("Anna palkka! ");
			palkka=Lue.dluku();
		}
 	}
 }