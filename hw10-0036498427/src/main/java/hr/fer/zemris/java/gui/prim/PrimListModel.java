package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * The model of the list which shows prime numbers.
 * 
 * @author ivan
 *
 */
public class PrimListModel implements ListModel<Integer> {

	//=============================Properties===============================
	
	/**
	 * The list of numbers which are shown in the list.
	 */
	List<Integer> primeNumbers = new ArrayList<>();
	
	/**
	 * The list of listeners of this model.
	 */
	private List<ListDataListener> listeners = new ArrayList<>();
	
	//=============================Constructor==============================
	
	/**
	 * The constructor which initializes the {@link #primeNumbers}
	 * list such that it puts the first number in the list.
	 * 
	 */
	public PrimListModel() {
		primeNumbers.add(1);
	}
	
	//========================Method implementations========================
	
	@Override
	public int getSize() {
		return primeNumbers.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return primeNumbers.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}
	
	/**
	 * This method adds the next prime number to the list.
	 * 
	 */
	public void next() {
		int pos = getSize();
		int lastPrime = primeNumbers.get(pos - 1) + 1;
		
		while(true) {
			if(isPrime(lastPrime)) break;
			lastPrime++;
		}
		primeNumbers.add(lastPrime);
		ListDataEvent e = new ListDataEvent(this,
				ListDataEvent.INTERVAL_ADDED, pos, pos);
		listeners.forEach(l -> l.intervalAdded(e));
	}

	/**
	 * This method checks if the specified number is
	 * prime and returns the boolean value accordingly.
	 * 
	 */
	private boolean isPrime(int lastPrime) {
		for(int i = 2; i < lastPrime / 2 + 1; i++) {
			if(lastPrime % i == 0) return false;
		}
		return true;
	}
}
