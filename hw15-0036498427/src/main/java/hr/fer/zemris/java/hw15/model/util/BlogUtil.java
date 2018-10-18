package hr.fer.zemris.java.hw15.model.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import static hr.fer.zemris.java.hw15.model.util.EncodingUtil.bytetohex;

/**
 * This class offers some utility methods which come in handy when the encoded
 * hash value of the password is needed or some other operations have to be done
 * in order for application to work properly.
 * 
 * @author ivan
 *
 */
public class BlogUtil {

	/** The algorithm to use for hashing. */
	private static final String ALGORITHM = "sha-1";

	/**
	 * This method creates a secure hash value using the sha algorithm and returns
	 * the hex encoded value of the password hash. The method returns null if
	 * exception occurs during digesting.
	 * 
	 * @param pw
	 *            the password to create a hex encoded value for.
	 * @return the hex encoded value of the hash value of the provided password.
	 *         <code>null</code> if exception occurs and the calculation of the hex
	 *         value did not succeed.
	 */
	public static String getHexEncodedHashOfPassword(String pw) {
		try {
			MessageDigest md = MessageDigest.getInstance(ALGORITHM);
			byte[] bytes = pw.getBytes();
			md.update(bytes, 0, bytes.length);
			byte hash[] = md.digest();
			return bytetohex(hash);

		} catch (NoSuchAlgorithmException ignorable) {
		}
		return null;
	}
	
	
}
