package hr.fer.zemris.java.hw16.jvdraw.geometricObjects;

import java.awt.Color;

/**
 * This class models the geometric object filled circle. It contains the data
 * about the center of the circle and its radius as well as fill and line color
 * of the circle.
 * 
 * @author ivan
 *
 */
public class FilledCircle extends GeometricalObject {
	
	/** The x coordinate of the center of the circle. */
	private int centerX;

	/** The y coordinate of the center of the circle */
	private int centerY;

	/** The radius of the circle. */
	private int radius;

	/** The color of the circle outline */
	private Color outlineColor;

	/** The color of the fill of the circle */
	private Color fillColor;

	/**
	 * Constructor which initializes all the properties of this class.
	 * 
	 * @param centerX
	 * 		the x coordinate of the center of the circle
	 * @param centerY
	 * 		the y coordinate of the center of the circle
	 * @param radius
	 * 		the radius of the circle.
	 * @param outlineColor
	 * 		the color of the outline.
	 * @param fillColor
	 * 		the color of the fill.
	 */
	public FilledCircle(int centerX, int centerY, int radius, Color outlineColor,
			Color fillColor) {
		super();
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
		this.outlineColor = outlineColor;
		this.fillColor = fillColor;
	}

	
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
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
	 * This method returns the value of the property {@link #outlineColor}
	 * 
	 * @return
	 * 		the value of the property {@link #outlineColor}.
	 */
	public Color getOutlineColor() {
		return outlineColor;
	}

	/**
	 * Sets the value of the property {@link #outlineColor} to the specified value.
	 * 
	 * @param outlineColor
	 * 		the new value of the property {@link #outlineColor}.
	 */
	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
		listeners.forEach(l -> l.geometricalObjectChanged(this));
	}

	/**
	 * This method returns the value of the property {@link #fillColor}
	 * 
	 * @return
	 * 		the value of the property {@link #fillColor}.
	 */
	public Color getFillColor() {
		return fillColor;
	}

	/**
	 * Sets the value of the property {@link #fillColor} to the specified value.
	 * 
	 * @param fillColor
	 * 		the new value of the property {@link #fillColor}.
	 */
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
		listeners.forEach(l -> l.geometricalObjectChanged(this));
	}

	@Override
	public String toString() {
		return String.format("Filled circle (%d,%d), %d, #%s", centerX, centerY, radius,
				Integer.toHexString(fillColor.getRGB()));
	}

}
