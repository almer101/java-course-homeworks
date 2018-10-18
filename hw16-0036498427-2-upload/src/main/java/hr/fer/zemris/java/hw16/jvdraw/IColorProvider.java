package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

/**
 * This interface models the objects which can provide method for fetching the current color and
 * adding and removing listeners to and from the list.
 * 
 * @author ivan
 *
 */
public interface IColorProvider {

	/**
	 * This method returns the current color of the color area.
	 * 
	 * @return
	 * 		the current color of the color area.
	 */
	public Color getCurrentColor();

	/**
	 * This method adds the specified listener to the list of listeners.
	 * 
	 * @param l
	 * 		the listener to add to the list of listeners.
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * This method removes the specified listener from the list of listeners. 
	 * 
	 * @param l
	 * 		the listener to remove from the list of listeners.
	 */
	public void removeColorChangeListener(ColorChangeListener l);
}
