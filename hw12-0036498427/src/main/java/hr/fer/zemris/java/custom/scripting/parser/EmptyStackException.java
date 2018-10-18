package hr.fer.zemris.java.custom.scripting.parser;

/**
 * @author ivan
 *
 */
public class EmptyStackException extends RuntimeException {

	/**The serial version UID*/
	private static final long serialVersionUID = 1L;

	/**
	 * An empty (default) constructor.
	 */
	public EmptyStackException() {
		super();
	}
	
	/**
	 * A constructor which gets one parameter 
	 * {@code message} that describes the exception
	 * @param message message that describes the 
	 * 			exception
	 */
	public EmptyStackException(String message) {
		super(message);
	}
	
	/**
	 * A constructor which gets one parameter 
	 * {@code cause}. 
	 * @param cause cause of the exception.
	 */
	public EmptyStackException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * A constructor which gets two parameters {@code message}
	 * which describes the exception and other parameter 
	 * {@code cause} which is the cause of the exception.
	 * @param message message that describes the exception.
	 * @param cause cause of the exception.
	 */
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
