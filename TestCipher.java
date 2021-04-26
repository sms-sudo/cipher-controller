
/*
 * Name: Salwa Abdalla 
 * Class: ICS3U Mr. Radulovic
 * Assignment 1 - Testing Cipher Encryption
 * 
 * A main method that includes my method of hacking an encrypted message.
 * Note that this used a slightly altered Cipher class, as I had to change the encryption table.
 */

import java.util.Scanner;

public class TestCipher {

	public static void main(String[] args) {
		Cipher cipher = new Cipher();
		Scanner scanner = new Scanner(System.in);

		String encryptString = scanner.next(); // STEP ONE: set a value to the string you wish to
												// encrypt
		String encryptedString;
		String encryptedString2;

		int key_a = 98;
		int key_b = 89;

		// STEP TWO: use your preferred encryption method
		encryptedString = cipher.encrypt(encryptString, key_b); // this is the addition only
																// encryption as it accepts only ONE
																// keys
		// The method with two arguments: the string being decrypted, and an integer that will be
		// the addition offset
		encryptedString2 = cipher.encrypt(encryptString, key_a, key_b); // this is the addition and
																		// multiplication encryption
																		// as it accepts TWO keys
		// The method with three arguments: the string being decrypted, an integer for
		// multiplication and an integer for addition, in that order

		System.out.println(encryptedString2);
		
		// REMEMBER, to decrypt the addition encryption, you are able to use both decrypt methods
		// (as long as key_a is set to 1)
		// HOWEVER, to decrypt the multiplicatio and addition encrytion, you MUST use the second
		// decrypt method with three arguments

		String decryptString = scanner.next(); // STEP THREE: set a value to the string you wish to
												// decrypt
		String decryptedString;
		String decryptedString2;

		// STEP FOUR: use your preferred decryption method
		decryptedString = cipher.decrypt(decryptString, key_b); // this is the addition only
																// encryption as it accepts only ONE
																// keys
		// The method with two arguments: the string being decrypted, and an integer that was the
		// addition offset
		decryptedString2 = cipher.decrypt(decryptString, key_a, key_b); // this is the addition and
																		// multiplication encryption
																		// as it accepts TWO keys
		// The method with three arguments: the string being decrypted, an integer for
		// multiplication and an integer for addition, in that order

		
		//To see the encoded and decoded strings, please create a System.out.print statement and 
		//refer to the string you wish to see
		
		scanner.close();
	}

}