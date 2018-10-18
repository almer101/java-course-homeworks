package hr.fer.zemris.java.custom.collections;

/**
 * A class which represents some general collection of
 * objects.
 * @author ivan
 *
 */
public class Collection {

	/**
	 * An empty constructor
	 */
	protected Collection() {
		super();
	}
	
	/**
	 * Method that returns true if collection
	 * contains no objects, and false otherwise.
	 * @return {@code true} if collection contains no objects;
	 * 			 {@code false} otherwise
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * Method which returns number of objects contained
	 * within collection.
	 * @return number of objects in collection
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Adds an object given by parameter {@code value}
	 * to the collection
	 * @param value an object to be added to the collection
	 */
	public void add(Object value) {
	}
	
	/**
	 * Method which returns if given object {@code value}
	 * is contained within the collection. As determined by
	 * equals method.
	 * @param value is an object for which has to be checked if
	 * it is contained in the collection
	 * @return {@code true} if object is contained within the collection;
	 * 			 {@code false} otherwise
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Returns true only if the collection contains the 
	 * given object {@code value},as determined by equals 
	 * method and removes one occurrence of that object.
	 * @param value object which needs to be removed from 
	 * 			the collection
	 * @return {@code true} if the collection contained that 
	 * 				object before removing it; 
	 * 				{@code false} otherwise
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Method which returns an array of all the objects 
	 * contained within collection.
	 * @return an array containing all the objects from collection.
	 * @throws UnsupportedOperationException throws one
	 * 			if the method is not implemented
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException("This method is not implemented");
	}
	
	/**
	 * Method which calls {@code processor.process(.)} for each element
	 * of the collection.
	 * @param processor object which has the method {@code process()} and performs
	 *  			that operation for each element in the collection.
	 */
	public void forEach(Processor processor) {
	}
	
	/**
	 * Method that adds all the elements from the given {@code other}
	 * collection to the current collection. During this the 
	 * collection {@code other} remains unchanged.
	 * @param other
	 */
	public void addAll(Collection other) {
		
		class MyProcessor extends Processor {
			Collection current;
			
			/**
			 * A constructor which receives a reference to the collection
			 * where the elements will be added.
			 * @param current reference to the collection where the 
			 * 			elements will be added.
			 */
			public MyProcessor(Collection current) {
				this.current = current;
			}
			
			/**
			 * Method adds the given object to the
			 * collection {@code current}.
			 * @param obj object to be added to the collection.
			 */
			@Override
			public void process(Object obj) {
				current.add(obj);
			}
		}
		
		other.forEach(new MyProcessor(this));
	}
	
	/**
	 * Method which removes all elements from this
	 * Collection.
	 */
	public void clear() {
	}
}
