
/**
 * Title:      		Harjoitus 15
 * Description:		Tulosta kaikki alkuluvut 0-100
 * Copyright:    	Copyright (c) 2003
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
 public class harjoitus15{
 	public static void main(String []args){
 		int j=0;
 		for(int i=1;i<=100;i++){
 			int jluku=2;
 			//Testataan jaottomuus kaikilla luvuilla kahdesta i:hin
 			for(j=2;j<i;j++){
 				if(i%j!=0)
 					jluku++;
 				else
 					jluku--;
 			}
 			//Jos yhtään jakojäännös==0 ei ole löytynyt niin jluku==j
 			if(jluku==j)
				System.out.print(i +"  ");
 		}
 		
 	}
 }