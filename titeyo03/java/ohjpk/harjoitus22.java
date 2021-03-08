/**
 * Title:      		Harjoitus 22
 * Description:		Laadi ohjelma, joka poistaa kaikki erikoismerkit merkkijonosta 
 * Copyright:    	Copyright (c) 2003
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
 public class harjoitus22{
 	public static void main(String []args){
 		String lause;
 		int k=0;
 		char muunnos[]=new char[100];
 		//Luodaan aakostaulu johon verrataan
 		char aakkos[]={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','ä','ö','å'};
 		System.out.println("Anna lause joka sisältää erikoismerkkejä");
 		lause=Lue.rivi();
 		//vertaillaan jokaista merkkiä erikseen kaikkiin aakkostaulun alkioihin
 		for(int i=0;i<lause.length();i++){
 			for(int j=0;j<29;j++){
 				if(lause.charAt(i)==aakkos[j]){
 					muunnos[k]=lause.charAt(i);
 					k++;
 				}
 			}
 		}
 		//Tulostetaan siivottu lause
 		System.out.print(new String(muunnos));
 	}
 	//Tämä ohjelma luulee isoja kirjaimia erikoismerkeiksi! 
 }