// Tehtävä 18 - op189696 - titeyo03

public class tehtava18_op189696{
    public static void main(String[] args){

int[] taulu = new int[15];

for (int i=0;i<15;i++){
taulu[i] = (int)(100*Math.random());
System.out.print(taulu[i]+"  ");

}System.out.println("");
System.out.println("");

	for (int k = 0; k < taulu.length; k++){
		for (int l = k; l < taulu.length; l++){
			if (taulu[k] < taulu[l]){
			int temp = taulu[k];
			taulu[k] = taulu[l];
			taulu[l] = temp;
		}
	}System.out.print(taulu[k]+"  ");
	
}System.out.println("");
}
}