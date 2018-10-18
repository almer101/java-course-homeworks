package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.swing.JComponent;

/**
 * This is the chart component which has the reference
 * to the model of the bar chart and draws itself. 
 * 
 * @author ivan
 *
 */
public class BarChartComponent extends JComponent {

	//===========================Properties==============================
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The model of the chart.
	 */
	private BarChart chart;
	
	/**
	 * This is the distance in pixels from the axis name and
	 * the numbers on the axis.
	 * 
	 */
	private static final int NAME_NUMBERS = 15;
	
	/**
	 * This is the distance in pixels from the number on the axis
	 * and the axis itself.
	 */
	private static final int NUMBERS_AXIS = 5;
	
	/**
	 * The length of the little part of line which crosses 
	 * the axis.
	 */
	private static final int GAP = NUMBERS_AXIS / 2; 
	
	/**
	 * The color of the grid lines.
	 */
	private static Color GRID_COLOR = new Color(240,195,90);
	
	/**
	 * The color of the axis lines.
	 */
	private static Color AXIS_COLOR = Color.BLACK;
	
	/**
	 * The color of the axis names.
	 */
	private static Color TEXT_COLOR = Color.BLACK;
	
	/**
	 * The color of the bars.
	 */
	private static Color BAR_COLOR = new Color(255,127,80);
	
	//===========================Constructor==============================
	
	/**
	 * This method initializes the model of the chart property.
	 * 
	 * @param chart
	 * 		the model of the chart.
	 */
	public BarChartComponent(BarChart chart) {
		super();
		this.chart = Objects.requireNonNull(chart);
	}
	
	//======================Method implementations=========================
	
	@Override
	protected void paintComponent(Graphics gr) {
		Graphics2D g = (Graphics2D)gr;
		Insets insets = getInsets();
		int maxNumberWidth = getMaxNumberWidth(g);
		
		drawAxisNames(g, insets, maxNumberWidth);
		drawGrid(g, insets, maxNumberWidth);
		drawBars(g, insets, maxNumberWidth);
		
	}
	
	/**
	 * This method draws the axis names on the component.
	 * 
	 * @param g
	 * 		the graphics object.
	 * 
	 * @param insets
	 * 		the insets of the component.
	 * 
	 * @param maxNumberWidth
	 * 		the maximum width of the number to display.
	 * 
	 */
	private void drawAxisNames(Graphics2D g, Insets insets, int maxNumberWidth) {
		FontMetrics fm = g.getFontMetrics();
		int yAxisXCoordinate =  insets.left + fm.getHeight() + 
				NAME_NUMBERS + NUMBERS_AXIS;
		int xAxisYCoordinate = getHeight() - (insets.bottom + fm.getHeight() + 
				NAME_NUMBERS + fm.getHeight() + NUMBERS_AXIS);
		
		drawXAxisName(g, insets, yAxisXCoordinate, fm);
		drawYAxisName(g, insets, xAxisYCoordinate, fm);

	}

	/**
	 * This method draw the grid and the axes.
	 * 
	 * @param g
	 * 		the graphics object.
	 * 
	 * @param insets
	 * 		the insets of the component.
	 * 
	 * @param maxNumberWidth
	 * 		the maximum width of the number to be
	 * 		displayed on the y axis.
	 */
	private void drawGrid(Graphics2D g, Insets insets, int maxNumberWidth) {
		FontMetrics fm = g.getFontMetrics();
		int x0 =  insets.left + fm.getHeight() + 
				NAME_NUMBERS + maxNumberWidth + NUMBERS_AXIS;
		int y0 = getHeight() - (insets.bottom + fm.getHeight() + 
				NAME_NUMBERS + fm.getHeight() + NUMBERS_AXIS);
		
		int maxWidth = getParent().getWidth();
		
		int yAxisLength = y0 - insets.top - GAP * 3;
		int xAxisLength = maxWidth - insets.right - GAP * 3 - x0;
		
		g.setColor(AXIS_COLOR);
		g.drawLine(x0 - GAP, y0, x0 + xAxisLength, y0);
		g.drawLine(x0, y0 + GAP, x0, y0 - yAxisLength);
		
		drawArrows(x0, y0, xAxisLength, yAxisLength, g);

		
		int numberOfLines = (chart.getyMax() - chart.getyMin()) / chart.getStep();
		double step = (double)yAxisLength / (numberOfLines + 1);	
		int y = (int)(y0 - step);
		int numberToDraw = chart.getyMin();
		drawNumber(x0 - NUMBERS_AXIS - fm.stringWidth(String.valueOf(numberToDraw)), 
				y0 + fm.getAscent()/2,
				numberToDraw, 
				g);
		numberToDraw += chart.getStep();
		
		for(int i = 0; i < numberOfLines; i++) {
			g.setColor(AXIS_COLOR);
			g.drawLine(x0 - GAP * 2, y, x0, y);
			drawNumber(x0 - NUMBERS_AXIS - fm.stringWidth(String.valueOf(numberToDraw)), 
						(int)(y0 - (i + 1)*step) + fm.getAscent()/2,
						numberToDraw, 
						g);
			numberToDraw += chart.getStep();
			g.setColor(GRID_COLOR);
			g.drawLine(x0, y, maxWidth - insets.right - GAP * 3, y);
			y = (int)(y0 - (i + 2)*step);
		}
		
		List<XYValue> values = chart.getValues();
		numberOfLines =  values.size();
		step = (double)(xAxisLength - 5 * GAP) / numberOfLines;
		int x = (int)(x0 + step);
		int numbers[] = getNumbersToDraw(values);
		
		for(int i = 0; i < numberOfLines; i++) {
			g.setColor(AXIS_COLOR);
			g.drawLine(x, y0 + GAP * 2, x, y0);
			drawNumber((int)(x - step/2 - fm.stringWidth(String.valueOf(numberToDraw))/2), 
					y0 + NUMBERS_AXIS + fm.getAscent(),
					numbers[i], 
					g);
			g.setColor(GRID_COLOR);
			g.drawLine(x, y0, x, y0 - yAxisLength);
			x = (int)(x0 + (i + 2)*step);
		}
		
	}
	
	/**
	 * This method draws the columns (i.e. bars) of the chart.
	 * 
	 * @param g
	 * 		the graphics object		
	 * 
	 * @param insets
	 * 		the insets of the component.
	 * 
	 * @param maxNumberWidth
	 * 		the maximum width of the number.
	 */
	private void drawBars(Graphics2D g, Insets insets, int maxNumberWidth) {
		FontMetrics fm = g.getFontMetrics();
		int x0 =  insets.left + fm.getHeight() + 
				NAME_NUMBERS + maxNumberWidth + NUMBERS_AXIS;
		int y0 = getHeight() - (insets.bottom + fm.getHeight() + 
				NAME_NUMBERS + fm.getHeight() + NUMBERS_AXIS);
		
		int maxWidth = getParent().getWidth();
		
		int yAxisLength = y0 - insets.top - GAP * 3;
		int xAxisLength = maxWidth - insets.right - GAP * 3 - x0;
		
		List<XYValue> values = chart.getValues();
		Collections.sort(values, (v1,v2) -> v1.getX() - v2.getX());
		
		int numberOfLines = (chart.getyMax() - chart.getyMin()) / chart.getStep();
		double yStep = (double)yAxisLength / (numberOfLines + 1) / chart.getStep();	
		numberOfLines =  values.size();
		double xStep = (double)(xAxisLength - 5 * GAP) / numberOfLines;
		g.setColor(BAR_COLOR);
		int i = 0;
		int minValue = chart.getyMin();
		
		for(XYValue v : values) {
			int x = (int)(x0 + 1 + i * xStep);
			int y = (int)(y0 - (v.getY() - minValue) * yStep);
			g.fillRect(x, y, (int)(xStep - 1), (int)((v.getY() - minValue) * yStep));
			i++;
		}
	}
	
	/**
	 * This method draws the x axis name.
	 * 
	 * @param g
	 * 		the graphics object.
	 * 
	 * @param insets
	 * 		the insets of the component
	 * 
	 * @param yAxisXCoordinate
	 * 		the x coordinate of the y axis.
	 * 		
	 * @param fm
	 * 		font metrics object.
	 */
	private void drawXAxisName(Graphics2D g, Insets insets, 
			int yAxisXCoordinate, FontMetrics fm) {
		g.setColor(TEXT_COLOR);
		String xName = chart.getxDescription();
		int y = getHeight() - (insets.bottom + fm.getDescent() + GAP);
		int x = yAxisXCoordinate + 
				((getWidth() - insets.right - yAxisXCoordinate)/2 - 
						fm.stringWidth(xName)/2);
		g.drawString(xName, x, y);
		
	}
	
	/**
	 * This method draws the y axis name.
	 * 
	 * @param g
	 * 		the graphics object.
	 * 
	 * @param insets
	 * 		the insets of the component
	 * 
	 * @param xAxisYCoordinate
	 * 		the y coordinate of the x axis.
	 * 		
	 * @param fm
	 * 		font metrics object.
	 */
	private void drawYAxisName(Graphics2D g, Insets insets, int xAxisYCoordinate, FontMetrics fm) {
		String yName = chart.getyDescription();
		AffineTransform defalutAt = g.getTransform();
		AffineTransform at = new AffineTransform(defalutAt);
		at.rotate(-Math.PI / 2);
		g.setTransform(at);
		
		int y = insets.left + fm.getAscent();
		int x = (-insets.top - (xAxisYCoordinate - insets.top) / 2) - fm.stringWidth(yName);
		g.drawString(yName, x, y);
		
		g.setTransform(defalutAt);
	}

	/**
	 * This method returns the array of numbers which
	 * are to be drawn below the x-axis.
	 * 
	 * @param values
	 * 		the list of {@link XYValue}s.
	 * 
	 * @return
	 * 		the array of number to draw below x-axis.
	 */
	private int[] getNumbersToDraw(List<XYValue> values) {
		int array[] = new int[values.size()];
		
		for(int i = 0; i < array.length; i++) {
			array[i] = values.get(i).getX();
		}
		Arrays.sort(array);
		return array;
	}

	/**
	 * This method draws arrow to the y and x axis.
	 * The chart origin is located at <code>(x0,y0)</code>.
	 * The length of the x axis and y axis are specified 
	 * by the parameters.
	 * 
	 * @param x0
	 * 		the x coordinate of the origin.
	 * 
	 * @param y0
	 * 		the y coordinate of the origin.
	 * 
	 * @param xAxisLength
	 * 		the length of the x axis.
	 * 
	 * @param yAxisLength
	 * 		the length of the y axis.
	 * 
	 * @param g
	 * 		the graphics object.
	 */
	private void drawArrows(int x0, int y0, int xAxisLength, int yAxisLength, Graphics2D g) {
		Polygon poly = new Polygon(new int[] {x0 - 2 * GAP,
				x0, 
				x0 + 2 * GAP}, 
		new int[] {y0 - yAxisLength + 2 *GAP, 
				y0 - yAxisLength - 2 * GAP,
				y0 - yAxisLength + 2 * GAP}, 3);
		g.fill(poly);

		poly = new Polygon(new int[] {x0 + xAxisLength - 2 * GAP,
					x0 + xAxisLength + 2 * GAP,
					x0 + xAxisLength - 2 * GAP}, 
		new int[] {y0 + 2 * GAP, 
					y0,
					y0 - 2 * GAP}, 3);
		g.fill(poly);	
	}

	/**
	 * This method draws number at the specified coordinates.
	 * 
	 * @param x
	 * 		the x coordinate where to draw a number.
	 * 
	 * @param y
	 * 		the y coordinate where to draw a number.
	 * 
	 * @param numberToDraw
	 * 		the number to draw
	 * 
	 * @param g
	 * 		the graphics object.
	 */
	private void drawNumber(int x, int y, int numberToDraw, Graphics2D g) {
		g.drawString(String.valueOf(numberToDraw), x, y);
	}
	
	/**
	 * This method returns the maximum width of the
	 * number to be displayed on the y axis. 
	 * 
	 * @param g
	 * 		graphics object.
	 * 
	 * @return
	 * 		the maximum width of the number displayed on
	 * 		the y axis.
	 */
	private int getMaxNumberWidth(Graphics2D g) {
		FontMetrics fm = g.getFontMetrics();
		int yMin = chart.getyMin();
		int yMax = chart.getyMax();
		int step = chart.getStep();
		int maxWidth = fm.stringWidth(String.valueOf(yMin));
		
		for(int i = yMin; i <= yMax; i += step) {
			int w = fm.stringWidth(String.valueOf(yMin));
			if(w > maxWidth) {
				maxWidth = w;
			}
		}
		return maxWidth;
	}

}
