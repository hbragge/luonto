//Tehtävä 29 - Henri Bragge - op189696 - titeyo03

     public class tehtava29_op189696 {
        private static int kertoma(int n) {
            if (n <= 1) {
                return 1;
            } else {
                return n*kertoma(n-1);
            }
        }
     
       public static void main(String[] args) {
           System.out.println("Anna jokin luonnollinen luku:");
 		   int luku = Lue.kluku();
           int k = kertoma(luku);
           System.out.println("Luvun " + luku + " kertoma on " + k);
       }
    }