/* Tietorakenteet 2006
 *
 * Harjoitus 20 - Graafin syvyyshaun ja syklien tulostus
 * Henri Bragge 189696 titeyo03 */

import java.util.ArrayList;

public class syvyys {
	
		static boolean[] tutkittu = new boolean[13];
		static char[] kirjaimet = new char[13];
		static StringBuffer ketju = new StringBuffer();
		
		public static void main (String args[]){
			
			System.out.println("Graafit syvyysjärjestyksessä:\n");
			System.out.println("Graafi 2: ");
			syvyysPrint(alusta(1), 0, 0);
			System.out.println("");
			
			System.out.println("Graafi 3: ");
			syvyysPrint(alusta(2),0, 0);
			System.out.println("");
			System.out.println("");
			
			System.out.println("Syklit graafissa 3: ");
			System.out.println("");
			syklit();	
		}
		
		
		static void syvyysPrint(boolean[][] graafi, int a, int b){
		
			boolean onko = false;
			for(int i = a;i<13;i++){
				for(int j = b;j<13;j++){
					if(graafi[i][j]==true){
						if(!tutkittu[j]){ 
							System.out.print(kirjaimet[i]+"-"+kirjaimet[j]+" ");
							tutkittu[j]=true;
						}
						graafi[i][j] = false;
						int p = j;
						
						for(int k = j+1; k<12; k++){
							onko = graafi[p][k];
							if(onko == true) syvyysPrint(graafi,p,j);
						}
					}
				}
			}
		}

		static void syklit(){
			
			for(int i=0;i<13;i++){
				syklita(alusta(3), 0, 0, i);
				ketju.delete(0,ketju.length());
			}
		}
		
		static void syklita(boolean[][] graafi, int a, int b, int root){
			
			boolean onko = false;
			
			for(int i = a;i<13;i++){
				for(int j = b;j<13;j++){
	
					if(graafi[i][j]==true){
						
						ketju.append(kirjaimet[i]+"-"+kirjaimet[j]+" ");

						if(j==root){
							if(!tutkittu[j]){
								System.out.println(ketju);
							
							tutkittu[root]=true;
							System.out.println("");}
							ketju.delete(0,ketju.length());
							
							return;
						}
						graafi[i][j] = false;
						int p = j;
						for(int k = j+1; k<12; k++){
							onko = graafi[p][k];
							if(onko == true)syklita(graafi,p,j,root);
						}	
					}
				}
			}
		}
		
		
	static boolean[][] alusta(int a){
	
		boolean[][] graafi = new boolean[13][13];
		
		for(int i=0;i<13;i++)
			tutkittu[i]=false;
		
		kirjaimet[0] = 'A';
		kirjaimet[1] = 'B';
		kirjaimet[2] = 'C';
		kirjaimet[3] = 'D';
		kirjaimet[4] = 'E';
		kirjaimet[5] = 'F';
		kirjaimet[6] = 'G';
		kirjaimet[7] = 'H';
		kirjaimet[8] = 'I';
		kirjaimet[9] = 'J';
		kirjaimet[10] = 'K';
		kirjaimet[11] = 'L';
		kirjaimet[12] = 'M';
		
		if(a == 1){
			graafi[0][1] = true;
			graafi[0][2] = true;
			graafi[0][5] = true;
			graafi[0][6] = true;
			graafi[2][6] = true;
			graafi[3][4] = true;
			graafi[3][5] = true;
			graafi[4][5] = true;
			graafi[4][6] = true;
			graafi[6][7] = true;
			graafi[6][9] = true;
			graafi[6][11] = true;
			graafi[7][8] = true;
			graafi[9][10] = true;
			graafi[9][11] = true;
			graafi[9][12] = true;
			graafi[11][12] = true;
			
		}else if(a == 2) {
		
			graafi[0][1] = true;
			graafi[0][5] = true;
			graafi[0][6] = true;
			graafi[1][2] = true;
			graafi[1][3] = true;
			graafi[1][4] = true;
			graafi[2][4] = true;
			graafi[3][4] = true;
			graafi[3][5] = true;
			graafi[4][5] = true;
			graafi[4][6] = true;
			graafi[4][11] = true;
			graafi[5][11] = true;
			graafi[6][7] = true;
			graafi[6][9] = true;
			graafi[6][11] = true;
			graafi[7][8] = true;
			graafi[8][10] = true;
			graafi[9][10] = true;
			graafi[9][11] = true;
			graafi[9][12] = true;
			graafi[11][12] = true;
			
		}else if(a == 3) {
		
			graafi[0][5] = true;
			graafi[0][6] = true;
			graafi[1][2] = true;
			graafi[1][3] = true;
			graafi[1][4] = true;
			graafi[1][5] = true;
			graafi[2][4] = true;
			graafi[2][5] = true;
			graafi[3][4] = true;
			graafi[3][5] = true;
			graafi[4][5] = true;
			graafi[4][6] = true;
			graafi[4][11] = true;
			graafi[5][11] = true;
			graafi[6][7] = true;
			graafi[6][9] = true;
			graafi[6][11] = true;
			graafi[7][8] = true;
			graafi[8][10] = true;
			graafi[9][10] = true;
			graafi[9][11] = true;
			graafi[9][12] = true;
			graafi[11][12] = true;
		}
		return graafi;	
	}
}

