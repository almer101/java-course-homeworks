package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * This interface models the objects which can produce geometric objects.
 * 
 * @author ivan
 *
 */
public interface Tool {

	/**
	 * This is the method called when the mouse is pressed.
	 * 
	 * @param e
	 * 		the mouse event.
	 */
	public void mousePressed(MouseEvent e);

	/**
	 * This is the method called when the mouse is released.
	 * 
	 * @param e
	 * 		the mouse event.
	 */
	public void mouseReleased(MouseEvent e);

	/**
	 * This is the method called when the mouse is clicked.
	 * 
	 * @param e
	 * 		the mouse event.
	 */
	public void mouseClicked(MouseEvent e);

	/**
	 * This is the method called when the mouse is moved.
	 * 
	 * @param e
	 * 		the mouse event.
	 */
	public void mouseMoved(MouseEvent e);

	/**
	 * This is the method called when the mouse is dragged.
	 * 
	 * @param e
	 * 		the mouse event.
	 */
	public void mouseDragged(MouseEvent e);

	/**
	 * The method which actually paints the geometric object.
	 * 
	 * @param g2d
	 * 		the graphics object which can draw.
	 */
	public void paint(Graphics2D g2d);
}
