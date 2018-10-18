package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * This is the interface which models the visitor of the parsed
 * nodes. The each method is specific for each node and nodes
 * themselves call the appropriate method.
 * 
 * @author ivan
 *
 */
public interface INodeVisitor {

	/**
	 * The method which called when visiting the
	 * {@link TextNode}
	 * 
	 * @param node
	 * 		the node to visit.
	 */
	public void visitTextNode(TextNode node);
	
	/**
	 * This is the method called when visiting the
	 * {@link ForLoopNode}.
	 * 
	 * @param node
	 * 		the node to visit.
	 */
	public void visitForLoopNode(ForLoopNode node); 
	
	/**
	 * This is the method called when visiting the
	 * {@link EchoNode}
	 * 
	 * @param node
	 * 		the node to visit.
	 */
	public void visitEchoNode(EchoNode node);
	
	/**
	 * This is the method called when visiting the
	 * {@link DocumentNode}.
	 * 
	 * @param node
	 * 		the node to visit.
	 */
	public void visitDocumentNode(DocumentNode node);
}
