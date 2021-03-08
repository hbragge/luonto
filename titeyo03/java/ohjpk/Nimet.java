// Opiskelijoiden tulostaminen satunnaisjärjestyksessä
// Markku Nevanranta

public class Nimet {
    public static void main(String[] args){
        
        String[] opiskelijat={"Kalle Kehveli","Ville Vekseli",
        "Liisa Liimatainen","Virpi Vilkas"};
        int pituus=opiskelijat.length;
        boolean[] varattu=new boolean[pituus];
        int sat, kpl=0;
        
        while (kpl<pituus){
            sat = (int)(pituus*Math.random());   
            if (!(varattu[sat])){
            	System.out.println(opiskelijat[sat]);
	            varattu[sat]=true;
        	    kpl++;
        	 }
        }    
    }
}                
                       
        
