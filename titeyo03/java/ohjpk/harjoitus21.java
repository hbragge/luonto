
/**
 * Title:      		Harjoitus 21
 * Description:		Merkkijonon k�sittely�
 					Tee ohjelma joka muokkaa lausetta "TERVE sulle" seuraavasti:
 					
 					a)tulostaa kaikki pieniksi kirjaimiksi
 					b)tulostaa kaikki isoiksi kirjaimiksi
 					c)poistaa v�lily�nnin sanojen v�list�
 					d)tulostaa tekstin k��nteisen�
 					
 					vihje: monisteen merkkijonometodit
 * Copyright:    	Copyright (c) 2003
 * Company:
 * @author Mika Saari
 * @version 0.1
 */
public class harjoitus21{
	public static void main(String []args){
		String moi="TERVE sulle";
		//Tulostetataan pienin�, isoina ja ilman  v�lily�nti�
		System.out.println(moi.toLowerCase() +"\n" +moi.toUpperCase() +"\n" +moi.substring(0,5)+moi.substring(6,11));
		//Tulostetaan k��nteisess� j�rjestyksess�
		for(int i=(moi.length()-1);i>=0;i--)
			System.out.print(moi.charAt(i));
	}
}