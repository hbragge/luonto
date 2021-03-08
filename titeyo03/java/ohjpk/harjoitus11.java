
/**
 * Title:      		Harjoitus 11
 * Description:		Tee ohjelma joka kysyy kolme lukua, summaa ne keskenään ja tulostaa vastauksen.
 * Copyright:    	Copyright (c) 2003
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
 public class harjoitus11{
 	public static void main(String []args){
 		//Muuttujat luku1, luku2, luku3 ja summa esitellään vasta kun niitä tarvitaan.
 		//Muuttujat voitaisiin esitellä myös koodin alussa tyyliin:
 		//int luku1=0, luku2=0, luku3=0, summa=0; 
 		
 		System.out.println("Anna eka luku");
 		int luku1=Lue.kluku();//Lue -luokan avulla luetaan näppäimistöltä luku muuttujaan luku1
 		System.out.println("Anna toka luku");
 		int luku2=Lue.kluku();
 		System.out.println("Anna kolmas luku");
 		int luku3=Lue.kluku();
 		int summa=luku1+luku2+luku3;
 		//Tulostetaan merkkijono "Lukujen summa on " ja muuttujan summa sisältämä arvo 
 		System.out.println("Lukujen summa on " +summa); 		
 	}
 }