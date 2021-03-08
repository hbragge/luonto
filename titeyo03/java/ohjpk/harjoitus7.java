/**
 * Title:      		Harjoitus 7
 * Description:		Tee ohjelma, joka käyttää Lue-luokkaa. 
					a)kysy käyttäjältä kokonaisluku ja tulosta se.
					b)Kysy desimaaliluku ja tulosta se
					c)Kysy merkki ja tulosta se
					d)Kysy lause ja tulosta se 
					
					vihje: Lue-luokka löytyy kirjan sivulta 255.
					Tai tästä. 
					Lue -luokka toimii helpoiten kun sen sijoittaa työhakemistoon,
					jossa harjoitustehtävätkin ovat. 

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