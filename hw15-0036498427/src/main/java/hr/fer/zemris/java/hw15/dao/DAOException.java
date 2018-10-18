package hr.fer.zemris.java.hw15.dao;

/**
 * This is the exception thrown by DAO objects every time some other exception
 * occurs.
 * 
 * @author ivan
 *
 */
public class DAOException extends RuntimeException {

	/** The default serial version UID */
	private static final long serialVersionUID = 1L;

	/**
	 * An empty default constructor.
	 * 
	 */
	public DAOException() {
	}

	/**
	 * This is the constructor which gets the message, cause and two boolean flags
	 * as parameter.
	 * 
	 * @param message
	 *            the message describing the exception.
	 * @param cause
	 *            the cause of the exception.
	 * @param enableSuppression
	 *            whether or not suppression is enabled or disabled
	 * @param writableStackTrace
	 *            whether or not the stack trace should be writable
	 */
	public DAOException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * This is the constructor which gets the message describing the exception and
	 * the cause of the exception.
	 * 
	 * @param message
	 *            the message describing the exception.
	 * @param cause
	 *            the cause of the exception.
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * This is the constructor which gets the message describing the exception.
	 * 
	 * @param message
	 *            the message describing the exception.
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * This is the constructor which gets the cause of the exception.
	 * 
	 * @param cause
	 *            the cause of the exception.
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}