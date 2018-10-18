package hr.fer.zemris.java.custom.scripting.parser;

/**
 * This class is the exception thrown by the 
 * {@link SmartScriptParser} if something did
 * not go well.
 * 
 * @author ivan
 *
 */
public class SmartScriptParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * An empty constructor.
	 * 
	 */
	public SmartScriptParserException() {
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
	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * The constructor which one parameter <code>message</code> 
	 * that describes the exception.
	 * 
	 * @param message 
	 * 		the message describing an exception.
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

	/**
	 * The constructor which gets one parameter <code>cause</code>
	 * that represents the cause of the exception.
	 * 
	 * @param cause
	 * 		the cause of the exception.
	 */
	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}
	
	
}
