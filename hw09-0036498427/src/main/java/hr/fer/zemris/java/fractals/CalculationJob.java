package hr.fer.zemris.java.fractals;

import java.util.concurrent.Callable;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * This class performs the calculation of the colors
 * to be shown on the picture. The color is calculated
 * for each complex number.
 * 
 * @author ivan
 *
 */
public class CalculationJob implements Callable<Void> {

	/**The minimum real value.*/
	private double reMin;
	
	/**The maximum real value*/
	private double reMax;
	
	/**The minimum imaginary value.*/
	private double imMin;
	
	/**The maximum imaginary value.*/
	private double imMax;
	
	/**The width of the frame.*/
	private int width;
	
	/**The height of the frame*/
	private int height;
	
	/**
	 * The smaller y coordinate of the strip this 
	 * job will draw.
	 */
	private int yMin;
	
	/**
	 * The bigger y coordinate of the strip this 
	 * job will draw.
	 */
	private int yMax;
	
	/**The number of iterations for checking the convergence*/
	private int m;
	
	/**The array where to store data*/
	private short data[];
	
	/**The polynomial whose convergence has to be checked.*/
	private ComplexRootedPolynomial polynom;
	
	/**The threshold of the convergence of the root.*/
	private static double CONVERGENCE_THRESHOLD = 1E-03;
	
	/**
	 * The threshold for accepting or declining the
	 * number.
	 */
	private static double MODULE_CLOSENESS = 1E-03;
	
	/**
	 * The constructor initializes all the parameters of the
	 * class.
	 * 
	 * @param reMin the initial value of the {@link #reMin}
	 * @param reMax the initial value of the {@link #reMax}
	 * @param imMin the initial value of the {@link #imMin}
	 * @param imMax the initial value of the {@link #imMax}
	 * @param width the initial value of the {@link #width}
	 * @param height the initial value of the {@link #height}
	 * @param yMin the initial value of the {@link #yMin}
	 * @param yMax  the initial value of the {@link #yMax}
	 * @param m the initial value of the {@link #m}
	 * @param data the initial value of the {@link #data}
	 * @param polynom the initial value of the {@link #polynom}
	 */
	public CalculationJob(double reMin, double reMax, double imMin, 
			double imMax, int width, int height, int yMin, 
			int yMax, int m, short[] data, ComplexRootedPolynomial polynom) {
		super();
		this.reMin = reMin;
		this.reMax = reMax;
		this.imMin = imMin;
		this.imMax = imMax;
		this.width = width;
		this.height = height;
		this.yMin = yMin;
		this.yMax = yMax;
		this.m = m;
		this.data = data;
		this.polynom = polynom;
	}

	@Override
	public Void call() throws Exception {
		ComplexPolynomial derived = polynom.toComplexPolynom().derive();
		for(int y = yMin; y <= yMax; y++) {
			for(int x = 0; x < width; x++) {
				double cre = (double)x/(width - 1) * (reMax - reMin) + reMin;
				double cim = (double)(height - 1 - y)/(height - 1) * (imMax - imMin) + imMin;
				Complex zn = new Complex(cre, cim);
				double module;
				int iter = 0;
				do {
					Complex numerator = polynom.apply(zn);
				    Complex denominator = derived.apply(zn);
				    Complex fraction = numerator.divide(denominator);
				    Complex zn1 = zn.sub(fraction);
				    module = zn1.sub(zn).module();
				    zn = zn1;
				    iter++;
				} while(!withinThreshold(module) && iter < m);
				int index = polynom.indexOfClosestRootFor(zn, CONVERGENCE_THRESHOLD) + 1;
				int offset = (y % height)*width + x;
				data[offset] = (short)index;
			}
		}
		return null;
	}

	/**
	 * This method checks if the specified number is 
	 * smaller than {@link #MODULE_CLOSENESS}.
	 * 
	 * @param module
	 * 		number to check for.		
	 * 
	 * @return
	 * 		<code>true</code> if the distance is within the threshold.
	 * 		<code>false</code> otherwise.
	 */
	private boolean withinThreshold(double module) {
		return module < MODULE_CLOSENESS;
	}

}
