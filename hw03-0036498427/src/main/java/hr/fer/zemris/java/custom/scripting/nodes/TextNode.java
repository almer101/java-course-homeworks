package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * This class is a node representing a piece
 * of textual data. It inherits from {@link Node}
 * class.
 * 
 * @author ivan
 *
 */
public class TextNode extends Node {

	/**
	 * This is read-only property.
	 * 
	 */
	private String text;
	
	
	/**
	 * A constructor which initializes the
	 * <code>text</code> property of this class.
	 * 
	 * @param text
	 * 		initial value for the <code>text</code>
	 * 		property
	 */
	public TextNode(String text) {
		super();
		this.text = text;
	}



	/**
	 * This method returns a value of the
	 * property <code>text</code>.
	 * 
	 * @return
	 * 		the value of the property
	 * 		<code>text</code>
	 */
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		
		if(text.contains("{")) {
			int index = text.indexOf("{");
			String firstPart = text.substring(0,index);
			String escape = "\\";
			String secondPart = text.substring(index);
			return firstPart + escape + secondPart;
		}
		return text;
	}
	
}
