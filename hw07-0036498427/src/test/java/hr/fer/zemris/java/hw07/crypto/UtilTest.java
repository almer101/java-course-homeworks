package hr.fer.zemris.java.hw07.crypto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UtilTest {

	@Test
	public void hextobyteTest() {
		
		byte array[] = Util.hextobyte("01ff22");
		assertEquals(1, array[0]);
		assertEquals(-1, array[1]);
		assertEquals(34, array[2]);
		
		array = Util.hextobyte("ffffffaa3456e3");
		assertEquals(-1, array[0]);
		assertEquals(-1, array[1]);
		assertEquals(-86, array[3]);
		assertEquals(52, array[4]);
		assertEquals(-29, array[6]);
		
		array = Util.hextobyte("e52217e3ee213ef1ffdee3a192e2ac7e");
		printArray(array);
	}
	
	@Test
	public void hextobyteEmptyHexTest() {
		byte array[] = Util.hextobyte("");
		assertEquals(0, array.length);
	}
	
	private void printArray(byte[] array) {
		System.out.print("[");
		for(int i = 0; i < array.length; i++) {
			if(i != 0) System.out.print(", " + array[i]);
			else System.out.print(array[i]);
		}
		System.out.println("]");
	}

	@Test
	public void bytetohexTest() {
		
		String s = Util.bytetohex(new byte[] {-1, 68, 40});
		assertEquals("ff4428", s);
		
		s = Util.bytetohex(new byte[] {123, 10}); 
		assertEquals("7b0a", s);
		
		s = Util.bytetohex(new byte[] {-4, 40, 127});
		assertEquals("fc287f", s);
		
		s = Util.bytetohex(new byte[] {-4, 40, -128});
		assertEquals("fc2880", s);
		
		s = Util.bytetohex(new byte[] {0, 0, 0, 0});
		assertEquals("00000000", s);
		
		s = Util.bytetohex(new byte[] {-128, 126, 0, 0});
		assertEquals("807e0000", s);
	}
	
	@Test
	public void bytetohexEmptyArrayTest() {
		String s = Util.bytetohex(new byte[] {});
		assertEquals("", s);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void hextobyteIllegalStringFormatExceptionTest() {
		Util.hextobyte("aaff345j");
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void hextobyteOddLengthOfTheStringWithException() {
		Util.hextobyte("1fa");
	}
}
