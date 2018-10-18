package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.geometricObjects.Circle;

/**
 * This is the tool used for drawing unfilled circles. When the mouse is clicked
 * for the first time that is the point where the center of the circle is. When
 * it is clicked for the second time that is the point where the circle is. So
 * the radius is the length from the center to the position of the mouse.
 * 
 * @author ivan
 *
 */
public class CircleTool implements Tool {

	/** The x coordinate of the circle center */
	private int centerX;

	/** The y coordinate of the circle center */
	private int centerY;

	/** The radius of the circle. */
	private double radius;

	/** Indicator whether it is the first click of the mouse. */
	private boolean wasFirstClick = false;

	/** The provider of the foreground color. */
	private IColorProvider fgColorProvider;

	/** The model which contains all the objects which are on the canvas. */
	private DrawingModel model;
	
	/**The flag indicating whether the object was generated or not*/
	private boolean objectGenerated = false;

	/**
	 * Constructor which gets the foreground color provider as a parameter. The
	 * provider is then used when drawing the shape.
	 * 
	 * @param fgColorProvider
	 *            the provider of the foreground color
	 * @param model 
	 * 		the drawing model, contains all the drawings.
	 */
	public CircleTool(IColorProvider fgColorProvider, DrawingModel model) {
		this.fgColorProvider = fgColorProvider;
		this.model = model;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		objectGenerated = false;
		if (!wasFirstClick) {
			wasFirstClick = true;
			registerCenter(e);
			return;
		}
		wasFirstClick = false;
		int x = e.getX();
		int y = e.getY();
		radius = Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
		Circle c = new Circle(centerX, centerY, (int) radius,
				fgColorProvider.getCurrentColor());
		objectGenerated = true;
		model.add(c);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (wasFirstClick) {
			finishCircle(e);
			e.getComponent().repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void paint(Graphics2D g2d) {
		if(objectGenerated) return;
		g2d.setColor(fgColorProvider.getCurrentColor());
		g2d.drawOval((int) (centerX - radius), (int) (centerY - radius), (int) (2 * radius),
				(int) (2 * radius));
	}

	/**
	 * This method registers the center of the circle as coordinates.
	 * 
	 * @param e
	 *            the mouse event.
	 */
	private void registerCenter(MouseEvent e) {
		centerX = e.getX();
		centerY = e.getY();
	}

	/**
	 * This method calculates the radius of the circle and draws the circle with
	 * that radius and center in the (centerX, centerY)
	 * 
	 * @param e
	 *            the mouse event.
	 */
	private void finishCircle(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		radius = Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
	}

}
