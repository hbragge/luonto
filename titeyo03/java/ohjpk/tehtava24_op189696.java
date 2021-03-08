// Tehtävä 24 - Henri Bragge - op189696 - titeyo03

public class tehtava24_op189696{
    public static void main(String[] args){
    	
    	System.out.println("Henkilotunnus!:");
    	String jono = Lue.rivi();
    	
    	char[] lause = jono.toCharArray();
    	char konsonantit[] = {'b','c','d','f','g','h','j','k','l','m','n','p','q','r','s','t','w','x','z'};
    	
    	for(int i=0; i<konsonantit.length; i++){
    		b=i+1;
			if((lause[i])==(konsonantit[]) && (lause[b])==(konsonantit[]))
			System.out.print(lause[i]);
			}System.out.println("");
		}
	}