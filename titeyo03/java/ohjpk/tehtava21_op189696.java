// Tehtävä 21 - Henri Bragge - op189696 - titeyo03

public class tehtava21_op189696{
    public static void main(String[] args){
    	
    String jono = "TERVE sulle";
    
    System.out.println(jono.toLowerCase());
    
    System.out.println(jono.toUpperCase());
    
	for(int i=0; i<jono.length(); i++){
	if(i==5)continue;
	System.out.print(jono.charAt(i));
	}System.out.println("");
}
}