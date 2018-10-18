package hr.fer.zemris.java.hw06.observer2;

import java.util.Objects;

/**
 * This class encapsulates the read-only properties:
 * reference to {@link IntegerStorage}, the old value
 * (i.e. the value before the change occurred), 
 * the new value (i.e. the value after the change occurred)
 * 
 * 
 * @author ivan
 *
 */
public class IntegerStorageChange {

	//==================Properties===============================
	
	/**
	 * The {@link IntegerStorage} where the change
	 * occurred
	 */
	private IntegerStorage istorage;
	
	/**
	 * The value contained in the storage before the 
	 * change occurred.
	 */
	private int oldValue;
	
	/**
	 * The value contained in the storage after the 
	 * change occurred.
	 */
	private int newValue;

	//======================Constructor===========================

	/**
	 * This constructor receives initial values of the
	 * properties of this class and sets the values of the
	 * properties to the specified values.
	 * 
	 * @param istorage
	 * 		the {@link IntegerStorage} where the change occurred
	 * 
	 * @param oldValue
	 * 		the value from the storage before the change occurred
	 * 
	 * @param newValue
	 * 		the value from the storage after the change occurred.
	 */
	public IntegerStorageChange(IntegerStorage istorage, 
			int oldValue, int newValue) {
		
		Objects.requireNonNull(istorage, "The given storage "
				+ "must not be null!");
		this.istorage = istorage;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	//========================Getters=============================
	
	/**
	 * This method returns the value of the 
	 * <code>istorage</code> property.
	 * 
	 * @return
	 * 		the value of the <code>istorage</code>
	 * 		property.
	 */
	public IntegerStorage getIstorage() {
		return istorage;
	}

	/**
	 * This method returns the value of the 
	 * <code>oldValue</code> property.
	 * 
	 * @return
	 * 		the value of the <code>oldValue</code>
	 * 		property.
	 */
	public int getOldValue() {
		return oldValue;
	}

	/**
	 * This method returns the value of the 
	 * <code>newValue</code> property.
	 * 
	 * @return
	 * 		the value of the <code>newValue</code>
	 * 		property.
	 */
	public int getNewValue() {
		return newValue;
	}
	
}
