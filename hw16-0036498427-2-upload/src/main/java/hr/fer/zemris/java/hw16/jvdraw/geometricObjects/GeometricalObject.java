package hr.fer.zemris.java.hw16.jvdraw.geometricObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the abstract model of the geometric object. The object has the list of listeners
 * which it is possible to register of unregister. Each geometric object can be visited by the
 * visitor so it has the accept method. It can also produce the {@link GeometricalObjectEditor}. 
 * 
 * @author ivan
 *
 */
public abstract class GeometricalObject {

	/**The listeners of the object.*/
	List<GeometricalObjectListener> listeners = new ArrayList<>();
	
	/**
	 * The method which accepts the specified visitor, i.e. calls the appropriate
	 * visit method from the {@link GeometricalObjectVisitor} interface.
	 * 
	 * @param v
	 * 		the visitor to accept.
	 */
	public abstract void accept(GeometricalObjectVisitor v);

	/**
	 * This method creates the {@link GeometricalObjectEditor}. It renders the dialog 
	 * 
	 * @return
	 * 		the created {@link GeometricalObjectEditor}
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * Adds the specified listener to the list of listeners.
	 * 
	 * @param l
	 * 		listener to add to the list of listeners.
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);
	}

	/**
	 * Removes the specified listener from the list of listeners.
	 * 
	 * @param l
	 * 		a listener to remove from the list.
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}

}
