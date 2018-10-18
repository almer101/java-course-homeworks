package hr.fer.zemris.java.gui.calc.buttons;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import static java.lang.Math.pow;
import static java.lang.Math.E;
import static java.lang.Math.PI;

/**
 * This class offer static methods which create 
 * {@link DoubleUnaryOperator} objects.
 * 
 * @author ivan
 *
 */
public class Util {

	/**
	 * Returns the unary operator which returns
	 * 1/x.
	 * 
	 * @return
	 * 		{@link DoubleUnaryOperator} which 
	 * 		calculates 1/x
	 */
	public static DoubleUnaryOperator reciprocal() {
		return v -> 1/v;
	}
	
	/**
	 * Returns the unary operator which returns
	 * ln(x).
	 * 
	 * @return
	 * 		{@link DoubleUnaryOperator} which 
	 * 		calculates ln(x)
	 */
	public static DoubleUnaryOperator ln() {
		return v ->  Math.log(v);
	}
	
	/**
	 * Returns the unary operator which returns
	 * e^x.
	 * 
	 * @return
	 * 		{@link DoubleUnaryOperator} which 
	 * 		calculates e^x
	 */
	public static DoubleUnaryOperator powerE() {
		return v -> pow(E, v);
	}
	
	/**
	 * Returns the unary operator which returns
	 * log10(x).
	 * 
	 * @return
	 * 		{@link DoubleUnaryOperator} which 
	 * 		calculates log10(x)
	 */
	public static DoubleUnaryOperator log10() {
		return v -> Math.log10(v);
	}
	
	/**
	 * Returns the unary operator which returns
	 * 10^x.
	 * 
	 * @return
	 * 		{@link DoubleUnaryOperator} which 
	 * 		calculates 10^x.
	 */
	public static DoubleUnaryOperator power10() {
		return v -> pow(10, v);
	}
	
	/**
	 * Returns the unary operator which returns
	 * sin(x).
	 * 
	 * @return
	 * 		{@link DoubleUnaryOperator} which 
	 * 		calculates sin(x).
	 */
	public static DoubleUnaryOperator sin() {
		return v -> Math.sin(v);
	}
	
	/**
	 * Returns the unary operator which returns
	 * asin(x).
	 * 
	 * @return
	 * 		{@link DoubleUnaryOperator} which 
	 * 		calculates asin(x).
	 */
	public static DoubleUnaryOperator asin() {
		return v -> Math.asin(v);
	}
	
	/**
	 * Returns the unary operator which returns
	 * cos(x).
	 * 
	 * @return
	 * 		{@link DoubleUnaryOperator} which 
	 * 		calculates cos(x).
	 */
	public static DoubleUnaryOperator cos() {
		return v -> Math.cos(v);
	}
	
	/**
	 * Returns the unary operator which returns
	 * acos(x).
	 * 
	 * @return
	 * 		{@link DoubleUnaryOperator} which 
	 * 		calculates acos(x).
	 */
	public static DoubleUnaryOperator acos() {
		return v -> Math.acos(v);
	}
	
	/**
	 * Returns the unary operator which returns
	 * tan(x).
	 * 
	 * @return
	 * 		{@link DoubleUnaryOperator} which 
	 * 		calculates tan(x).
	 */
	public static DoubleUnaryOperator tan() {
		return v -> Math.tan(v);
	}
	
	/**
	 * Returns the unary operator which returns
	 * atan(x).
	 * 
	 * @return
	 * 		{@link DoubleUnaryOperator} which 
	 * 		calculates atan(x).
	 */
	public static DoubleUnaryOperator atan() {
		return v -> Math.atan(v);	
	}
	
	/**
	 * Returns the unary operator which returns
	 * ctg(x).
	 * 
	 * @return
	 * 		{@link DoubleUnaryOperator} which 
	 * 		calculates ctg(x).
	 */
	public static DoubleUnaryOperator ctg() {
		return v -> 1/Math.tan(v);
	}
	
	/**
	 * Returns the unary operator which returns
	 * actg(x).
	 * 
	 * @return
	 * 		{@link DoubleUnaryOperator} which 
	 * 		calculates actg(x).
	 */
	public static DoubleUnaryOperator actg() {
		return v -> PI/2 - Math.atan(v);
	}
	
	/**
	 * Returns the binary operator which returns
	 * x^n.
	 * 
	 * @return
	 * 		{@link DoubleUnaryOperator} which 
	 * 		calculates x^n.
	 */
	public static DoubleBinaryOperator xPowerN() {
		return (x,n) -> Math.pow(x, n);
	}
	
	/**
	 * Returns the binary operator which returns
	 * nth root of x.
	 * 
	 * @return
	 * 		{@link DoubleUnaryOperator} which 
	 * 		calculates nth root of x.
	 */
	public static DoubleBinaryOperator nthRoot() {
		return (x,n) -> Math.pow(x, 1/n);
	}
}
