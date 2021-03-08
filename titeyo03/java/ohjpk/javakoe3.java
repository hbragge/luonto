/**
 * Title:      		Koeteht‰v‰ 3
 * Description:		Tulosta syˆtt‰miesi lukujen keskiarvo ja varianssi. Alla
 *					varianssin kaava
 * Copyright:    	Copyright (c) 2003
 * Company:
 * @author Mika Saari
 * @version 1.0
 */
public class javakoe3{
	public static void main(String args[]) {
		int l1, l2, l3, l4, l5;
		System.out.println("Ker‰t‰‰n viisi lukua ");
		System.out.println("Anna luku");
		l1=Lue.kluku();
		System.out.println("Anna luku");
		l2=Lue.kluku();
		System.out.println("Anna luku");
		l3=Lue.kluku();
		System.out.println("Anna luku");
		l4=Lue.kluku();
		System.out.println("Anna luku");
		l5=Lue.kluku();
		double ka=l1+l2+l3+l4+l5;
		ka=ka/5;
		System.out.println("Lukujen keskiarvo on: "+ ka);
		double va=(5*((l1*l1+l2*l2+l3*l3+l4*l4+l5*l5))-((l1+l2+l3+l4+l5)*(l1+l2+l3+l4+l5)))/(5*(5-1));
		System.out.println("Lukujen varianssi on: "+ va);
		//Lis‰ksi voitaisiin antaa k‰ytt‰j‰n p‰‰tt‰‰ montako lukua antaa...
		
	}
}

		
		