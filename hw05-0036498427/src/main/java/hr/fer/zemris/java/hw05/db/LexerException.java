package hr.fer.zemris.java.hw05.db;

/**
 * This is the type of exception a {@link Lexer}
 * throws if something goes wrong.
 * 
 * @author ivan
 *
 */
public class LexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * A default constructor.
	 * 
	 */
	public LexerException() {
		super();
	}
	
	/**
	 * A constructor which gets a message describing a problem
	 * and a cause of the problem.
	 * 
	 * @param message
	 * 		the message describing a problem
	 * 
	 * @param cause
	 * 		the cause of the problem
	 */
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * A constructor which gets a message describing a problem.
	 * 
	 * @param message
	 * 		the message describing a problem

	 */
	public LexerException(String message) {
		super(message);
	}

	/**
	 * A constructor which gets a cause of the problem.

	 * @param cause
	 * 		the cause of the problem
	 */
	public LexerException(Throwable cause) {
		super(cause);
	}	
}

