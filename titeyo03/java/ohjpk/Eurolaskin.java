// Ohjelma muuttaa markat euroiksi
// ja tulostaa erikseen eurojen määrän
// ja senttien määrän
// Markku Nevanranta

public class Eurolaskin {
	public static void main (String[] args){
		double markat;
		
		System.out.print("Anna euroiksi muutettavat markat: ");
		markat = Lue.dluku();
		muunnaEuroiksi(markat);
	}

	private static void muunnaEuroiksi (double rahaa){
		double euroina;
		int eurot, sentit;
		
		euroina = rahaa/5.94573;
		eurot = (int) euroina;
		sentit = (int)((euroina-eurot)*100+0.5); //pyöristetään tasan senteiksi
		System.out.println("\nMarkat "+rahaa+"\nEurot "+eurot+
			"\nSentit "+sentit);
	}
}