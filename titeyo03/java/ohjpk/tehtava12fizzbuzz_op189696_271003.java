// Tehtava 12 - Henri Bragge - op189696

public class tehtava12{
	public static void main(String[] args){
		
int luku=1, i=0;
double keskiarvo, summa=0;

while (luku!=0){
System.out.println("Anna kokonaisluku:");
luku=Lue.kluku();
i++;
summa=summa+luku;
keskiarvo=summa/i;

	if (luku>5) {System.out.println("Luku on suurempi kuin 5");}
	else System.out.println("Luku on 5 tai pienempi");
	if (luku%10==0) {System.out.println("Luku on jaollinen kymmenella");}
	else System.out.println("Luku ei ole jaollinen kymmenella");
	if (luku>summa) {System.out.println("Antamasi luku oli edellista suurempi");}
	else System.out.println("Luku on pienempi tai yhtasuuri kuin edellinen");
    System.out.println("Lukujen keskiarvo on "+keskiarvo);}
}
}
