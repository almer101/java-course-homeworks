package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * This is the program asks user to enter the
 * roots of the polynomial. At least two roots have
 * to be entered. After that the program draws an
 * image.
 * 
 * @author ivan
 *
 */
public class Newton {

	/**
	 * This is the method called when the program is run.
	 * 
	 * @param args
	 * 		the command line arguments.
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based "
				+ "fractal viewer.");
		System.out.println("Please enter at least two roots, one root "
				+ "per line. Enter 'done' when done.");
		
		List<Complex> roots = new ArrayList<>();		
		
		inputNumbers(roots);
		
		Complex rootArray[] = new Complex[roots.size()];
		roots.toArray(rootArray);
		ComplexRootedPolynomial p = new ComplexRootedPolynomial(rootArray);
		System.out.println("Image of fractal will appear shortly. Thank you.");
		
		FractalViewer.show(new MyProducer(p));
	}

	/**
	 * Reads number from the user, parses them and adds 
	 * them to the specified list.
	 * 
	 * @param roots
	 * 		the list to store parsed complex numbers in.
	 */
	private static void inputNumbers(List<Complex> roots) {
		Scanner sc = new Scanner(System.in);
		int i = 1;
		System.out.format("Root %d> ", i);
		
		while(sc.hasNext()) {
			String line = sc.nextLine().trim();
			if(line.equalsIgnoreCase("done")) {
				if(i <= 2) {
					System.out.println("At least two roots have to be "
							+ "given!");
					System.out.format("Root %d> ", i);
					continue;
				}
				break;
			}
			if(line.length() == 0) {
				System.out.println("Empty string is not a valid "
						+ "root!");
				System.out.format("Root %d> ", i);
				continue;
			}
			if(parse(line, roots)) i++;
			System.out.format("Root %d> ", i);
		}
		sc.close();
	}

	/**
	 * Parses the number specified by the parameter <code>line</code>
	 * and adds the parsed number to the specified {@link List}.
	 * The method returns <code>true</code> if parsing succeeded and 
	 * <code>false</code> otherwise.
	 * 
	 * @param line
	 * 		a line to be parsed.
	 * 
	 * @param roots
	 * 		the list to add to.
	 * 
	 * @return
	 * 		<code>true</code> if parsing succeeded. <code>false</code>
	 * 		otherwise.
	 */
	private static boolean parse(String line, List<Complex> roots) {
		try {
			Complex z = parseInput(line);
			roots.add(z);
			return true;
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid format of the number.");
			return false;
		}
	}

	/**
	 * This method parses a line (user input) and returns
	 * a parsed complex number.
	 * 
	 * @param line
	 * 		a line to be parsed.	
	 * 
	 * @return
	 * 		the parsed complex number.
	 */
	private static Complex parseInput(String line) {
		boolean firstNumberNegative = false;
		if(line.startsWith("+")) {
			line = line.substring(1);	
			
		} else if(line.startsWith("-")) {
			firstNumberNegative = true;
			line = line.substring(1);
		}
		
		int numOfSigns = countSigns(line);
		if(numOfSigns > 1) {
			throw new IllegalArgumentException("Invalid number format!");
		}
		
		return numOfSigns == 0 ? parseSingleNumber(line, firstNumberNegative) : 
								parseNormalComplexNumber(line, firstNumberNegative);
	}

	/**
	 * This method parses the number of type 1i, i, 2, -3, etc.
	 * So numbers which have only one part.
	 * 
	 * @param line
	 * 		a line to be parsed
	 * 
	 * @param firstNumberNegative
	 * 		an indicator if the number is negative.
	 * 
	 * @return
	 * 		the {@link Complex} which is the result of parsing.
	 */
	private static Complex parseSingleNumber(String line, 
			boolean firstNumberNegative) {
		if(line.trim().equals("i")) 
			return new Complex(0, firstNumberNegative ? -1 : 1);
		
		if(line.contains("i")) {
			return parseSingleImaginary(line, firstNumberNegative);
			
		} else {
			return parseSingleReal(line, firstNumberNegative);
		}
	}
	
	/**
	 * This method parses a number which is of type i, -i, 1i, i2, ect.
	 * So number which have only the imaginary part.
	 * 
	 * @param line
	 * 		a line to be parsed
	 * 
	 * @param firstNumberNegative
	 * 		an indicator that the number is negative.
	 * 
	 * @return
	 * 		the parsed number.
	 */
	private static Complex parseSingleImaginary(String line, 
			boolean firstNumberNegative) {
		if(line.trim().startsWith("i")) {
			line = line.trim().substring(1);
			
		} else {
			line = line.substring(0, line.indexOf("i"));
		}
		double im = Double.parseDouble(line);
		return new Complex(0, firstNumberNegative ? -im : im);
	}

	/**
	 * This method parses the number which have only a real part
	 * and nothing more.
	 * 
	 * @param line
	 * 		a line to be parsed.
	 * 
	 * @param firstNumberNegative
	 * 		an indicator that the first number is negative.
	 * 
	 * @return
	 * 		the parsed number.
	 */
	private static Complex parseSingleReal(String line, 
			boolean firstNumberNegative) {
		double re = Double.parseDouble(line.trim());
		return new Complex(firstNumberNegative ? -re : re, 0);
	}

	/**
	 * This method parses the number which have both, real and 
	 * imaginary part. So a+bi, or a-bi
	 * 
	 * @param line
	 * 		a line to be parsed.
	 * 
	 * @param firstNumberNegative
	 * 		an indicator that the first number is negative.
	 * 
	 * @return
	 * 		the parsed number.
	 */
	private static Complex parseNormalComplexNumber(String line, 
			boolean firstNumberNegative) {
		char c = getSplitSign(line);
		boolean secondNumberNegative = false;
		if(c == '-') {
			secondNumberNegative = true;
		}
		
		String parts[] = line.split("\\" + String.valueOf(c));
		Complex re = parseSingleNumber(parts[0], firstNumberNegative);
		Complex im = parseSingleNumber(parts[1], secondNumberNegative);
		
		return re.add(im);
	}

	/**
	 * This method returns the sign by which to split the
	 * number. The sign can be either + or -. If no sign
	 * is found method returns 0.
	 * 
	 * @param line
	 * 		a line to check.
	 * 
	 * @return
	 * 		a sign by which to split.
	 */
	private static char getSplitSign(String line) {
		for(int i = 0, len = line.length(); i < len; i++) {
			char c = line.charAt(i);
			if(c == '+' || c == '-') {
				return c;
			}
		}
		return 0;
	}

	/**
	 * This method counts how many + or - signs
	 * appear in the line to be parsed.
	 * 
	 * @param line
	 * 		a line to check for + and -.
	 * 
	 * @return
	 * 		the number of + or - signs.
	 */
	private static int countSigns(String line) {
		int num = 0;
		int len = line.length();
		
		for(int i = 0; i < len; i++) {
			if(line.charAt(i) == '+' || line.charAt(i) == '-') {
				num++;
			}
		}
		return num;
	}
}
