// Tehtävä 20 - Henri Bragge - op189696 - titeyo03

public class tehtava20_op189696{
    public static void main(String[] args){
    	
    	System.out.println("Anna haluamasi sana:");
    	String jono = Lue.rivi();
    	
    	char[] mtaulu = jono.toCharArray();
    	
    	for (int i=0; i<mtaulu.length; i++)
    		if (mtaulu[i] == 'ä') mtaulu [i] = 'a';
    		
    	int i=0;
    	boolean[] varattu=new boolean[mtaulu.length];
    	for(i=0; i<mtaulu.length; i++){
    	int s=((int)(mtaulu.length*Math.random()));
    		if(!(varattu[s])){
    		mtaulu[i]=mtaulu[s];
    		varattu[s]=true;
    		

    		jono = new String(mtaulu);
    		System.out.print(jono+" ");}
    		
    }
}
}