import java.util.Scanner;

public class RSA_Cipher_Testing {

	public static void main(String[] args) {
		RSA_Cipher cipher = new RSA_Cipher();
		Scanner scan = new Scanner(System.in);
		
		long[] Keys = cipher.generateKeys();
		long[] PublicKey = {Keys[0], Keys[2]};
		long[] PrivateKey = {Keys[1], Keys[2]};
		//the keys are generated and split up to be entered into the encrypting/decrypting method
		
		System.out.print("Enter the text you wish to encrypt: ");
		String msg = scan.nextLine(); //message to encrypt
		System.out.print("The original text message is: " + msg + "\n");
		
		System.out.print("The encrypted text message is: ");
		long[] EncryptedMessage = cipher.encrypt(msg, PublicKey);
		for (int i = 0; i < EncryptedMessage.length; i++)
			System.out.print(EncryptedMessage[i] + " ");
		//printing out the "ASCII" value of the encrypted message
		
		String DecryptedMessage = cipher.decrypt(EncryptedMessage, PrivateKey);
		//decrypting the just encrypted Message
		System.out.print("\nThe decrypted text message is: " + DecryptedMessage);
		//printing out the decrypted message
		
		scan.close();
	}

}
