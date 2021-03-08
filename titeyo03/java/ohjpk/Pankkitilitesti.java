// Testausluokka, joka luo pankkitilin käsittelyvalikon
// ja luo olion Pankkitililuokasta
// Markku Nevanranta

public class Pankkitilitesti{
	public static void main(String[] args){
		Pankkitili omatili=new Pankkitili(1000, "rahaa");
		boolean jatka=true;

		while (jatka){
			System.out.println();
			System.out.println("1 Tilille kirjautuminen");
			System.out.println("2 Tilille pano");
			System.out.println("3 Tililtä otto");
			System.out.println("9 Lopeta tilin käsittely");
			int vastaus = Lue.kluku();
			switch (vastaus){
				case 1: omatili.salasana(); break;
				case 2: omatili.pano(); break;
				case 3: omatili.otto(); break;
				case 9: omatili.logout();
						jatka=false; break;
				default: System.out.println("Valitse laillinen toiminto");
			}
		}
	}
}