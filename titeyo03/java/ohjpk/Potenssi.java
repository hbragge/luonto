// Potenssifunktiot, joista toinen tulostaa tuloksen
// ja toinen palauttaa tuloksen return-lauseella
// Markku Nevanranta

public class Potenssi {
	public static void main(String[] arg){
		pot(3.0,4);
		System.out.println(pot(2,9)+pot(2,5)+pot(2,3)+pot(2,0));
	}

	private static void pot(double luku, int eksp){
		double tulos=1;
		for (int i=1; i<=Math.abs(eksp); i++)
			tulos *= luku;
		if (eksp<0) tulos=1.0/tulos;
		System.out.println(tulos);
	}

	private static double pot(int luku, int eksp){
		double tulos=1;
		for (int i=1; i<=Math.abs(eksp); i++)
			tulos *= luku;
		if (eksp<0) tulos=1.0/tulos;
		return tulos;
	}
}