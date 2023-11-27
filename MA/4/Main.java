public class Main {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";

	public static void main(String[] args) {
		int a, b, c, d;
		for (int i = 0; i < 25; i++) {
			a = i % 5;
			b = (i-a)/5;

			// calculate (a + b)^5
			c = a + b;
			c = c * c * c * c * c;
			c %= 5;

			// calculate a^5 + b^5
			d = a*a*a*a*a + b*b*b*b*b;
			d %= 5;

			System.out.printf ("[%2d]: %s (%d + %d)^5 %s %d^5 + %d^5 mod 5 (= %d) %s\n", i, c == d? ANSI_GREEN : ANSI_RED, a, b, c == d? "==" : "!=", a, b, c, ANSI_RESET);
		}

	}
}