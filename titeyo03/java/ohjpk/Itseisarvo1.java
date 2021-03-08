//Tulostaa luvun itseisarvon käyttäen abs-metodia luokasta Math
//Markku Nevanranta

import java.lang.Math;// Löytää lang-luokat ilman tätä riviä

public class Itseisarvo1{
	public static void main(String[] args){
		int luku;	
	
		System.out.println("Anna luku: ");	
		luku = Lue.kluku();
		
		System.out.println(Math.abs(luku));	
			
	}	
}