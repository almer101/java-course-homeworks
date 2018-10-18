package hr.fer.zemris.java.hw05.collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.Test;

public class SimpleHashtableTest {

	@Test
	public void putGetTest() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(2);
		table.put("Pizza", 5);
		table.put("muffin", 4);
		table.put("Pizza", 6);
		table.put("blabla", -3);
		table.put("krafna", 34);
		table.put("krafna2", 34);
		table.put("krafna3", null);
		table.remove("muffin");
		
		assertTrue(table.size() == 5);
		assertTrue(table.get(null) == null);
		assertTrue(table.get("Pizza") == 6);
		assertTrue(table.get("blabl") == null);

	}
	
	@Test (expected = NullPointerException.class)
	public void putGetisEmptyTest2() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		
		table.put("Avion", 45);
		table.put("brod", 23);
		table.put("auto", -45);
		table.put("Avion", 30);
		
		assertTrue(table.size() == 3);
		assertTrue(table.get("Avion") == 30);
		table.remove("brod");
		
		assertTrue(table.size() == 2);
		table.clear();
		assertTrue(table.get("Avion") == null);
		
		assertTrue(table.isEmpty());
		table.put(null, 45);
	}
	
	@Test
	public void clearTest() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>();
		
		assertTrue(table.isEmpty());
		table.clear();
		table.clear();
		assertFalse(table.containsKey("key"));
		table.put("key", 34);
		
		assertTrue(table.containsKey("key"));
		table.clear();
		assertFalse(table.containsKey("key"));
	}
	
	@Test
	public void containsTest() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(2);
		table.put("kljuc", null);
		assertTrue(table.containsValue(null));
		
		table.put("key1", 1);
		table.put("key2", 2);
		table.put("key3", 3);
		table.put("key4", 4);
		table.put("key5", 5);
		
		assertTrue(table.containsKey("key5"));
		assertFalse(table.containsKey("key6"));
		assertTrue(table.containsValue(4));
	}
	
	@Test (expected = IllegalStateException.class)
	public void iteratorTest() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(2);
		
		table.put("blabla", 44);
		table.put("nogomet", 22);
		table.put("hokej", 26);
		table.put("key1", 1);
		table.put("key1", 2);
		table.put("key2", 2);
		table.put("key3", 3);
		
		assertTrue(table.size() == 6);
		
		System.out.println(table.toString());
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = table.iterator(); 
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next(); 
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue()); 
			iter.remove();
			iter.remove();
		}
		System.out.printf("Veličina: %d%n", table.size());
	}
	
	@Test (expected = ConcurrentModificationException.class)
	public void iteratorTest2() {
		SimpleHashtable<String, Integer> table = new SimpleHashtable<>(2);
		
		table.put("blabla", 44);
		table.put("nogomet", 22);
		table.put("hokej", 26);
		table.put("key1", 1);
		table.put("key1", 2);
		table.put("key2", 2);
		table.put("key3", 3);
		
		assertTrue(table.size() == 6);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = table.iterator(); 
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next(); 
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue()); 
			iter.remove();
			table.remove("key2");
		}
	}
}
