public class Cipher {

  /** Empfangene Nachricht */
	public static final String MESSAGE = "Myl aymugny R1 qoyhmwbn Rbhyh pcyf Nlzifa.";
  /** Länge des Alphabets */
	public static final int ALPHABET_LEN = 26;

	public static void main (String[] args) {
    // für alle möglichen Schlüßel:
		for (int i=0; i < 128; i++)
      // Gebe den Schlüßel und die entschlüßelte Nachricht aus
			System.out.printf("[%2x]: %42s \n\n", i, dec (MESSAGE, i));
	}

  /** Verschlüßelt eine Zeichenkette, indem sie alle Zeichen separat verschlüßelt */
	public static String enc (String m, int key) {
		var str = "";
		for(char c : m.toCharArray())
			str += enc(c, key);
		return str;
	}

  /** Entschlüßelt eine Zeichenkette, indem sie alle Zeichen separat entschlüßelt */
	public static String dec (String m, int key) {
		return enc (m, -key);
	}

  /** Verschlüßelt ein Zeichen */
	public static char enc (char m, int key){
		return Character.isLetter(m)?
					Character.isUpperCase(m)?
						(char)(0x40+(m-3*key)%ALPHABET_LEN) :
						(char)(0x60+(m+2*key)%ALPHABET_LEN) :
					m;
	}

  /** Entschlüßelt ein Zeichen */
	public static char dec (char m, int key ) {
		return enc (m, -key);
	}
}
