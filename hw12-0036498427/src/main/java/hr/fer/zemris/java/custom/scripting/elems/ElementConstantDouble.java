package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class inherits the class {@link Element} and
 * represents a double constant.
 * 
 * @author ivan
 *
 */
public class ElementConstantDouble extends Element {

	/**
	 * A value of the constant. This is a read only
	 * property. 
	 * 
	 */
	private double value;

	/**
	 * This constructor gets one parameter of type double
	 * and sets the initial value of the <code>value</code> property
	 * of this class to that value.
	 * 
	 * @param value
	 * 		the initial value of this classes' <code>value</code>
	 */
	public ElementConstantDouble(double value) {
		super();
		this.value = value;
	}
	
	/**
	 * This method returns a double value of the
	 * <code>value</code>. 
	 * 
	 * @return
	 * 		the value of the property <code>value</code>
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * This method returns a string representation of the
	 * value property.
	 * 
	 */
	@Override
	public String asText() {
		return String.valueOf(value);
	}
	
}
