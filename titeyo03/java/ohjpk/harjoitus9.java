/**
 * Title:      		Harjoitus 9
 * Description:		Vertailee onko annettu luku pienempi vai suurempi kuin nolla
 * Copyright:    	Copyright (c) 2002
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
 public class harjoitus9{
 	public static void main(String []args){
 		int luku;	//Luodaan muuttuja kysyttävää lukua varten
 		System.out.println("Anna luku");	//Pyydetään luku
 		//Luvun lukemiseen näppäimistöltä käytetään Lue-luokan kluku-metodia, joka lukee kokonaislukuja.
 		luku=Lue.kluku();	//Talletetaan näppäimistöltä saatava luku muuttujaan.
 		if(luku<0)	//Tarkastellaan onko luku pienempi kuin nolla
 			System.out.println("Lukusi on negatiivinen");	//luku on pienempi kuin nolla
 		else	//Tämä suoritetaan mikäli luku ei ole pienempi kuin nolla
 			System.out.println("Lukusi on positiivinen");
 	}
 }