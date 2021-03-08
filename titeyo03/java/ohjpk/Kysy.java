// T‰m‰ on Lue-luokasta periytetty Kysy-luokka
// jolla on yhden parametrin versiot metodeista.
// Voit siis k‰ytt‰‰ metodeita parametrilla tai ilman.
// Markku Nevanranta

public class Kysy extends Lue{
	public static String rivi(String teksti){
   		System.out.println(teksti);
   		return rivi();
   }
   
   public static int kluku(String teksti){
   		System.out.println(teksti);
   		return kluku();
   }
   
   public static double dluku(String teksti){
   		System.out.println(teksti);
   		return dluku();
   }
   
   public static char merkki(String teksti){
   		System.out.println(teksti);
   		return merkki();
   }
}