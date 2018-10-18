package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

/**
 * This is the interface which models the listeners on the color area which
 * are informed each time the color is changed.
 * 
 * @author ivan
 *
 */
public interface ColorChangeListener {

	/**
	 * This is the method called when the new color is set.
	 * 
	 * @param source
	 * 		the source which changed the color.
	 * @param oldColor
	 * 		the old color
	 * @param newColor
	 * 		the new color which was chosen.
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
