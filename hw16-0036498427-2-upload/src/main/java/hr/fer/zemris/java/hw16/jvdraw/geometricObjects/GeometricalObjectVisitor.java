package hr.fer.zemris.java.hw16.jvdraw.geometricObjects;

/**
 * This is the interface which models the geometric object visitor
 * 
 * @author ivan
 *
 */
public interface GeometricalObjectVisitor {

	/**
	 * This method visits the line.
	 * 
	 * @param line
	 * 		the line to visit
	 */
	public abstract void visit(Line line);

	/**
	 * This method visits the circle
	 * 
	 * @param circle
	 * 		the circle to visit.
	 */
	public abstract void visit(Circle circle);

	/**
	 * This method visits the filled circle
	 * 
	 * @param filledCircle
	 * 		the filled circle to visit.
	 */
	public abstract void visit(FilledCircle filledCircle);
}
