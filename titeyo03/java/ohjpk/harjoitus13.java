
/**
 * Title:      		Harjoitus 13
 * Description:		Tulosta kaikki kahden potenssit arvoon 2 potenssiin 32 asti
 * Copyright:    	Copyright (c) 2002
 * Company:
 * @author Mika Saari
 * @version 0.1
 */
 public class harjoitus13{
 	public static void main(String []args){
 		double tulos=2; //double koska int tyyppiin ei mahdu 2 potenssiin 32 tulos
 		//for -silmukka joka tekee 31 kierrosta
 		for(int i=0;i<31;i++){
 			//joka kierroksella tulos -muuttuja kerrotaan kahdella 
 			//ja talletetaan takaisin muuttujaan tulos
 			tulos=tulos*2;
	 		System.out.print(" " +tulos);
	 	}
 	}
 }