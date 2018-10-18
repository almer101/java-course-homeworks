package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * This enumeration defines all the token types 
 * which can appear in the document.
 * 
 * @author ivan
 *
 */
public enum SmartScriptTokenType {

	/**
	 * This token type defines that the 
	 * token is a word.
	 * 
	 */
	TEXT,
	
	/**
	 * This token type defines that the 
	 * token is a keyword.
	 * 
	 */
	KEYWORD,
	
	/**
	 * This token type represents a number.
	 * 
	 */
	NUMBER,
	
	/**
	 * This token type defines the end of
	 * file. After which no more tokens are
	 * generated.
	 * 
	 */
	EOF,
	
	/**
	 * This token type is a type that is defining
	 * the opened parentheses ({)
	 * 
	 */
	OPENED_PARENTHESES,
	
	/**
	 * This token type is a type that is defining
	 * the closed parentheses (})
	 * 
	 */
	CLOSED_PARENTHESES,
	
	/**
	 * This token type represents a variable name.
	 * 
	 */
	ID,
	
	/**
	 * This token type is a type that is defining
	 * the dollar sign ($)
	 * 
	 */
	DOLLAR,
	
	/**
	 * This token type is a type that is defining
	 * a string in a tag.
	 * 
	 */
	STRING,
	
	/**
	 * This token type represents a name 
	 * of the function.
	 * 
	 */
	FUNCTION_ID,
	
	
	/**
	 * This token type is type defining the symbol
	 * which is neither number nor a letter.
	 * 
	 */
	SYMBOL;
	
}
