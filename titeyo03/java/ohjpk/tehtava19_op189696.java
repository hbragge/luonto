// Tehtävä 19 - op189696 - titeyo03

public class tehtava19_op189696{
    public static void main(String[] args){
int[][] taulukko=new int [15][10];

	for(int i=0;i<taulukko.length;i++){
		for(int j=0;j<taulukko[i].length;j++){
			taulukko[i][j]=(i*j);
			taulukko[0][j]=j;
			taulukko[i][0]=(i*100);
			System.out.print(taulukko[i][j]+"\t");
			

		}				System.out.println("");
	}

}
}
