package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * This is the base class for all graph nodes.
 * 
 * @author ivan
 *
 */
public class Node {

	/**
	 * A collection of the children nodes.
	 * 
	 */
	ArrayIndexedCollection childNodes;
	
	/**
	 * This method adds a {@link Node} 
	 * <code>child</code> to the collection
	 * of children <code>childNodes</code>.
	 * 
	 * @param child
	 * 		a child {@link Node} to be added.
	 */
	public void addChildNode(Node child) {
		if(child == null) {
			throw new NullPointerException("The given argument must not be null!");
			
		}
		//initialize collection if this is the first element to be added.
		if(childNodes == null) {
			childNodes = new ArrayIndexedCollection();
			
		}
		childNodes.add(child);
	}
	
	/**
	 * This method determines and returns 
	 * a number of direct children in the
	 * collection
	 * 
	 * @return
	 * 		the number of the children in the collection.
	 */
	public int numberOfChildren() {
		if(childNodes == null) {
			return 0;
		}
		return childNodes.size();
	}
	
	/**
	 * This method gets a child located on the index
	 * position in the <code>childNodes</code> collection
	 * and returns it.
	 * 
	 * @param index
	 * 		index at which the wanted element is.
	 * 
	 * @return
	 * 		the value of the node on the index 
	 * 		<code>index</code> in the
	 * 		<code>childNodes</code> collection.
	 * 
	 * @throws IndexOutOfBoundsException 
	 * 		if the given <code>index</code> is 
	 * 		invalid.
	 */
	public Node getChild(int index) throws IndexOutOfBoundsException {
		return (Node)childNodes.get(index);
	}
	
	/**
	 * This method returns the {@link String}
	 * representation of this Node.
	 * 
	 * @return
	 * 		String representation of this node.
	 */
	@Override
	public String toString() {
		return "";
	}
}
