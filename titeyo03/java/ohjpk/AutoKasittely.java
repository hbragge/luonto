// Olioita taulukossa (joka kuvaa autokauppaa)
// Markku Nevanranta

class Auto{
	private double hinta;
	private String vari;
	private String merkki;
	
	public Auto(double value, String color, String model){
		hinta = value;
		vari = color;
		merkki = model;	
	}
	
	public double annaHinta(){
		return hinta;
	}
	
	public String annaMerkki(){
		return merkki;
	}
	
	public String annaVari(){
		return vari;	
	}
}

class Autokauppa{
	Auto[] autotaulu;
	int kpl;
	
	public Autokauppa(int koko){
		autotaulu = new Auto[koko];
	}
	
	public void lisaa(String merkki, String vari, double hinta){
		autotaulu[kpl++] = new Auto(hinta, vari, merkki);	
	}

	public double keskihinta(){
		double summa=0;
		for (int i=0; i<kpl; i++)
			summa += autotaulu[i].annaHinta();
		return summa/kpl;
	}
	
	public void merkki(String color){
		for (int i=0; i<kpl; i++)
			if (autotaulu[i].annaVari().equals(color))
				System.out.println(autotaulu[i].annaMerkki()+" on "+color);
	}
		
}

public class Autokasittely{
	public static void main(String[] args){
		Autokauppa veho = new Autokauppa(100);
		
		veho.lisaa("Mersu","hopea",50000);	
		veho.lisaa("Lada","punainen",15000);
		veho.lisaa("Honda","sininen",25000);
		
		System.out.println("Autojen keskihinta on "+veho.keskihinta());
		veho.merkki("punainen");
	}	
}
