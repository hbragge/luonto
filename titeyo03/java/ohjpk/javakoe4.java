/**
 * Title:      		Koetehtävä 4
 * Description:		Ohjelmasi hallitsee salasanan tarkistuksen ja muuttamisen. 
 *					Tietyllä pääkäyttäjän salasanalla ohjelma antaa muuttaa 
 *					peruskäyttäjän salasanaa. Peruskäyttäjän salasanaa saa
 *					yrittää antaa kolme kertaa. Kolmen kerran jälkeen ohjelma
 *					ilmoittaa epäonnistumisesta ja lopettaa toimintansa. 
 *					Pääkäyttäjän salasana on kiinteästi annettu ohjelmassa.
 * Copyright:    	Copyright (c) 2003
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
public class javakoe4{
	public static void main(String args[]) {
		String arvaus;
		String psala="moi";//Pääsalasana
		String sala="terve";
		//Tietyllä pääkäyttäjän salasanalla ohjelma antaa muuttaa peruskäyttäjän salasanaa.
		System.out.println("Anna pääsalasana");
		arvaus=Lue.rivi();
		if(arvaus.equals(psala)){
			System.out.println("Oikein. voit vaihtaa peruskäyttäjän salasanan");
			System.out.println("anna uusi salasana:");
			sala=Lue.rivi();
		}
		else
			System.out.println("Väärin!");
		int arv=0;
		//Peruskäyttäjän salasanaa saa yrittää antaa kolme kertaa.
		while(arv<3){
			System.out.println("anna peruskäyttäjän salasana");
			arvaus=Lue.rivi();
			if(sala.equals(arvaus)){
				System.out.println("oikein");
				System.out.println("Olet kirjautunut järjestelmään");
				break;
				
			}
			arv++;
		}
		//Kolmen kerran jälkeen ohjelma ilmoittaa epäonnistumisesta ja lopettaa toimintansa.
		if(arv==3)
			System.out.println("Et tiedä salasanaa! Poistutaan ohjelmasta...");
	}
}
			
		
		