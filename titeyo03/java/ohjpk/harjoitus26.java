/**
 * Title:      		Harjoitus 26
 * Description:		Laadi salakirjoitusohjelma, jossa on salaus ja purku-metodit.
 					
 					Tämän ohjelman toiminta perustuu char merkkien unicode-taulukkoon.
 					Eli char muuttuja ilmoitetaan int:nä ilman että se muutetaan int:ksi
 * Copyright:    	Copyright (c) 2003
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
 public class harjoitus26{
 	//Luodaan koodausavain-taulu(Tämä ei ole ROT13-salaus)
 	private static int koodiAvain[]={1,5,3,7,9,3,7,4,6,2,5,9,2,3,6,4,5,2,7,9,4};
 	public static void main(String []args){
 		salaa();
 		pura();
 	}
 	
 	public static void salaa(){
 		System.out.println("Anna avaimen pituus");
 		int avain=Lue.kluku();
 		int taulu[]=new int[avain];
 		String lause;
 		//kopioidaan koodiavainta haluttu pätkä
 		for(int i=0;i<avain;i++)
 			taulu[i]=koodiAvain[i];
 		System.out.println("Anna lause!");
 		lause=Lue.rivi();
 		int j=0;
 		//Käydään annettu lause merkki merkiltä läpi
 		while(j<lause.length()){
 			for(int k=0;k<avain;k++){
 				if(j<lause.length()){//ilman if:iä tulee virhe
 					//Talletetaan char int muuttujaan
	 				int apu=lause.charAt(j);	
	 				//Otetaan välilyönnit erikoiskäsittelyyn
	 				if (apu==32)
	 					apu=67;
	 				//Salaus-algoritmi, 
	 				//joka laskee jokaiselle merkille uuden arvon avaimen perusteella
	 				apu-=taulu[k];
	 				//Tulostetaan int-muuttujan arvo char:ina
	 				System.out.print((char)apu);
	 				j++;
 				}
 				else 
 				break;
 			}
 		}
 		System.out.println("");
 	}
 	//Sama toisinpäin
 	public static void pura(){
		System.out.println("Anna avaimen pituus");
		int avain=Lue.kluku();
		int taulu[]=new int[avain];
		for(int i=0;i<avain;i++)
			taulu[i]=koodiAvain[i];
		System.out.println("Anna lause!");
		String koodattu=Lue.rivi();
		int j=0;
		while(j<koodattu.length()){
			for(int k=0;k<avain;k++){
				if(j<koodattu.length()){
					int apu=koodattu.charAt(j);	
					//purku-algoritmi lisää jokaiseen int arvoon koodiaAvain muuttujan arvon
					apu+=taulu[k];
					if (apu==67)//Välilyönnit käsitellään erikseen
						apu=32;
					System.out.print((char)apu);
					j++;
				}
				else 
					break;
			}
 		}
 		System.out.println("");
 	}
 }