//Tulostaa luvun itseisarvon k�ytt�en abs-metodia luokasta Math
//Markku Nevanranta

import java.lang.Math;// L�yt�� lang-luokat ilman t�t� rivi�

public class Itseisarvo1{
	public static void main(String[] args){
		int luku;	
	
		System.out.println("Anna luku: ");	
		luku = Lue.kluku();
		
		System.out.println(Math.abs(luku));	
			
	}	
}