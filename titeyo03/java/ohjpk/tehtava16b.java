// Tehtävä 16 - Henri Bragge titeyo03 - op 189696
    public class tehtava16b{
        public static void main(String[] args) {
        
        int kierros=1, vastaus, arvaus, saldo=0;
        vastaus=(int)(10*Math.random());
do {
        System.out.println("Anna kokonaisluku:");
        arvaus=Lue.kluku();
        if (arvaus==vastaus && kierros==1) {
        System.out.println("Voitit 30e");
        kierros=1;
        saldo=saldo+30;}
        else System.out.println("Arvasit vaarin"+vastaus);
        kierros++;}
        if (arvaus==vastaus && kierros==2) {
        System.out.println("Voitit 20e");
        kierros=1;
        saldo=saldo+30;}
        else System.out.println("Arvasit taas vaarin"+vastaus);
        kierros++;}
        if (arvaus==vastaus && kierros==1) {
        System.out.println("Voitit 30e");
        kierros=1;
        saldo=saldo+30;}
        else System.out.println("Arvasit taas kerran vaarin"+vastaus);
        kierros++;}
        while (kierros<=3);
    }
}