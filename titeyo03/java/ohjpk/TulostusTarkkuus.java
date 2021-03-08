// Muotoilee tulostuksen kokonaisosaa ja
// desimaaliosaa NumberFormat-luokkaa käyttäen
// Markku Nevanranta

import java.text.NumberFormat;

public class TulostusTarkkuus{
	public static void main(String[] args){
		NumberFormat f= NumberFormat.getInstance();
		f.setMaximumIntegerDigits(2);
		f.setMinimumIntegerDigits(2);
		f.setMaximumFractionDigits(2);
		f.setMinimumFractionDigits(2);
		for (int i=1; i<10; i++){
			for (int j=1; j<10; j++)
				System.out.print(f.format(10.0*i/j)+"  ");
			System.out.println();
		}
	}
}