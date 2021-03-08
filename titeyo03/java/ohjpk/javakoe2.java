/**
 * Title:      		Koeteht‰v‰ 2
 * Description:		Laadi ohjelma, joka antaa satunnaisesti valitun kuukauden nimen
 *					ja satunnaisen p‰iv‰m‰‰r‰n kyseisest‰ kuukaudesta(esim Toukokuu18).
 * Copyright:    	Copyright (c) 2003
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
public class javakoe2{
	public static void main(String args[]) {
		int pv=0;
		int kk=(int)(12*Math.random());//Arvotaan kuukausi
		switch(kk){
			case 0: System.out.print("Tammikuu "); pv=31; break;
			case 1: System.out.print("Helmikuu "); pv=28;break;
			case 2: System.out.print("Maaliskuu "); pv=31;break;
			case 3: System.out.print("Huhtikuu "); pv=30;break;
			case 4: System.out.print("Toukokuu "); pv=31;break;
			case 5: System.out.print("Kes‰kuu "); pv=30;break;
			case 6: System.out.print("Hein‰kuu "); pv=31;break;
			case 7: System.out.print("Elokuu "); pv=31;break;
			case 8: System.out.print("Syyskuu "); pv=30;break;
			case 9: System.out.print("Lokakuu "); pv=31;break;
			case 10: System.out.print("Marraskuu "); pv=30;break;
			case 11: System.out.print("Joulukuu "); pv=31;break;
			default: System.out.print("Virheellinen kuukausi!");
		}
		//Arvotaan p‰iv‰
		int pp=(int)(pv*Math.random())+1;
		System.out.print(pp +"\n");		
			
	}
}
				