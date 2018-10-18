package hr.fer.zemris.java.gui.charts;

import java.util.List;
import java.util.Objects;

/**
 * This class models the chart and gets the values
 * of the bars in the constructors as a list of {@link XYValue}
 * objects.
 * 
 * @author ivan
 *
 */
public class BarChart {

	//==========================Properties===============================
	
	/**
	 * The list of values of the chart.
	 */
	private List<XYValue> values;
	
	/**
	 * The description of the x axis.
	 */
	private String xDescription;
	
	/**
	 * The description of the y axis.
	 */
	private String yDescription;
	
	/**
	 * The minimal value of the y to show.
	 */
	private int yMin;
	
	/**
	 * The maximal value of the y to show.
	 */
	private int yMax;
	
	/**
	 * The distance between two neighbouring y values.
	 */
	private int step;
	
	//==========================Constants=================================
	
	/**
	 * Constructor which initializes all the properties of this class. 
	 * See the description of the variables for details.
	 * 
	 * @param values
	 * 		list of {@link XYValue}s
	 * 
	 * @param xDescription
	 * 		the description of the x axis
	 * 
	 * @param yDescription
	 * 		the description of the y axis.		
	 * 
	 * @param yMin
	 * 		the minimal value of the y.
	 * 
	 * @param yMax
	 * 		the maximal value of the y.
	 * 
	 * @param step
	 * 		distance between two neighbouring y values
	 */
	public BarChart(List<XYValue> values, String xDescription, 
			String yDescription, int yMin, int yMax, int step) {
		super();
		this.values = Objects.requireNonNull(values);
		this.xDescription = Objects.requireNonNull(xDescription);
		this.yDescription = Objects.requireNonNull(yDescription);
		this.yMin = yMin;
		this.yMax = yMax;
		if(step <= 0) {
			throw new IllegalArgumentException("The step can not "
					+ "be less or equal than zero!");
		}
		this.step = step;
	}

	/**
	 * This method returns the value of the
	 * <code>values</code> property.
	 * 
	 * @return
	 * 		the value of the <code>values</code>
	 * 		property.
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * This method returns the value of the
	 * <code>xDescription</code> property.
	 * 
	 * @return
	 * 		the value of the <code>xDescription</code>
	 * 		property.
	 */
	public String getxDescription() {
		return xDescription;
	}

	/**
	 * This method returns the value of the
	 * <code>yDescription</code> property.
	 * 
	 * @return
	 * 		the value of the <code>yDescription</code>
	 * 		property.
	 */
	public String getyDescription() {
		return yDescription;
	}

	/**
	 * This method returns the value of the
	 * <code>yMin</code> property.
	 * 
	 * @return
	 * 		the value of the <code>yMin</code>
	 * 		property.
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * This method returns the value of the
	 * <code>yMax</code> property.
	 * 
	 * @return
	 * 		the value of the <code>yMax</code>
	 * 		property.
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * This method returns the value of the
	 * <code>step</code> property.
	 * 
	 * @return
	 * 		the value of the <code>step</code>
	 * 		property.
	 */
	public int getStep() {
		return step;
	}	
}
