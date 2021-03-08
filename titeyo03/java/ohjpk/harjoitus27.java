
/**
 * Title:      		Harjoitus 27
 * Description:		Laskukone
 					Tee vähennys- ja yhteenlaskumetodit, joita kutsut main metodista
 * Copyright:    	Copyright (c) 2003
 * Company:
 * @author Mika Saari
 * @version 0.1
 */
public class harjoitus27{
	public static void main(String []args){
		char komento;
		System.out.println("Laskukone");
		System.out.println("Y Yhteenlasku");
		System.out.println("V Vähennyslasku");
		System.out.println("L Lopeta");
		do{
			System.out.println("Anna komento!");
			komento=Lue.merkki();
			
			switch(komento){
				case 'V':vahennys();
						break;
				case 'Y':yhteen();
						break;
				case 'L':break;
				default:System.out.println("Anna oikea komento(Y ,V tai L)!");
						break;
			}
		}while(komento!='L');
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