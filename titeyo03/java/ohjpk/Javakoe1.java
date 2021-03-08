/**
 * Title:      		Koetehtävä 1
 * Description:		Laadi ohjelma, joka osaa etsiä syöttämästäsi tekstistä, 
 *					montako kertaa kysytty merkki siinä esiintyy. Samasta
 *					tekstistä halutaan tehdä useita kyselyjä.
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
		//Syöttämästäsi tekstistä
		System.out.println("Anna rivi");
		String lause=Lue.rivi();
		//tekstistä halutaan tehdä useita kyselyjä
		while(jatko!='e'){
			//kysytty merkki
			System.out.println("Anna etsittävä merkki ");
			merkki=Lue.merkki();
			//montako kertaa
			for(int i=0;i<lause.length();i++){
				if(lause.charAt(i)==merkki){
					lkm++;
				}
			}
			//esiintyy
			System.out.println("Merkkejä löytyi:"+ lkm);
			
			//jotta kaikkia merkkejä voi etsiä niin lopetusmerkki on erotettava etsinnästä
			System.out.println("Haluatko jatkaa k/e");
			jatko=Lue.merkki();
			
			lkm=0;
		}
	}
}
