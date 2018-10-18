package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * This class is an Exception which inherits everything
 * from <code>RuntimeException</code> and is the 
 * exception used when something in the lexical analysis
 * does not go well.
 * 
 * @author ivan
 * @version 1.0
 *
 */
public class LexerException extends RuntimeException {

	/**The serial version UID*/
	private static final long serialVersionUID = 1L;
	
	/**
	 * An empty constructor.
	 * 
	 */
	public LexerException() {
		super();
	}

	/**
	 * The constructor which get two parameters describing
	 * the exception.
	 * 
	 * @param message
	 * 		the message describing the exception
	 * @param cause
	 * 		the cause of the exception
	 */
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * The constructor which one parameter <code>message</code> 
	 * that describes the exception.
	 * 
	 * @param message 
	 * 		the message describing an exception.
	 */
	public LexerException(String message) {
		super(message);
	}

	/**
	 * The constructor which gets one parameter <code>cause</code>
	 * that represents the cause of the exception.
	 * 
	 * @param cause
	 * 		the cause of the exception.
	 */
	public LexerException(Throwable cause) {
		super(cause);
	}
	
	
}
