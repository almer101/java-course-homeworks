package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class inherits {@link Element} and represents
 * an element variable.
 * 
 * @author ivan
 *
 */
public class ElementVariable extends Element {

	/**
	 * A name of the variable. This is read-only 
	 * value.
	 * 
	 */
	private String name;
	
	/**
	 * This constructor gets the {@link String} name
	 * and initializes the <code>name</code> variable 
	 * to the value of the parameter <code>name</code>
	 * 
	 * @param name 
	 * 		initial value for this classes property
	 * 		<code>name</code>
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	
	/**
	 * This method returns the value of the
	 * String <code>name</code>.
	 * 
	 * @return
	 * 		the String <code>name</code>
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method returns the String <code>name</code>.
	 * 
	 * @return
	 * 		the String <code>name</code>
	 */
	@Override
	public String asText() {
		return name;
	}
}
