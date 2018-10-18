package hr.fer.zemris.java.hw07.shell;

/**
 * This is the exception thrown when the error occurs
 * in the shell.
 * 
 * @author ivan
 *
 */
public class ShellIOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * This is the default constructor.
	 * 
	 */
	public ShellIOException() {
		super();
	}

	/**
	 * This is the constructor which gets a message and
	 * a cause of the exception.
	 * 
	 * @param message
	 * 		a message describing the exception.
	 * 
	 * @param cause
	 * 		the cause of the exception.
	 */
	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * This is the constructor which gets a message as
	 * a parameter. The message is describing the exception.
	 * 
	 * @param message
	 * 		the message describing the exception.
	 */
	public ShellIOException(String message) {
		super(message);
	}

	/**
	 * This is the constructor which gets one parameter which
	 * is the cause of the exception.
	 * 
	 * @param cause
	 * 		the cause of the exception.
	 */
	public ShellIOException(Throwable cause) {
		super(cause);
	}	
}
