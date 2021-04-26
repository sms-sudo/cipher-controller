/*
 * Name: Salwa Abdalla 
 * Class: ICS3U Mr. Radulovic
 * Assignment 1 - RSA Cipher Encryption [Bonus]
 * 
 * A collection of public methods to both encrypt and decrypt all possible strings 
 * using the ASCII characters, with a higher security to make it harder to hack.
 * 
 * All methods are commented within, and all work towards generating a public and private key
 * that is valid to encrypt and decrypt any message represented by the ASCII table.
 */

import java.util.Random;

public class RSA_Cipher {
	
	public long[] generateKeys() {
		Random random = new Random();
		//imports random to help generate a "random" prime
		long p = givePrime((long)random.nextInt(10000) + 1);
		long q = givePrime((long)random.nextInt(10000) + 1);
		while (q == p)
			q = givePrime(random.nextInt(100));
		//generates two random primes, and makes sure they are different
		long n = p * q;
		//calculates the value of n
		long phi = (p-1)*(q-1);
		//calculates the value of phi n
		long start = phi;
		//start is the addition version of k (in d = (k*phi + 1)/e)
		long Candidates = findCandidate(phi, start);
		//a candidate to help derive both exponents as we know that:
		// PublicEx * PrivateEx = 1 % phi n
		long[] Exponents = findFactors(Candidates, phi);
		while (Exponents[0] == 0 || Exponents[1] == 0) {
			start += phi; //it increased by phi, meaning it increased the multiplicative value by one
			Candidates = findCandidate(phi, start);
			Exponents = findFactors(Candidates, phi);
		}
		//uses a while look to find a value of e and d that prove the formula to be true
		//and that they are relatively prime to phi n
		long ex = Exponents[0];
		long d = Exponents[1];
		//returns the public and private exponents
		long[] Keys = {ex, d, n};
		return Keys;
		//generates the key and returns the value necessary to excrypt and decrypt
	}
	
	public long givePrime(long a) {
		//used to generate p and q
		long prime = Math.abs(a) + 1;
		//to generate a prime, it takes the random positive int value and adds one
		while (true) { 
		//to continue the loop until a prime is found, the only way to exit is to find a prime
			if (checkPrime(prime) == true)
				return prime;
			//if the number is prime, it returns it
			else
				prime ++;
			//if the number is not prime, it increments by one until it is
		}
	}
	
	public boolean checkPrime(long num) {
		//used to reduce the error within producing the private and public exponent
		if (num == 2)
			return true;
		//if two, it is prime, which wouldn't be caught in the following loop
		for (int i = 2; i <= Math.sqrt(num); i++) {
			//goes up to the sqrt because the largest number before it repeats factors is sqrt^2
	        if (num % i == 0)
	            return false;
	        //if the number is divisible by a number greater than 2, it returns not prime
	    }
	    return true;
	    //returns prime once it has checked all possible factors
	}
	
	public long[] encrypt(String message, long[] key) {
		//the method used to encrypt character per character (ASCII to usually outofbounds index)
		//therefore, I made the method return a value of longs to hold the value
		long[] Encrypted = new long[message.length()];
		//creates an array of longs the length of the method
		for (int i = 0; i < message.length(); i++) 
			Encrypted[i] = (long)(FastEx(message.charAt(i), key[0], key[1]));
		//for each character, used the formula to encode
		return Encrypted;
	}
	
	public String decrypt(long[] message, long[] key) {
		//the method used to decrypt the out of bound indexes to original ASCII
		char[] Decrypted = new char[message.length];
		//creates an array of longs the length of the method
		for (int i = 0; i < message.length; i++) 
			Decrypted[i] = (char) (FastEx(message[i], key[0], key[1]));
		//decrypts each character using the formula to decode
		String decrypted = String.valueOf(Decrypted);
		return decrypted;
	}

	public long findCandidate(long phi_n, long start) {
		long candidate = start;
		//the candidate (another representation of future e*d) must be fufill this expression:
		// candidate % phi n = 1, and candidate = e * d so e and d are relatively prime to phi n
		if (candidate % phi_n != 1); // if the candidate does not fufill the first expression
			candidate += 1; //increment by one
		return candidate;
	}
	
	public long[] findFactors(long candidate, long phi) {
		//used to take the candidate (product of e and d) and find the values of e and d that work
		long[] exponents = new long[2];
		//I used a array of longs to hold the two values
		for (long i = 2; i <= Math.sqrt(candidate); i++) {
			if (candidate % i == 0) { //once I find a factor of the candidate
				if (GCD(phi, i) == 1 & GCD(phi, candidate/i) == 1) { 
					//I check that e and d are relatively prime to phi n
					exponents[0] = i;
					exponents[1] = candidate/i;
				}
				else
					continue; //if not I continue until I find the factors that work
			}
		}
		return exponents;
	}
	
	public long GCD(long a, long b) {
		while (b != 0) {
			long temp = b;
			b = a % b;
			a = temp;
		}
		return a; 
		//previously explained in original cipher, but a brief summary is that:
		//I am using Euclidean algorithm to find the GCD, which is used to check many 
		//of the requirements for e and d being relatively prime with phi n
		
	}

	public String toBinary(long num) {
		if (num == 0) {
			return "0";
		}
		//if the number is 0, the binary is 0 immediately
		String binary = ""; //creating a binary string to hold the values
		long remainder; //initializing the remainder
		while (num > 0) { //if the number is greater than 0 there is going to be a binary string
			remainder = num % 2; //the remainder will be counting column by column (right to left)
			//eg. 1, 2, 4, 8, 16 and seeing what the remainder of it will be
			binary = remainder + binary; //concatenating the binary string, and the remainder of 
			//anything modded by two is either 1 or 0, and notice the order of remainder and binary
			//is important because it is putting the values from right to left
			num = num / 2; //dividing by two to move one column value to the left
		}
		return binary;
	}
	
	public long FastEx(long base, long ex, long mod) {
		//using the fast exponentiation with the binary number of the exponent
		String binary = toBinary(ex);
		long mainBase = base;
		//keeping a long to hold the original base
		for (int i = 0; i < binary.length()-1; i++) { //for each binary digit
			if (binary.charAt(i+1) == '0') { //if the next binary digit is zero
				base = (base * base) % mod; //square the base and mod it
			}
			else if (binary.charAt(i+1) == '1') { //if the next binary digit is one
				base = (base * base) % mod; //square the base and mod it
				base = (base * mainBase) % mod; //then multiply by original base and mod it
			}
		}
		return base; //when all are binary digits are done, the answer it completed
	}

}
