/*
 * Name: Salwa Abdalla 
 * Class: ICS3U Mr. Radulovic
 * Assignment 1 - Cipher Encryption
 * 
 * A collection of public methods to both encrypt and decrypt all possible strings 
 * using the ASCII characters.
 * The first two methods (encrypt/decrypt (String x, int key)) are encryption and 
 * decryption using only addition.
 * The second two methods (encrypt/decrypt (String x, int key_a, int key_b)) are encryption and 
 * decryption using both addition and multiplication.
 * 
 * The last three methods are used in the second two methods to simplify the code within them.
 */

public class Cipher {

	private static int modulo = 128;

	public String encrypt(String x, int key) { // String x is what is being encoded, int key is the
												// offset of the characters
		char[] EncryptedX = x.toCharArray(); // making a mutable array with each character separated
												// as strings are immutable
		String enx;
		for (int i = 0; i < x.length(); i++) // for each character:
			EncryptedX[i] = (char) ((EncryptedX[i] + key) % Cipher.modulo); // using the formula by
			// offsetting ASCII int value by key and modding by number of characters
		enx = String.valueOf(EncryptedX); // combining the encrypted char array into a string
		return enx;
	}

	public String decrypt(String y, int key) {
		char[] DecryptedX = y.toCharArray();
		String dex;
		int add = 0; // a value to deter negative ASCII values that could arise
		for (int i = 0; i < y.length(); i++) {
			if (DecryptedX[i] - key < 0) // if the key is larger than the original ASCII value it
											// will be a negative value
				add = Cipher.modulo; // make the add value the max character value to wrap around
										// the table
			DecryptedX[i] = (char) (((DecryptedX[i] - key) % Cipher.modulo) + add);
			add = 0; // reset the character wrap around to 0 for the next instances
		}
		dex = String.valueOf(DecryptedX);
		return dex;
	}

	public String encrypt(String x, int key_a, int key_b) {
		char[] EncryptedX = x.toCharArray();
		String enx;

		if (ValidKey(key_a, Cipher.modulo) == false) // uses the valid key method to check whether
														// my decrypt method would be able to
														// decrypt
			return "key_a is not a valid key multiplier"; // unless my method could decrypt, this
															// method does not encrypt

		else // if my method can decrypt, the method continues to encrypt
			for (int i = 0; i < x.length(); i++)
				EncryptedX[i] = (char) (((EncryptedX[i] * key_a) + key_b) % Cipher.modulo); // using
				// the formula of multiplying, adding and then modding
		enx = String.valueOf(EncryptedX);
		return enx;
	}

	public String decrypt(String y, int key_a, int key_b) {
		char[] DecryptedX = y.toCharArray();
		String dnx;
		int inversekey_a; // initialize the inverse of key a
		int add = 0; // initialize and set value for deterring negative ASCII values

		if (ValidKey(key_a, 128) == false) // checks that the decryption key_a is a valid key
			return "key_a is not a valid key multiplier";

		else
			inversekey_a = Inversekey_a(key_a, Cipher.modulo); // using the Inversekey_a method to
																// find the inverse key
		for (int i = 0; i < y.length(); i++) {
			if (DecryptedX[i] - key_b < 0) // checks for the negative ASCII cases
				add = Cipher.modulo; // deters by wrapping around
			DecryptedX[i] = (char) ((((DecryptedX[i] - key_b) * inversekey_a) % Cipher.modulo)
					+ add);
			add = 0;
		}
		dnx = String.valueOf(DecryptedX);
		return dnx;
	}

	public int findgcd(int a, int b) { // findgcd() finds the greatest common denominator [using
										// euclidean theorem]
		while (b != 0) { // while one of the numbers is not 0, continue running
			int temp = b; // using a temp value to be able to shift the values from b to temp to a
			b = a % b; // using the modulo operator to set b as the remainder of a mod b (to
						// simplifiy the number as much as possible)
			a = temp; // shifting the old value of b to a, and if b still does not equal zero repeat
		}
		return a; // the greatest common denominator of any number and zero is the number, hence
					// when b == 0, a is the gcd
	}

	public int Inversekey_a(int key_a, int mod) {
		int inversekey_a = 0; // initializing the value the inverse of key_a

		while (((key_a * inversekey_a) % mod) != 1 && inversekey_a < Cipher.modulo) // while the
		// expression is not true, and a inverse is less than the number of characters
			inversekey_a += 1; // increase until a value for inverse a is found
		return inversekey_a;
	}

	public boolean ValidKey(int key_a, int mod) { // a combination of both findgcd() and
											// Inversekey_a() to minimize error within the method
		if (findgcd(key_a, mod) == 1 && Inversekey_a(key_a, mod) != 0) // if both are true, it
																		// verifies its' validity
			return true;
		else // if one is false, it is not a valid key
			return false;
	}
}