package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class inherits {@link Element} and has
 * a single read-only property. Represents an 
 * operator
 * 
 * @author ivan
 *
 */
public class ElementOperator extends Element {

	/**
	 * This is read-only property. 
	 * Represents a symbol for the operator.
	 * 
	 */
	private String symbol;

	/**
	 * A constructor which get one parameter
	 * that is the initial value of the <code>symbol</code>
	 * property.
	 * 
	 * @param symbol
	 * 		the initial value of the 
	 * 		<code>symbol</code> property.
	 */
	public ElementOperator(String symbol) {
		super();
		this.symbol = symbol;
	}
	
	/**
	 * This method returns a String value of the
	 * property <code>symbol</code>
	 * 
	 * @return
	 * 		the String value of the 
	 * 		property <code>symbol</code>.
	 */
	public String getSymbol() {
		return symbol;
	}
	
	/**
	 * This method returns a String value of the
	 * <code>symbol</code> property.
	 * 
	 * @return
	 * 		String value of the <code>symbol</code>
	 * 		property.
	 */
	@Override
	public String asText() {
		return symbol;
	}
	
}
