package hr.fer.zemris.java.hw05.db;

/**
 * This class represents a token which.
 * 
 * @author ivan
 *
 */
public class Token {

	//===================Properties=========================
	
	/**
	 * The type of the token.
	 */
	private TokenType type;
	
	/**
	 * The value of the token.
	 */
	private Object value;

	//===================Constructors=======================
	
	/**
	 * This constructor gets and initializes the properties
	 * of this class.
	 * 
	 * @param type
	 * 		the initial type of the token.
	 * 
	 * @param value
	 * 		the initial value of the token.
	 */
	public Token(TokenType type, Object value) {
		super();
		this.type = type;
		this.value = value;
	}

	//=======================Getters=========================
	
	/**
	 * This method gets and returns the value
	 * of <code>type</code>.
	 * 
	 * @return
	 * 		the value of this token's <code>type</code>
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * This method gets and returns the value
	 * of <code>value</code>.
	 * 
	 * @return
	 * 		the value of this token's <code>value</code>
	 */
	public Object getValue() {
		return value;
	}
}
