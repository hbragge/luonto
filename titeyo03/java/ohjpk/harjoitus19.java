
/**
 * Title:      		Harjoitus 19
 * Description:		Asuntolainataulukko. 
 					Luo taulukko,joka kertoo paljonko eri lainam‰‰rist‰ on maksettava 
 					korkoa kuukaudessa erilaisilla lainaprosenteilla.
 					Pist‰ lainasumma taulukon ensimm‰iseen sarakkeeseen ja korkoprosentti 
 					ensimm‰iselle riville.
 * Copyright:    	Copyright (c) 2003
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
 public class harjoitus19{
 	//Luodaan pysyv‰ taulu, joka ei n‰y luokan ulkopuolelle.
 	private static double taulu[][]={{0,   2,3,4,5},
			 						{30000,0,0,0,0},
			 						{40000,0,0,0,0},
			 						{60000,0,0,0,0},
			 						{80000,0,0,0,0}};
 	public static void main(String[] args){
		//Lasketaan alkioille oikeat arvot
 		for(int i=1;i<5;i++){
 			for(int j=1;j<5;j++){
 				taulu[i][j]=(((taulu[i][0]*taulu[0][j])/100)/12);
 			}
 		}
 		//Tulostetaan taulukko yksinkertaisesti
 		for(int k=0;k<5;k++){
 			for(int l=0;l<5;l++){
 				System.out.print(Math.round(taulu[k][l]) +" ");
 			}
 			System.out.println(" ");
 		}
 		//kutsutaan tulostuskikkailua() -metodia, joka tulostaa taulukon hieman sievemp‰n‰
 		tulostusKikkailua();
 	}
 	
 	//Metodi tulostaa taulukon ja muotoilee sit‰ hieman
 	public static void tulostusKikkailua(){
 		System.out.println(" ");
 		System.out.println(" Tulostus kikkailua taulukon avulla");
 		System.out.println(" ");
 		System.out.println(" Taulukosta n‰et paljonko joudut maksamaan korkoa asuntolainastasi");
 		System.out.println(" ");
 		System.out.println("korko %   2   3   4   5  ");
 		System.out.println("-------------------------------");
 		for(int k=1;k<5;k++){
 			for(int l=0;l<5;l++){
 				if(taulu[k][l]<100)
 					System.out.print(" ");
 				System.out.print(Math.round(taulu[k][l]) +" ");
 				if(l==0)
 					System.out.print("| ");
 			}
 			System.out.println(" ");
 		}
 	}
 }
 				