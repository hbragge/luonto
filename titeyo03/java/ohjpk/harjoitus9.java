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
 		int luku;	//Luodaan muuttuja kysytt�v�� lukua varten
 		System.out.println("Anna luku");	//Pyydet��n luku
 		//Luvun lukemiseen n�pp�imist�lt� k�ytet��n Lue-luokan kluku-metodia, joka lukee kokonaislukuja.
 		luku=Lue.kluku();	//Talletetaan n�pp�imist�lt� saatava luku muuttujaan.
 		if(luku<0)	//Tarkastellaan onko luku pienempi kuin nolla
 			System.out.println("Lukusi on negatiivinen");	//luku on pienempi kuin nolla
 		else	//T�m� suoritetaan mik�li luku ei ole pienempi kuin nolla
 			System.out.println("Lukusi on positiivinen");
 	}
 }