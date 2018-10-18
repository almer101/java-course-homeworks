package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.geometricObjects.Line;

/**
 * This is the tool used for drawing lines. When the mouse is clicked for the
 * first time that is the starting point of the line and when it is clicked for
 * the second time that is the ending point of the line.
 * 
 * @author ivan
 *
 */
public class LineTool implements Tool {

	/** The x coordinate of the start of the line. */
	private int xStart;

	/** The y coordinate of the start of the line */
	private int yStart;

	/** The x coordinate of the end of the line. */
	private int xEnd;

	/** The y coordinate of the end of the line */
	private int yEnd;

	/** Indicator whether it is the first click of the mouse. */
	private boolean wasFirstClick = false;

	/** The provider of the foreground color. */
	private IColorProvider fgColorProvider;
	
	/**The model which contains all the objects which are on the canvas.*/
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
	 * 		the model of the drawings.
	 */
	public LineTool(IColorProvider fgColorProvider, DrawingModel model) {
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
			xStart = e.getX();
			yStart = e.getY();
			return;
		} 
		wasFirstClick = false;
		Line line = new Line(xStart, yStart, e.getX(), e.getY(),
				fgColorProvider.getCurrentColor());
		objectGenerated = true;
		model.add(line);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (wasFirstClick) {
			finishLine(e);
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
		g2d.drawLine(xStart, yStart, xEnd, yEnd);
	}

	/**
	 * This gets the current draws the line from the coordinates (xStart, yStart) to
	 * the current position of the mouse.
	 * 
	 * @param e
	 *            the mouse event.
	 */
	private void finishLine(MouseEvent e) {
		xEnd = e.getX();
		yEnd = e.getY();
	}
	
}
