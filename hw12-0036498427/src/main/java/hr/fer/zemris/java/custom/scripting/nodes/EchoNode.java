package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;

/**
 * This class is a node representing a command which
 * generates some textual output dynamically. It
 * inherits from the {@link Node} class.
 * 
 * @author ivan
 *
 */
public class EchoNode extends Node {

	/**
	 * An array of elements.
	 * 
	 */
	private Element[] elements;

	/**
	 * This constructor initializes the
	 * property <code>elements</code> with
	 * the value of the parameter.
	 * The given value can be <code>null</code>.
	 * 
	 * @param elements
	 * 		initial value of the property
	 * 		<code>elements</code>.
	 */
	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("{$= ");
		for(Element e : elements) {
			if(e instanceof ElementFunction) {
				sb.append("@" + e.asText() + " ");
				
			} else if (e instanceof ElementString) {
				sb.append("\"" + e.asText() + "\" ");
				
			} else {
				sb.append(e.asText() + " ");
			}
		}
		sb.append("$}");
		
		return sb.toString();
	}
	
	/**
	 * This is the method which calls the appropriate
	 * visit method from the visitor.
	 * 
	 * @param visitor
	 * 		the visitor whose method is called.
	 */
	public void accept (INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
	
	/**
	 * This is the method which returns the elements
	 * of this node.
	 * 
	 * @return
	 * 		the elements array of this node.
	 */
	public Element[] getElements() {
		return elements;
	}
}
