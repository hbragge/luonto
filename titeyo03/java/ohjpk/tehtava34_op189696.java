// Tehtava 34 - Henri Bragge - op189696



class Ruoka{
private String elintarvikeNimi;
private int elintarvikeMaara;
private String elintarvikeOstopvm;

public Ruoka(String name, int qnt, String pvm){
elintarvikeNimi = name;
elintarvikeMaara = qnt;
elintarvikeOstopvm = pvm;
}

public void tulosta(){
System.out.println(elintarvikeNimi+"  "+elintarvikeMaara+"  "+elintarvikeOstopvm);
}

public static void main(String[] args){
Ruoka ruoka1 = new Ruoka("Maito",4, "15.12.2003");
Ruoka ruoka2 = new Ruoka("Nakki",20, "1.1.2004");

ruoka1.tulosta();
ruoka2.tulosta();
}
}
public class tehtava34_op189696{}