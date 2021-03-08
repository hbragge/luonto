// Tehtävä 23 - Henri Bragge - op189696 - titeyo03

public class tehtava23_op189696{
    public static void main(String[] args){
    	
    	System.out.println("Henkilotunnus!:");
    	String jono = Lue.rivi();
    	
    	char[] mtaulu = jono.toCharArray();
    	
		int pva = Integer.parseInt(jono.substring(0,2));
		int kk = Integer.parseInt(jono.substring(2,4));
		int vsi = Integer.parseInt(jono.substring(4,6));
		int jaettava = Integer.parseInt(jono.substring(0,6)+jono.substring(7,10));
		char lista[] = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','H','J','K','L','M','N','P','R','S','T','U','V','W','X','Y'};
		int tarkaste = jaettava%31;
		
		if(pva<32 && kk<13 && jono.length()==11 && jono.charAt(10)==lista[tarkaste])
			System.out.println("Sotu on laillinen");
			else System.out.println("Sotu on invaliidi");
			
		
		}
	}
		
    	