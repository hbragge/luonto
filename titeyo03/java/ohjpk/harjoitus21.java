
/**
 * Title:      		Harjoitus 21
 * Description:		Merkkijonon käsittelyä
 					Tee ohjelma joka muokkaa lausetta "TERVE sulle" seuraavasti:
 					
 					a)tulostaa kaikki pieniksi kirjaimiksi
 					b)tulostaa kaikki isoiksi kirjaimiksi
 					c)poistaa välilyönnin sanojen välistä
 					d)tulostaa tekstin käänteisenä
 					
 					vihje: monisteen merkkijonometodit
 * Copyright:    	Copyright (c) 2003
 * Company:
 * @author Mika Saari
 * @version 0.1
 */
public class harjoitus21{
	public static void main(String []args){
		String moi="TERVE sulle";
		//Tulostetataan pieninä, isoina ja ilman  välilyöntiä
		System.out.println(moi.toLowerCase() +"\n" +moi.toUpperCase() +"\n" +moi.substring(0,5)+moi.substring(6,11));
		//Tulostetaan käänteisessä järjestyksessä
		for(int i=(moi.length()-1);i>=0;i--)
			System.out.print(moi.charAt(i));
	}
}