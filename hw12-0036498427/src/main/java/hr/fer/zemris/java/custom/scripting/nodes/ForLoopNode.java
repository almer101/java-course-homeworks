package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * This class is a node representing a single
 * for-loop construct. It inherits from the
 * {@link Node} class.
 * 
 * @author ivan
 *
 */
public class ForLoopNode extends Node{

	/**
	 * A variable.
	 * 
	 */
	private ElementVariable variable;
	
	/**
	 * A starting expression.
	 * 
	 */
	private Element startExpression;
	
	/**
	 * An ending expression.
	 */
	private Element endExpression;
	
	/**
	 * A step expression.
	 * 
	 */
	private Element stepExpression;

	/**
	 * This constructor initializes all the values of
	 * this class to the values given as parameters.
	 * 
	 * @param variable
	 * 		value of <code>variable</code>
	 * 
	 * @param startExpression
	 * 		value of <code>startExpression</code>
	 * 
	 * @param endExpression
	 * 		value of <code>endExpression</code>
	 * 
	 * @param stepExpression
	 * 		value of <code>stepExpression</code>
	 * 
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("{$FOR ");
		sb.append(variable.asText() + " ");
		if(startExpression instanceof ElementString) {
			sb.append("\"" + startExpression.asText() + "\"" + " ");
			
		} else {
			sb.append(startExpression.asText() + " ");
			
		}
		if(endExpression instanceof ElementString) {
			sb.append("\"" + endExpression.asText() + "\"" + " ");
			
		} else {
			sb.append(endExpression.asText() + " ");
			
		}
		if(stepExpression != null) {
			if(stepExpression instanceof ElementString) {
				sb.append("\"" + stepExpression.asText() + "\"");
				
			} else {
				sb.append(stepExpression.asText());
				
			}
		}
		sb.append("$}");
		
		return sb.toString();
	}

	/**
	 * This method returns the value of the
	 * property <code>variable</code>
	 * 
	 * @return
	 * 		the value of <code>variable</code>
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 *  This method returns the value of the
	 * property <code>startExpression</code>
	 * 
	 * @return
	 * 		the value of <code>startExpression</code>
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 *  This method returns the value of the
	 * property <code>endExpression</code>
	 * 
	 * @return
	 * 		the value of <code>endExpression</code>
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 *  This method returns the value of the
	 * property <code>stepExpression</code>
	 * 
	 * @return
	 * 		the value of <code>stepExpression</code>
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	/**
	 * This is the method which calls the appropriate
	 * visit method from the visitor.
	 * 
	 * @param visitor
	 * 		the visitor whose method is called.
	 */
	public void accept (INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
}
