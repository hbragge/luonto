// Tehtävä 26 - Henri Bragge

public class tehtava26_op189696 {
	
	
	// Salausmetodi
	
	public static String salaa(){
		
		char aakkos[]={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		
		System.out.println("Anna salattava teksti:");
 		String teksti=Lue.rivi();
 		int p=teksti.length();
 		
 		int k=0;
 		int l=0;
 		char muunnos[]=new char[p];
 		
 		for(int i=0;i<p;i++){
 			for(int j=0;j<26;j++){
 				if(teksti.charAt(i)==aakkos[j]){
 					if(j<=12){
 						muunnos[k]=aakkos[j+13];
 						k++;}
 					else {
 						muunnos[k]=aakkos[j-13];
 						k++;}
 					l++;
 				}
 			}
 		}
 		teksti = new String(muunnos);
 		return teksti;
 	}
 	
 	// Purkumetodi
 	
 	public static String pura(){
 		
 		char aakkos[]={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		
		System.out.println("Anna salattava teksti:");
 		String teksti=Lue.rivi();
 		int p=teksti.length();
 		
 		int k=0;
 		int l=0;
 		char muunnos[]=new char[p];
 		
 		for(int i=0;i<p;i++){
 			for(int j=0;j<26;j++){
 				if(teksti.charAt(i)==aakkos[j]){
 					if(j>=12){
 						muunnos[k]=aakkos[j-13];
 						k++;}
 					else {
 						muunnos[k]=aakkos[j+13];
 						k++;}
 					l++;
 				}
 			}
 		}teksti = new String(muunnos);
 		return teksti;
 }

	// Ohjelma itse
	
	public static void main(String[] args){
		
	System.out.println("Salaatko vai puratko? (S/P)");
 	char kumpi=Lue.merkki();
 	String rivi=" ";
 	if(kumpi=='S' || kumpi=='s')
 		System.out.println(salaa());
	if(kumpi=='P' || kumpi=='p')
		System.out.println(pura());
	if(kumpi!='S' && kumpi!='s' && kumpi!='P' && kumpi!='p')
		System.out.println("Virheellinen komento.\n");	
	}
	}
	
		
	