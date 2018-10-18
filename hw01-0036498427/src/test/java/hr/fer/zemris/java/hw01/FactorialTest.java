package hr.fer.zemris.java.hw01;

import org.junit.Test;
import org.junit.Assert;

/**
 * izrada testa za metodu calculateFactorial
 * iz razreda Factorial. 
 * 
 * @author ivan
 * @version 1.0
 */
public class FactorialTest {

	@Test
	public void argumentNula () {
		Assert.assertEquals(1, Factorial.calculateFactorial(0));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void argumentPrevelik () {
		Factorial.calculateFactorial(21);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void argumentNegativan () {
		Factorial.calculateFactorial(-5);
	}
	
	@Test
	public void argumentURedu () {
		final int GORNJA_GRANICA = 21;
		final int DONJA_GRANICA = 1;
		long num = 1;
		for(int i = DONJA_GRANICA; i < GORNJA_GRANICA; i++ ) {
			Assert.assertEquals(num, Factorial.calculateFactorial(i));
			num = num*(i+1);
		}
	}
}
