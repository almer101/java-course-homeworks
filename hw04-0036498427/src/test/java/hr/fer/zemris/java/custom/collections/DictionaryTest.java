package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DictionaryTest {

	@Test
	public void isEmptyTest() {
		Dictionary dict = new Dictionary();
		assertTrue(dict.isEmpty());
		dict.put("key", "value");
		assertFalse(dict.isEmpty());
		dict.clear();
		assertTrue(dict.isEmpty());
	}
	
	@Test
	public void sizeTest() {
		Dictionary dict = new Dictionary();
		assertTrue(dict.size() == 0);
		
		dict.put("key1", 2);
		dict.put("key2", -4);
		dict.put(45.5, "value1");
		dict.put("key2", 4);
		assertTrue(dict.size() == 3);
		
		dict.put("key1", 45.5);
		dict.put("key3", "value1");
		assertTrue(dict.size() == 4);
		dict.clear();
		assertTrue(dict.isEmpty());
	}
	
	@Test(expected = NullPointerException.class)
	public void putTest() {
		Dictionary dict = new Dictionary();
		
		dict.put("value", null);
		assertTrue(dict.get("value") == null);
		dict.put("value", 3);
		assertTrue(dict.size() == 1);
		dict.put(null, "value");
	}
	
	@Test
	public void putGetTest() {
		Dictionary dict = new Dictionary();
		dict.put("key1", 9);
		dict.put("key2", 21);
		dict.put(36, 24);
		
		assertTrue((Integer)dict.get("key2") == 21);
		assertTrue((Integer)dict.get("key1") == 9);
		
		dict.put("key2", 22);
		assertTrue((Integer)dict.get("key2") == 22);
		assertTrue(dict.get("blabla") == null);
		
		dict.clear();
		dict.put("key3", 15);
		assertTrue(dict.get("key1") == null);
		assertTrue((Integer)dict.get("key3") == 15);
		assertTrue(dict.size() == 1);
	}
}
