package hr.fer.zemris.java.hw16.jvdraw;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.geometricObjects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometricObjects.GeometricalObjectListener;

/**
 * The model which contains the geometric objects and listeners of this model.
 * 
 * @author ivan
 *
 */
public class MyDrawingModel implements DrawingModel, GeometricalObjectListener {

	/**The collection of geometric objects*/
	private List<GeometricalObject> objects = new ArrayList<>();
	
	/**The collection of listeners on the model.*/
	private List<DrawingModelListener> listeners = new ArrayList<>();
	
	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		object.addGeometricalObjectListener(this);
		objects.add(object);
		int index = objects.indexOf(object);
		listeners.forEach(l -> l.objectsAdded(this, index, index));
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void remove(GeometricalObject object) {
		int index = objects.indexOf(object);
		if(index == -1) return;
		objects.remove(object);
		listeners.forEach(l -> l.objectsRemoved(this, index, index));
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int index = objects.indexOf(object);
		if((index + offset) < 0 || (index + offset) > (objects.size() - 1)) return;
		int otherIndex = index + offset;
		GeometricalObject tmp = objects.get(otherIndex);
		objects.set(index + offset, object);
		objects.set(index, tmp);
		listeners.forEach(l -> l.objectsChanged(this, 
				Math.min(index, index + offset), Math.max(index, index + offset)));
	}

	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		int index = objects.indexOf(o);
		listeners.forEach(l -> l.objectsChanged(this, index, index));
	}
	
}
