/**
 * Title:      		Koeteht‰v‰ 4
 * Description:		Ohjelmasi hallitsee salasanan tarkistuksen ja muuttamisen. 
 *					Tietyll‰ p‰‰k‰ytt‰j‰n salasanalla ohjelma antaa muuttaa 
 *					perusk‰ytt‰j‰n salasanaa. Perusk‰ytt‰j‰n salasanaa saa
 *					yritt‰‰ antaa kolme kertaa. Kolmen kerran j‰lkeen ohjelma
 *					ilmoittaa ep‰onnistumisesta ja lopettaa toimintansa. 
 *					P‰‰k‰ytt‰j‰n salasana on kiinte‰sti annettu ohjelmassa.
 * Copyright:    	Copyright (c) 2003
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
public class javakoe4{
	public static void main(String args[]) {
		String arvaus;
		String psala="moi";//P‰‰salasana
		String sala="terve";
		//Tietyll‰ p‰‰k‰ytt‰j‰n salasanalla ohjelma antaa muuttaa perusk‰ytt‰j‰n salasanaa.
		System.out.println("Anna p‰‰salasana");
		arvaus=Lue.rivi();
		if(arvaus.equals(psala)){
			System.out.println("Oikein. voit vaihtaa perusk‰ytt‰j‰n salasanan");
			System.out.println("anna uusi salasana:");
			sala=Lue.rivi();
		}
		else
			System.out.println("V‰‰rin!");
		int arv=0;
		//Perusk‰ytt‰j‰n salasanaa saa yritt‰‰ antaa kolme kertaa.
		while(arv<3){
			System.out.println("anna perusk‰ytt‰j‰n salasana");
			arvaus=Lue.rivi();
			if(sala.equals(arvaus)){
				System.out.println("oikein");
				System.out.println("Olet kirjautunut j‰rjestelm‰‰n");
				break;
				
			}
			arv++;
		}
		//Kolmen kerran j‰lkeen ohjelma ilmoittaa ep‰onnistumisesta ja lopettaa toimintansa.
		if(arv==3)
			System.out.println("Et tied‰ salasanaa! Poistutaan ohjelmasta...");
	}
}
			
		
		