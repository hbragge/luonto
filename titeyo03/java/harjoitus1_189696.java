/* Tietorakenteet 2006
 * Harjoitus 1 (laaja)
 *
 * Henri Bragge 189696
 * titeyo03
 */
 
import java.util.*;
import java.sql.Timestamp;

public class harjoitus1_189696 {
	static final int koko = 40000;
    static int[] temp = new int[koko];
    static long timesort1_1, timesort1_2, timesort1_3, timesort2_1,
    			timesort2_2, timesort2_3, timesort3_1, timesort3_2, timesort3_3;
    
    
    public static void main (String[] args) {
       
       

       // Testi 1

 		
       		// menetelmä 1
       timesort1_1 = sort1(alusta(1));
       
       		// menetelmä 2
       timesort1_2 = sort2(alusta(1));
       
       		// menetelmä 3
       timesort1_3 = sort3(alusta(1));
       
       
       // Testi 2
       
       
       		// menetelmä 1
       timesort2_1 = sort1(alusta(2));
 			
       		// menetelmä 2
       timesort2_2 = sort2(alusta(2));
       
       		// menetelmä 3
       timesort2_3 = sort3(alusta(2));
       
       
       // Testi 3
       
       
       		// menetelmä 1
       timesort3_1 = sort1(alusta(3));
 			
       		// menetelmä 2
       timesort3_2 = sort2(alusta(3));
       
       		// menetelmä 3
       timesort3_3 = sort3(alusta(3));
       
       System.out.println("Testi 1 (data melkein järjestyksessä):");
       System.out.println("1: "+timesort1_1);
       System.out.println("2: "+timesort1_2);
       System.out.println("3: "+timesort1_3);
       
       System.out.println("Testi 2 (data satunnaisessa järjestyksessä):");
       System.out.println("1: "+timesort2_1);
       System.out.println("2: "+timesort2_2);
       System.out.println("3: "+timesort2_3);
       
       System.out.println("Testi 3 (data vastakkaisessa järjestyksessä):");
       System.out.println("1: "+timesort3_1);
       System.out.println("2: "+timesort3_2);
       System.out.println("3: "+timesort3_3);
       
    }
      
    public static long sort1(int[] table) {
       Date d = new Date();
       Timestamp time = new Timestamp(d.getTime());
       long time1 = time.getTime();
        
    		
    			
        int i = 0; 
		int k = table.length - 1; 
		while (i < k) { 
			int min = i; 
			int max = i; int j; 
			for (j = i + 1; j <= k; j++) { 
				 if (table[j] < table[min]) { 
							min = j; 
						} 
							
							if (table[j] > table[max]) { 
							max = j; 
							} 
			} 
			int T = table[min]; 
			table[min] = table[i]; 
			table[i] = T; 
			if (max == i) { 
				T = table[min]; 
				table[min] = table[k]; 
				table[k] = T; 
			} 
			else { 
				T = table[max]; 
				table[max] = table[k]; 
				table[k] = T; 
			}  
			i++; 
			k--;
			}
        
       
        
       d = new Date();
       time = new Timestamp(d.getTime());
       long time2 = time.getTime();
       long sorttime = time2 - time1;
       
       
 			
       return sorttime;
 
    }
    
    public static long sort2(int[] table) {
    		
    		
    	
            
        Date d = new Date();
        Timestamp time = new Timestamp(d.getTime());
        long time1 = time.getTime();
     
        Arrays.sort(table);
    			
        d = new Date();
        time = new Timestamp(d.getTime());
        long time2 = time.getTime();
        
        long sorttime = time2 - time1;
        
        
        return sorttime;
       
     }
    
    public static long sort3(int[] table) {
		
 			
		List<Integer> lista2 = new ArrayList<Integer>();
    	for (int i=0; i<table.length; i++)
            lista2.add(table[i]);
   
	    Date d = new Date();
	    Timestamp time = new Timestamp(d.getTime());
	    long time1 = time.getTime();
	    
	    Collections.sort(lista2);
	    
	    d = new Date();
	    time = new Timestamp(d.getTime());
	    long time2 = time.getTime();
	    
	    long sorttime = time2 - time1;
	    
        return sorttime;
	   
 }
 
 	public static int[] alusta(int a){
 		
 		int[] taulu = new int[koko];
 		
 		for(int i=0;i<taulu.length;i++){
	   		taulu[i]=i;
	   		temp[i]=i;
	   		}
 		
 		
 		// Testidata 1 (melkein järjestyksessä):
 		
 		if(a == 1){
 			
	 		for(int i=0;i<taulu.length-12;i+=12){
		   		int tmp = taulu[i];
		   		taulu[i] = taulu[i+12];
		   		taulu[i+12] = tmp;
		   		}
	   		
	   	}else if(a == 2) {
	   		
	   		// Testidata 2 (satunnaisessa järjestyksessä):
       
	       List<Integer> lista = new ArrayList<Integer>();
	       for (int i=0; i<taulu.length; i++)
	           lista.add(taulu[i]);
	       Collections.shuffle(lista);
	       for (int i=0; i<lista.size(); i++){
	           taulu[i] = lista.get(i);  
	       		}	
       	
       }else if(a == 3) {
       	
       // Testidata 3 (vastakkaisessa järjestyksessä):
       
	       	for(int i=0;i<taulu.length;i++){
	       		
		   		taulu[i] = temp[taulu.length-1-i];		

		   		}	
	   		}
	   	
	   	return taulu;	
	   		
 	}
    
    
}
