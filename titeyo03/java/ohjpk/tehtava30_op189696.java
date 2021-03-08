//Tehtävä 30 - Henri Bragge - op189696 - titeyo03

public class tehtava30_op189696 {
	public static void main(String[] args){
		
		
		System.out.println("Anna paivamaara:");
		String a=Lue.rivi();
		String apupv=a.substring(0,2);
		int d=Integer.valueOf(apupv).intValue();
		String apukk=a.substring(3,4);
		int m=Integer.valueOf(apukk).intValue();
		
		System.out.println("Anna maksuaika:");
		int ma=Lue.kluku();
																																																																																																		m+=1;
		int fd=d+ma;
																																																																																																													if(fd>31)
																																																																																																																							fd=22;
		
		System.out.println("Maksupaiva on "+fd+"."+m);
		
		
	}
}