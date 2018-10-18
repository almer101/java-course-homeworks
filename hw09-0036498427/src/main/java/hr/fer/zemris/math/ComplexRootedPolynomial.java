package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class models the polynomial of complex
 * numbers. The polynomial is of type: 
 * f(z) = (z-z1)*(z-z2)*(z-z3)... where z1, z2..
 * are the roots of the polynomial and are specified in
 * the constructor. 
 * 
 * @author ivan
 *
 */
public class ComplexRootedPolynomial {

	//===========================Properties===============================
	
	/**
	 * The list of the roots of the polynomial.
	 */
	private List<Complex> roots = new ArrayList<>();
	
	//===========================Constructor==============================
	
	/**
	 * This constructor gets a variable number of roots
	 * as a parameter and initializes the list of roots.
	 * 
	 * @param roots
	 * 		the roots of this polynomial.
	 */
	public ComplexRootedPolynomial(Complex ...roots) {
		Objects.requireNonNull(roots, "The roots can not be null!");
		if(roots.length == 0) {
			throw new IllegalArgumentException("Constructor must be called "
					+ "with at least one argument!");
		}
		for(Complex z : roots) {
			this.roots.add(z);
		}
	}
	
	//======================Method implementations========================
	
	/**
	 * This method calculates the value of the polynomial
	 * for the specified complex number <code>z</code>.
	 * 
	 * @param z
	 * 		the complex number for which to calculate the value
	 * 		of the polynomial.
	 * 
	 * @return
	 * 		the value of the polynomial in the specified
	 * 		number.
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ONE;
		for(Complex zi : roots) {
			result = result.multiply(z.sub(zi));
		}
		return result;
	}
	
	/**
	 * This method converts this representation of the
	 * polynomial to the {@link ComplexPolynomial} type.
	 * 
	 * @return
	 * 		the {@link ComplexPolynomial} representation
	 * 		of this polynomial.
	 */
	public ComplexPolynomial toComplexPolynom() {
		int size = roots.size() + 1;
		Complex factors[] = new Complex[size];
		factors[size - 1] = Complex.ONE;
		
		for(int i = 0; i < size; i++) {
			Complex factor = getFactor(i, 0);
			factors[size - i - 1] = i % 2 == 0 ? factor : factor.negate();
		}
		return new ComplexPolynomial(factors);
	}
	
	/**
	 * This method recursively calculates the factor 
	 * for the specified depth. (e.g. the coefficient 
	 * next to z^n is 1 and the depth is 0, the coefficient
	 * next to z^(n-1) is -(z1+z2+z3+...) and the depth is 1.
	 * Furthermore z1*z2 + z1*z3 +...+ z2*z3 + z2*z4 + ... + zn-1*zn
	 * is the coefficient next to z^(n-2) where the depth is 2. 
	 * 
	 * @param depth
	 * 		the depth to which to calculate.
	 * 
	 * @param
	 * 		the starting index(used to recursively control the 
	 * 		for-loop)
	 * 
	 * @return
	 * 		the factor for that depth.
	 */
	private Complex getFactor(int depth, int start) {
		if(depth == 0) return Complex.ONE;
		Complex factor = Complex.ZERO;
		int size = roots.size();
		
		for(int i = start; i < size; i++) {
			Complex z = roots.get(i).multiply(
					getFactor(depth - 1, i + 1));
			factor = factor.add(z);
		}
		return factor;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		boolean appendStar = false;
		for(Complex zi : roots) {
			if(appendStar) sb.append("*");
			sb.append("(z-(" + zi +"))");
			appendStar = true;
		}
		return sb.toString();
	}
	
	/**
	 * This method returns the index of the closest 
	 * root to the specified number <code>z</code> which
	 * is within the threshold. If there is no such root
	 * returns <code>-1</code>.
	 * 
	 * @param z
	 * 		the number to which the closest root is 
	 * 		to be found.
	 * 
	 * @param treshold
	 * 		the threshold, the distance of the specified
	 * 		number and the root, has to be within.
	 * 	
	 * @return
	 * 		the index of the closest root to the specified
	 * 		number.
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		int index = 0;
		double distance = roots.get(index).sub(z).module();
		int size = roots.size();
		
		for(int i = 1; i < size; i++) {
			double d = roots.get(i).sub(z).module();
			if(d < distance) {
				index = i;
				distance = d;
			}
		}
		return distance < treshold ? index : -1;
	}
}
