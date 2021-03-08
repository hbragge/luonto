// Tehtävä 25 - Henri Bragge titeyo03 - op189696

import java.text.NumberFormat;
public class tehtava25 {
	public static void main(String[] args){
		
		
		System.out.println("Anna javaharjoituksiin kayttamasi aika maanantaina (hh.mm):");
		String ma=Lue.rivi();
		String apuhhma=ma.substring(0,2);
		int tunnitma=Integer.valueOf(apuhhma).intValue();
		String apummma=ma.substring(3,5);
		int minuutitma=Integer.valueOf(apummma).intValue();
		
		System.out.println("Anna javaharjoituksiin kayttamasi aika tiistaina (hh.mm):");
		String ti=Lue.rivi();
		String apuhhti=ti.substring(0,2);
		int tunnitti=Integer.valueOf(apuhhti).intValue();
		String apummti=ti.substring(3,5);
		int minuutitti=Integer.valueOf(apummti).intValue();
		
		System.out.println("Anna javaharjoituksiin kayttamasi aika keskiviikkona (hh.mm):");
		String ke=Lue.rivi();
		String apuhhke=ke.substring(0,2);
		int tunnitke=Integer.valueOf(apuhhke).intValue();
		String apummke=ke.substring(3,5);
		int minuutitke=Integer.valueOf(apummke).intValue();
		
		System.out.println("Anna javaharjoituksiin kayttamasi aika torstaina (hh.mm):");
		String to=Lue.rivi();
		String apuhhto=to.substring(0,2);
		int tunnitto=Integer.valueOf(apuhhto).intValue();
		String apummto=to.substring(3,5);
		int minuutitto=Integer.valueOf(apummto).intValue();
		
		System.out.println("Anna javaharjoituksiin kayttamasi aika perjantaina (hh.mm):");
		String pe=Lue.rivi();
		String apuhhpe=pe.substring(0,2);
		int tunnitpe=Integer.valueOf(apuhhpe).intValue();
		String apummpe=pe.substring(3,5);
		int minuutitpe=Integer.valueOf(apummpe).intValue();
		
		System.out.println("Anna javaharjoituksiin kayttamasi aika lauantaina (hh.mm):");
		String la=Lue.rivi();
		String apuhhla=la.substring(0,2);
		int tunnitla=Integer.valueOf(apuhhla).intValue();
		String apummla=la.substring(3,5);
		int minuutitla=Integer.valueOf(apummla).intValue();
		
		System.out.println("Anna javaharjoituksiin kayttamasi aika sunnuntaina (hh.mm):");
		String su=Lue.rivi();
		String apuhhsu=su.substring(0,2);
		int tunnitsu=Integer.valueOf(apuhhsu).intValue();
		String apummsu=su.substring(3,5);
		int minuutitsu=Integer.valueOf(apummsu).intValue();
		
		
		double aikahh=(tunnitma+tunnitti+tunnitke+tunnitto+tunnitpe+tunnitla+tunnitsu);
		double aikamm=(minuutitma+minuutitti+minuutitke+minuutitto+minuutitpe+minuutitla+minuutitsu);
		if(aikamm>=60){
			aikahh=(aikahh+((aikamm/60)-(aikamm%60)/60));
			aikamm=(aikamm%60);
			
			}
			
			int i=1;
			NumberFormat f= NumberFormat.getInstance();
			f.setMinimumFractionDigits(0);
			System.out.println("Javaharkkoihin kayttamasi yhteisaika on "+f.format(aikahh)+":"+f.format(aikamm));
			
		
		
		
	}
}