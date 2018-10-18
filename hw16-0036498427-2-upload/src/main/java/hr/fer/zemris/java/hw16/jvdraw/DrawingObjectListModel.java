package hr.fer.zemris.java.hw16.jvdraw;

import javax.swing.AbstractListModel;
import hr.fer.zemris.java.hw16.jvdraw.geometricObjects.GeometricalObject;

/**
 * This is the model of the list of objects which is on the right side of
 * the {@link JVDraw} frame.
 * 
 * @author ivan
 *
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> {

	/**Default serial version UID*/
	private static final long serialVersionUID = 1L;
	
	/**The model which contains all the drawings.*/
	private DrawingModel model;
	
	/**
	 * Constructor which gets the model through the constructor.
	 * 
	 * @param model
	 * 		the model of the drawings.
	 */
	public DrawingObjectListModel(DrawingModel model) {
		this.model = model;
		model.addDrawingModelListener(new DrawingModelListener() {
			
			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				fireIntervalRemoved(this, index0, index1);
			}
			
			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
				fireContentsChanged(this, index0, index1);
			}
			
			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				fireIntervalAdded(this, index0, index1);
			}
		});
	}
	
	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}
	
}
