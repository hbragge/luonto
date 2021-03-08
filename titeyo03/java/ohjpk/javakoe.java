// Javakoe2 - Henri Bragge

public class javakoe{
	
public static void main(String[] args){
		System.out.println("Anna numeroiden maara:");
    	int num = Lue.kluku();
    	System.out.println("Anna kirjainten maara:");
    	int kir = Lue.kluku();
    	System.out.println(arvo.arvo(num, kir));
    }
}


class arvo{
	public static String arvo(int numerot, int kirjaimet){
		
    	
    	
    	char[] numtaulu = new char[numerot];
    	for(int i=0;i<numerot;i++){
    		int sat1=(int)(10*Math.random());
    		String numjoukko="0123456789";
    		numtaulu[i]=numjoukko.charAt(sat1);}
    		
    	String numjono = new String(numtaulu);
    	
    	
    	
    	
    	char[] kirtaulu = new char[kirjaimet];
    	for(int j=0;j<kirjaimet;j++){
    		int sat2=(int)(26*Math.random());
    		String aakkoset="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    		kirtaulu[j]=aakkoset.charAt(sat2);}
    		
    	String kirjono = new String(kirtaulu);
    	
    
    String rekisteri=new String(numjono+"-"+kirjono);
    return rekisteri;
}
}

