package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class models the polynomial of type:
 *  zn*z^n+(zn1)*z^(n-1)+...+z2*z^2+z1*z+z0
 *  The zn, zn1, etc are the factors of the polynomial.
 *  The factors are defined in the constructor.
 *   
 * 
 * @author ivan
 *
 */
public class ComplexPolynomial {

	//============================Properties==================================
	
	/**
	 * The factors of the polynomial. On the index 0
	 * there is the z0 factor, on the index 1 z1, etc.
	 */
	private List<Complex> factors = new ArrayList<>();
	
	//============================Constructor=================================
	
	/**
	 * This constructor gets a variable number of the
	 * factors of the polynomial, first one being the factor next
	 * to z^0, the second next to z^1 etc.
	 * 
	 * @param factors
	 * 		the factors of the polynomial.
	 */
	public ComplexPolynomial(Complex ...factors) {
		Objects.requireNonNull(factors, "The factors can not be null!");
		if(factors.length == 0) {
			throw new IllegalArgumentException("Constructor must be called "
					+ "with at least one argument!");
		}
		int index = findIndexOfNonZeroFactor(factors);
//		int index = factors.length - 1;
		for(int i = 0; i < index + 1; i++) {
			this.factors.add(factors[i]);
		}
	}
	
	/**
	 * This method finds the first index of the non zero
	 * factor starting from the end of the specified array.
	 * This is required to be done because if the polynomial looks
	 * like this: 0*z^4 + 1*z^3 + 3, this is the polynomial of
	 * the 3rd order, not 4th. 
	 * 
	 * @return
	 * 		the index of the first non zero factor starting 
	 * 		from the end of the array.
	 */
	private int findIndexOfNonZeroFactor(Complex[] factors) {
		int len = factors.length;
		for(int i = len - 1; i >= 0; i--) {
			if(!factors[i].equals(Complex.ZERO)) return i;
		}
		return 0;
	}

	//========================Method implementations===========================
	
	/**
	 * This method returns the order of this polynomial.
	 * (e.g. for (7+2i)z^3+2z^2+5z+1 returns 3)
	 * 
	 * @return
	 * 		the order of this polynomial.
	 */
	public short order() {
		return (short)(factors.size() - 1);
	}
	
	/**
	 * This method multiplies this polynomial with the 
	 * specified one and returns the resulting polynomial.
	 * 
	 * @param p
	 * 		the polynomial to multiply with.
	 * 
	 * @return
	 * 		the resulting polynomial.
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		int order = this.order() + p.order();
		Complex newFactors[] = initArray(order + 1); 
		
		for(int i = 0; i < order + 1; i++) {
			for(int j = 0; j <= i; j++) {
				Complex thisFactor = j < factors.size() ? 
						factors.get(j) : Complex.ZERO;
				Complex otherFactor = i-j < p.factors.size() ? 
						p.factors.get(i-j) : Complex.ZERO;
				newFactors[i] = newFactors[i].add(
						thisFactor.multiply(otherFactor));
			}
		}
		return new ComplexPolynomial(newFactors);
	}
	
	/**
	 * This method initializes an array of {@link Complex}
	 * numbers with complex zeros. The initial size of
	 * the array is specified by the parameter.
	 * 
	 * @param size
	 * 		the size of the array.
	 */
	private static Complex[] initArray(int size) {
		Complex array[] = new Complex[size];
		
		for(int i = 0; i < array.length; i++) {
			array[i] = Complex.ZERO;
		}
		return array;
	}

	/**
	 * This method derives the polynomial and returns
	 * the resulting one.
	 * 
	 * @return
	 * 		the derived polynomial.
	 */
	public ComplexPolynomial derive() {
		int size = factors.size();
		Complex newFactors[] = new Complex[size - 1];
		
		for(int i = 1; i < size; i++) {
			newFactors[i - 1] = factors.get(i).multiply(new Complex(i,0));
		}
		return newFactors.length != 0 ? 
				new ComplexPolynomial(newFactors) : new ComplexPolynomial(Complex.ZERO);
	}
	
	/**
	 * This method applies the specified complex number to the
	 * polynomial and returns the resulting value.
	 * 
	 * @param z
	 * 		the {@link Complex} to apply to the polynomial		
	 * 
	 * @return
	 * 		the result after application.
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		int size = factors.size();
		
		for(int i = 0; i < size; i++) {
			result = result.add(factors.get(i).multiply(z.power(i)));
		}
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int size = factors.size();
		boolean printPlus = false;
		
		for(int i = size - 1; i >= 0; i--) {
			if(printPlus) sb.append(" + ");
			printPlus = true;
			sb.append("(" + factors.get(i) + ")*z^" + i);
		}
		return sb.toString(); 
	}
}
