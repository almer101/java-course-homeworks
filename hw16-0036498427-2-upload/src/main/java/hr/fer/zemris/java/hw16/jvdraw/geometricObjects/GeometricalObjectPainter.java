package hr.fer.zemris.java.hw16.jvdraw.geometricObjects;

import java.awt.Graphics2D;
import java.util.Objects;

/**
 * This is the visitor which can paint the
 * 
 * @author ivan
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	/** The graphics object used for painting the objects */
	private Graphics2D g2d;

	/**
	 * Constructor which initializes the graphics object.
	 * 
	 * @param g2d
	 *            the graphics object which can draw shapes.
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = Objects.requireNonNull(g2d);
	}

	@Override
	public void visit(Line line) {
		g2d.setColor(line.getColor());
		g2d.drawLine(line.getxStart(), line.getyStart(), line.getxEnd(), line.getyEnd());
	}

	@Override
	public void visit(Circle circle) {
		g2d.setColor(circle.getColor());
		g2d.drawOval(circle.getCenterX() - circle.getRadius(),
				circle.getCenterY() - circle.getRadius(), 2 * circle.getRadius(),
				2 * circle.getRadius());
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		g2d.setColor(filledCircle.getFillColor());
		g2d.fillOval(filledCircle.getCenterX() - filledCircle.getRadius(),
				filledCircle.getCenterY() - filledCircle.getRadius(),
				2 * filledCircle.getRadius(), 2 * filledCircle.getRadius());
		g2d.setColor(filledCircle.getOutlineColor());
		g2d.drawOval(filledCircle.getCenterX() - filledCircle.getRadius(),
				filledCircle.getCenterY() - filledCircle.getRadius(),
				2 * filledCircle.getRadius(), 2 * filledCircle.getRadius());
	}

}
