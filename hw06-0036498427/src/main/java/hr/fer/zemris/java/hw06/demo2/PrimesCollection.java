package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class represents a collection which
 * generates consecutive prime numbers.
 * 
 * @author ivan
 *
 */
public class PrimesCollection implements Iterable<Integer>{

	//=========================Properties=============================
	
	/**
	 * This is the number of prime numbers in this collection.
	 */
	private final int count;
	
	/**
	 * This is the first prime from which the program starts.
	 */
	private static final int STARTING_NUMBER = 1;

	//=========================Constructor============================
	
	/**
	 * This constructor gets one parameter <code>count</code> which
	 * is the initial value of this class' property 
	 * <code>count</code>.
	 * 
	 * @param count
	 * 		the initial value of the property <code>count</code>.
	 * 
	 * @throws IllegalArgumentException
	 * 		if the given argument is less than zero.
	 */
	public PrimesCollection(int count) {
		if(count < 0) {
			throw new IllegalArgumentException("The given number of"
					+ " prime numbers must not be less than zero! "
					+ "Was " + count);
		}
		this.count = count;
	}
	
	//======================Iterator class============================
	
	/**
	 * This class is an implementation of {@link Iterator}.
	 * When next() method is called the next prime number 
	 * is returned, but only if the hasNext() method returns 
	 * true beforehand.
	 * 
	 * @author ivan
	 *
	 */
	private class IteratorImpl implements Iterator<Integer> {

		/**
		 * Number of prime numbers this iterator will
		 * print out.
		 */
		int count;
		
		/**
		 * The last prime number.
		 */
		int lastPrime;
		
		public IteratorImpl() {
			count = PrimesCollection.this.count;
			lastPrime = PrimesCollection.STARTING_NUMBER;
		}
		
		@Override
		public boolean hasNext() {
			return count > 0;
		}

		@Override
		public Integer next() {
			if(!hasNext()) {
				throw new NoSuchElementException("");
			}
			count--;
			lastPrime = nextPrime();
			return lastPrime;
		}
		
		/**
		 * This method calculates the next prime number
		 * after <code>lastPrime</code> and returns it.
		 * 
		 * @return
		 * 		the next prime number after 
		 * 		<code>lastPrime</code>.
		 */
		private Integer nextPrime() {
			int n = lastPrime + 1;
			while(true) {
				if(isPrime(n)) return n;
				n++;
			}
		}

		/**
		 * This method check if the number is prime
		 * or not and returns a boolean value 
		 * accordingly.
		 * 
		 * @param n
		 * 		number to be checked.
		 * 
		 * @return
		 * 		<code>true</code> if the number is prime. 
		 * 		<code>false</code> otherwise.
		 */
		private boolean isPrime(int n) {
			if(n == 2) return true;
			
			for(int i = 2; i < Math.sqrt(n) + 1; i++) {
				if(n % i == 0) return false; 
			}
			return true;
		}
	}
	
	//====================Method implementations======================
	
	@Override
	public Iterator<Integer> iterator() {
		return new IteratorImpl();
	}
}
