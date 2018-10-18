package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class is the base class of this hierarchy.
 * This class and classes which inherit {@link Element}
 * will be used for representation of expressions
 * 
 * @author ivan
 *
 */
public class Element {

	/**
	 * This method returns an empty {@link String}
	 * 
	 * @return
	 * 		empty {@link String}
	 */
	public String asText() {
		return "";
	}
}
