// Tulostetaan karkausvuosien vuosiluvut halutulta vuosiväliltä
// Markku Nevanranta

public class Karkausvuosi {
    public static void main(String[] args) {
      
	    int alku, loppu;
	    
	    System.out.println("Anna alkuvuosi: ");
	    alku = Lue.kluku();
	    System.out.println("Anna loppuvuosi: ");
	    loppu = Lue.kluku();
	    
	    for (int i=alku; i<=loppu; i++){
	        if ((i%4==0 && i%100 !=0) || i%400==0)
	           System.out.print(i+"  ");
	    }
	}   
}
         
 