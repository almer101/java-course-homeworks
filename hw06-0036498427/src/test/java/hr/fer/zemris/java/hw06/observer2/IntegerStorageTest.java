package hr.fer.zemris.java.hw06.observer2;

import org.junit.Test;

public class IntegerStorageTest {

	@Test
	public void observersTest() {
		IntegerStorage istorage = new IntegerStorage(5);
		ChangeCounter cc = new ChangeCounter(); //writes every time the
												//number of changes
		DoubleValue dv = new DoubleValue(3); //writes 3 times double value of
											//the set value.
		istorage.addObserver(cc);
		istorage.addObserver(dv);
		istorage.setValue(7);
		istorage.setValue(6);
		istorage.setValue(23);
		istorage.setValue(14);
		istorage.setValue(15);
		istorage.clearObservers();
		istorage.setValue(34);
	}
}
