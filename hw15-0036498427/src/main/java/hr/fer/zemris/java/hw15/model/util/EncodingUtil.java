package hr.fer.zemris.java.hw15.model.util;

import java.util.Objects;
import static java.lang.Math.abs;


/**
 * This is the class which provides two static
 * utility methods for transforming hex-encoded
 * {@link String} to byte array and vice versa.
 * 
 * @author ivan
 *
 */
public class EncodingUtil {
	
	//======================================Constants=============================================

	private static final int STEP = 2;
	private static final byte BYTE_XOR_MASK = 0xF;
	private static final int INT_XOR_MASK = 0xFF;
	private static final byte MASK = 0x8;
	private static final byte AND_MASK = 0xF;
	private static final String hexToNumber[] = {"0", "1", "2" , "3", 
			"4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	private static final int NUMBER_OF_DIGITS = 16;

	//=================================Method implementations=====================================
	
	/**
	 * This method transforms a {@link String} of hex 
	 * digits to the array of {@link Byte}s. Each number
	 * has STEP digits.
	 * 
	 * @param s
	 * 		the {@link String} to be transformed.
	 * 
	 * @return
	 * 		a byte array containing numbers from the
	 * 		given string.
	 */
	public static byte[] hextobyte(String s) {
		checkString(s);
		byte array[] = new byte[s.length() / STEP];
		
		for(int i = 0, len = s.length(); i < len - 1; i += STEP) {
			
				String firstDigitString = s.substring(i, i+1).toLowerCase();
				String secondDigitString = s.substring(i+1, i+2).toLowerCase();
	
				byte firstDigit = findNumber(firstDigitString);
				byte secondDigit = findNumber(secondDigitString);
				
				if((firstDigit & MASK) > 0) {
					//the number will be negative.
					firstDigit = (byte) ((firstDigit ^ BYTE_XOR_MASK));
					secondDigit = (byte) ((secondDigit ^ BYTE_XOR_MASK) + 1);
					firstDigit <<= 4;
					array[i / STEP] = (byte)(-(firstDigit + secondDigit));
					
				} else {
					//the number is positive
					firstDigit <<= 4;
					array[i / STEP] = (byte)(firstDigit + secondDigit);
							
				}
		}
		return array;
	}

	/**
	 * This method transforms the byte array of numbers
	 * to the {@link String} containing hex representation
	 * of that number. Big-endian notation is used.
	 * 
	 * @param array
	 * 		the array to be transformed.
	 * 
	 * @return
	 * 		the String representation of the number.
	 */
	public static String bytetohex(byte[] array) {
		Objects.requireNonNull(array, "The given array must not "
				+ "be null!");
		StringBuilder sb = new  StringBuilder();
		
		for(byte b : array) {
			int firstPart = abs(b) / 10;
			int secondPart = abs(b) % 10;
			int number = (firstPart*10 + secondPart);
			byte firstDigit;
			byte secondDigit;
			
			if(b < 0) {
				number = (number ^ INT_XOR_MASK);
				number += 1;
			} 
			secondDigit = (byte)(number & AND_MASK);
			firstDigit = (byte)((number>>4) & AND_MASK);
			
			sb.append(hexToNumber[firstDigit]);
			sb.append(hexToNumber[secondDigit]);
		}
		return sb.toString();
	}

	/**
	 * This method returns the byte value of the
	 * given hex {@link String}.
	 * 
	 * @param firstDigit	
	 *		{@link String} for which to find a number.
	 *
	 * @return
	 * 		the number which is represented by the given
	 * 		{@link String}.
	 */
	private static byte findNumber(String firstDigit) {
		for(int i = 0; i < NUMBER_OF_DIGITS; i++) {
			if(firstDigit.equals(hexToNumber[i])) return (byte)i;
		}
		return -1;
	}

	/**
	 * This method checks the given string if it
	 * is valid for conversion to byte array.
	 * 
	 * @param s
	 * 		a {@link String} to be checked.
	 * 
	 * @throws IllegalArgumentException
	 * 		if the {@link String} is invalid.
	 */
	private static void checkString(String s) {
		Objects.requireNonNull(s, "The given object must not be null!");
		if(s.length() % 2 != 0) {
			throw new IllegalArgumentException("The given string must have "
					+ "an even length.");
		}
		char array[] = s.toCharArray();
		for(char c : array) {
			if(!isValidChar(c)) {
				throw new IllegalArgumentException("The "
					+ "given string cotains illegal characters for a "
					+ "hex number: " + c);
			}
		}
	}

	/**
	 * This method checks if the given char is valid, i.e.
	 * can such char be contained within a hex {@link String}.
	 * Returns boolean accordingly.
	 * 
	 * @param c
	 * 		the char to be checked.
	 * 
	 * @return
	 * 		<code>true</code> if the char is valid.
	 * 		<code>false</code> otherwise.
	 */
	private static boolean isValidChar(char c) {
		c = Character.toLowerCase(c);
		return (c >= '0' && c <= '9') ||
				(c >= 'a' && c <= 'f');
	}
}
