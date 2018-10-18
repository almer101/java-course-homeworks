package hr.fer.zemris.java.gui.charts;

/**
 * This class models the (x,y) value of the bar in
 * the chart. (e.g. XYValue (1,2)) is the bar located
 * at the x = 1 and is 2 units high.
 * 
 * @author ivan
 *
 */
public class XYValue {

	//================Properties========================
	
	/**
	 * The read-only x value
	 */
	private int x;
	
	/**
	 * The read-only y value
	 */
	private int y;
	
	//================Constructor=======================
	
	/**
	 * The constructor which gets the initial
	 * values of the x and y component of the 
	 * (x,y)
	 * 
	 * @param x
	 * 		the initial value for x.
	 * 
	 * @param y
	 * 		the initial value for the y.
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	//==================Getters=========================
	
	/**
	 * This method returns the value of the x.
	 * 
	 * @return
	 * 		the value of the x.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * This method returns the value of the y.
	 * 
	 * @return
	 * 		the value of the y.
	 */
	public int getY() {
		return y;
	}
}
