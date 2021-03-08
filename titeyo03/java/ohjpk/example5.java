//Tehtävä 27 - Henri Bragge

public class tehtava27_op189696 {
	
	// Yhteenlaskumetodi
	
	public static double yhteen(double eka, double toka){
		double c = eka + toka;
		return c;
	}
	
	// Miinuslaskumetodi
	
	public static double vahen(double eka, double toka){
		double c = eka - toka;
		return c;
	}
	
	// Ohjelma itse
	
	public static void main(String[] args){
		System.out.println("Yhteen vai vahennys? (Y/V)");
		char kumpi=Lue.merkki();
 		if(kumpi=='Y' || kumpi=='y'){
 			System.out.println("Anna ensimmäinen luku:");
 			double ekay = Lue.dluku();
 			System.out.println("Anna toinen luku:");
 			double tokay = Lue.dluku();
 			double summa = yhteen(ekay, tokay);
 			System.out.println("Summa on "+summa);
 			}
		if(kumpi=='V' || kumpi=='v'){
			System.out.println("Anna ensimmäinen luku:");
 			double ekav = Lue.dluku();
 			System.out.println("Anna toinen luku:");
 			double tokav = Lue.dluku();
 			double erotus = vahen(ekav, tokav);
 			System.out.println("Erotus on "+erotus);
 			}
	
	}
}
		
		
		
		