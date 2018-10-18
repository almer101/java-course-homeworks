package hr.fer.zemris.java.hw06.observer2;

/**
 * This class represents an observer which writes
 * out to the standard output the doubled value
 * (i.e. value*2), but only first <code>times</code>
 * number of times. The initial value of the 
 * <code>times</code> is given through a constructor.
 * After <code>times</code> number of times this 
 * observer automatically de-registers itself
 * from the subject.
 * 
 * @author ivan
 *
 */
public class DoubleValue implements IntegerStorageObserver {

	//=========================Property================================
	
	/**
	 * This variable is the number of how many times
	 * the method <code>valueChanged</code> will be 
	 * executed.
	 */
	private int times;
	
	//=======================Constructor===============================
	
	/**
	 * This constructor gets one parameter <code>times</code>
	 * which is the initial value of the class' property 
	 * <code>times</code>.
	 * 
	 * @param times
	 * 		the initial value of this class' property
	 * 	 	<code>times</code>.
	 */
	public DoubleValue(int times) {
		if(times < 0) {
			throw new IllegalArgumentException("The number of times "
					+ "can not be less than zero! Was " + times);
		}
		this.times = times;
	}
	
	//===================Method implementations=========================
	
	/**
	 * This method prints out the doubled value of the 
	 * current value stored in the <code>istorageChange</code>'s 
	 * property <code>istorage</code> but only the first 
	 * <code>times</code> number of times. After 
	 * <code>times</code> number of times this observer automatically 
	 * de-registers itself from the subject.
	 * 
	 * @param istorageChange
	 * 		the object which encapsulates the storage, old
	 * 		and new value from the storage.
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorageChange) {
		if(times > 0) {
			times--;
			System.out.println("Double value: " + 
								istorageChange.getNewValue() * 2);
			return;
		}
		// after "times" number of times de-registers itself.
		istorageChange.getIstorage().removeObserver(this);
	}
}
