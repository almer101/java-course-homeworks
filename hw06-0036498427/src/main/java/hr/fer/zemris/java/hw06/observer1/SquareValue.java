package hr.fer.zemris.java.hw06.observer1;

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
	 * parameter.
	 * 
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		int value = istorage.getValue();
		System.out.println("Provided new value: " + value + 
							", square is " + value * value);
	}

}
