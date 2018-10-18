package hr.fer.zemris.java.hw06.observer1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a Subject which stores
 * an integer and can have multiple observers.
 * Each time a value is changed all registered 
 * observers all notified.
 * 
 * @author ivan
 *
 */
public class IntegerStorage {

	//=======================Properties==================================
	
	/**
	 * This is the stored value.
	 */
	private int value;
	
	/**
	 * This is the list of registered observers. 
	 * All observers implement a {@link IntegerStorageObserver}
	 * interface.
	 */
	private List<IntegerStorageObserver> observers = new ArrayList<>();
	
	//========================Constructors===============================
	
	/**
	 * This constructor receives an initial value which
	 * the <code>value</code> property will have.
	 * 
	 * @param initialValue
	 * 		initial value of the property
	 * 		<code>value</code>.
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue; 
	}
	
	//=======================Method implementations=======================
	
	/**
	 * This method gets one parameter <code>observer</code> and
	 * adds it to the internal list of registered
	 * observers.
	 * 
	 * @param observer
	 * 		an observer to add to the internal list.
	 */
	public void addObserver(IntegerStorageObserver observer) {
		Objects.requireNonNull(observer, "The observer to register "
				+ "must not be null!");
		if(!observers.contains(observer)) {
			observers.add(observer);
		}
	}
	
	/**
	 * This method gets one parameter <code>observer</code> which
	 * is to be removed from the internal list of 
	 * registered observers.
	 * 
	 * @param observer
	 * 		an observer to be removed.
	 */
	public void removeObserver(IntegerStorageObserver observer) { 
            Objects.requireNonNull(observer, "The observer to be removed "
            		+ "must not be null!");
            if(observers.contains(observer)) {
            	observers.remove(observer);
            }
	}
	
	/**
	 * This method removes all the registered observers from the
	 * internal list of registered observers.
	 * 
	 */
	public void clearObservers() {
		observers.clear();
	}
	
	/**
	 * This method returns the value of the <code>value</code>
	 * property
	 * 
	 * @return
	 * 		the value of the <code>value</code> property.
	 */
	public int getValue() { 
		return value;
	}

	/**
	 * This method gets one parameter <code>value</code> and
	 * sets the <code>value</code> property of this class to
	 * that specified value. Also this method notifies all the
	 * registered observers in the list of observers that
	 * the change happened.
	 * 
	 * @param value
	 * 		a value to set the <code>value</code> property to.
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		 if(this.value != value) {
			 //Update current value
			 this.value = value;
			 //notify all registered observers
			 if(observers != null) {
				 int size = observers.size();
				 int i = 0;
				 while(i < size) {
					 observers.get(i).valueChanged(this);
					 if(observers.size() != size) {
						 size = observers.size();
						 continue;
					 }
					 i++;
				 }
			 }
		 }		
	}
}
