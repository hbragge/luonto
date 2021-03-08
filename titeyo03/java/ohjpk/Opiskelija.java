// Harjoitellaan olioiden luontia parametreilla
// ja konstruktorin käyttöä
// Markku Nevanranta

public class Opiskelija{
	private String nimi;
	private int ov;
	private String hetu;
	
	public Opiskelija(String name, String id){
		nimi = name;
		hetu = id;	
	}
	
	public void tulosta(){
		System.out.println(nimi+"  "+hetu.substring(0,6));
	}
	
	public static void main(String[] args){
		Opiskelija opiskelija1 = new Opiskelija("Kalle Kehveli", "121212-123A");
		Opiskelija opiskelija2 = new Opiskelija("Irene Ihmeellinen", "010191-011B");
		
		opiskelija1.tulosta();
		opiskelija2.tulosta();
	}
}