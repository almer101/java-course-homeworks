package hr.fer.zemris.java.hw06.observer1;

/**
 * This is the interface all the observers
 * have to implement so they can be notified
 * from the Subject that the change in the
 * {@link IntegerStorage} occurred.
 * 
 * @author ivan
 *
 */
public interface IntegerStorageObserver {

	/**
	 * This is the method called to notify the
	 * observer that the change happened. It 
	 * receives one parameter which is the 
	 * reference to the Subject which changed 
	 * its state.
	 * 
	 * @param istorage
	 * 		the Subject which changed its
	 * 		state.
	 */
	public void valueChanged(IntegerStorage istorage);
}
