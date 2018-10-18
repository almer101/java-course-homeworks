package hr.fer.zemris.java.hw07.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Objects;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static hr.fer.zemris.java.hw07.crypto.Util.bytetohex;
import static hr.fer.zemris.java.hw07.crypto.Util.hextobyte;

/**
 * This program allows user to encrypt/decrypt
 * given file using the AES crypto-algorithm and 
 * the 128-bit encryption key or calculate and check the 
 * SHA-256 file digest.
 * 
 * @author ivan
 *
 */
public class Crypto {

	
	private static final int BUFFER_SIZE = 4096;
	
	/**
	 * This is the method called when the program is
	 * run.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		if(args.length == 2) {
			String mode = args[0];
			String fileName = args[1];
			chechSha(mode, fileName);
			
		} else if(args.length == 3) {
			String mode = args[0];
			String srcFile = args[1];
			String destFile = args[2];
			encryptDecrypt(mode, srcFile, destFile);
			
		} else throw new IllegalArgumentException("Illegal number of arguments! Expected 2 or 3");

	}

	/**
	 * This method creates a sha-256 digest of the file
	 * with the specified name. The users is requested to
	 * input an expected digest and the program compares
	 * the given and calculated digest and prints the 
	 * message accordingly.
	 * 
	 * @param mode
	 * 		the mode of the program (only "checksha" 
	 * 		is accepted in this case)
	 * 
	 * @param fileName
	 * 		the name of the file of which the digest is 
	 *		supposed to be calculated.
	 */
	private static void chechSha(String mode, String fileName) {
		checkShaMode(mode);

		Scanner sc = new Scanner(System.in);
		System.out.print("Please provide expected sha-256 digest for " + fileName + ":\n> ");
		String expectedDigest = sc.nextLine();
		sc.close();
		
		try (InputStream is = new BufferedInputStream(
									Files.newInputStream(Paths.get("./" + fileName), StandardOpenOption.READ))){
			byte buff[] = new  byte[BUFFER_SIZE];
			MessageDigest md = MessageDigest.getInstance("sha-256");
			
			while(true) {
				int numberOfReadBytes = is.read(buff);
				if(numberOfReadBytes < 1) break;
				md.update(buff, 0, numberOfReadBytes);
			}
			byte hash[] = md.digest();
			String messageDigest = bytetohex(hash);
			
			if(expectedDigest.equals(messageDigest)) {
				System.out.println("Digesting completed. Digest of "
						+ "hw06test.bin matches expected digest.");
				
			} else {
				System.out.println("Digesting completed. Digest of hw06test.bin does "
						+ "not match the expected digest. Digest\n" 
						+ "was: " + messageDigest);
			}
			
		} catch (Exception ignorable) {
		}
	}

	/**
	 * This method checks if the specified sha mode keyword is valid. Throws
	 * exception if it is not. Valid is only checksha, no matter the case.
	 * 
	 * @param mode
	 * 		a {@link String} to be checked.
	 */
	private static void checkShaMode(String mode) {
		Objects.requireNonNull(mode, "The given mode can not be null!");
		if(!mode.equalsIgnoreCase("checksha")) {
			throw new IllegalArgumentException("The given mode of program is not valid. "
					+ "Expected checksha. Was " +  mode);
		}
	}
	
	/**
	 * This method performs encryption and decryption of data depending
	 * on the specified mode. The method asks the user to input
	 * password and initialization vector via {@link System}.in. 
	 * After performing the wanted operation, the destionation file is created.
	 * 
	 * @param mode
	 * 		"encrypt" or "decrypt".
	 * 
	 * @param srcFile
	 * 		the name of the source file.
	 * 	
	 * @param destFile
	 * 		the name of the destination file.
	 */
	private static void encryptDecrypt(String mode, String srcFile, String destFile) {
		boolean encrypt = checkEncryptDecryptMode(mode);
		
		
		String keyText = "";
		String ivText = "";
		Scanner sc = new Scanner(System.in);
		System.out.print("Please provide password as hex-encoded text "
				+ "(16 bytes, i.e. 32 hex-digits):\n> ");
		keyText = sc.nextLine();
		
		System.out.print("Please provide initialization vector as "
				+ "hex-encoded text (32 hex-digits):\n> ");
		ivText = sc.nextLine();
		
		sc.close();

		try {
			InputStream is = new BufferedInputStream(
					Files.newInputStream(Paths.get("./" + srcFile), StandardOpenOption.READ));
			OutputStream os = new BufferedOutputStream(
					Files.newOutputStream(Paths.get("./" + destFile), StandardOpenOption.CREATE));
			
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES"); 
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(hextobyte(ivText));
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
			
			byte helperArray[];
			byte buff[] = new byte[BUFFER_SIZE];
			while(true) {
				int numberOfReadFiles = is.read(buff);
				if(numberOfReadFiles == -1) break;
				helperArray = cipher.update(buff, 0, numberOfReadFiles);
				os.write(helperArray, 0, helperArray.length);
			}
			
			helperArray = cipher.doFinal();
	        os.write(helperArray, 0, helperArray.length);
			
			System.out.println((encrypt ? "Encryption" : "Decryption") +  " completed. Generated "
					+ "file " + destFile + " based on file " + srcFile);
			
			is.close();
			os.close();
			
		} catch (Exception ignorable) {
			System.out.println("An exception during encryption/decryption!");
			System.exit(0);
		}
		
	}

	/**
	 * This method checks if the specified encryption/decryption
	 * mode keyword is valid. Throws exception if it is not. 
	 * Valid are "encrypt" and "decrypt". If the keyword is alright
	 * the method returns true if the mode is encryption and false if
	 * it is decryption.
	 * 
	 * @param mode
	 * 		a mode to be checked.
	 * 
	 * @return
	 * 		<code>true</code> if the mode is encryption.
	 * 		<code>false</code> if mode is decryption.
	 * 		
	 */
	private static boolean checkEncryptDecryptMode(String mode) {
		Objects.requireNonNull(mode, "The given mode can not be null!");
		if(!mode.equalsIgnoreCase("encrypt") &&
		!mode.equalsIgnoreCase("decrypt")) {
			throw new IllegalArgumentException("The given mode is not valid. Expected "
					+ "encrypt or decrypt. Was " + mode);
		}
		return mode.equalsIgnoreCase("encrypt");
	}
}
