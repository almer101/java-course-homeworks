package hr.fer.zemris.java.hw16.jvdraw;

import hr.fer.zemris.java.hw16.jvdraw.geometricObjects.GeometricalObject;

/**
 * This interface models the drawing models which contain the data about the
 * objects which are drawn on the canvas.
 * 
 * @author ivan
 *
 */
public interface DrawingModel {

	/**
	 * Returns the number of drawn objects.
	 * 
	 * @return the number of objects in the model.
	 */
	public int getSize();

	/**
	 * Returns the object at the specified index.
	 * 
	 * @param index
	 *            the index of the wanted object
	 * @return the object at the specified index.
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Adds the specified object to the list of objects.
	 * 
	 * @param object
	 *            the object to add.
	 */
	public void add(GeometricalObject object);

	/**
	 * Adds the specified listener to the list of listeners.
	 * 
	 * @param l
	 *            the listener to add to the list.
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes the specified listener from the list.
	 * 
	 * @param l
	 *            the listener to remove from the list.
	 */
	public void removeDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes the specified object from the list of objects.
	 * 
	 * @param object
	 *            the object to remove.
	 */
	void remove(GeometricalObject object);

	/**
	 * Moves the object for the specified offset. (if offset is 0 it stays at the
	 * same place.)
	 * 
	 * @param object
	 *            the object to move for offset.
	 * @param offset
	 *            the offset for which the object is to be moved.
	 */
	void changeOrder(GeometricalObject object, int offset);
}
