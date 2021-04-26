
/*
 * Name: Salwa Abdalla 
 * Class: ICS3U Mr. Radulovic
 * Assignment 1 - Hack Cipher
 * 
 * A main method that includes my method of hacking an encrypted message.
 * First of all, the encryption that this decrypts is the full ASCII table.
 * Please note that a limitation of this, would be if it doesn't use the same system it WILL NOT decode properly
 * 
 */

import java.util.Scanner;

public class HackCipher {

	private static int modulo = 128;
	
	public static void main(String[] args) {
		Cipher cipher = new Cipher();
		Scanner scanner = new Scanner(System.in);

		String decryptstring = " "; // SET THIS TO string you wish to decrypt

		System.out.println("Enter false to being decryption: "); // enter false to begin decryption
		Boolean trigger = scanner.nextBoolean();
		int key_a = 1;
		int key_b = 1;

		while (trigger == false) {
			while (cipher.ValidKey(key_a, HackCipher.modulo) == false) // making sure what we are
																	// decrypting is using a valid
																	// key
				key_a += 1;
			System.out.println(
					"\nDecoded text: '" + cipher.decrypt(decryptstring, key_a, key_b) + "'"); // printing
					// out the potential decrypted text
			System.out.print("Enter true to terminate, or enter false to continue: ");
			trigger = scanner.nextBoolean(); // Write false to continue, or true to terminate
			if (key_b > HackCipher.modulo) { // running through all potential addition for each
											// multiplication factor, then resetting
				key_b = 0;
				key_a += 1;
			} else
				key_b += 1;

		}

		System.out.println("The key used to decrypt the text was: " + key_a + " " + (key_b - 1)); // the
		//key_b is always increased by one at the end, meaning it is one more than it should be
																			
		scanner.close();
	}

}