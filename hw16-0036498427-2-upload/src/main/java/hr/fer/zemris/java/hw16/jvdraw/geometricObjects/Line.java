package hr.fer.zemris.java.hw16.jvdraw.geometricObjects;

import java.awt.Color;

/**
 * This class models the geometric object Line. It contains the data about the
 * starting and ending point of the line, as well as line's color.
 * 
 * @author ivan
 *
 */
public class Line extends GeometricalObject {

	/**The x coordinate of the start of the line.*/
	private int xStart;
	
	/**The y coordinate of the start of the line.*/
	private int yStart;
	
	/**The x coordinate of the end of the line.*/
	private int xEnd;
	
	/**The y coordinate of the end of the line.*/
	private int yEnd;
	
	/**The color of the line.*/
	private Color color;
	
	/**
	 * This constructor initializes all the properties of this class. 
	 * 
	 * @param xStart
	 * 		the x coordinate of the line start
	 * @param yStart
	 * 		the y coordinate of the line start
	 * @param xEnd
	 * 		the x coordinate of the line end
	 * @param yEnd
	 * 		the y coordinate of the line end
	 * @param color	
	 * 		the color of the line.
	 */
	public Line(int xStart, int yStart, int xEnd, int yEnd, Color color) {
		super();
		this.xStart = xStart;
		this.yStart = yStart;
		this.xEnd = xEnd;
		this.yEnd = yEnd;
		this.color = color;
	}
	
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}	
	
	/**
	 * Returns the value of the start x coordinate of the line.
	 * 
	 * @return
	 * 		the x coordinate of the line start.
	 */
	public int getxStart() {
		return xStart;
	}

	/**
	 * Returns the value of the start y coordinate of the line.
	 * 
	 * @return
	 * 		the y coordinate of the line start.
	 */
	public int getyStart() {
		return yStart;
	}

	/**
	 * Returns the value of the end x coordinate of the line.
	 * 
	 * @return
	 * 		the x coordinate of the line end.
	 */
	public int getxEnd() {
		return xEnd;
	}

	/**
	 * Returns the value of the end y coordinate of the line.
	 * 
	 * @return
	 * 		the y coordinate of the line end.
	 */
	public int getyEnd() {
		return yEnd;
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
	 * Sets the value of the property {@link #xStart} to the specified value
	 * 
	 * @param xStart
	 * 		the new value of the {@link #xStart} property.
	 */
	public void setxStart(int xStart) {
		this.xStart = xStart;
		listeners.forEach(l -> l.geometricalObjectChanged(this));
	}
	
	/**
	 * Sets the value of the property {@link #xEnd} to the specified value
	 * 
	 * @param xEnd
	 * 		the new value of the {@link #xEnd} property.
	 */
	public void setxEnd(int xEnd) {
		this.xEnd = xEnd;
		listeners.forEach(l -> l.geometricalObjectChanged(this));
	}
	
	/**
	 * Sets the value of the property {@link #yStart} to the specified value
	 * 
	 * @param yStart
	 * 		the new value of the {@link #yStart} property.
	 */
	public void setyStart(int yStart) {
		this.yStart = yStart;
		listeners.forEach(l -> l.geometricalObjectChanged(this));
	}
	
	/**
	 * Sets the value of the property {@link #yEnd} to the specified value
	 * 
	 * @param yEnd
	 * 		the new value of the {@link #yEnd} property.
	 */
	public void setyEnd(int yEnd) {
		this.yEnd = yEnd;
		listeners.forEach(l -> l.geometricalObjectChanged(this));
	}
	
	/**
	 * Sets the value of the property {@link #color} to the specified value
	 * 
	 * @param color
	 * 		the new value of the {@link #color} property.
	 */
	public void setColor(Color color) {
		this.color = color;
		listeners.forEach(l -> l.geometricalObjectChanged(this));
	}
	
	@Override
	public String toString() {
		return String.format("Line (%d,%d)-(%d,%d)", xStart, yStart, xEnd, yEnd);
	}
	
}
