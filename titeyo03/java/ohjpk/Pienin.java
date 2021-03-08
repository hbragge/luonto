// Tulosta syötetyistä luvuista pienin ja suurin
// Markku Nevanranta

public class Pienin {
    public static void main(String[] args) {

        int pienin, suurin, luku;

        System.out.println("Anna luku ");
        pienin = suurin = luku = Lue.kluku();

        while (luku != 0) {
            if (luku<pienin) pienin=luku;
            if (luku>suurin) suurin=luku;
            System.out.println("Anna luku ");
            luku = Lue.kluku();
        }
        System.out.println("Pienin luvuista on: "+pienin);
        System.out.println("Suurin luvuista on: "+suurin);
    }
}