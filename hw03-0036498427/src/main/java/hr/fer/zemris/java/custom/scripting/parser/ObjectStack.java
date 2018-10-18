package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.nodes.ArrayIndexedCollection;

/**
 * This class represents a stack for storing objects
 * with characteristic methods for stack (i.e. push,
 * pop and peek)
 * @author ivan
 * @version 1.0
 */
public class ObjectStack {

	private ArrayIndexedCollection stack;
	
	/**
	 * A constructor which creates a new 
	 * {@link ArrayIndexedCollection} and hands over that
	 * reference to {@code stack} variable.
	 */
	public ObjectStack() {
		stack = new ArrayIndexedCollection();
	}
	
	/**
	 * Method returns the size of the stack (number of objects in 
	 * the {@code stack}).
	 * @return number of objects in the {@code stack}.
	 */
	public int size() {
		return stack.size();
	}
	
	/**
	 * Method that checks if there are elements in the stack,
	 * and returns {@code true} in case there is at least 1 element,
	 * and {@code false}	otherwise.
	 * @return {@code true} if there are more than {@code 0} elements;
	 * 			{@code false} otherwise.
	 */
	public boolean isEmpty() {
		return stack.size() == 0;
	}
	
	/**
	 * Method that pushes the element {@code value} on the 
	 * top of the stack.
	 * @param value element to be pushed in the stack.
	 */
	public void push(Object value) {
		try {
			stack.add(value);
		} catch (NullPointerException e) {
			System.out.println("The element could not be added in "
					+ "the stack because the given reference is null!");
		}
	}
	
	/**
	 * Method which returns and removes an element from 
	 * the top of the stack.
	 * @return element from the top of the stack.
	 * @throws EmptyStackException if there are no elements
	 * 			to be poped from the stack.
	 */
	public Object pop() {
		if(size() == 0) {
			throw new EmptyStackException("There are no elements on the stack!"
					+ " Cannot pop when there are no elements in stack!");
		}
		Object obj = stack.get(size() - 1);
		stack.remove(size() - 1);
		return obj;
	}
	
	/**
	 * Method which returns an element from the 
	 * top of the stack, but does not remove it.
	 * @return an element from the top of the stack.
	 * @throws EmptyStackException if there are no 
	 *			elements on the stack.
	 */
	public Object peek() {
		if(size() == 0) {
			throw new EmptyStackException("There are no elements on the stack!"
					+ " Cannot pop when there are no elements in stack!");
		}
		return stack.get(size() - 1);
	}
	
	/**
	 * Method which removes all elements from the stack.
	 */
	public void clear() {
		stack.clear();
	}
}
