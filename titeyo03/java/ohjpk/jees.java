// Tehtävä 19 - op189696 - titeyo03

public class tehtava19_op189696{
    public static void main(String[] args){
int[][] taulukko=new int [15][10] ;
	for(int i=0;i<taulukko.length;i++){
		for(int j=0;j<taulukko[i].length;j++){
			System.out.print(taulukko[i][j] +"\t");
			
		// sarakeotsakkeiden tulostaminen
		System.out.print("     ");
		int minsumma=500;
		int maxsumma=2000;
		int minpros=2;
		int maxpros=20;
		for (int summa=minsumma; summa<=maxsumma; i+=100) System.out.print(i+"   ");
		System.out.println();

		for (int pros=minpros; pros<=maxpros; pros+=2){
			System.out.print("\n"+pros+"  ");
			for (int summa=minsumma; summa<=maxsumma; summa+=100)
				System.out.print(summa*((pros/100)*summa)+"   ");
				
			}
			System.out.println("");}
		}
	}
}