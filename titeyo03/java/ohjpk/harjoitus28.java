
/**
 * Title:      		Harjoitus 28
 * Description:		Laskukone
 					Tee edellisen harjoituksen ohjelmaan hexadesimaaliksi-muunnos metodi.
 * Copyright:    	Copyright (c) 2003
 * Company:
 * @author Mika Saari
 * @version 0.1
 */
public class harjoitus28{
	public static void main(String []args){
		char komento;
		System.out.println("Laskukone");
		System.out.println("Y Yhteenlasku");
		System.out.println("V Vähennyslasku");
		System.out.println("H Kymmenjärjestelmän luvun muuntaminen hexadesimaaliksi");
		System.out.println("L Lopeta");
		do{
			System.out.println("Anna komento!");
			komento=Lue.merkki();
			
			switch(komento){
				case 'V':vahennys();
						break;
				case 'Y':yhteen();
						break;
				case 'H':hexaksi();
						break;
				case 'L':break;
				default:System.out.println("Anna oikea komento(Y ,V ,H tai L)!");
						break;
			}
		}while(komento!='L');
	}
	public static void hexaksi(){
		System.out.println("Valitsit luvun muuntamisen hexaksi");
		System.out.println("Anna luku");
		int luku=Lue.kluku();
		int hexaa[]=new int[10];
		int hexa=0;
		int i=9;
		//Jaetaan annettua lukua kuudellatoista kunnes se on pienempikuin 16
		//Jokaisen jaon jakojäännös talletetaan taulukkoon,
		//Jolloin meillä on taulukossa kantaluvun kertoimet.
		//Kertomet muutetaan vielä 0-f hexadesimaaleiksi. 
		//Huom!! Taulukon tallennus suunta(isoimmasta pienimpään)
		while(luku>=16){
			hexaa[i]=luku%16;
			luku=luku/16;
			i--;
			if(luku<16)
				hexaa[i]=luku;
		}
		char hexataulu[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
		for(int j=0;j<10;j++)
			System.out.print(hexataulu[hexaa[j]]);
	}		
		
	public static void vahennys(){
		System.out.println("Valitsit vähennyslaskun A-B");
		System.out.println("Anna luku A");
		double luku1=Lue.dluku();
		System.out.println("Anna luku B");
		double luku2=Lue.dluku();
		System.out.println(luku1 +" - " +luku2 +" = " +(luku1-luku2));
	}
	public static void yhteen(){
		System.out.println("Valitsit yhteenlaskun A+B");
		System.out.println("Anna luku A");
		double luku1=Lue.dluku();
		System.out.println("Anna luku B");
		double luku2=Lue.dluku();
		System.out.println(luku1 +" + " +luku2 +" = " +(luku1+luku2));
	}
}