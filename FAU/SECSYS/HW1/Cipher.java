public class Cipher {

	/** Recieved Message */
	public static final String MESSAGE = "Myl aymugny R1 qoyhmwbn Rbhyh pcyf Nlzifa.";

	/** Länge des Alphabets */
	public static final int ALPHABET_LEN = 26;

	/** Pre-Computed En-/De-Cryption offsets*/
	public static final int[] UC_ENC_OFFSET = {0, 75, 72, 69, 66, 63, 60, 57, 54, 51, 48, 45, 42, 39, 36, 33, 30, 27, 24, 21, 18, 15, 12,  9,  6,  3};
	public static final int[] UC_DEC_OFFSET = {0,  3,  6,  9, 12, 15, 18, 21, 24, 27, 30, 33, 36, 39, 42, 45, 48, 51, 54, 57, 60, 63, 66, 69, 72, 75};
	public static final int[] LC_ENC_OFFSET = {0,  2,  4,  6,  8, 10, 12, 14, 16, 18, 20, 22, 24,  0,  2,  4,  6,  8, 10, 12, 14, 16, 18, 20, 22, 24};
	public static final int[] LC_DEC_OFFSET = {0, 24, 22, 20, 18, 16, 14, 12, 10,  8,  6,  4,  2,  0, 24, 22, 20, 18, 16, 14, 12, 10,  8,  6,  4,  2};

	public static void main (String[] args) {
		for (int i=0; i < 26; i++)
			System.err.printf("[%2d]: %42s \n", i, dec (MESSAGE, i));
	}

	/** Encodes a String, character by character */
	public static String enc (String m, int key) {
		var str = "";
		for(char c : m.toCharArray())
			str += enc(c, key);
		return str;
	}

	/** Decodes a String, character by character */
	public static String dec (String m, int key) {
		var str = "";
		for(char c : m.toCharArray())
			str += dec(c, key);
		return str;
	}

	/** Encodes a Character */
	public static char enc (char m, int key){
		return Character.isLetter(m)?
					Character.isUpperCase(m)?
						(char)(0x41 + ( m + UC_ENC_OFFSET[key] - 0x41 ) % ALPHABET_LEN) :
						(char)(0x61 + ( m + LC_ENC_OFFSET[key] - 0x61 ) % ALPHABET_LEN) :
					m;
	}

	/** Decodes a Character */
	public static char dec (char m, int key ) {
		return Character.isLetter(m)?
				Character.isUpperCase(m)?
						(char)(0x41 + ( m + UC_DEC_OFFSET[key] - 0x41 ) % ALPHABET_LEN) :
						(char)(0x61 + ( m + LC_DEC_OFFSET[key] - 0x61 ) % ALPHABET_LEN) :
				m;
	}
}
