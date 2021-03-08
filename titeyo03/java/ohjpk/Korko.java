// Sijoituksen arvon kasvaminen
// annetun koron ja vuosim‰‰r‰n j‰lkeen
// (while-versio)
// Markku Nevanranta

/*

	saldo = 10000
	korko = 4
	vuodet = 5
	vuosi = 0
	
alku:
	summa = (korko/100+1)*saldo
	saldo = saldo+summa
	vuosi = vuosi+1
	jos vuosi<vuodet mene alku
	tulosta saldo	
	

*/
public class Korko {
	public static void main(String[] args){
		
		double saldo, korko;
		int vuodet, v=0;
		
		System.out.println("Anna sijoituksen m‰‰r‰ ");
		saldo = Lue.dluku();
		System.out.println("Anna korkoprosentti ");
		korko = Lue.dluku();
		System.out.println("Anna sijoitusaika vuosissa ");
		vuodet = Lue.kluku();
				
		while (v<vuodet){
			saldo = (1+korko/100)*saldo;
			v++;
		}
		
		System.out.println("Tilill‰ on rahaa "+vuodet+
			" vuoden kuluttua "+saldo+" markkaa");
	}
}