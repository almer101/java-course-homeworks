package hr.fer.zemris.java.hw16.jvdraw.geometricObjects;

import java.awt.Rectangle;

/**
 * This is the
 * 
 * @author ivan
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	/** The smaller x coordinate of the bounding rectangle */
	int x1;

	/** The bigger x coordinate of the bounding rectangle */
	int x2;

	/** The smaller y coordinate of the bounding rectangle */
	int y1;

	/** The bigger y coordinate of the bounding rectangle */
	int y2;

	/**
	 * The flag indicating that the object is the first one in the calculation of
	 * the bounding rectangle.
	 */
	boolean isFirstObject = true;

	@Override
	public void visit(Line line) {
		if (isFirstObject) {
			isFirstObject = false;
			x1 = Math.min(line.getxStart(), line.getxEnd());
			x2 = Math.max(line.getxStart(), line.getxEnd());
			y1 = Math.min(line.getyStart(), line.getyEnd());
			y2 = Math.max(line.getyStart(), line.getyEnd());
			return;
		}
		y1 = Math.min(y1, Math.min(line.getyStart(), line.getyEnd()));
		y2 = Math.max(y2, Math.max(line.getyStart(), line.getyEnd()));
		x1 = Math.min(x1, Math.min(line.getxStart(), line.getxEnd()));
		x2 = Math.max(x2, Math.max(line.getxStart(), line.getxEnd()));
	}

	@Override
	public void visit(Circle circle) {
		visitCircle(circle.getCenterX(), circle.getCenterY(), circle.getRadius());
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		visitCircle(filledCircle.getCenterX(), filledCircle.getCenterY(), filledCircle.getRadius());
	}

	/**
	 * This method checks if the bounding rectangle was exceeded by the circle with
	 * specified coordinates and radius.
	 * 
	 * @param centerX
	 *            the x coordinate of the center.
	 * @param centerY
	 *            the y coordinate of the center.
	 * @param radius
	 *            the radius of the circle.
	 */
	private void visitCircle(int centerX, int centerY, int radius) {
		if (isFirstObject) {
			isFirstObject = false;
			x1 = centerX - radius;
			x2 = centerX + radius;
			y1 = centerY - radius;
			y2 = centerY + radius;
			return;
		}
		x1 = Math.min(x1, centerX - radius);
		x2 = Math.max(x2, centerX + radius);
		y1 = Math.min(y1, centerY - radius);
		y2 = Math.max(y2, centerY + radius);
	}

	/**
	 * This method returns the bounding box (i.e. rectangle) around the objects on
	 * the canvas.
	 * 
	 * @return the bounding box around the object painted on the canvas.
	 */
	public Rectangle getBoundingBox() {
		return new Rectangle(x1, y1, x2 - x1, y2 - y1);
	}

}
