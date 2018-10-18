package hr.fer.zemris.java.hw06.observer1;

/**
 * This class represents an observer which
 * counts how many changes occurred since
 * the registration of the observer. It 
 * writes out the number of changes each
 * time a change occurs.
 * 
 * @author ivan
 *
 */
public class ChangeCounter implements IntegerStorageObserver {

	//==========================Property============================
	/**
	 * Number of changes occurred from the registration
	 * of the observer.
	 */
	int changeCount = 0;
	
	//=========================Method implementation================
	
	/**
	 * This method prints out the number of times the value
	 * stored in <code>istorage</code> has been changed 
	 * since the registration of this observer.
	 * 
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		changeCount++;
		System.out.println("Number of value changes since tracking: " 
							+ changeCount);
	}

	
}
