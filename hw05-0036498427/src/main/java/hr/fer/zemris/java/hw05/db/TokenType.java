package hr.fer.zemris.java.hw05.db;

/**
 * This enumeration defines all the possible
 * token types used in the {@link Lexer} in
 * order to parse query expressions.
 * 
 * @author ivan
 *
 */
public enum TokenType {

	/**
	 * This represents a field name.
	 * (e.g. lastName)
	 * 
	 */
	FIELD,
	
	/**
	 * This represents a string used
	 * for comparison.
	 * 
	 */
	STRING,
	
	/**
	 * This represents a symbol such as
	 * >, <, >=, =, <=.
	 * 
	 */
	SYMBOL,
	
	/**
	 * This represents an end of the file.
	 */
	EOF;
}
