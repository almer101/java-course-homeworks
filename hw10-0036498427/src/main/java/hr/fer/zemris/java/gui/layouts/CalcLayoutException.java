package hr.fer.zemris.java.gui.layouts;

/**
 * This is the exception thrown when the requested
 * position for the component is illegal. Illegal positions
 * are:
 * <ul>
 * <li>any position from (1,2) to (1,5) inclusive. </li>
 * <li>row position is larger than 5 or smaller than 1</li>
 * <li>column position is larger than 7 and smaller then 1</li>
 * 
 * @author ivan
 *
 */
public class CalcLayoutException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * A default constructor.
	 * 
	 */
	public CalcLayoutException() {
		super();
	}

	/**
	 * The constructor which gets the message for the user
	 * describing the exception and the cause of the 
	 * exception.
	 * 
	 * @param message
	 * 		the message describing the exception.
	 * 
	 * @param cause
	 * 		the cause of the exception.
	 */
	public CalcLayoutException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * The constructor which gets the message describing 
	 * the exception.
	 * 
	 * @param message
	 * 		the message describing the exception.
	 */
	public CalcLayoutException(String message) {
		super(message);
	}

	/**
	 * The constructor which gets the cause of the
	 * exception.
	 * 
	 * @param cause
	 * 		the cause of the exception.
	 */
	public CalcLayoutException(Throwable cause) {
		super(cause);
	}
}
