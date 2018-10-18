package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * This class represents one Token for
 * lexical analysis. Token is 
 * 
 * @author ivan
 *
 */
public class SmartScriptToken {
	
	/**
	 * Type of the token, <code>TokenType</code>.
	 * 
	 */
	SmartScriptTokenType type;
	
	/**
	 * Value of the token.
	 * 
	 */
	Object value;

	/**
	 * A constructor which gets two parameters
	 * and creates an object with those values.
	 * 
	 * @param type
	 * 		Type of the token.
	 * @param value
	 * 		Value of the token.
	 */
	public SmartScriptToken(SmartScriptTokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * A method returns returns the value of the Token.
	 * 
	 * @return
	 * 		the value of the token.
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * A method which returns the token type.
	 * 
	 * @return
	 * 		the token type
	 */
	public SmartScriptTokenType getType() {
		return type;
	}
}
