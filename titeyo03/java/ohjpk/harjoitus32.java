/**
 * Title:      		Harjoitus 32
 * Description:		Laadi peruskoululaiselle kertolaskun harjoitusohjelma. 
 					Ohjelma satunnaisesti laatii kahden luvun v‰lisenkertoteht‰v‰n ja 
 					laskee oikean vastauksen. Ohjelma n‰ytt‰‰ teht‰v‰n, 
 					mutta ei tietenk‰‰n oikeaa vastausta. Kun k‰ytt‰j‰ antaa vastauksen, 
 					vertaa ohjelma sit‰ oikeaanvastaukseen ja antaa k‰ytt‰j‰lle tiedon onnistumisesta.
 					K‰ytt‰j‰ voi harjoitella kertolaskuja haluamansa ajan ja lopetettuaan, 
 					ohjelma kertoo montako teht‰v‰‰ kaikkiaan suoritettiin ja montako niist‰ oli oikein.
 					Lopetuksen testaamista varten tee metodi, joka kysyy haluaako k‰ytt‰j‰ lopettaa ja 
 					se ymm‰rt‰‰ mahdollisimman hyvink‰ytt‰j‰n vastausta
 					(Esim KYLLƒ, kyll‰, k,K, EI,ei,E,e)sek‰ palauttaa totuusarvon.
 * Copyright:    	Copyright (c) 2003
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
 //Harjoitus31 -luokka joka sis‰lt‰‰ main ja tulostus-metodit
 public class harjoitus32{
 	public static void main(String []args){
 		int oikein=0, kaikki=0;
 		int eka=0, toka=0, tulos=0;
 		boolean totuus=true;
 		System.out.println("Kertolaskuohjelma v1.0");
 		while(totuus){
 			//Arvotaan lukuja 0-10
 			eka=(int)(10*Math.random());
 			toka=(int)(10*Math.random());
 			System.out.println("Paljonko on " +eka +" x " +toka );
 			tulos=Lue.kluku();
 			if(tulos==(eka*toka)){
 				oikein++;
 				System.out.println("Oikein");
 			}
 			else
 				System.out.println("V‰‰rin! Oikea vastaus on " +(eka*toka));
 			System.out.println("Haluatko uuden teht‰v‰n");
 			kaikki++;
 			//Kutsutaan lopetuksen testausta
 			totuus=lopeta();
 		}
 		System.out.println("Laskit " +kaikki +" teht‰v‰‰, joista oikein oli " +oikein +" teht‰v‰‰.");
 	}
 	private static boolean lopeta(){
 		boolean totuus=true;
 		String vastaus=Lue.rivi();
 		//Testataan t‰ll‰kertaa vain oikein osunut lopetus komento
 		if (vastaus.equals( "E") || 
 			vastaus.equals( "e") || 
 			vastaus.equals("EI") || 
 			vastaus.equals( "ei"))
 			return false;
 		else
 			return true;
 	}
 }
 		
 	