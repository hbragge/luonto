//Tulostaa luvun itseisarvon
//Markku Nevanranta

public class Itseisarvo {
	public static void main(String[] args){
		int luku;
		
		System.out.println("Anna luku: ");
		luku = Lue.kluku();
		
		if (luku < 0) luku = -luku;
		System.out.println("Luvun itseisarvo on "+luku);
	}
}