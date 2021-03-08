
/**
 * Title:      		Harjoitus 20
 * Description:		Anagrammi generaattori. 
 					Tee ohjelma joka muokkaa antamastasi tekstist‰ 20 anagrammia.
 					
 					vihje: sivu 68, String luokan metodeja.(length(), toCharArray())
 * Copyright:    	Copyright (c) 2002
 * Company:
 * @author Mika Saari
 * @version 0.1
 */
 public class harjoitus20{
 	public static void main(String[] args){
 		System.out.println("Anna kymmenen merkkinen sana! ");
 		String nimi=Lue.rivi();
 		char[] jono=nimi.toCharArray();//Talletetaan String char-taulukkoon 
 		int pit=nimi.length();//Selvitet‰‰n Stringin pituus
 		char[] jono2=new char[pit];
		int[] tukitaulu=new int[pit];
		System.out.println("10 Anagrammia antamastasi sanasta on :");
		for(int i=0;i<10;i++){//koska haluuttiin 10 anagrammia, niin on silmukka suoritettava 10 kertaa
			for(int j=0;j<pit;j++){
				int satluku=(int)(pit*Math.random());//arvotaan seuraavan kirjaimen indeksi
				if(tukitaulu[satluku]==1){//Onko saatu indeksi jo k‰ytetty
					j--;
				}
				else{
					tukitaulu[satluku]=1;
					jono2[j]=jono[satluku];
				}
			}
			//tulostetaan anagrammi
 			for(int k=0;k<pit;k++){
 				System.out.print(jono2[k]);
 				tukitaulu[k]=0;
 			}
 			System.out.println(" ");		
 		}
 	}
}		
 	