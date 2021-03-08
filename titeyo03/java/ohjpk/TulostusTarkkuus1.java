// Muotoilee tulostuksen kokonaisosaa ja
// desimaaliosaa NumberFormat-luokkaa käyttäen
// Markku Nevanranta

import java.text.DecimalFormat;

public class TulostusTarkkuus1{
	public static void main(String[] args){
		DecimalFormat f= new DecimalFormat("00.00");
		
		for (int i=1; i<10; i++){
			for (int j=1; j<10; j++)
				System.out.print(f.format(10.0*i/j)+"  ");
			System.out.println();
		}
	}
}