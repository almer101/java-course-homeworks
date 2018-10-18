package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class inherits {@link Element} and represents
 * a constant integer.
 * 
 * @author ivan
 *
 */
public class ElementConstantInteger extends Element {

	/**
	 * The value of the constant. This is read-only
	 * property
	 * 
	 */
	private int value;

	/**
	 * This constructor gets one parameter value, which
	 * will be initial value of the <code>value</code>
	 * property of this class.
	 * 
	 * @param value
	 * 		initial value of the <code>value</code>
	 * 		property of this class.
	 */
	public ElementConstantInteger(int value) {
		super();
		this.value = value;
	}
	
	/**
	 * This method returns a value of the integer
	 * <code>value</code>. 
	 * 
	 * @return
	 * 		the value of the integer <code>value</code>
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * This method returns a String representation of
	 * the property <code>value</code>.
	 * 
	 * @return 
	 * 		a {@link String} representation of the 
	 * 		<code>value</code> property.
	 */
	@Override
	public String asText() {
		return String.valueOf(value);
	}
}
