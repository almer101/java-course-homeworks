package hr.fer.zemris.java.hw16.jvdraw;

/**
 * This interface models the objects which are drawing model listeners
 * and are informed every time the object is added to the list, removed from the list
 * or the content of the list changed.
 * 
 * @author ivan
 *
 */
public interface DrawingModelListener {

	/**
	 * Method which is called each time the objects are added to the list of objects.
	 * 
	 * @param source
	 * 		the model where the objects were added.
	 * @param index0
	 * 		the starting index where the objects were added.
	 * @param index1
	 * 		the ending index where the objects were added.
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Method which is called each time the objects are removed from the list of objects.
	 * 
	 * @param source
	 * 		the model from where the objects were removed.
	 * @param index0
	 * 		the starting index where the objects were removed.
	 * @param index1
	 * 		the ending index where the objects were removed.
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * The method called if the content of the collection of objects has changed.
	 * 
	 * @param source
	 * 		the model where the change occurred.
	 * @param index0
	 * 		the starting index where the change happened.
	 * @param index1
	 * 		the ending index where the change happened.
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
