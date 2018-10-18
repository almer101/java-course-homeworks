package hr.fer.zemris.java.custom.collections;

/**
 * This collection is a linked list backed implementation of
 * the {@code Collection}. 
 * 
 * @author ivan
 * @version 1.0
 */
public class LinkedListIndexedCollection extends Collection {

	private int size;
	private ListNode first;
	private ListNode last;
	
	/**
	 * This class represents one list node and these {@code ListNode}s
	 * are used for storing values, as well as pointing to next
	 * and previous element in the collection.
	 * @author ivan
	 *
	 */
	private static class ListNode {
		Object value;
		ListNode next;
		ListNode previous;
		
		/**
		 * a constructor which sets the value of a new {@code ListNode}
		 * to the given {@code value}.
		 * @param value the value which the new ListNode is going
		 * 			to have.
		 */
		public ListNode(Object value) {
			this.value = value;
			this.next = null;
			this.previous = null;
		}
	}
	
	/**
	 * This is a default constructor which initiates references to
	 * first and last element in the collection to null. As well as
	 * {@code size} to zero.
	 */
	public LinkedListIndexedCollection() {
		first = null;
		last = null;
		size = 0;
	}
	
	/**
	 * A constructor which adds all the elements from the
	 * {@code other} collection to this collection.
	 * @param other
	 */
	public LinkedListIndexedCollection(Collection other) {
		if(other == null) {
			throw new NullPointerException("The given reference to "
					+ "other collection must not be null!");
		}
		this.addAll(other);
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException("The given Object reference "
					+ "must not be null!");
		}
		
		ListNode newNode = new ListNode(value);
		
		if(this.isEmpty()) {
			this.first = newNode;
			this.last = newNode;
			
		} else {
			this.last.next = newNode;
			newNode.previous = this.last;
			this.last = newNode;
			
		}
		size++;
	}

	/**
	 * This method returns an object which is
	 * on the {@code index} position. 
	 * @param index position where the desired element 
	 * 			is located.
	 * @return an object which is on the {@code index} position.
	 * @throws IndexOutOfBoundsException if the given {@code index} 
	 * 			is not in a valid interval.
	 */
	public Object get(int index) {
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index should be in interval "
					+ "[0, " + (size - 1) + " ]. Was " + index);
		}
		
		ListNode iterator;
		if(index < (size/2 + 1)) {
			//it's in the first half
			iterator = first;
			
			for(int i = 0; i < index; i++) {
				iterator = iterator.next;
			}
		} else {
			//it's in the second half
			iterator = last;
			
			for(int i = size - 1; i > index; i--) {
				iterator = iterator.previous;
			}
		}
		return iterator.value;
	}
	
	@Override
	public void clear() {
		//we should put all references to null so that garbage collector can
		//collect those ListNode-s
		ListNode iterator = first;
		ListNode nextNode;
		
		while(iterator != null) {
			iterator.previous = null;
			nextNode = iterator.next;
			iterator.next = null;
			iterator = nextNode;
		}
		first = null;
		last = null;
		size = 0;
	}
	
	/**
	 * This method inserts an object {@code value} to the 
	 * collection on the index position. But does not change
	 * existing elements in the collection.
	 * @param value an object to be added to the collection
	 * @param position index where the {@code value} should be added.
	 * @throws NullPointerException if the given {@code value} is null.
	 * @throws IndexOutOfBoundsException if the {@code position} is 
	 * 			not in the valid interval.
	 */
	public void insert(Object value, int position) {
		if(value == null) {
			throw new NullPointerException("The object, which is going to"
					+ " be added to the collection must not be null!");
		}
		if(position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Position index should be in"
					+ "interval [0, " + size + "]. Was " + position);
		}
		if(first == null && last == null) {
			this.add(value);
			return;
		}
		
		ListNode iterator;
		ListNode newNode = new ListNode(value);
		
		if(position < size/2 + 1) {
			//it's in the first half
			iterator = first;
			
			for(int i = 0; i < position; i++) {
				iterator = iterator.next;
			}
			newNode.previous = iterator.previous;
			newNode.next = iterator;
			newNode.next.previous = newNode;
			if(newNode.previous != null) {
				newNode.previous.next = newNode;
			}
			else {
				first = newNode;
			}
			
			
		} else {
			//it's in the second half
			iterator = last;
			
			for(int i = size - 1; i > position - 1; i--) {
				iterator = iterator.previous;
			}
			newNode.previous = iterator;
			newNode.next = iterator.next;
			newNode.previous.next = newNode;
			if(newNode.next != null) {
				newNode.next.previous = newNode;
			} else {
				last = newNode;
			}
		}
		
		size++;
	}
	
	/**
	 * Method returns the index of the first ocurrence 
	 * of the object {@code value} if it is contained 
	 * in the collection, and {@code -1} otherwise.
	 * @param value an object whose index the method should return.
	 * @return an index of the object {@code value}. And {@code -1}
	 * 			if the object is not present in the collection.
	 */
	public int indexOf(Object value) {
		ListNode iterator = first;
		
		for(int i = 0; i < size; i++) {
			if(iterator.value.equals(value)) {
				return i;
			}
			iterator = iterator.next;
		}
		return -1;
	}
	
	/**
	 * This method removes the object at the location {@code index}.
	 * The element that was on location {@code index + 1} is going to be
	 * on the location {@code index} after removing the object.
	 * @param index the location of the object which is to be removed.
	 * @throws IndexOutOfBoundsException if the given {@code index} is 
	 * 			not in the valid interval.
	 */
	public void remove(int index) {
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index of the element to be"
					+ "removed must be in interval [0, " + (size - 1) +"]. Was " + index);
		}
		
		ListNode iterator;
		
		if(index < size/2 + 1) {
			//it's in the first half
			iterator = first;
			for(int i = 0; i < index; i++) {
				iterator = iterator.next;
			}
		} else {
			//it's in the second half
			iterator = last;
			for(int i = size - 1; i > index; i--) {
				iterator = iterator.previous;
			}
		}
		iterator.previous.next = iterator.next;
		iterator.next.previous = iterator.previous;
	}
	
	@Override
	public boolean contains(Object value) {
		return this.indexOf(value) != -1;
	}
	
	@Override
	public void forEach(Processor processor) {
		ListNode iterator = first;
		
		for(int i = 0; i < size; i++) {
			processor.process(iterator.value);
			iterator = iterator.next;
		}
	}
	
	@Override
	public Object[] toArray() {
		Object elements[] = new Object[size];
		ListNode iterator = first;
		for(int i = 0; i < size; i++) {
			elements[i] = iterator.value;
			iterator = iterator.next;
		}
		
		return elements;
	}
}
