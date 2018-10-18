package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class inherits the class {@link Element}
 *  and has a single read-only String property.
 * 
 * @author ivan
 *
 */
public class ElementString extends Element {
	
	/**
	 * This is the read-only property of this class.
	 * 
	 */
	private String value;

	/**
	 * A constructor which gets one parameter value.
	 * It is used for initializing the String <code>value</code>.
	 * 
	 * @param value
	 * 		initial value of the property <code>value</code>.
	 */
	public ElementString(String value) {
		super();
		this.value = value;
	}
	
	/**
	 * This method returns a value of the property <code>value</code>
	 * 
	 * @return
	 * 		a {@link String} with the value of <code>value</code>
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * This method returns the value of the property <code>value</code>.
	 * 
	 * @return
	 * 		the {@link String} value of the property <code>value</code>.
	 */
	@Override
	public String asText() {
		StringBuilder sb = new StringBuilder();
		char[] data = value.toCharArray();
		for(char c : data) {
			if(c == '\"') {
				sb.append("\\");
			}
			sb.append(c);
		}
		return sb.toString();
	}
}
