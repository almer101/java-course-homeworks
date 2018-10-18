package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * This class provides possibility of
 * showing fractals.
 * 
 * @author ivan
 *
 */
public class Context {

	//===============Properties=============================
	
	/**
	 * This is the stack for storing turtle
	 * states. The current state of the turtle
	 * is the one on the top of the stack.
	 * 
	 */
	private ObjectStack states;
	
	//==================Constructors=======================
	
	/**
	 * This is a default constructor which initializes this
	 * classes' property {@link ObjectStack} <code>states</code>.
	 * 
	 */
	public Context() {
		states = new ObjectStack();
	}
	
	//===============Method implementations=================
	
	/**
	 * This method returns the current state of the turtle
	 * from the stack (the current state is the one on the
	 * top of the stack) but does not remove it.
	 * 
	 * @return
	 * 		a current state of the turtle.
	 */
	public TurtleState getCurrentState() {
		return (TurtleState)states.peek();
	}
	
	/**
	 * This method pushes a specified state on the stack
	 * of states.
	 * 
	 * @param state
	 * 		a state to push on the stack.
	 */
	public void pushState(TurtleState state) {
		states.push(state);
	}
	
	/**
	 * This method removes the first state from the top
	 * of the stack of states.
	 * 
	 */
	public void popState() {
		states.pop();
	}
}
