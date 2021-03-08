// Ohjelmiston testaus - Henri Bragge 189696

public class futispeli{
	public static void main(String []args){
		
		String pelaaja;
		System.out.println("Futispeli");
		System.out.println("Hyokkaajat                h1     h2");
		System.out.println("Keskikenttapelaajat    k1     k2    k3");
		System.out.println("Puolustajat            p1  p2   p3  p4");
		System.out.println("Maalivahti                    m1");
		System.out.println("L Lopeta");
		
		
			System.out.println("Valitse pelaaja (esim k2):");
			pelaaja=Lue.rivi();
			do{
				if(pelaaja.equalsIgnoreCase("h1"))hyok1();
				else if(pelaaja.equalsIgnoreCase("h2"))hyok2();
				else if(pelaaja.equalsIgnoreCase("k1"))kesk1();
		//		else if(pelaaja.equalsIgnoreCase("k2"))kesk2();
				else if(pelaaja.equalsIgnoreCase("k3"))kesk3();
				else if(pelaaja.equalsIgnoreCase("p1"))puol1();
				else if(pelaaja.equalsIgnoreCase("p2"))puol2();
				else if(pelaaja.equalsIgnoreCase("p3"))puol3();
				else if(pelaaja.equalsIgnoreCase("p4"))puol4();
				else if(pelaaja.equalsIgnoreCase("m1"))maal1();
				else System.out.println("Anna oikea komento");
			}while(pelaaja.equalsIgnoreCase("L")==false);
		}
	
	
	public static void hyok1(){

		char tapahtuma;
		int pisteet=0;

		do{
			
			System.out.println("\nHyokkaaja 1:");
			System.out.println("M Maali");
			System.out.println("S maaliSyotto");
			System.out.println("E Epaonnistunut rankkari");
			System.out.println("K Keltainen kortti");
			System.out.println("U Ulosajo");
			System.out.println("L Lopeta\n");
			System.out.println("Pelaajan pisteet: "+pisteet+"\n");
			System.out.println("Anna tapahtuma:");
			tapahtuma=Lue.merkki();
			System.out.println("");
			
			switch(tapahtuma){
				case 'm':pisteet=pisteet+4;
						 break;
				case 'M':pisteet=pisteet+4;
						 break;
				case 's':pisteet=pisteet+3;
						 break;
				case 'S':pisteet=pisteet+3;
						 break;
				case 'e':pisteet=pisteet-2;
						 break;
				case 'E':pisteet=pisteet-2;
						 break;
				case 'k':pisteet=pisteet-1;
					     break;
				case 'K':pisteet=pisteet-1;
					     break;
				case 'u':pisteet=pisteet-3;
						 break;
				case 'U':pisteet=pisteet-3;
						 break;
				case 'l':break;
				case 'L':break;
				default:System.out.println("Anna oikea komento!");
						break;
			}
		}while(tapahtuma!='L');
		
		 System.out.println(pisteet);
		 System.exit(0);
	}
	
	
	public static void hyok2(){

		char tapahtuma;
		int pisteet=0;

		do{
			
			System.out.println("\nHyokkaaja 2:");
			System.out.println("M Maali");
			System.out.println("S maaliSyotto");
			System.out.println("E Epaonnistunut rankkari");
			System.out.println("K Keltainen kortti");
			System.out.println("U Ulosajo");
			System.out.println("L Lopeta\n");
			System.out.println("Pelaajan pisteet: "+pisteet+"\n");
			System.out.println("Anna tapahtuma:");
			tapahtuma=Lue.merkki();
			System.out.println("");
			
			switch(tapahtuma){
				case 'm':pisteet=pisteet+4;
						 break;
				case 'M':pisteet=pisteet+4;
						 break;
				case 's':pisteet=pisteet+3;
						 break;
				case 'S':pisteet=pisteet+3;
						 break;
				case 'e':pisteet=pisteet-2;
						 break;
				case 'E':pisteet=pisteet-2;
						 break;
				case 'k':pisteet=pisteet-1;
					     break;
				case 'K':pisteet=pisteet-1;
					     break;
				case 'u':pisteet=pisteet-3;
						 break;
				case 'U':pisteet=pisteet-3;
						 break;
				case 'l':break;
				case 'L':break;
				default:System.out.println("Anna oikea komento!");
						break;
			}
		}while(tapahtuma!='L');
		 System.out.println(pisteet);
		 System.exit(0);
	}
	
	public static void kesk1(){

		char tapahtuma;
		int pisteet=0;

		do{
			
			System.out.println("\nKeskikenttapelaaja 1:");
			System.out.println("M Maali");
			System.out.println("S maaliSyotto");
			System.out.println("N Nollapeli");
			System.out.println("V Vastustajalle yli 2 maalia");
			System.out.println("E Epaonnistunut rankkari");
			System.out.println("K Keltainen kortti");
			System.out.println("U Ulosajo");
			System.out.println("L Lopeta\n");
			System.out.println("Pelaajan pisteet: "+pisteet+"\n");
			System.out.println("Anna tapahtuma:");
			tapahtuma=Lue.merkki();
			System.out.println("");

			
			switch(tapahtuma){
				case 'm':pisteet=pisteet+5;
						 break;
				case 'M':pisteet=pisteet+5;
						 break;
				case 's':pisteet=pisteet+3;
						 break;
				case 'S':pisteet=pisteet+3;
						 break;
				case 'n':pisteet=pisteet+1;
						 break;
				case 'N':pisteet=pisteet+1;
						 break;
				case 'v':pisteet=pisteet-1;
						 break;
				case 'V':pisteet=pisteet-1;
						 break;
				case 'e':pisteet=pisteet-2;
					     break;
				case 'E':pisteet=pisteet-2;
					     break;
				case 'k':pisteet=pisteet-1;
					     break;
				case 'K':pisteet=pisteet-1;
					     break;
				case 'u':pisteet=pisteet-3;
						 break;
				case 'U':pisteet=pisteet-3;
						 break;
				case 'l':break;
				case 'L':break;
				default:System.out.println("Anna oikea komento!");
						break;
			}
		}while(tapahtuma!='L');
		 System.out.println(pisteet);
		 System.exit(0);
	}
	
	public int kesk2(int tapahtuma){

	//	char tapahtuma;
		int pisteet=0;

/*		do{
			
			System.out.println("\nKeskikenttapelaaja 2:");
			System.out.println("M Maali");
			System.out.println("S maaliSyotto");
			System.out.println("N Nollapeli");
			System.out.println("V Vastustajalle yli 2 maalia");
			System.out.println("E Epaonnistunut rankkari");
			System.out.println("K Keltainen kortti");
			System.out.println("U Ulosajo");
			System.out.println("L Lopeta\n");
			System.out.println("Pelaajan pisteet: "+pisteet+"\n");
			System.out.println("Anna tapahtuma:");
			tapahtuma=Lue.merkki();
			System.out.println("");*/
			
			switch(tapahtuma){
				case 'm':pisteet=pisteet+5;
						 break;
				case 'M':pisteet=pisteet+5;
						 break;
				case 's':pisteet=pisteet+3;
						 break;
				case 'S':pisteet=pisteet+3;
						 break;
				case 'n':pisteet=pisteet+1;
						 break;
				case 'N':pisteet=pisteet+1;
						 break;
				case 'v':pisteet=pisteet-1;
						 break;
				case 'V':pisteet=pisteet-1;
						 break;
				case 'e':pisteet=pisteet-2;
					     break;
				case 'E':pisteet=pisteet-2;
					     break;
				case 'k':pisteet=pisteet-1;
					     break;
				case 'K':pisteet=pisteet-1;
					     break;
				case 'u':pisteet=pisteet-3;
						 break;
				case 'U':pisteet=pisteet-3;
						 break;
				case 'l':break;
				case 'L':break;
				default:System.out.println("Anna oikea komento!");
						break;
			}
	/*	}while(tapahtuma!='L');
		 System.out.println(pisteet);
		 System.exit(0);*/
		 return pisteet;
	}
	
	public static void kesk3(){

		char tapahtuma;
		int pisteet=0;

		do{
			
			System.out.println("\nKeskikenttapelaaja 3:");
			System.out.println("M Maali");
			System.out.println("S maaliSyotto");
			System.out.println("N Nollapeli");
			System.out.println("V Vastustajalle yli 2 maalia");
			System.out.println("E Epaonnistunut rankkari");
			System.out.println("K Keltainen kortti");
			System.out.println("U Ulosajo");
			System.out.println("L Lopeta\n");
			System.out.println("Pelaajan pisteet: "+pisteet+"\n");
			System.out.println("Anna tapahtuma:");
			tapahtuma=Lue.merkki();
			System.out.println("");
			
			switch(tapahtuma){
				case 'm':pisteet=pisteet+5;
						 break;
				case 'M':pisteet=pisteet+5;
						 break;
				case 's':pisteet=pisteet+3;
						 break;
				case 'S':pisteet=pisteet+3;
						 break;
				case 'n':pisteet=pisteet+1;
						 break;
				case 'N':pisteet=pisteet+1;
						 break;
				case 'v':pisteet=pisteet-1;
						 break;
				case 'V':pisteet=pisteet-1;
						 break;
				case 'e':pisteet=pisteet-2;
					     break;
				case 'E':pisteet=pisteet-2;
					     break;
				case 'k':pisteet=pisteet-1;
					     break;
				case 'K':pisteet=pisteet-1;
					     break;
				case 'u':pisteet=pisteet-3;
						 break;
				case 'U':pisteet=pisteet-3;
						 break;
				case 'l':break;
				case 'L':break;
				default:System.out.println("Anna oikea komento!");
						break;
			}
		}while(tapahtuma!='L');
		 System.out.println(pisteet);
		 System.exit(0);
	}
	
	public static void puol1(){

		char tapahtuma;
		int pisteet=0;

		do{
			
			System.out.println("\nPuolustaja 1:");
			System.out.println("M Maali");
			System.out.println("S maaliSyotto");
			System.out.println("N Nollapeli");
			System.out.println("V Vastustajalle yli 2 maalia");
			System.out.println("E Epaonnistunut rankkari");
			System.out.println("K Keltainen kortti");
			System.out.println("U Ulosajo");
			System.out.println("L Lopeta\n");
			System.out.println("Pelaajan pisteet: "+pisteet+"\n");
			System.out.println("Anna tapahtuma:");
			tapahtuma=Lue.merkki();
			System.out.println("");

			
			switch(tapahtuma){
				case 'm':pisteet=pisteet+6;
						 break;
				case 'M':pisteet=pisteet+6;
						 break;
				case 's':pisteet=pisteet+3;
						 break;
				case 'S':pisteet=pisteet+3;
						 break;
				case 'n':pisteet=pisteet+4;
						 break;
				case 'N':pisteet=pisteet+4;
						 break;
				case 'v':pisteet=pisteet-3;
						 break;
				case 'V':pisteet=pisteet-3;
						 break;
				case 'e':pisteet=pisteet-2;
						 break;
				case 'E':pisteet=pisteet-2;
						 break;
				case 'k':pisteet=pisteet-1;
					     break;
				case 'K':pisteet=pisteet-1;
					     break;
				case 'u':pisteet=pisteet-3;
						 break;
				case 'U':pisteet=pisteet-3;
						 break;
				case 'l':break;
				case 'L':break;
				default:System.out.println("Anna oikea komento!");
						break;
			}
		}while(tapahtuma!='L');
		 System.out.println(pisteet);
		 System.exit(0);
	}
	
	public static void puol2(){

		char tapahtuma;
		int pisteet=0;

		do{
			
			System.out.println("\nPuolustaja 2:");
			System.out.println("M Maali");
			System.out.println("S maaliSyotto");
			System.out.println("N Nollapeli");
			System.out.println("V Vastustajalle yli 2 maalia");
			System.out.println("E Epaonnistunut rankkari");
			System.out.println("K Keltainen kortti");
			System.out.println("U Ulosajo");
			System.out.println("L Lopeta\n");
			System.out.println("Pelaajan pisteet: "+pisteet+"\n");
			System.out.println("Anna tapahtuma:");
			tapahtuma=Lue.merkki();
			System.out.println("");
			
			switch(tapahtuma){
				case 'm':pisteet=pisteet+6;
						 break;
				case 'M':pisteet=pisteet+6;
						 break;
				case 's':pisteet=pisteet+3;
						 break;
				case 'S':pisteet=pisteet+3;
						 break;
				case 'n':pisteet=pisteet+4;
						 break;
				case 'N':pisteet=pisteet+4;
						 break;
				case 'v':pisteet=pisteet-3;
						 break;
				case 'V':pisteet=pisteet-3;
						 break;
				case 'e':pisteet=pisteet-2;
						 break;
				case 'E':pisteet=pisteet-2;
						 break;
				case 'k':pisteet=pisteet-1;
					     break;
				case 'K':pisteet=pisteet-1;
					     break;
				case 'u':pisteet=pisteet-3;
						 break;
				case 'U':pisteet=pisteet-3;
						 break;
				case 'l':break;
				case 'L':break;
				default:System.out.println("Anna oikea komento!");
						break;
			}
		}while(tapahtuma!='L');
		 System.out.println(pisteet);
		 System.exit(0);
	}
	
	public static void puol3(){

		char tapahtuma;
		int pisteet=0;

		do{
			
			System.out.println("\nPuolustaja 3:");
			System.out.println("M Maali");
			System.out.println("S maaliSyotto");
			System.out.println("N Nollapeli");
			System.out.println("V Vastustajalle yli 2 maalia");
			System.out.println("E Epaonnistunut rankkari");
			System.out.println("K Keltainen kortti");
			System.out.println("U Ulosajo");
			System.out.println("L Lopeta\n");
			System.out.println("Pelaajan pisteet: "+pisteet+"\n");
			System.out.println("Anna tapahtuma:");
			tapahtuma=Lue.merkki();
			System.out.println("");
			
			switch(tapahtuma){
				case 'm':pisteet=pisteet+6;
						 break;
				case 'M':pisteet=pisteet+6;
						 break;
				case 's':pisteet=pisteet+3;
						 break;
				case 'S':pisteet=pisteet+3;
						 break;
				case 'n':pisteet=pisteet+4;
						 break;
				case 'N':pisteet=pisteet+4;
						 break;
				case 'v':pisteet=pisteet-3;
						 break;
				case 'V':pisteet=pisteet-3;
						 break;
				case 'e':pisteet=pisteet-2;
						 break;
				case 'E':pisteet=pisteet-2;
						 break;
				case 'k':pisteet=pisteet-1;
					     break;
				case 'K':pisteet=pisteet-1;
					     break;
				case 'u':pisteet=pisteet-3;
						 break;
				case 'U':pisteet=pisteet-3;
						 break;
				case 'l':break;
				case 'L':break;
				default:System.out.println("Anna oikea komento!");
						break;
			}
		}while(tapahtuma!='L');
		 System.out.println(pisteet);
		 System.exit(0);
	}
	
	public static void puol4(){

		char tapahtuma;
		int pisteet=0;

		do{
			
			System.out.println("\nPuolustaja 4:");
			System.out.println("M Maali");
			System.out.println("S maaliSyotto");
			System.out.println("N Nollapeli");
			System.out.println("V Vastustajalle yli 2 maalia");
			System.out.println("E Epaonnistunut rankkari");
			System.out.println("K Keltainen kortti");
			System.out.println("U Ulosajo");
			System.out.println("L Lopeta\n");
			System.out.println("Pelaajan pisteet: "+pisteet+"\n");
			System.out.println("Anna tapahtuma:");
			tapahtuma=Lue.merkki();
			System.out.println("");
			
			switch(tapahtuma){
				case 'm':pisteet=pisteet+6;
						 break;
				case 'M':pisteet=pisteet+6;
						 break;
				case 's':pisteet=pisteet+3;
						 break;
				case 'S':pisteet=pisteet+3;
						 break;
				case 'n':pisteet=pisteet+4;
						 break;
				case 'N':pisteet=pisteet+4;
						 break;
				case 'v':pisteet=pisteet-3;
						 break;
				case 'V':pisteet=pisteet-3;
						 break;
				case 'e':pisteet=pisteet-2;
						 break;
				case 'E':pisteet=pisteet-2;
						 break;
				case 'k':pisteet=pisteet-1;
					     break;
				case 'K':pisteet=pisteet-1;
					     break;
				case 'u':pisteet=pisteet-3;
						 break;
				case 'U':pisteet=pisteet-3;
						 break;
				case 'l':break;
				case 'L':break;
				default:System.out.println("Anna oikea komento!");
						break;
			}
		}while(tapahtuma!='L');
		 System.out.println(pisteet);
		 System.exit(0);
	}
	
		public static void maal1(){

		char tapahtuma;
		int pisteet=0;

		do{
			
			System.out.println("\nMaalivahti:");
			System.out.println("M Maali");
			System.out.println("S maaliSyotto");
			System.out.println("T Torjuttu rankkari");
			System.out.println("N Nollapeli");
			System.out.println("V Vastustajalle yli 2 maalia");
			System.out.println("K Keltainen kortti");
			System.out.println("U Ulosajo");
			System.out.println("L Lopeta\n");
			System.out.println("Pelaajan pisteet: "+pisteet+"\n");
			System.out.println("Anna tapahtuma:");
			tapahtuma=Lue.merkki();
			System.out.println("");

			
			switch(tapahtuma){
				case 'm':pisteet=pisteet+6;
						 break;
				case 'M':pisteet=pisteet+6;
						 break;
				case 's':pisteet=pisteet+3;
						 break;
				case 'S':pisteet=pisteet+3;
						 break;
				case 't':pisteet=pisteet+3;
						 break;
				case 'T':pisteet=pisteet+3;
						 break;
				case 'n':pisteet=pisteet+4;
					     break;
				case 'N':pisteet=pisteet+4;
					     break;
				case 'v':pisteet=pisteet-3;
					     break;
				case 'V':pisteet=pisteet-3;
					     break;
				case 'k':pisteet=pisteet-1;
					     break;
				case 'K':pisteet=pisteet-1;
					     break;
				case 'u':pisteet=pisteet-3;
						 break;
				case 'U':pisteet=pisteet-3;
						 break;			
				case 'l':break;
				case 'L':break;
				default:System.out.println("Anna oikea komento!");
						break;
			}
		}while(tapahtuma!='L');
		 System.out.println(pisteet);
		 System.exit(0);
	}
	
	
	
	}