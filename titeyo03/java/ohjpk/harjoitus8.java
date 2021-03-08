/**
 * Title:      		Harjoitus 8
 * Description:		Laadi ohjelma, joka laskee sy�tetylle palkkatiedolle annetun 
 					prosentin suuruisen palkankorotuksen.
 					Tulostaa alkuper�isen palkan, uuden palkan ja palkkojen v�lisen eron euroissa.
 					T�m�n j�lkeen kysyt��n seuraava palkkatieto ja lasketaan sek� tulostetaan kuten edell�.
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
 		//while silmukka py�rii kunnes palkaksi annetaan 0 
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