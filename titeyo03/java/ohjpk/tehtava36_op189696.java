// Tehtava 36 - Henri Bragge - op189696



class Osake{
private int osakeMaara;
private int osakeHinta;
private int osakeOstoera;

public Osake(int qnt1, int prc1){
osakeMaara = qnt1;
osakeHinta = prc1;
}

public Osake(int qnt2, int prc2, int oe2){
osakeMaara = qnt2;
osakeHinta = prc2;
osakeOstoera = oe2;
}

public static void main(String[] args){
Osake osake1 = new Osake(5, 200);
Osake osake2 = new Osake(3, 250, 2);

int arvo=5*200+3*250;
System.out.println(arvo);
}
}
public class tehtava36_op189696{}