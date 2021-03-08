// Laskee milloin radioaktiivisen aineen
// puoliintumisaika on laskenut alle annetun arvon
// Markku Nevanranta

/*
	sateilytaso = 10000
	paika = 10
	ttaso = 100
	aika = 0
	
alku:
	sateilytaso = sateilytaso/2
	aika = aika+paika
	jos sateilytaso>ttaso mene alku
	tulosta aika

*/

public class Puoliintumisaika {
	public static void main(String[] args){
		
		// paika=puoliintumisaika, ttaso=tavoitetaso
		// laika=lähtöaika
		double sateilytaso, paika, ttaso;
		double aika;	
		
		System.out.println("Anna säteilyn lähtötaso ");
		sateilytaso = Lue.dluku();
		System.out.println("Anna säteilyn tavoitetaso ");
		ttaso = Lue.dluku();
		System.out.println("Anna aineen puoliintumisaika ");
		paika = Lue.dluku();
		System.out.println("Anna puoliintumisen lähtöaika ");
		aika = Lue.dluku();
				
		while (sateilytaso>ttaso){
			sateilytaso /=2;
			aika += paika;
		}
		System.out.println("Aine saavutti tavoitetasonsa "+aika);
	}
}
						 