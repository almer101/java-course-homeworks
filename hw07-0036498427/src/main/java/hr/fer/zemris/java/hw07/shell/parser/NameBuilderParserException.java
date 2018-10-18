package hr.fer.zemris.java.hw07.shell.parser;

/**
 * This class models an exception thrown
 * if an error occurs during parsing.
 * 
 * @author ivan
 *
 */
public class NameBuilderParserException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/**
	 * A default constructor.
	 * 
	 */
	public NameBuilderParserException() {
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
	public NameBuilderParserException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * A constructor which gets a message describing a problem.
	 * 
	 * @param message
	 * 		the message describing a problem

	 */
	public NameBuilderParserException(String message) {
		super(message);
	}

	/**
	 * A constructor which gets a cause of the problem.

	 * @param cause
	 * 		the cause of the problem
	 */
	public NameBuilderParserException(Throwable cause) {
		super(cause);
	}	
}
