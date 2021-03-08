/**
 * Title:      		Harjoitus 17
 * Description:		Taulukon käsittelyä
					a) Luo 15x10 taulukko, alusta taulukko kokonaisluvuilla ja tulosta se. 
					b) Etsi taulukosta suurin arvo ja ilmoita sen indeksi. 
					c) Vaihda neljännen sarakkeen luvut ykkösiksi 
					d) Vaihda kolmannen rivin luvut nolliksi. 
					e) Mikä on taulukon alkioiden summa
 * Copyright:    	Copyright (c) 2003
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
 public class harjoitus17 {
 	public static void main (String[] args){
		//Luo 15x10 taulukko, alusta taulukko kokonaisluvuilla ja tulosta se.
		int taulukko[][]=new int[15][10];
		for(int i=0;i<15;i++){
			for(int j=0;j<10;j++){
				taulukko[i][j]=(int)(100*Math.random());
			}
 		} 
 		for(int i=0;i<15;i++){
			for(int j=0;j<10;j++){
				System.out.print(" " +taulukko[i][j]);
			}
			System.out.println("");
 		} 
 		
 		//Etsi taulukosta suurin arvo ja ilmoita sen indeksi.
 		int suurin=0;
 		int indi=0, indj=0;
 		for(int i=0;i<15;i++){
			for(int j=0;j<10;j++){
				if(suurin<taulukko[i][j]){
					suurin=taulukko[i][j];
					indi=i;
					indj=j;
				}
			}
 		} 
 		System.out.println("Suurin luku on" +suurin +"(" +indi +", " +indj +")");
 		
 		
		
		//Vaihda neljännen sarakkeen luvut ykkösiksi 
 		for(int j=0;j<15;j++){
			taulukko[j][3]=1;
		}
		
		//Vaihda kolmannen rivin luvut nolliksi.
 		
 		for(int j=0;j<10;j++){
			taulukko[2][j]=0;
		}
		//Tulostus
		for(int i=0;i<15;i++){
			for(int j=0;j<10;j++){
				System.out.print(" " +taulukko[i][j]);
			}
			System.out.println("");
 		} 
 		
 		//Mikä on taulukon alkioiden summa
 		int summa=0;
 		for(int i=0;i<15;i++){
			for(int j=0;j<10;j++){
				summa+=taulukko[i][j];
			}
 		} 
 		System.out.println("Summa" +summa);
 	}
 }
 		
