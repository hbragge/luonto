//Mika Saari
public class harjoitus6{
	public static void main(String[] args){
		int a=2, b=2, c=9, d=0;
		System.out.println("a=" +a +"  b=" +b +"  c=" +c +"  d=" +d);
		a++;
		System.out.println("a=" +a +"  b=" +b +"  c=" +c +"  d=" +d);
		d=c%b;
		System.out.println("a=" +a +"  b=" +b +"  c=" +c +"  d=" +d);
		b+=c;
		System.out.println("a=" +a +"  b=" +b +"  c=" +c +"  d=" +d);
		c=c/a+a++;
		System.out.println("a=" +a +"  b=" +b +"  c=" +c +"  d=" +d);
		b=b*a--;
		System.out.println("a=" +a +"  b=" +b +"  c=" +c +"  d=" +d);
	}
}