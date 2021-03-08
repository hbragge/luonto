/**
 * Title:      		Harjoitus 7
 * Description:		Tee ohjelma, joka k�ytt�� Lue-luokkaa. 
					a)kysy k�ytt�j�lt� kokonaisluku ja tulosta se.
					b)Kysy desimaaliluku ja tulosta se
					c)Kysy merkki ja tulosta se
					d)Kysy lause ja tulosta se 
					
					vihje: Lue-luokka l�ytyy kirjan sivulta 255.
					Tai t�st�. 
					Lue -luokka toimii helpoiten kun sen sijoittaa ty�hakemistoon,
					jossa harjoitusteht�v�tkin ovat. 

 * Copyright:    	Copyright (c) 2002
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
 public class harjoitus7{
 	public static void main(String []args){
 		System.out.println("Anna kokonaisluku");
 		//kutsutaan Lue -luokan kluku()-metodia
 		int luku=Lue.kluku();
 		System.out.println(luku);
 		System.out.println("Anna desimaaliluku");
 		double luku2=Lue.dluku();
 		System.out.println(luku2);
 		System.out.println("Anna merkki");
 		char merkki=Lue.merkki();
 		System.out.println(merkki);
 		System.out.println("Anna lause");
 		String lause=Lue.rivi();
 		System.out.println(lause);
 	}
 }