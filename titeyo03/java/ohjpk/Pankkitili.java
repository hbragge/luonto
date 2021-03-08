// Pankkitililuokka, jonka mukaisia olioita voi luoda
// toisissa tiedostoissa olevista javaluokista
// Markku Nevanranta

public class Pankkitili{

	private double saldo;
	private String salasana;
	private boolean lupa=false;

	public Pankkitili(double rahaa, String ssana){
		saldo=rahaa;
		salasana=ssana;
	}

	public void salasana() {
		System.out.print("Anna salasana: ");
		String sala=Lue.rivi();
		if (sala.equals(salasana)){
			lupa=true;
			System.out.println("Tili avattu");
		} else
			System.out.println("Salasana v‰‰rin");
	}

	public void pano() {
		if (lupa){
			System.out.println("Paljonko rahaa lis‰‰t tilillesi("+saldo+")");
			double markat=Lue.dluku();
			saldo+=markat;
		}else
			System.out.println("Anna ensin salasanasi");
	}

	public void otto() {
		if (lupa){
			System.out.println("Paljonko rahaa nostat tililt‰si("+saldo+")");
			double markat=Lue.dluku();
			if (markat<=saldo)
				saldo-=markat;
			else
				System.out.println("Tilill‰si ei ole haluamaasi raham‰‰r‰‰");
		}else
			System.out.println("Anna ensin salasanasi");
	}

	public void logout(){
		lupa=false;
		System.out.println("Tili on suljettu");
	}
}