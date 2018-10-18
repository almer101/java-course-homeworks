package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ValueWrapperTest {

	@Test 
	public void addTest() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.add(v2.getValue());
		assertTrue(v1.getValue().equals(Integer.valueOf(0)));
		
		v1.setValue("12.4");
		v2.setValue(Double.valueOf(2.0));
		v1.add(v2.getValue());
		assertTrue(v1.getValue().equals(Double.valueOf(14.4)));
		assertTrue(v2.getValue().equals(Double.valueOf(2.0)));
		
		v1.setValue(Integer.valueOf(450));
		v2.setValue("20");
		v2.add(v1.getValue());
		assertTrue(v2.getValue().equals(Integer.valueOf(470)));
		assertTrue(v1.getValue().equals(Integer.valueOf(450)));
		
		v1.setValue("1.3E2");
		v2.setValue("30.50");
		v2.add(v1.getValue());
		assertTrue(v2.getValue().equals(Double.valueOf(160.5)));
		assertTrue(v1.getValue().equals("1.3E2"));		
	}
	
	@Test(expected = RuntimeException.class)
	public void addWithExceptionTest() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);

		v1.setValue("Broj5");
		v2.setValue(Integer.valueOf(3));
		v1.add(v2); //throws an exception
	}
	
	@Test 
	public void subtractTest() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.subtract(v2.getValue());
		assertTrue(v1.getValue().equals(Integer.valueOf(0)));
		assertNull(v2.getValue());
		
		v1.setValue("3.4E1");
		v2.setValue(5);
		v1.subtract(v2.getValue());
		assertTrue(v1.getValue().equals(29.0));
		assertTrue(v2.getValue().equals(Integer.valueOf(5)));
		
		v1.setValue(120);
		v2.setValue("-1E1");
		v1.subtract(v2.getValue());
		assertTrue(v1.getValue().equals(Double.valueOf(130)));
		assertTrue(v2.getValue().equals("-1E1"));
		
		v1.setValue(-2.59);
		v2.setValue("-2.59");
		v1.subtract(v2.getValue());
		assertTrue(v1.getValue().equals(Double.valueOf(0)));
		assertTrue(v2.getValue().equals("-2.59"));
		
		v1.setValue(null);
		v2.setValue(-58);
		v1.subtract(v2.getValue());
		assertTrue(v1.getValue().equals(Integer.valueOf(58)));
	}
	
	@Test (expected = RuntimeException.class)
	public void subtractWithExceptionTest() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		
		v1.setValue("Krafna");
		v2.setValue(45);
		v2.subtract(v1.getValue()); //throws an exception
	}
	
	@Test
	public void multiplyTest() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(2.999);
		
		v2.multiply(v1.getValue());
		assertTrue(v2.getValue().equals(Double.valueOf(0)));
		assertNull(v1.getValue());
		
		v1.setValue("4.5");
		v2.setValue(20);
		v1.multiply(v2.getValue());
		assertTrue(v1.getValue().equals(Double.valueOf(90)));
		assertTrue(v2.getValue().equals(Integer.valueOf(20)));
		
		v1.setValue("1.2E-2");
		v2.setValue(100);
		v1.multiply(v2.getValue());
		assertTrue(v1.getValue().equals(Double.valueOf(1.2)));
		assertTrue(v2.getValue().equals(Integer.valueOf(100)));
		
		v1.setValue(null);
		v2.setValue("20.4");
		v2.multiply(v1.getValue());
		assertTrue(v2.getValue().equals(Double.valueOf(0)));
		assertNull(v1.getValue());
		
		v1.setValue("4E04");
		v2.setValue("9E-3");
		v1.multiply(v2.getValue());
		assertTrue(v1.getValue().equals(Double.valueOf(360)));
		assertTrue(v2.getValue().equals("9E-3"));
	}
	
	@Test
	public void divideTest() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(2.999);
		
		v1.divide(v2.getValue());
		assertTrue(v1.getValue().equals(Double.valueOf(0)));
		assertTrue(v2.getValue().equals(2.999));
		
		v1.setValue(20);
		v2.setValue(8);
		v1.divide(v2.getValue());
		assertTrue(v1.getValue().equals(Integer.valueOf(2)));
		
		v1.setValue("24.0");
		v2.setValue("4.0");
		v1.divide(v2.getValue());
		assertTrue(v1.getValue().equals(6.0));
		assertTrue(v2.getValue().equals("4.0"));
		
		v1.setValue(30.0);
		v2.setValue("5.0");
		v1.divide(v2.getValue());
		assertTrue(v1.getValue().equals(6.0));
		assertTrue(v2.getValue().equals("5.0"));
	}
	
	@Test (expected = RuntimeException.class)
	public void divideWithExceptionTest() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(2.999);
		
		v1.setValue(5);
		v2.setValue(null);
		v1.divide(v2.getValue()); //throws an exception division by zero
	}
	
	@Test
	public void numCompareTest() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		
		assertTrue(v1.numCompare(v2.getValue()) == 0);
		
		v1.setValue("0.0");
		v2.setValue(0);
		assertTrue(v1.numCompare(v2.getValue()) == 0);
		
		v1.setValue(1E-04);
		v2.setValue(-2E-04);
		assertTrue(v1.numCompare(v2.getValue()) > 0);
		
		v1.setValue("34");
		v2.setValue(34.0004);
		assertTrue(v2.numCompare(v1.getValue()) > 0);
		
		v1.setValue(null);
		v2.setValue(-3.4567);
		assertTrue(v2.numCompare(v1.getValue()) < 0);
		
		v1.setValue("1E-09");
		v2.setValue("0");
		//this comparison will treat these numbers as equal
		//because the threshold for double comparison is set to
		//1E-08
		assertTrue(v1.numCompare(v2.getValue()) == 0);
		
		v1.setValue(35);
		v2.setValue(35.45);
		assertTrue(v1.numCompare(v2.getValue()) < 0);
	}
}
