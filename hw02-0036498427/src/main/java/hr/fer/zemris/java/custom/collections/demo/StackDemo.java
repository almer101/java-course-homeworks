package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

public class StackDemo {

	public static void main(String[] args) {
	
		ObjectStack stack = new ObjectStack();
		if(args.length != 1) {
			System.out.println("The given number of parameters "
					+ "in the command line is not valid. "
					+ "Expected 1, but was " + args.length);
			return;
		}
		
		String[] input = args[0].trim().split("\\s+");
		String operators = "+-*/%";
		
		for(int i = 0, len = input.length; i < len; i++) {
			
			if(operators.contains(input[i])) {
				//it's an operator
				try {
					performOperation(stack, input[i].charAt(0));
				} catch (EmptyStackException e) {
					System.out.println("The stack is empty! The given "
							+ "expression is not valid!");
					return;
				} catch (ArithmeticException e) {
					System.out.println("Cannot divide by zero!");
					return;
				}
				
			} else {
				//it's possibly a number
				try {
					int number = Integer.parseInt(input[i]);
					stack.push(number);
				} catch (NumberFormatException e) {
					System.out.println("The given expression is not valid!");
					return;
				}
			}
		}
		
		if(stack.size() != 1) {
			System.out.println("The given expression is not valid!");
			return;
		} else {
			System.out.println("Expression evaluates to " + stack.pop());
		}
	}

	/**
	 * Method which performs the operation given by the argument
	 * {@code operator}. The two arguments for the operation are popped
	 * from the {@code stack}. The method pushes the result back 
	 * on the stack.
	 * @param stack a stack where the arguments should be popped from
	 * 			and where the result should be put back to.
	 * @param operator the operation which must be performed
	 * 			(e.g. {@code +}, {@code -} etc.)
	 * @throws EmptyStackException if there are no elements on the
	 * 			stack and method pop() is performed.
	 * @throws ArithmeticException if there is division by zero.
	 */
	private static void performOperation(ObjectStack stack, char operator) 
			throws EmptyStackException, ArithmeticException{
		
		int second = (Integer)stack.pop();
		int first = (Integer)stack.pop();
		
		if(operator == '/' && second == 0) {
			throw new ArithmeticException("Division by zero.");
		}
		
		switch(operator) {
			case '+':	stack.push(first + second);
						//System.out.println("Doing: " + first + " + " + second);
						break;
			case '-':	stack.push(first - second);
						//System.out.println("Doing: " + first + " - " + second);
						break;
			case '*':	stack.push(first * second);
						//System.out.println("Doing: " + first + " * " + second);
						break;
			case '/':	stack.push(first / second);
						//System.out.println("Doing: " + first + " / " + second);
						break;
			case '%':	stack.push(first % second);
						//System.out.println("Doing: " + first + " % " + second);
						break;
			default:		System.out.println("The given expression"
						+ "is not valid.");
						return;
		}
	}
}
