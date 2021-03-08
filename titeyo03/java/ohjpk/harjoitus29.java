/**
 * Title:      		Harjoitus 29
 * Description:		Laadi ohjelma, joka laskee/tulostaa annetusta luvusta kertoman
 					k‰ytt‰en rekursiota.
 
 					Vihje:  Kertoma n! on m‰‰rittelty 
					luonnollisille luvuille seuravasti: 0! = 1 ja n! = n*(n-1)!, mik‰li n > 0.
					Sivulla 127 on fibotesti ohjelma , josta voi ottaa mallia. 
 					(Tosin kertoman ratkaisu on helpompi)
 * Copyright:    	Copyright (c) 2003
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
 public class harjoitus29{
 	public static void main(String []args){
 		System.out.println("Anna luku");
 		int luku = Lue.kluku();
 		System.out.println("Luvun " + luku + " kertoma on " + kertoma(luku));
 	}
 	//Rekursiota k‰ytt‰v‰ kertoma-metodi, jota kutsutaan p‰‰ohjelmasta vain kerran 
	private static int kertoma(int luku) {
    	if (luku <= 1) 
        	return 1;
		else 
			return luku*kertoma(luku-1);
	}
 }