package hr.fer.zemris.java.gui.layouts;

import static org.junit.Assert.assertEquals;
import static hr.fer.zemris.java.gui.layouts.RCPosition.fromString;

import org.junit.Test;

public class RCPostionTest {

	@Test
	public void fromStringTest() {
		RCPosition p = fromString("1,1");
		assertEquals(1, p.getRow());
		assertEquals(1, p.getColumn());
		
		p = fromString(" 4 , 7");
		assertEquals(4, p.getRow());
		assertEquals(7, p.getColumn());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void fromStringTestInvalidNumbers() {
		RCPosition p = fromString("1$, 1");
		assertEquals(1, p.getRow());
		assertEquals(1, p.getColumn());
	}
	
	@Test
	public void fromStringTest2() {
		RCPosition p = fromString("-3,  3");
		assertEquals(-3, p.getRow());
		assertEquals(3, p.getColumn());
		
		p = fromString("4, 23 ");
		assertEquals(4, p.getRow());
		assertEquals(23, p.getColumn());
	}
}
