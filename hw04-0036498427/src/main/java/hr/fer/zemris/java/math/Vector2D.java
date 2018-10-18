package hr.fer.zemris.java.math;

import static java.lang.Math.atan;
import static java.lang.Math.PI;
import static java.lang.Math.sqrt;
import static java.lang.Math.sin;
import static java.lang.Math.cos;

/**
 * This class is a model of a 2-dimensional
 * vector with real components x and y.
 * 
 * @author ivan
 *
 */
public class Vector2D {

	//===================Properties===================================
	/**
	 * The x-component of the vector
	 * 
	 */
	private double x;
	
	/**
	 * The y-component of the vector
	 * 
	 */
	private double y;

	//========================Constructors===============================

	/**
	 * This constructor initializes the {@link Vector2D}
	 * object with the specified parameters <code>x</code>, 
	 * <code>y</code>, <code>tailX</code> and
	 * <code>tailY</code>.
	 * 
	 * @param x
	 * 		the initial value of the x-component of the
	 * 		vector
	 * 
	 * @param y
	 * 		the initial value of the y-component of the
	 * 		vector
	 */
	public Vector2D(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	//===========================Getters==================================

	/**
	 * This method gets and returns the value of
	 * the x-component of the vector.
	 * 
	 * @return
	 * 		the x-component of the vector
	 */
	public double getX() {
		return x;
	}

	/**
	 * This method gets and returns the value of
	 * the y-component of the vector.
	 * 
	 * @return
	 * 		the y-component of the vector
	 */
	public double getY() {
		return y;
	}

	//==============Method implementations==============================
	
	/**
	 * This method translates this vector
	 * for the offset given by the parameter 
	 * <code>offset</code>.
	 * 
	 * @param offset
	 * 		the offset to translate this vector for.
	 */
	public void translate(Vector2D offset) {
		if(offset == null) {
			throw new NullPointerException("The offset of the "
					+ "translation must not be null!");
			
		}
		this.x += offset.x;
		this.y += offset.y;
	}
	
	/**
	 * This method returns a new vector which has the same properties
	 * as this vector translated for the <code>offset</code>.
	 *  
	 * @param offset
	 * 		the offset to translate this vector for.
	 * 
	 * @return
	 * 		the new {@link Vector2D} with the same properties as
	 * 		this vector but translated for the <code>offset</code>.
	 */
	public Vector2D translated(Vector2D offset) {
		Vector2D v = this.copy();
		v.translate(offset);
		
		return v;
	}
	
	/**
	 * This method rotates this vector for the value of
	 * <code>angle</code>.
	 * 
	 * @param angle
	 * 		value to rotate the vector for.
	 */
	public void rotate(double angle) {
		double currentAngle = getAngle();
		angle += currentAngle;
		double magnitude = getMagnitude();
		
		x = magnitude * cos(angle);
		y = magnitude * sin(angle);
	}
	
	/**
	 * This method returns a new {@link Vector2D} which
	 * has the same properties as this vector rotates
	 * for the value of <code>angle</code>
	 * 
	 * @param angle
	 * 		the value to rotate the vector for.
	 * 
	 * @return
	 * 		the new rotated vector with the same properties
	 * 		as this vector rotated for the value of
	 * 		<code>angle</code>.
	 */
	public Vector2D rotated(double angle) {
		Vector2D v = this.copy();
		v.rotate(angle);
		
		return v;
	}
	
	/**
	 * This method scales this vector for the value of
	 * <code>scaler</code>. 
	 * 
	 * If <code>abs</code>(scaler) is less than 1 then 
	 * the vector shrinks in magnitude but retains the angle. 
	 * 
	 * If <code>abs</code>(scaler) is larger than 1 then the 
	 * vector expands in magnitude for the value of scaler 
	 * but retains the angle.
	 * 
	 * Finally if the <code>sgn</code>(scaler) = -1 then the
	 * orientation of the vector is changed.
	 * 
	 * @param scaler
	 * 		value to scale the vector for.
	 */
	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}
	
	/**
	 * This method returns a new vector with the
	 * same properties as this vector but scaled for
	 * the value of <code>sclaer</code>.
	 * 
	 * If <code>abs</code>(scaler) is less than 1 then 
	 * the vector shrinks in magnitude but retains the angle. 
	 * 
	 * If <code>abs</code>(scaler) is larger than 1 then the 
	 * vector expands in magnitude for the value of scaler 
	 * but retains the angle.
	 * 
	 * Finally if the <code>sgn</code>(scaler) = -1 then the
	 * orientation of the vector is changed.
	 * 
	 * @param scaler
	 * 		value to scale the vector for.
	 * @return
	 * 		a new {@link Vector2D} with the same properties
	 *		as this vector but scaled for the value of 
	 *		<code>scaler</code>.
	 */
	public Vector2D scaled(double scaler) {
		Vector2D v = this.copy();
		v.scale(scaler);
		
		return v;
	}

	/**
	 * This method returns a copy of this vector as a new
	 * object of type {@link Vector2D};
	 * 
	 * @return
	 * 		a copy of this vector.
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}
	
	/**
	 * This method returns the magnitude of this vector.
	 * 
	 * @return
	 * 		the magnitude of this vector.
	 */
	private double getMagnitude() {
		return sqrt(x*x + y*y);
	}

	/**
	 * This method returns the value of the current angle 
	 * of this vector in radians. 
	 * 
	 * @return
	 * 		the angle of the vector in radians.
	 */
	private double getAngle() {
		if(x == 0 && y >= 0) {
			return PI/2;
			
		} else if(x == 0 && y < 0) {
			return -PI/2;
			
		} 
		double angle = atan(y/x);
		
		if((x < 0 && y <= 0) || (x < 0 && y > 0)) {
			return angle + PI;
		}
		return angle;
	}
}
