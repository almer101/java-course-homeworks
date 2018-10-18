package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.abs;
import static java.lang.Math.atan;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.pow;

/**
 * This class models a complex number which
 * is immutable once it is created.
 * 
 * @author ivan
 *
 */
public class Complex {

	//==========================Properties==============================
	
	/**
	 * The real part of the complex number
	 */
	private double re;
	
	/**
	 * The imaginary part of the complex number
	 */
	private double im;
	
	//==========================Constants================================
	
	/**
	 * The complex number with real and imaginary parts 0.
	 */
	public static final Complex ZERO = new Complex(0,0); 
	
	/**
	 * The complex number with real part equal to 1,
	 * and imaginary part equal to 0
	 */
	public static final Complex ONE = new Complex(1,0); 
	
	/**
	 * The complex number with real part equal to -1,
	 * and imaginary part equal to 0
	 */
	public static final Complex ONE_NEG = new Complex(-1,0);
	
	/**
	 * The complex number with real part equal to 0,
	 * and imaginary part equal to 1
	 */
	public static final Complex IM = new Complex(0,1);
	
	/**
	 * The complex number with real part equal to 0,
	 * and imaginary part equal to -1
	 */
	public static final Complex IM_NEG = new Complex(0,-1);
	private static final double EPSILON = 1E-06;
	
	//=======================Constructors================================

	/**
	 * This is the constructor which gets real and imaginary part of
	 * the complex number and creates such.
	 * 
	 * @param re
	 * 		the real part of the imaginary number.
	 * 
	 * @param im
	 * 		the imaginary part of the complex number
	 */
	public Complex(double re, double im) {
		super();
		this.re = re;
		this.im = im;
	}
	
	/**
	 * This is the default constructor which creates a
	 * complex number with both real and imaginary part 
	 * equal to zero.
	 * 
	 */
	public Complex() {
		this(0, 0);
	}
	
	//==================Method implementations============================
	
	/**
	 * This method returns the distance of the complex number
	 * to the origin of the coordinate system.
	 * 
	 * @return
	 * 		the module of the complex number(i.e. 
	 * 		the distance from the complex number 
	 * 		to the origin of the coordinate system)
	 */
	public double module() {
		return Math.sqrt(re*re + im*im);
	}
	
	/**
	 * This method returns a new {@link Complex} which
	 * is the result of the multiplication of this 
	 * complex number and the specified one.
	 * 
	 * @param c
	 * 		the number to multiply with.
	 * 
	 * @return
	 * 		the new complex number which is the result of
	 * 		the multiplication.
	 */
	public Complex multiply(Complex c) {
		double real = this.re*c.re - this.im*c.im;
		double imaginary = this.re*c.im + this.im*c.re;
		return new Complex(real, imaginary);
	}

	/**
	 * This method returns a new {@link Complex} which 
	 * is the result of the division of this number 
	 * by the specified one.
	 * 
	 * @param c
	 * 		the number to divide by.
	 * 
	 * @return
	 * 		the new {@link Complex} which is the result
	 * 		of the division.
	 */
	public Complex divide(Complex c) {
		double moduleSquared = c.module()*c.module();
		double real = (this.re*c.re + this.im*c.im)/moduleSquared;
		double imaginary = (this.im*c.re - this.re*c.im)/moduleSquared;
		return new Complex(real, imaginary);
	}
	
	/**
	 * This method returns a new {@link Complex} which is 
	 * the result of adding the specified complex number
	 * to this number.
	 * 
	 * @param c
	 * 		the number to add to this one.
	 * 
	 * @return
	 * 		a new complex number which is the result of
	 * 		the addition.
	 */
	public Complex add(Complex c) {
		return new Complex(this.re + c.re, this.im + c.im);
	}
	
	/**
	 * This method returns a new {@link Complex} which is 
	 * the result of the subtraction of the specified number
	 * from this number.
	 * 
	 * @param c
	 * 		the number to subtract from this one.
	 * 
	 * @return
	 * 		the result of the subtraction.
	 */
	public Complex sub(Complex c) {
		return new Complex(this.re - c.re, this.im - c.im);
	}
	
	/**
	 * This method returns the new {@link Complex} which
	 * is this one, only negated. (i.e. both real and imaginary
	 * parts are negated.)
	 * 
	 * @return
	 * 		this result of the negation.
	 */
	public Complex negate() {
		return new Complex( -this.re, -this.im);
	}
	
	/**
	 * This method returns a new {@link Complex} which is 
	 * the result of the raising this number to the
	 * specified power. The specified power must be a 
	 * non-negative integer.
	 * 
	 * @param n
	 * 		the power to raise to.
	 * 
	 * @return
	 * 		the result of the operation.
	 */
	public Complex power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("The power to raise to"
					+ " must be a non- negative integer!");
		}
		double module = pow(module(), n);
		double angle = getAngle() * n;
		
		return getForModuleAndAngle(module, angle);
	}
	
	/**
	 * This method returns a list of all n-th roots
	 * of this number. The n is given as an argument.
	 * 
	 * @param n
	 * 		which root to calculate.
	 * 
	 * @return
	 * 		the list of all roots of the complex
	 * 		number.
	 */
	public List<Complex> root(int n) {
		
		if(n <= 0) {
			throw new IllegalArgumentException("The root to calculate"
					+ " must be a positive integer!");
		}
		double angle = getAngle()/n;
		double module = doubleEquals(module(), 0) ? 0 : pow(module(), 1.0/n);
		List<Complex> roots = new ArrayList<>();
		
		for(int i = 0; i < n; i++) {
			roots.add(getForModuleAndAngle(module, angle + (2*PI*i)/n));
		}
		return roots;
	}
	
	@Override
	public String toString() {
		return im > 0 ? String.format("%f + %fi", re, im) : 
						String.format("%f - %fi", re, abs(im));
	}
	
	@Override
	public boolean equals(Object obj) {
		Objects.requireNonNull(obj);
		Complex other = (Complex)obj;
		return doubleEquals(re, other.re) &&
				doubleEquals(im, other.im);
	}
	
	/**
	 * This method returns a new complex number for the
	 * specified module and angle.
	 * 
	 * @param module
	 * 		the module of the number.
	 * 
	 * @param angle
	 * 		the angle of the number.
	 * 
	 * @return
	 * 		the new complex number with the specified
	 * 		module and angle.
	 */
	private Complex getForModuleAndAngle(double module, double angle) {
		return new Complex(module*cos(angle), module*sin(angle));
	}

	/**
	 * This method returns the angle of this complex number.
	 * 
	 * @return
	 * 		the angle of the complex number.
	 */
	private double getAngle() {
		if(doubleEquals(re, 0) && doubleEquals(im, 0)) return 0;
		if(doubleEquals(re, 0)) return im > 0 ? PI/2 : -PI/2;
		
		double angle = atan(im/re);
		return re < 0 ? angle + PI : angle;
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
	private boolean doubleEquals(double d1, double d2) {
		return abs(d1 - d2) < EPSILON;
	}
}
