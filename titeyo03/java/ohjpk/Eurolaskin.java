// Ohjelma muuttaa markat euroiksi
// ja tulostaa erikseen eurojen m��r�n
// ja senttien m��r�n
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
		sentit = (int)((euroina-eurot)*100+0.5); //py�ristet��n tasan senteiksi
		System.out.println("\nMarkat "+rahaa+"\nEurot "+eurot+
			"\nSentit "+sentit);
	}
}