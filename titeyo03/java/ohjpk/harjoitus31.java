/**
 * Title:      		Harjoitus 31
 * Description:		Tee samaan tiedostoon kaksi luokkaa.
 					Ensimm‰iseen luokkaan kopioi kirjan sivujen78-80 
 					vaihtoJarjesta-, kuplaJarjesta- ja lisaysJarjesta -metodit.
 					(Joudut muuttelemaan metodien n‰kyvyys m‰‰rittely‰).
 					Toiseen luokkaa kirjoista main- ja tulostus-metodit.
 					Tulostus-metodi tulostaa j‰rjestetyn taulukon ja viittauksien m‰‰r‰n.
 					(viittaus=jarjesta-metodi kasittelee taulukon alkiota)
 * Copyright:    	Copyright (c) 2003
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
 //Harjoitus31 -luokka joka sis‰lt‰‰ main ja tulostus-metodit
 public class harjoitus31{
 	public static void main(String []args){
 		//Luodaan muuttujina kolme samanlaista taulukko oliota.
 		//Miksi? Vastaus sivu 19.
 		int a[]={6,7,4,8,2,4,7,8,9,1};
 		int b[]={6,7,4,8,2,4,7,8,9,1};
 		int c[]={6,7,4,8,2,4,7,8,9,1};
 		int d[]={6,7,4,8,2,4,7,8,9,1};
 		int alkio=0;
 		tulosta(a,alkio);
 		alkio=jarjesta.vaihto(b);
		tulosta(b, alkio);
 		alkio=jarjesta.kupla(c);	
		tulosta(c, alkio);
		alkio=jarjesta.lisays(d);
		tulosta(d, alkio);
 	}
 	private static void tulosta(int[] taulu, int alkio){
 		for(int j=0;j<taulu.length;j++)
 			System.out.print(taulu[j] +" ");
 		System.out.println();
 		if(alkio!=0)
 			System.out.println(alkio +" on algoritmissa viitattujen alkioiden m‰‰r‰");
 		
 	}
 }
 
 //J‰rjesta -luokka, jossa vaaditut metodit modifioituina kirjan esimerkeist‰
 class jarjesta{
 	public static int vaihto(int[] taulu){
 		int alkio=0;
 		for(int i=0;i<taulu.length-1;i++)
 			for(int j=i+1;j<taulu.length;j++)
 				if(taulu[i]>taulu[j]){
 					int apu=taulu[i];
 					taulu[i]=taulu[j];
 					taulu[j]=apu;
 					alkio++;
 				}
 		//alkio-muuttuja palauttaa taulukkoon tehtyjen viittausten lukum‰‰r‰n
 		return alkio;
 	}
 	public static int kupla(int[] taulu){
 		int alkio=0;
 		for(int i=taulu.length;i>0;i--)
 			for(int j=0;j<i-1;j++)
 				if(taulu[j]>taulu[j+1]){
 					int apu=taulu[j];
 					taulu[j]=taulu[j+1];
 					taulu[j+1]=apu;
 					alkio++;
 				}
 		return alkio;
 	}
 	
 	public static int lisays(int[] taulu){
 		int alkio=0;
 		for(int i=0;i<taulu.length;i++){
 			int apu=taulu[i];
 			int j=i;
 			while(j>0 && taulu[j-1]>apu){
 				taulu[j]=taulu[j-1];
 				j--;
 				alkio++;
 			}
 			taulu[j]=apu;
 		}
 		return alkio;
 	}
 } 				 