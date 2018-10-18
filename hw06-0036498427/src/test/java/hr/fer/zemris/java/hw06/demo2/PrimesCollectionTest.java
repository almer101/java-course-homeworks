package hr.fer.zemris.java.hw06.demo2;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

public class PrimesCollectionTest {

	@Test (expected = IllegalArgumentException.class)
	public void constructorTest() {
		PrimesCollection pc1 = new PrimesCollection(4); //OK
		PrimesCollection pc2 = new PrimesCollection(-2); //throws exception
	}
	
	@Test (expected = NoSuchElementException.class)
	public void iteratorTest() {
		PrimesCollection pc = new PrimesCollection(10);
	
		Iterator<Integer> it = pc.iterator();
		int primes[] = {2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,
						53,59,61,67,71,73,79,83};
		int i = 0;
		while(it.hasNext()) {
			assertTrue(it.next() == primes[i++]);
		}
		
		it.next(); //throws an exception.
	}
	
	@Test
	public void multipleIteratorNumberOfIterationsTest() {
		PrimesCollection pc = new PrimesCollection(8);
		
		int n = 0;
		for(Integer i : pc) {
			for(Integer j : pc) {
				//System.out.println(i + ", " + j);
				n++;
			}
		}
		assertTrue(n == 8*8);
		
		PrimesCollection pc2 = new PrimesCollection(0);
		
		n = 0;
		for(Integer i : pc2) {
			for(Integer j : pc2) {
				//System.out.println(i + ", " + j);
				n++;
			}
		}
		assertTrue(n == 0);
		
		PrimesCollection pc3 = new PrimesCollection(4);
		
		n = 0;
		for(Integer i : pc3) {
			for(Integer j : pc3) {
				for(Integer k : pc3) {
					//System.out.println(i + ", " + j + ", " + k);
					n++;
				}
			}
		}
		assertTrue(n == 4*4*4);
	}	
}
