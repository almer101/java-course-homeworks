package hr.fer.zemris.java.custom.collection;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

public class ArrayIndexedCollectionTest {
	
	Integer i1 = Integer.valueOf(5);
	Integer i2 = Integer.valueOf(35);
	Integer i3 = Integer.valueOf(-455);
	Integer i4 = Integer.valueOf(23);
	
	private ArrayIndexedCollection loadElements() {
		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		elements.add(i1);
		elements.add(i2);
		elements.add(i3);
		elements.add(i4);
		
		return elements;
	}
	
	@Test
	public void addAndSizeTest() {
		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		assertEquals(true, elements.isEmpty());
		
		assertEquals(0, elements.size());
		elements.add(i1);
		elements.add(i2);
		
		assertTrue(elements.contains(i1));
		assertFalse(elements.contains(null));
		assertEquals(2, elements.size());
		assertFalse(elements.isEmpty());
	}
	
	@Test (expected = NullPointerException.class)
	public void addElementExceptionTest() {
		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		elements.add(null);	
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void getElementExceptionTest() {
		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		elements = loadElements();
		
		assertEquals(i1, elements.get(0));
		assertEquals(i2, elements.get(1));
		assertEquals(i3, elements.get(2));
		elements.get(4);
	}
	
	@Test
	public void clearTest() {
		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		elements = loadElements();
		
		assertEquals(i3, elements.get(2));
		elements.clear();
		assertEquals(0, elements.size());
	}
	
	@Test
	public void insertAndIndexOfTest() {
		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		
		elements.add(i1);
		elements.add(i2);
		elements.insert(i4, 0);
		elements.insert(i4, elements.size());
		
		assertEquals(0, elements.indexOf(i4));
		assertEquals(1, elements.indexOf(i1));
		assertEquals(-1, elements.indexOf(null));
		assertEquals(-1, elements.indexOf(i3));
		assertEquals(i2, elements.get(2));
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void removeTest() {
		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		assertEquals(false, elements.remove(null));
		elements = loadElements();
		
		assertEquals(4, elements.size());
		elements.remove(0);
		elements.remove(0);
		assertEquals(2, elements.size() );
		elements.remove(i4);
		assertEquals(1, elements.size());
		elements.remove(0);
		elements.remove(0);
	}
	
	@Test
	public void ensureCapacityTest() {
		ArrayIndexedCollection elements = new ArrayIndexedCollection(8);
		
		for(int i = 0; i < 20; i++) {
			elements.add(Integer.valueOf(i));
		}
		
		assertEquals(20, elements.size());
	}
	
}
