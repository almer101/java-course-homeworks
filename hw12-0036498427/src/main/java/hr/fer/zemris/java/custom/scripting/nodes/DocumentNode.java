package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * This class is a node representing an entire document.
 * It inherits from the {@link Node} class.
 * 
 * @author ivan
 *
 */
public class DocumentNode extends Node {

	/**
	 * A constructor which calls the
	 * constructor from the superclass
	 * 
	 */
	public DocumentNode() {
		super();
	}
	
	/**
	 * This is the method which calls the appropriate
	 * visit method from the visitor.
	 * 
	 * @param visitor
	 * 		the visitor whose method is called.
	 */
	public void accept (INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
}
