// Tulostaa mwerkkijonon käänteisesnä
// Markku Nevanranta

public class Kaanteinen {
	public static void main(String[] args){
		
		String teksti;
		
		System.out.println("Anna merkkijono ");
		teksti=Lue.rivi();
		
		for (int i=teksti.length()-1; i>=0; i--)
			System.out.println((int)teksti.charAt(i)+" "+teksti.charAt(i)+" ");
	}
}