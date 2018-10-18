package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Arrays;

/**
 * This class is an implementation of an 
 * array backed {@link Collection}.
 * @author ivan
 * @version 1.0
 */
public class ArrayIndexedCollection extends Collection {

	private int size;
	private int capacity;
	private Object elements[];
	private static final int DEFAULT_CAPACITY = 16;
	
	/**
	 * An empty constructor which initializes an
	 * {@link ArrayIndexedCollection} with the 
	 * default capacity of 16.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * A constructor which creates an 
	 * {@link ArrayIndexedCollection} with capacity
	 * of {@code initialCapacity}
	 * @param initialCapacity initial capacity of collection
	 * @throws IllegalArgumentException throws one if
	 * 			the {@code initialCapacity} is not larger than 1!
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity < 1) {
			throw new IllegalArgumentException("The initial capacity "
					+ "should be larger than 1! Was " + initialCapacity);
		}
		this.capacity = initialCapacity;
		elements = new Object[initialCapacity];
	}
	
	/**
	 * A constructor which creates an {@link ArrayIndexedCollection}
	 * which contains all the elements from the {@code other}
	 * collection.
	 * @param other collection from which the elements will be 
	 * 					copied to this collection.
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULT_CAPACITY);
	}
	
	/**
	 * A constructor which creates an {@link ArrayIndexedCollection}
	 * which contains all the elements from the {@code other} collection 
	 * and has the capacity of {@code initialCapacity}
	 * @param other collection from which the elements will be
	 * 				copied to this collection.
	 * @param initialCapacity initial capacity of the collection
	 * @throws NullPointerException throws one if the given
	 *  			{@link Collection} {@code other} is null! 
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if(other == null) {
			throw new NullPointerException("The given reference to other "
									+ "collection must not be null!");
		}
		
		int otherSize = other.size();
		
		if(otherSize > initialCapacity) {
			this.capacity = otherSize;
			elements = new Object[otherSize];
		} else {
			this.capacity = initialCapacity;
			elements = new Object[initialCapacity];
		}
		
		this.addAll(other);
	}
	
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Method adds an object given by parameter {@code obj}
	 * to the collection.
	 * @param obj object to be added to the collection
	 * @throws NullPointerException if the given reference
	 * 			is null.
	 */
	@Override
	public void add(Object obj) {
		if(obj == null) {
			throw new NullPointerException("The given argument must not"
					+ "be null!");
		}
		ensureCapacity();
		elements[size] = obj;
		size++;
	}

	/**
	 * Method which ensures that this {@link ArrayIndexedCollection}
	 * has enough space to store new elements.
	 */
	private void ensureCapacity() {
		if(size >= capacity ) {
			capacity *= 2;
			elements = Arrays.copyOf(elements, capacity);
		}
	}
	
	@Override
	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}
	
	/**
	 * Method returns an object from the collection which is
	 * located on the index {@code index}.
	 * @param index
	 * @return an object on that location {@code index}
	 * @throws IndexOutOfBoundsException if the {@code index} is not
	 * 						in valid interval.
	 */
	public Object get(int index) {
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index should be in interval "
					+ "[0, " + (size - 1) + " ]. Was " + index);
		}
		
		return elements[index];
	}
	
	@Override
	public void clear() {
		for(int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}
	
	/**
	 * Method inserts an object {@code value} on the position
	 * {@code index} but does not overwrite the element on that
	 * position (i.e. keeps all the existing elements).
	 * @param value an object to be added to the collection
	 * @param position index of position where {@code value}
	 * 					is going to be added.
	 * @throws IndexOutOfBoundsException if the {@code position}
	 * 			index is not in valid interval.
	 * @throws NullPointerException if the given parameter {@code value}
	 * 			is null.
	 */
	public void insert(Object value, int position) {
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Position index should be in"
					+ "interval [0, " + size + "]. Was " + position);
		}
		if(value == null) {
			throw new NullPointerException("The object, which is going to"
					+ " be added to the collection must not be null!");
		}
		
		ensureCapacity();
		for(int i = size; i > position; i--) {
			elements[i] = elements[i-1];
		}
		
		elements[position] = value;
		size++;
	}
	
	/**
	 * Method searches and returns the index of first occurrence of
	 * the object {@code value}.
	 * @param value the object whose index should be returned
	 * @return returns the index of the object {@code value}
	 * 			if that value is contained within the collection
	 * 			and {@code -1} otherwise.
	 */
	public int indexOf(Object value) {
		for(int i = 0; i < size; i++) {
			if(elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Method remove the object on the index {@code index} from
	 * the collection.
	 * @param index position of the object to be removed from the
	 * 			collection.
	 * @throws IndexOutOfBoundsException if the given {@code index}
	 * 			is not in valid interval.
	 */
	public void remove(int index) {
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index of the element to be"
					+ "removed must be in interval [0, " + (size - 1) +"]. Was " + index);
		}
		elements[index] = null;
		for(int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}
		size--;
	}
	
	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);
		if(index != -1) {
			remove(index);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void forEach(Processor processor) {
		for(int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}
	
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, elements.length);
	}
}

