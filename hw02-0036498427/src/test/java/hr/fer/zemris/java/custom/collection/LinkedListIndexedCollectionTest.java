package hr.fer.zemris.java.custom.collection;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;

public class LinkedListIndexedCollectionTest {

	@Test
	public void insertTest() {
		LinkedListIndexedCollection lista = new LinkedListIndexedCollection();
		String s = "ivan";
		lista.insert(s, 0);
		lista.add("petra");
		lista.add("bla");
		lista.insert("umetni", 1);
		assertEquals(s, lista.get(0));
		assertEquals("umetni", lista.get(1));
		lista.insert("novi", 2);
		assertEquals("petra", lista.get(3));
		assertEquals("novi", lista.get(2));
		lista.clear();
		lista.add("pero");
		assertEquals("pero", lista.get(0));
	}
}
