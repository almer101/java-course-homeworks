package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.util.Objects;
import java.util.Stack;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.exec.Functions.Function;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;

/**
 * This is the engine which executes the document whose 
 * parsed tree is obtained via constructor. 
 * 
 * @author ivan
 *
 */
public class SmartScriptEngine {

	//=================================Properties====================================
	
	/**
	 * The root of the parsed tree (i.e. the starting node
	 * in the execution process)
	 */
	private DocumentNode documentNode;
	
	/**
	 * The context which manages the parameters and cookies
	 * and writes to the output stream.
	 */
	private RequestContext requestContext;
	
	/**
	 * This is the multistack used to store values in it.
	 */
	private ObjectMultistack multistack = new ObjectMultistack();
	
	/**
	 * The visitor which processes each node which calls 
	 * one of its methods.
	 */
	private INodeVisitor visitor = new MyNodeVisitor();
	
	/**
	 * The object which returns the functions for the name.
	 */
	private Functions functions;
	
	//======================================Constants=========================================
	
	/**The plus operator string representation.*/
	private static final String ADD = "+";
	
	/**The minus operator string representation.*/
	private static final String SUB = "-";
	
	/**The division operator string representation.*/
	private static final String DIV = "/";
	
	/**The multiplication operator string representation.*/
	private static final String MUL = "*";
	
	//=====================================Constructor========================================
	
	/**
	 * This is the constructor which accepts the initial value
	 * of the {@link #documentNode} and the initial value of
	 * the {@link #requestContext}.
	 * 
	 * @param documentNode
	 * 		the document node (i.e. the root of the parsed tree.)
	 * 
	 * @param requestContext
	 * 		the context which manages the parameters and cookies 
	 * 		and also writes to the output stream.
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
        this.documentNode = Objects.requireNonNull(documentNode);
        this.requestContext = Objects.requireNonNull(requestContext);
	}
	
	//==================================Method implementations=================================
	
	/**
	 * This method executes the engine. In this case the
	 * engine is executed by executing the root node of the 
	 * parsed tree.
	 */
	public void execute() { 
		documentNode.accept(visitor);
	}
	
	/**
	 * This is the private inner class which models the 
	 * node visitor. The modeled visitor manages each node 
	 * of the tree.
	 * 
	 * @author ivan
	 *
	 */
	private class MyNodeVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				System.out.println("The TextNode content could not be written!");
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			ElementVariable variable = node.getVariable();
			Element initialValue = node.getStartExpression();
			multistack.push(variable.getName(), new ValueWrapper(initialValue.asText()));

			Element endValue = node.getEndExpression();
			double end = Double.parseDouble(endValue.asText());
			Element stepValue = node.getStepExpression();
			double step = node.getStepExpression() == null ? 
					1 : Double.parseDouble(stepValue.asText());
			double var = Double.parseDouble(
					(String)multistack.peek(variable.getName()).getValue());
			boolean storeDoubles = endValue instanceof ElementConstantDouble ||
									initialValue instanceof ElementConstantDouble ||
									(stepValue != null && stepValue instanceof ElementConstantDouble);
			
			while(var <= end) {
				int size = node.numberOfChildren();
				for(int i = 0; i < size; i++) {
					node.getChild(i).accept(MyNodeVisitor.this);
				}
				multistack.pop(variable.getName());
				if(storeDoubles) {
					multistack.push(
							variable.getName(), 
							new ValueWrapper(Double.valueOf(var + step)));
				} else {
					multistack.push(
							variable.getName(), 
							new ValueWrapper(Integer.valueOf((int)(var + step))));
				}
				var = Double.parseDouble(
						((Number)multistack.peek(variable.getName()).getValue()).toString()
				);
			}
			multistack.pop(variable.getName());
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> tempStack = new Stack<>();
			if(functions == null) {
				functions = new Functions(tempStack, requestContext);
			} else {
				functions.setStack(tempStack);
			}
			Element[] elems = node.getElements();
			
			for(Element e : elems) {
				if(e instanceof ElementConstantInteger) {
					tempStack.push(((ElementConstantInteger)e).getValue());
					
				} else if (e instanceof ElementConstantDouble) {
					tempStack.push(((ElementConstantDouble)e).getValue());
					
				} else if (e instanceof ElementString) {
					tempStack.push(((ElementString)e).getValue());
					
				} else if(e instanceof ElementVariable) {
					processVariable(e, tempStack);
					
				} else if(e instanceof ElementOperator) {
					performOperation((ElementOperator)e, tempStack);
					
				} else if(e instanceof ElementFunction) {
					applyFunction((ElementFunction)e, tempStack);
					
				}
			}
			Stack<Object> helper = new Stack<>();
			transferObjects(tempStack, helper);
			int size = helper.size();
			try {
				for(int i = 0; i < size; i++) {
					requestContext.write(helper.pop().toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * This method processes the variable and stores the value to the
		 * specified stack. If the variable is of type double it stores double,
		 * if it is an integer stores an integer and if it's a string
		 * stores double or integer depending on whether the string contains
		 * the decimal dot or not.
		 * 
		 * @param e the variable element.
		 * @param tempStack
		 * 		the stack where to store the value of the variable.
		 */
		private void processVariable(Element e, Stack<Object> tempStack) {
			Object value = multistack
					.peek(((ElementVariable)e).getName()).getValue();
			if(value instanceof String) {
				String s = (String)value;
				if(s.contains(".")) {
					tempStack.push(Double.parseDouble(s));
				} else {
					tempStack.push(Integer.parseInt(s));
				}
				return;
			}
			Number var = (Number)value;
			if(var instanceof Double) {
				tempStack.push(var.doubleValue());
			} else {
				tempStack.push(var.intValue());
			}				
		}

		/**
		 * This method transfers the elements from the stack
		 * given by the first parameter to the stack given by 
		 * the second parameter but such that the resulting order 
		 * on the other stack is reversed order from the order 
		 * on the first stack.
		 * 
		 * @param tempStack
		 * 		the stack to transfer from.
		 * @param helper
		 * 		the stack to transfer to.
		 */
		private void transferObjects(Stack<Object> tempStack, Stack<Object> helper) {
			int size = tempStack.size();
			for(int i = 0 ; i < size; i++) {
				helper.push(tempStack.pop());
			}
		}

		/**
		 * This method performs the operation given by the
		 * parameter e on the required number of objects 
		 * from the specified stack
		 * 
		 * @param e the required operation
		 * 
		 * @param tempStack
		 * 		the stack where the parameters are.
		 */
		private void performOperation(ElementOperator e, Stack<Object> tempStack) {
			String operator = e.getSymbol();
			Number second = getNumber(tempStack);
			Number first = getNumber(tempStack);
			
			if(first instanceof Integer && second instanceof Integer) {
				int result = integerOperation
						(first.intValue(), second.intValue(), operator);
				tempStack.push(result);
			} else {
				double result = doubleOperation(
						first.doubleValue(), second.doubleValue(), operator);
				tempStack.push(result);
			}
		}
		
		/**
		 * This method returns the number, either double or
		 * integer from the top of the stack. If the number is
		 * of type string then it is parsed and the result is
		 * returned.
		 * 
		 * @param stack
		 * 		the stack where the number is
		 * @return
		 * 		the number from the top of the stack.
		 */
		private Number getNumber(Stack<Object> stack) {
			Object o = stack.pop();
			if(o instanceof Integer || o instanceof Double) {
				return (Number)o;
			}
			else if(o instanceof String) {
				String s = (String)o;
				if(s.contains(".")) {
					return Double.parseDouble(s);
				} else {
					return Integer.parseInt(s);
				}
			}
			throw new IllegalArgumentException("The number could not"
					+ "be recognized.");
		}
		
		/**
		 * This method performs the operation on integer numbers.
		 * 
		 * @param first the first operand
		 * @param second the second operand
		 * @param operator the operator to use
		 * @return
		 * 		the result of the operation.
		 */
		private int integerOperation(int first, int second, String operator) {
			return (int)doubleOperation(first, second, operator);
		}

		/**
		 * This is the method which performs the operation on
		 * double numbers and returns the result.
		 * 
		 * @param first the first operand
		 * @param second the second operand
		 * @param operator the operator to use
		 * @return
		 * 		the result of the operation.
		 */
		private double doubleOperation(double first, double second, String operator) {
			switch (operator) {
				case ADD:
					return first + second;
				case SUB:
					return first - second;
				case DIV:
					return first / second;
				case MUL:
					return first * second;
				default:
					throw new UnsupportedOperationException(
							"The " + operator + "operation is not supported.");
			}
		}
		
		/**
		 * This is the method which applies the specified function
		 * and returns the result back to the stack.
		 * 
		 * @param e the function to apply
		 * 
		 * @param tempStack
		 * 		the stack where the parameters are.
		 */
		private void applyFunction(ElementFunction e, Stack<Object> tempStack) {
			Function f = functions.getForName(e.getName());
			f.apply();
		}

		
		@Override
		public void visitDocumentNode(DocumentNode node) {
			int size = node.numberOfChildren();
			for(int i = 0; i < size; i++) {
				node.getChild(i).accept(MyNodeVisitor.this);
			}
		}
		
	}
}
