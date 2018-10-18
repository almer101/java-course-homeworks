package hr.fer.zemris.java.hw16.jvdraw.geometricObjects;

import java.awt.Color;


/**
 * This class models the geometric object circle. It contains the data about the
 * center point and the radius of the circle as well as the color of the circle.
 * 
 * @author ivan
 *
 */
public class Circle extends GeometricalObject {
	
	/**The x coordinate of the center of the circle.*/
	private int centerX;
	
	/**The y coordinate of the center of the circle*/
	private int centerY;
	
	/**The radius of the circle.*/
	private int radius;
	
	/**The color of the circle*/
	private Color color;
	
	/**
	 * Constructor initializes all the properties of this class.
	 * 
	 * @param centerX
	 * 		the x coordinate of the center.
	 * @param centerY
	 * 		the y coordinate of the center.
	 * @param radius
	 * 		the radius of the circle.
	 * @param color
	 * 		the color of the circle
	 */
	public Circle(int centerX, int centerY, int radius, Color color) {
		super();
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
		this.color = color;
	}

	
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}

	/**
	 * This method returns the value of the property {@link #centerX}
	 * 
	 * @return
	 * 		the value of the property {@link #centerX}.
	 */
	public int getCenterX() {
		return centerX;
	}

	/**
	 * Sets the value of the property {@link #centerX} to the specified value.
	 * 
	 * @param centerX
	 * 		the new value of the property {@link #centerX}.
	 */
	public void setCenterX(int centerX) {
		this.centerX = centerX;
		listeners.forEach(l -> l.geometricalObjectChanged(this));
	}


	/**
	 * This method returns the value of the property {@link #centerY}
	 * 
	 * @return
	 * 		the value of the property {@link #centerY}.
	 */
	public int getCenterY() {
		return centerY;
	}

	/**
	 * Sets the value of the property {@link #centerY} to the specified value.
	 * 
	 * @param centerY
	 * 		the new value of the property {@link #centerY}.
	 */
	public void setCenterY(int centerY) {
		this.centerY = centerY;
		listeners.forEach(l -> l.geometricalObjectChanged(this));
	}

	/**
	 * This method returns the value of the property {@link #radius}
	 * 
	 * @return
	 * 		the value of the property {@link #radius}.
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Sets the value of the property {@link #radius} to the specified value.
	 * 
	 * @param radius
	 * 		the new value of the property {@link #radius}.
	 */
	public void setRadius(int radius) {
		this.radius = radius;
		listeners.forEach(l -> l.geometricalObjectChanged(this));
	}

	/**
	 * This method returns the value of the property {@link #color}
	 * 
	 * @return
	 * 		the value of the property {@link #color}.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the value of the property {@link #color} to the specified value.
	 * 
	 * @param color
	 * 		the new value of the property {@link #color}.
	 */
	public void setColor(Color color) {
		this.color = color;
		listeners.forEach(l -> l.geometricalObjectChanged(this));
	}

	@Override
	public String toString() {
		return String.format("Circle (%d,%d), %d", centerX, centerY, radius);
	}
}
