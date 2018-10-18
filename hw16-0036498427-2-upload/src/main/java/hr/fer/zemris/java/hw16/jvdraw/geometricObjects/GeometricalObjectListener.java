package hr.fer.zemris.java.hw16.jvdraw.geometricObjects;

/**
 * This interface models the objects which are geometric object listeners and each
 * time the object is changed the {@link #geometricalObjectChanged(GeometricalObject)} 
 * method is called.
 * 
 * @author ivan
 *
 */
public interface GeometricalObjectListener {

	/**
	 * This is the method called when the object is changed.
	 * 
	 * @param o
	 * 		the object which was changed.
	 */
	public void geometricalObjectChanged(GeometricalObject o);
}
