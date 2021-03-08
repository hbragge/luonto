// Suhteellisen painon taulukko
// Markku Nevanranta

public class SuhteellinenPaino {
	public static void main(String[] args){

		int alkupaino=50, loppupaino=106;
		int alkupituus=152, loppupituus=200;

		// sarakeotsakkeiden tulostaminen
		System.out.print("     ");
		for (int i=alkupaino; i<=loppupaino; i+=4) System.out.print(i+"   ");
		System.out.println();

		for (int pituus=alkupituus; pituus<=loppupituus; pituus+=4){
			System.out.print("\n"+pituus+"  ");
			for (int paino=alkupaino; paino<=loppupaino; paino+=4)
				System.out.print(Math.round(10000.0*paino/(pituus*pituus))+"   ");
		}
	}
}
