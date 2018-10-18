package hr.fer.zemris.java.custom.scripting.exec;

/**
 * This is the exception thrown when the pop or
 * peek method is called on empty stack.
 * 
 * @author ivan
 *
 */
public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * A default constructor.
	 */
	public EmptyStackException() {
		super();
	}
	
	/**
	 * A constructor which gets an argument
	 * {@code message} which describes the exception
	 * 
	 * @param message 
	 * 		message which describes the exception
	 */
	public EmptyStackException(String message) {
		super(message);
	}
	
	/**
	 * A constructor which gets an argument
	 * {@code cause} which represents a cause
	 * of the exception.
	 * 
	 * @param cause 
	 * 		cause of the exception.
	 */
	public EmptyStackException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * A constructor which gets two arguments, {@code message}
	 * which describes the exception and other argument
	 * {@code cause} which represents the cause of the exception.
	 * 
	 * @param message
	 * 		 message that describes the exception.
	 * 
	 * @param cause 
	 * 		cause of the exception.
	 */
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
