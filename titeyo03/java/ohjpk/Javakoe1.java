/**
 * Title:      		Koeteht�v� 1
 * Description:		Laadi ohjelma, joka osaa etsi� sy�tt�m�st�si tekstist�, 
 *					montako kertaa kysytty merkki siin� esiintyy. Samasta
 *					tekstist� halutaan tehd� useita kyselyj�.
 * Copyright:    	Copyright (c) 2002
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
public class Javakoe1{
	public static void main(String args[]) {
		int lkm=0;
		char merkki=' ';
		char jatko='k';
		//Sy�tt�m�st�si tekstist�
		System.out.println("Anna rivi");
		String lause=Lue.rivi();
		//tekstist� halutaan tehd� useita kyselyj�
		while(jatko!='e'){
			//kysytty merkki
			System.out.println("Anna etsitt�v� merkki ");
			merkki=Lue.merkki();
			//montako kertaa
			for(int i=0;i<lause.length();i++){
				if(lause.charAt(i)==merkki){
					lkm++;
				}
			}
			//esiintyy
			System.out.println("Merkkej� l�ytyi:"+ lkm);
			
			//jotta kaikkia merkkej� voi etsi� niin lopetusmerkki on erotettava etsinn�st�
			System.out.println("Haluatko jatkaa k/e");
			jatko=Lue.merkki();
			
			lkm=0;
		}
	}
}
