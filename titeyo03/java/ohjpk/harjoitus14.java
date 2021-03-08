/**
 * Title:      		Harjoitus 14
 * Description:		Laadi ohjelma, joka laskee/tulostaa 20 ensimmäistä Fibonaccin lukua.
 					vihje Fibonaccin luvuissa luku on aina kahden edellisen luvun summa.
 * Copyright:    	Copyright (c) 2003
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
 public class harjoitus14{
 	public static void main(String []args){
 		int fibo1=0;
 		int fibo2=1;
 		//For silmukka pyörii 10 kertaa, joka kierroksella tulostetaan kaksi fibonaccin lukua.
 		for(int i=0;i<10;i++){
 			System.out.print(fibo1 +" " +fibo2 +" ");
 			fibo1=fibo1+fibo2;
 			fibo2=fibo2+fibo1;
 		}
 	}
 }