// Sijoituksen arvon kasvaminen
// annetun koron ja vuosim‰‰r‰n j‰lkeen
// (for-versio)
// Markku Nevanranta

/*
vuosi =0
korko =4
saldo =10000
vuodet=5

alku:
	saldo = saldo+saldo*korko/100
	vuosi = vuosi+1
	jos vuosi<vuodet mene alku
	tulosta saldo

*/

public class Korko1{
	public static void main(String[] args){

	int vuodet;
	double korko, saldo;

	System.out.println("Anna tilin saldo: ");
	saldo = Lue.dluku();
	System.out.println("Anna korko: ");
	korko = Lue.dluku();
	System.out.println("Anna s‰‰stˆvuodet: ");
	vuodet = Lue.kluku();
		
	for (int vuosi=1; vuosi<=vuodet; vuosi++)	
		saldo = saldo*(1+korko/100);
		
	System.out.println("S‰‰stˆsi on karttunut "+saldo+" euroksi");	
	}
}