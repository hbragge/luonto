/**
 * Title:      		Harjoitus 18
 * Description:		Taulukon käsittelyä.
 					Luo 15-alkioinen taulukko. 
 					Alusta taulukko Math.random()-funktion avulla. 
 					Tulosta se.
 					Lajittele kyseinen taulukko kuplalajittelun tai vaihtolajittelun avulla pienimmästä suurimpaan.
 					Tulosta lajiteltu taulukko.
 					sinut 78-80
 * Copyright:    	Copyright (c) 2002
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
 public class harjoitus18{
 	public static void main(String[] args){
 		int taulukko[]=new int[15];		//Luodaan taulukko-muuttuja
 		 
		//Alustetaan taulukko satunnaisluvuilla 0-100
 		for(int i=0;i<taulukko.length;i++){
 			taulukko[i]=(int)(100*Math.random());
 		}
 		
 		//Tulostetaan taulukko 	
 		for(int i=0;i<taulukko.length;i++)
 			System.out.print(" " +taulukko[i]);
 		
 		
 		//Kupla-lajitellaan taulukon alkiot uuteen järjestykseen
 		int luku=0;
 		for(int k=0;k<taulukko.length;k++){	 			
	 		for(int j=0;j<(taulukko.length-1);j++){
	 			if(taulukko[j]<taulukko[j+1]){
	 				luku=taulukko[j];
	 				taulukko[j]=taulukko[j+1];
	 				taulukko[j+1]=luku;
	 			}
	 		}
 		}
 		//Tulostetaan taulukko 	
 		System.out.println("");
 		for(int i=0;i<taulukko.length;i++)
 			System.out.print(" " +taulukko[i]);
 		
 		
 		//Vaihto-lajitellaan taulukon alkiot uuteen järjestykseen
 		int luku1=0;
 		int luku2=0;
 		for(int k=luku1;k<taulukko.length;k++){	
	 		for(int j=0;j<(taulukko.length-1);j++){
		 		if(taulukko[luku1]>taulukko[j]){
		 			luku2=taulukko[j];
		 			taulukko[j]=taulukko[luku1];		
		 			taulukko[luku1]=luku2;
		 		}
	 		}
	 		luku1++;
	 	}
	
		//Tulostetaan järjestetty taulukko
		System.out.println(" ");
 		for(int i=0;i<taulukko.length;i++)
 			System.out.print(" " +taulukko[i]);
 	}
 }