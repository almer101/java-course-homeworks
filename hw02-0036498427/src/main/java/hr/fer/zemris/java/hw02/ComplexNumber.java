package hr.fer.zemris.java.hw02;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.sqrt;

import java.util.Objects;

import static java.lang.Math.atan;
import static java.lang.Math.PI;
import static java.lang.Math.abs;

public class ComplexNumber {

	private double real;
	private double imaginary;
	private static final double EPSILON = 1E-06;
	
	/**
	 * A constructor with 2 arguments, real and
	 * imaginary part of the complex number.
	 * @param real the real part of a complex number
	 * @param imaginary the imaginary part of a
	 * 			complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Factory method which returns a new complex number
	 * that has the real part {@code real} and 
	 * imaginary part {@code 0}.
	 * @param real real part of a {@link ComplexNumber} to be
	 * 			created.
	 * @return a new {@link ComplexNumber} with real part 
	 * 			equal to {@code real}
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Factory method which returns a new complex number
	 * that has the imaginary part equal to {@code imaginary} and 
	 * real part {@code 0}.
	 * @param imaginary imaginary part of a {@link ComplexNumber}
	 * 			to be created.
	 * @return a new {@link ComplexNumber} with imaginary part
	 * 			equal to {@code imaginary}
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * 
	 * @param magnitude the absolute value (the length) of
	 * 			the complex number. 
	 * @param angle angle of the number in radians 
	 * 			(from 0 to 2*PI)
	 * @return a new {@link ComplexNumber} with the magnitude
	 * 			{@code magnitude} and angle {@code angle}.
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		if(magnitude < 0) {
			throw new IllegalArgumentException("The given magnutide must be larger than "
					+ "or equal to 0. Was " + magnitude);
		}
		
		return new ComplexNumber(magnitude*cos(angle),magnitude*sin(angle));
	}
	
	/**
	 * The method parses the given argument s into the complex number.
	 * Given parameter {@code s} must be of type: a + bi, ai + b, a , bi;
	 * @param s the string that should be parsed
	 * @return the {@link ComplexNumber} parsed from the string {@code s}
	 */
	public static ComplexNumber parse(String s) throws NumberFormatException{
		if(s == null) {
			throw new NullPointerException("The given argument which "
					+ "has to be parsed must not be null!");
		}
		s = s.trim();
		
		boolean firstNumberNegative = false;
		
		//checking if fist number is negative or positive
		if(s.charAt(0) == '-') {
			firstNumberNegative = true;
			s = s.substring(1);
		} else if (s.charAt(0) == '+') {
			s = s.substring(1);
		}
		
		int numberOfSigns = countSigns(s);
		
		//checking what is the type the given expression.
		if(numberOfSigns >= 2) {
			//the message will be written and the program
			//will return null
			
		} else if (numberOfSigns == 0) {
			//the number looks either like this: a, or like this: bi
			try {
				return parseSimple(s, firstNumberNegative);
			} catch (NumberFormatException e) {
				//the message will be written and the program
				//will return null.
			}
			
		} else {
			//the number looks like this: a + bi
			try {
				return parseAdvanced(s, firstNumberNegative);
			} catch (IllegalArgumentException e) { 
				//NumberFormatException extends IllegalArgumentException
				//the message will be written and the program
				//will return null
			}
		}
		
		System.out.println("The number could not be parsed! "
				+ "So the null value is returned!");
		return null; //if the number could not be parsed.
	}
	
	/**
	 * Method parses a complex number given by the string s. 
	 * And the string {@code s} must be either of type 
	 * {@code a} or type {@code bi} complex number.
	 * @param s string to be parsed
	 * @param firstNumberNegative {@code true} if the number is negative
	 * @return a {@link ComplexNumber} parsed from the string {@code s}.
	 */
	private static ComplexNumber parseSimple(String s, boolean numberNegative) {
		double imaginary = 0;
		double real = 0;
		
		if(s.contains("i")) {
			//if it contains i it's just an imaginary part (e.g. bi)
			if(s.equals("i")) {
				imaginary = numberNegative ? -1 : 1;
			} else {
				s = s.substring(0, s.indexOf('i')).trim();
				imaginary = parseMyNumber(s, numberNegative);
			}
			
		} else {
			//otherwise it's real (e.g. a)
			real = parseMyNumber(s, numberNegative);
		}
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Method parses the number of type "a+bi" or "bi+a" 
	 * and return a new {@link ComplexNumber} with real 
	 * part {@code a} and imaginary part {@code b}.
	 * @param s string to be parsed
	 * @param firstNumberNegative if the first number in
	 * 			the expression is negative
	 * @return a new {@link ComplexNumber} parsed from the
	 * 			String s.
	 */
	private static ComplexNumber parseAdvanced(String s, 
			boolean firstNumberNegative) {
		double imaginary = 0;
		double real = 0;
		
		if(!s.contains("i")) {
			throw new IllegalArgumentException("The given argument is not valid. "
					+ "A complex number should look like this: a + bi"
					+ " or bi or a!");
		}
		boolean secondNumberNegative = false;
		String delimiter = findDelimiter(s);
		
		if(delimiter.charAt(0) == '-') {
			secondNumberNegative = true;
		}
		
		String firstNumberString = s.substring(0, s.indexOf(delimiter)).trim();
		String secondNumberString = s.substring(s.indexOf(delimiter) + 1).trim();
		
		if(firstNumberString.contains("i")) {
			//first number is imaginary part and second is real
			if(firstNumberString.equals("i")) {
				imaginary = firstNumberNegative ? -1 : 1;
			} else {
				firstNumberString = firstNumberString
						.substring(0, firstNumberString.indexOf('i'))
						.trim();
				imaginary = parseMyNumber(firstNumberString, firstNumberNegative);
			}
			real = parseMyNumber(secondNumberString, secondNumberNegative);
		} else {
			//first number is real part and second is imaginary
			
			if(secondNumberString.equals("i")) {
				imaginary = secondNumberNegative ? -1 : 1;
			} else {
				secondNumberString = secondNumberString
						.substring(0, secondNumberString.indexOf('i'))
						.trim();
				imaginary = parseMyNumber(secondNumberString, secondNumberNegative);
			}
			real = parseMyNumber(firstNumberString, firstNumberNegative);
		}
		
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * The method finds and returns a delimiter either {@code +} or {@code -}.
	 * @param s the String where the delimiter must be found.
	 * @return the wanted delimiter + or -, or null if there was not 
	 * 			a delimiter + or - in the String {@code s};
	 */
	private static String findDelimiter(String s) {
		for(int i = 0, len = s.length(); i < len; i++) {
			char c = s.charAt(i);
			if(c == '+' || c == '-') {
				return String.valueOf(c);
			}
		}
		return null;
	}

	/**
	 * Method which counts how many signs + or - are there in 
	 * the argument {@code s}.
	 * @param s the string for which has to be checked how many
	 * 			+ or - signs does it have.
	 * @return the number of + or - signs in the string.
	 */
	private static int countSigns(String s) {
		int count = 0;
		
		for(int i = 0, len = s.length(); i < len; i++) {
			if(s.charAt(i) == '+' || s.charAt(i) == '-') {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Method returns the value of the real part of 
	 * {@link ComplexNumber}.
	 * @return the real part of complex number
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Method returns the value of the imaginary part of
	 * {@link ComplexNumber}.
	 * @return the imaginary part of the complex number.
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Method returns the magnitude (length) of the 
	 * {@link ComplexNumber}.
	 * @return the magnitude (length) of a complex number
	 */
	public double getMagnitude() {
		return sqrt(real*real + imaginary*imaginary);
	}
	
	/**
	 * The method returns an angle of the complex number.
	 * If the real and imaginary part are both 0 than the
	 * method returns 0.
	 * @return the angle of the complex number in radians.
	 */
	public double getAngle() {
		if(abs(real) < EPSILON && abs(imaginary) < EPSILON) {
			// if the number has magnitude 0, than returns the angle 0
			return 0;
		} else if(real >= 0 && imaginary >= 0) {
			//the number is in I quadrant so everything is fine
			if(real == 0) {
				return PI/2;
			}
			return atan(imaginary / real);
		} else if (real >= 0 && imaginary < 0){
			//the number is in IV quadrant so 2PI
			//must be added to the angle
			if(real == 0) {
				return 3*PI/2;
			}
			return atan(imaginary / real) + 2*PI;
		} else {
			//the number is in II, III quadrant
			//so PI must be added to the angle
			return atan(imaginary / real) + PI;
		}
	}
	
	/**
	 * Parses double positive number given by {@code number} string,
	 * and if {@code negative} is {@code true} the value of
	 * the parsed number is set to negative.
	 * @param number string to be parsed
	 * @param negative if {@code true} the returned number will be negative;
	 * 					if {@code false} then the returned number will
	 * 					be positive
	 * @return parsed value of the number {@code number}.
	 */
	private static double parseMyNumber(String number, boolean negative){
		return negative ? -Double.parseDouble(number) : Double.parseDouble(number);
	}
	
	/**
	 * Method adds a {@code z} {@link ComplexNumber} to the 
	 * which the function was called on and returns a new
	 * {@link ComplexNumber} which represents their sum.
	 * @param z is a number to add to {@code this} number
	 * @return sum of those two numbers.
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, 
									this.imaginary + c.imaginary);
	}
	
	/**
	 * Method subtracts a z {@link ComplexNumber} from the number this function
	 * was called on. 
	 * @param z number to be subtracted from the {@code this number}
	 * @return a result of the subtraction.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.real, 
									this.imaginary - c.imaginary);
	}
	
	/**
	 * Method multiplies two complex numbers.
	 * {@code this * c}.
	 * @param c factor in multiplication
	 * @return multiplied numbers
	 */
	public ComplexNumber mul(ComplexNumber c) {
		double magnitude = this.getMagnitude() * c.getMagnitude();
		double angle = this.getAngle() + c.getAngle();
		angle = normalizeAngle(angle);
		
		return ComplexNumber.fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * The method divides complex number this method was called on
	 * by the complex number {@code c} given by the parameter.
	 * @param c {@link ComplexNumber} to divide by
	 * @return a divided complex number.
	 */
	public ComplexNumber div(ComplexNumber c) {
		double magnitude = this.getMagnitude() / c.getMagnitude();
		double angle = this.getAngle() - c.getAngle();
		angle = normalizeAngle(angle);
		
		return ComplexNumber.fromMagnitudeAndAngle(magnitude, angle);
	}

	
	/**
	 * Method calculates a complex number raised to the power
	 * of {@code n}. 
	 * @param n the power to raise to
	 * @return complex number raised to the power of n.
	 */
	public ComplexNumber pow(int n) {
		if( n < 0) {
			throw new IllegalArgumentException("Power should "
					+ "be >= 0. Was " + n);
		}
		double magnitude = Math.pow(this.getMagnitude(), n);
		double angle = this.getAngle() * n;
		angle = normalizeAngle(angle);
		
		return ComplexNumber.fromMagnitudeAndAngle(magnitude, angle);
	}
	
	/**
	 * The method calculates all the roots of the complex number.
	 * @param n n-th root.
	 * @return an array of all the roots of the complex number.
	 */
	public ComplexNumber[] root(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException("The root of the number should "
					+ "be larger than zero! Was " + n);
		}
		ComplexNumber numbers[] = new ComplexNumber[n];
		double magnitude = Math.pow(this.getMagnitude(), 1/(double)n);
		
		for(int i = 0; i < n; i++) {
			double angle = (this.getAngle() + 2*PI*i) / ((double)n);
			angle = normalizeAngle(angle);
			numbers[i] = ComplexNumber.fromMagnitudeAndAngle(magnitude, angle);
		}
		return numbers;
	}
	/**
	 * Method normalizes the {@code angle} to be in interval 
	 * [0,2*PI).
	 * @param angle angle to be normalized
	 * @return normalized value
	 */
	private static double normalizeAngle(double angle) {
		if(angle >= 2*PI) {
			int k = (int)(angle / (2*PI));
			angle -= k * 2*PI;
		} else if(angle < 0) {
			int k = (int)(angle / (2*PI)) - 1;
			angle -= k * 2*PI;
		}
		return angle;
	}

	@Override
	public String toString() {
		if(imaginary < 0) {
			return String.format("%.3f - %.3fi", this.real, abs(this.imaginary));
		} else {
			return String.format("%.3f + %.3fi", this.real, abs(this.imaginary));
		}
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ComplexNumber)) return false;
		return (abs(this.real - ((ComplexNumber)obj).real) < EPSILON ) &&
				(abs(this.imaginary - ((ComplexNumber)obj).imaginary) < EPSILON);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(real, imaginary);
	}
}
