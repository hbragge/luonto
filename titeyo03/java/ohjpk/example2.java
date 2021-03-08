// Tehtävä 22 - Henri Bragge

public class tehtava22_op189696{
    public static void main(String[] args){
    	
    	System.out.println("Anna haluamasi sana:");
    	String jono = Lue.rivi();

    char lista[] = {'a','b'};
    
	for(int i=0; i<jono.length(); i++){
	if(jono.charAt(i)=='a' || jono.charAt(i)=='b' || jono.charAt(i)=='c' || jono.charAt(i)=='d' 
	|| jono.charAt(i)=='e' || jono.charAt(i)=='f' || jono.charAt(i)=='g' || jono.charAt(i)=='h'
	|| jono.charAt(i)=='i' || jono.charAt(i)=='j' || jono.charAt(i)=='k' || jono.charAt(i)=='l'
	|| jono.charAt(i)=='m' || jono.charAt(i)=='n' || jono.charAt(i)=='o' || jono.charAt(i)=='p'
	|| jono.charAt(i)=='q' || jono.charAt(i)=='r' || jono.charAt(i)=='s' || jono.charAt(i)=='t'
	|| jono.charAt(i)=='u' || jono.charAt(i)=='w' || jono.charAt(i)=='v' || jono.charAt(i)=='x' || jono.charAt(i)=='y'
	|| jono.charAt(i)=='z' || jono.charAt(i)=='å' || jono.charAt(i)=='ä' || jono.charAt(i)=='ö' 
	|| jono.charAt(i)=='A' || jono.charAt(i)=='B' || jono.charAt(i)=='C' || jono.charAt(i)=='D' 
	|| jono.charAt(i)=='E' || jono.charAt(i)=='F' || jono.charAt(i)=='G' || jono.charAt(i)=='H'
	|| jono.charAt(i)=='I' || jono.charAt(i)=='J' || jono.charAt(i)=='K' || jono.charAt(i)=='L'
	|| jono.charAt(i)=='M' || jono.charAt(i)=='N' || jono.charAt(i)=='O' || jono.charAt(i)=='P'
	|| jono.charAt(i)=='Q' || jono.charAt(i)=='R' || jono.charAt(i)=='S' || jono.charAt(i)=='T'
	|| jono.charAt(i)=='U' || jono.charAt(i)=='W' || jono.charAt(i)=='V' || jono.charAt(i)=='X' || jono.charAt(i)=='Y'
	|| jono.charAt(i)=='Z' || jono.charAt(i)=='Å' || jono.charAt(i)=='Ä' || jono.charAt(i)=='Ö')
	continue;
	System.out.print(jono.charAt(i));
	}System.out.println("");
}
}