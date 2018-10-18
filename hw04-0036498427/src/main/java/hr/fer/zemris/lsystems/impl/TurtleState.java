package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.math.Vector2D;

/**
 * This class stores the state of the turtle,
 * including its current position, direction
 * in which the turtle is headed, color of the
 * trail the turtle leaves behind itself and
 * a length of a step. 
 * 
 * @author ivan
 *
 */
public class TurtleState {

	//====================Properties====================================
	/**
	 * The current position of the turtle.
	 * 
	 */
	private Vector2D currentPosition;
	
	/**
	 * The direction where the turtle is headed.
	 * It is a unit vector.
	 * 
	 */
	private Vector2D direction;
	
	/**
	 * The color of the trail the turtle leaves
	 * behind itself.
	 * 
	 */
	private Color color;
	
	/**
	 * The effective length of a move this turtle makes.
	 * 
	 */
	private double delta;
	
	//================Constructors======================================
	
	/**
	 * This constructor receives 4 parameters and initializes
	 * the object's properties with the given values.
	 * 
	 * @param currentPosition
	 * 		initial current position of the turtle.
	 * 
	 * @param direction
	 * 		initial direction of the turtle.
	 * 
	 * @param color
	 * 		initial trail this turtle leaves behind itself.
	 * 
	 * @param delta
	 * 		initial value of the effective length of a move
	 * 		this turtle makes.
	 * 
	 */
	public TurtleState(Vector2D currentPosition, 
			Vector2D direction, Color color, double delta) {
		super();
		this.currentPosition = currentPosition;
		this.direction = direction;
		this.color = color;
		this.delta = delta;
	}
	
	//=====================Getters and setters===========================

	/**
	 * This method gets and returns a value of the vector
	 * of direction.
	 * 
	 * @return
	 * 		vector of direction of the turtle.
	 */
	public Vector2D getDirection() {
		return direction;
	}
	
	/**
	 * This method gets and returns the current
	 * position vector.
	 * 
	 * @return
	 * 		the current position vector.
	 */
	public Vector2D getCurrentPosition() {
		return currentPosition;
	}
	
	/**
	 * This method gets and returns the current color
	 * of the trail lines this turtle leaves behind itself.
	 * 
	 * @return
	 * 		the current color of the lines.
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * This method gets and returns the value of
	 * the effective move a turtle makes (i.e. 
	 * the value of delta)
	 * 
	 * @return
	 * 		the value of delta.
	 */
	public double getDelta() {
		return delta;
	}
	
	/**
	 * This method sets the value of the property 
	 * <code>color</code> to the specified value.
	 * 
	 * @param color
	 * 		a new value of the color.
	 */
	public void setColor(Color color) {
		if(color == null) {
			throw new NullPointerException("The color must "
					+ "not be null!");
		}
		
		this.color = color;
	}
	
	/**
	 * This method sets the value of the effective length 
	 * of the move to the new specified value.
	 * 
	 * @param delta
	 * 		the new value to set the <code>delta</code> property to.
	 */
	public void setDelta(double delta) {
		this.delta = delta;
	}

	//=====================Method implementations============================
	
	/**
	 * This method returns a copy of the state this
	 * turtle is in.
	 * 
	 * @return 
	 * 		a copy of this {@link TurtleState}.
	 */
	public TurtleState copy() {
		return new TurtleState(currentPosition.copy(), 
								direction.copy(), 
								color, 
								delta);
	}
	
	/**
	 * This method sets the current position to the new
	 * specified position.
	 * 
	 * @param currentPosition
	 * 		the new position of the <code>currentPosition</code>
	 * 		property.
	 */
	public void setCurrentPosition(Vector2D currentPosition) {
		if(currentPosition == null) {
			throw new NullPointerException("A new position to set "
					+ "the current position to, must not be null!");
		}
		
		this.currentPosition = currentPosition;
	}
}
