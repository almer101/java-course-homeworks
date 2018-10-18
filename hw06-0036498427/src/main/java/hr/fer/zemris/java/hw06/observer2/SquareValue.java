package hr.fer.zemris.java.hw06.observer2;

/**
 * This class represents an observer which, when 
 * change happens, prints out the square value
 * of the current value stored in the 
 * {@link IntegerStorage}.
 * 
 * @author ivan
 *
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * This method prints out the square value of the 
	 * current value stored in the <code>istorage</code>
	 * which is stored in the <code>istorageChange</code>
	 * parameter.
	 * 
	 * @param istorageChange
	 * 		the object which encapsulates the storage, old
	 * 		and new value from the storage.
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorageChange) {
		int value = istorageChange.getNewValue();
		System.out.println("Provided new value: " + value + 
							", square is " + value * value);
	}

}
