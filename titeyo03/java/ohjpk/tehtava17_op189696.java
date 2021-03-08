// Tehtävä 18 - op189696 - titeyo03

public class tehtava17_op189696 {
    public static void main(String[] args){
    	
int[][] taulukko=new int [15][10] ;
for(int i=0;i<taulukko.length;i++){
	for(int j=0;j<taulukko[i].length;j++){
		taulukko[i][j]=(int)(1000*Math.random());
		taulukko[i][3]=1;
		taulukko[2][j]=0;
		System.out.print(taulukko[i][j] +"\t");
		}
		System.out.println("");}
		
int suurin=0;
int imax=0;
int jmax=0;
for(int i=0;i<taulukko.length;i++){
	for(int j=0;j<taulukko[i].length;j++){
		if(taulukko[i][j]>suurin){
		suurin=taulukko[i][j];
		imax=i+1;
		jmax=j+1;}
	}
}
System.out.println("");
System.out.println("Suurin luku on "+suurin+"("+imax+","+jmax+")");


int summa=0;
for(int i=0;i<taulukko.length;i++){
	for(int j=0;j<taulukko[i].length;j++){
		summa=summa+taulukko[i][j];
	}
}	
System.out.println("");
System.out.println("Taulukon alkioiden summa on "+summa);
System.out.println("");
}
}