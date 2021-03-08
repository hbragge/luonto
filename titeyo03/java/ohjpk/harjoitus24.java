/**
 * Title:      		Harjoitus 24
 * Description:		Laadi tekstin tavutus ohjelma. 
 					Ohjelma tavuttaa annetun tekstin annetun levyiselle palstalle
 					Vihje:K-K sääntö
 * Copyright:    	Copyright (c) 2002
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
public class harjoitus24{
	public static void main(String []args){
 		char konsonantit[]={'B','C','D','F','G','H','J','K','L',
 						'M','N','P','Q','R','S','T','V','W','X',
 						'Z','b','c','d','f','g','h','j','k','l',
 						'm','n','p','q','r','s','t','v','w','x','z'};
 		char vokaalit[]={'A','E','I','O','U','Ä','Ö','Y','a','e','i','o','u','ä','ö','y'};
 		int lev2=0;
 		//Boolean muutujista on tässä apua
 		boolean vokaali=false;
 		boolean kons1=false;
 		boolean kons2=false;
 		System.out.println("Anna palstan leveys");
 		int leveys=Lue.kluku();
 		char palsta[]=new char[leveys];
 		System.out.println("Anna Tavutettava lause");
 		String lause=Lue.rivi();
		//Käydään koko teksti läpi merkki merkiltä
		for(int i=1;i<=lause.length();i++){
			System.out.print(lause.charAt(i-1));
			//Tutkitaan onko kyseessä vokaali
			for(int j=0;j<16;j++){
				if(i>2){
	 				if(lause.charAt(i-2)==vokaalit[j]){
	 					//Jos on vokaali niin muuttuja true arvoon
	 					vokaali=true;
	 					//Tutkitaan onko konsonantti
	 					for(int l=0;l<40;l++){
	 						//jos nykyinen kirjain on konsonantti niin true arvo
	 						if(lause.charAt(i-1)==konsonantit[l]){
	 							kons1=true;
	 						}
	 						//Jos myös seuraava kirjain on konsonantti niin sillekin true
	 						if(i<(lause.length()-1)){//Estetään taulukon ylivuoto
	 							if(lause.charAt(i)==konsonantit[l]){
	 								kons2=true;
	 							}
	 						}
	 					}
	 				}
				}
			}
			lev2++;//Muuttuja tarkkailemassa kirjoitetun rivin pituutta
			//VKK -sääntö, eli ensin vokaali ja sitten kaksi konsonanttia, jos näin niin rivinvaihto
			if(vokaali&&kons1&&kons2){
				//Tutkitaan myös ettei rivinvaihtoa heti aluksi
				if(((lev2%leveys)==leveys-1)||((lev2%leveys)==leveys-2)){
					System.out.println("-");
					lev2=0;
				}
				vokaali=kons1=kons2=false;
			}
			else{ //Tyhjäkin paikka voi aiheuttaa rivinvaihdon
				if(((lev2%leveys)==leveys-1)||((lev2%leveys)==leveys-2)&&lause.charAt(i-2)==' '){
					lev2=0;
					System.out.println("");
				}
				//Asetetaan boolean-muuttujat vielä false arvoon
				vokaali=kons1=kons2=false;
				}
			//Millä muilla ehdoilla voitaisiin suorittaa rivinvaihto(esim .,!?)
		}
	}
}
 