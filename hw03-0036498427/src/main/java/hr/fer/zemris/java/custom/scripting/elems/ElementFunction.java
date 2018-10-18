package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class inherits the class {@link Element}
 * and has a single read-only property.
 * 
 * @author ivan
 *
 */
public class ElementFunction extends Element {

	/**
	 * This is read-only property.
	 * Represents a name.
	 * 
	 */
	private String name;

	/**
	 * A constructor which gets one parameter 
	 * <code>name</code> and that parameter represents 
	 * an initial value of the
	 * property <code>name</code>
	 * 
	 * @param name
	 * 		initial value to be set.
	 */
	public ElementFunction(String name) {
		super();
		this.name = name;
	}
	
	/**
	 * This method returns a value of the 
	 * property <code>name</code>
	 * 
	 * @return
	 * 		value of the String <code>name</code>
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method returns the value of the
	 * <code>name</code> property.
	 * 
	 * @return
	 * 		the value of <code>name</code> property.
	 */
	@Override
	public String asText() {
		return name;
	}
	
}
