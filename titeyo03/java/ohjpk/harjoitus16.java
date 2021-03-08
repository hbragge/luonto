/**
 * Title:      	Harjoitus 16
 * Description:	Arvontakone
			Toteuta tietokonepeli, joka arpoo jonkin kokonaisluvun 0,1,2..9. 
			K‰ytt‰j‰ saa yritt‰‰ korkeintaan kolme kertaa arvata koneen arpoman luvun.
			Jos arvaa ensimm‰isell‰ voittaa 30 euroa, jos toisella voittaa 20 euroa ja kolmannella 10 euroa
			Jos ei arvaa kolmannellakaan h‰vi‰‰ 10 euroa.
			
			Vihje: Satunnaisluvun v‰lilt‰ 0-9 saat Math-luokkaa hyv‰ksik‰ytt‰en 
			esim.seuraavasti:
			int luku=(int)(10*Math.random());
 * Copyright:    	Copyright (c) 2003
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
 public class harjoitus16{
 	public static void main(String []args){
 		//Luodaan satunnaisluku
		int luku=(int)(10*Math.random());
		System.out.println("Anna arvauksesi! (0-9)");
		int voitto=30;
		//Pyˆritet‰‰n silmukkaa kolme kertaa
		for(int i=0;i<3;i++){
			int tulos=Lue.kluku();
			//Tarkistetaan tuliko oikea arvaus
			if(tulos==luku){
				System.out.println("oikein!!!! Voitit " +voitto +" euroa");
				break;
			}
			//Jos arvaus oli v‰‰rin v‰hennet‰‰n voittosummaa	
			else{
				voitto-=10;
				if(i<2)
					System.out.println("V‰‰rin! Anna uusi arvaus");
				else
					System.out.println("V‰‰rin! H‰visit " +voitto +" euroa");
			}
		}
 	}
 }