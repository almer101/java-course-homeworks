package hr.fer.zemris.math;

import java.util.Objects;

/**
 * This class represents a 3D vector. The vector
 * has 3 components x,y and z. The once created
 * vector is immutable.
 * 
 * @author ivan
 *
 */
public class Vector3 {

	//============================Properties===============================
	
	/**
	 * The value of the x component of the vector.
	 */
	private double x;
	
	/**
	 * The value of the y component of the vector.
	 */
	private double y;
	
	/**
	 * The value of the z component of the vector.
	 */
	private double z;
	
	//==============================Constants==============================
	
	private static final double EPSILON = 1E-06;
	
	//=============================Constructor=============================
	
	/**
	 * This constructor which gets initial values of all three
	 * components of the 3D vector
	 * 
	 * @param x
	 * 		the initial value of the x component of the vector
	 * 
	 * @param y
	 * 		the initial value of the y component of the vector
	 * 
	 * @param z
	 * 		the initial value of the Z component of the vector
	 */
	public Vector3(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	//==============================Getters=================================
	
	/**
	 * This method returns the value of the x
	 * component of the vector.
	 * 
	 * @return
	 * 		the value of the x component of the vector.
	 */
	public double getX() {
		return x;
	}

	/**
	 * This method returns the value of the y
	 * component of the vector.
	 * 
	 * @return
	 * 		the value of the y component of the vector.
	 */
	public double getY() {
		return y;
	}

	/**
	 * This method returns the value of the z
	 * component of the vector.
	 * 
	 * @return
	 * 		the value of the z component of the vector.
	 */
	public double getZ() {
		return z;
	}
	
	//========================Method implementations========================
	
	/**
	 * This method returns the norm (the length) of
	 * the vector.
	 * 
	 * @return
	 * 		the norm of the vector.
	 */
	public double norm() {
		return Math.sqrt(x*x + y*y + z*z);
	}
	
	/**
	 * This vector return a new vector which is
	 * the same direction as this vector but normalized
	 * (i.e. the length is 1).
	 * 
	 * @return
	 * 		the normalized vector.
	 */
	public Vector3 normalized() {
		if(doubleEquals(norm(), 0)) return new Vector3(0, 0, 0);
		return this.scale(1/this.norm());
	}
	
	/**
	 * This method returns a new vector which is the
	 * resulting vector of the adding specified vector to 
	 * this vector.
	 * 
	 * @param other
	 * 		the vector to add to this vector.
	 * 
	 * @return
	 * 		the new vector which is equal to
	 * 		this vector + other vector
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(this.x + other.x,
							this.y + other.y,
							this.z + other.z);
	}
	
	/**
	 * This method returns a new vector which is the
	 * resulting vector of the subtraction of the
	 * specified vector from this vector.
	 * (this vector - other vector)
	 * 
	 * @param other
	 * 		vector to subtract from this vector.
	 * 
	 * @return
	 * 		a new vector which is the result of
	 * 		subtraction.
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(this.x - other.x,
							this.y - other.y,
							this.z - other.z);
	}
	
	/**
	 * This method returns the dot product of
	 * this vector and specified other vector.
	 * 
	 * @param other
	 * 		the other vector for dot product.		
	 * 
	 * @return
	 * 		the dot product of this and other
	 * 		vector.
	 */
	public double dot(Vector3 other) {
		return this.x*other.x + this.y*other.y + this.z*other.z;
	}
	
	/**
	 * This method return the cross product of this vector
	 * and specified vector (i.e. (this vector)x(other vector))
	 * 
	 * @param other
	 * 		the other vector for cross product.		
	 * 
	 * @return
	 * 		the new vector which is the cross product
	 * 		of this vector with the specified one.
	 */
	public Vector3 cross(Vector3 other) {
		double newX = this.y*other.z - this.z*other.y;
		double newY = this.z*other.x - this.x*other.z;
		double newZ = this.x*other.y - this.y*other.x;
		return new Vector3(newX, newY, newZ);
	}
	
	/**
	 * This method scales this vector with the specified
	 * scaler and returns the resulting vector. This vector
	 * stays unchanged.
	 * 
	 * @param s
	 * 		a value to scale with.
	 * 
	 * @return		
	 * 		a new vector which is actually this vector
	 * 		scales with the specified scaler.
	 */
	public Vector3 scale(double s) {
		return new Vector3(this.x*s, this.y*s, this.z*s);
	}
	
	/**
	 * This method returns the value of the cosine of the
	 * angle between this vector and the specified vector.
	 * 
	 * @param other
	 * 		the other vector to which the angle(cosine of
	 * 		the angle) is needed.
	 * 
	 * @return
	 * 		the value of the cosine of the angle 
	 * 		between this vector and the other vector.
	 */
	public double cosAngle(Vector3 other) {
		if(doubleEquals(norm(), 0) ||
				doubleEquals(other.norm(), 0)) return 0; 
		return (this.dot(other))/(this.norm()*other.norm());
	}

	/**
	 * This method returns an array of the vector components.
	 * The one on the index 0 is x, on the index 1 is y, and
	 * on the index 2 is z.
	 * 
	 * @return
	 * 		the array of the vector components.
	 */
	public double[] toArray() {
		return new double[] {this.x, this.y, this.z};
	}
	
	/**
	 * This method compares two specified double numbers.
	 * The numbers are equal if the result of subtraction
	 * is by absolute value smaller than <code>EPSILON</code>
	 * 
	 * @param d1
	 * 		the first number to compare
	 * 
	 * @param d2
	 * 		the second number to compare.
	 * 
	 * @return
	 * 		<code>true</code> if numbers are equal. 
	 *  	<code>false</code> otherwise.
	 */
	private static boolean doubleEquals(double d1, double d2) {
		return Math.abs(d1 - d2) < EPSILON;
	}
	
	@Override
	public String toString() {
		return String.format("(%f, %f, %f)", this.x, this.y, this.z);
	}
	
	@Override
	public boolean equals(Object obj) {
		Objects.requireNonNull(obj, "The vector to compare with "
				+ "cannot be null!");
		
		Vector3 v = (Vector3)obj;
		if(!doubleEquals(x, v.x)) return false;
		if(!doubleEquals(y, v.y)) return false;
		if(!doubleEquals(z, v.z)) return false;
		
		return true;
	}
}
