// Luokkien v�listen metodikutsujen harjoittelua.
// Markku Nevanranta

class Summa {
	private int summa;
	
	public void lisaa(int luku){
		summa += luku;
	}
	
	public void tulosta(){
		System.out.println("Summa on "+summa);
	}
}

public class Summatesti{
	public static void main(String[] args){
		int nro;
		Summa sum = new Summa();
		
		do {
			System.out.println("Anna lis�tt�v� luku (0 lopettaa)");
			nro = Lue.kluku();
			sum.lisaa(nro);
			sum.tulosta();
		} while (nro!=0);
	}
}
