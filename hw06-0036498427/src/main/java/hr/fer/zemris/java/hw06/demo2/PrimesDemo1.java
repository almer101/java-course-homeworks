package hr.fer.zemris.java.hw06.demo2;

/**
 * This is the program which demonstrates
 * the functionalities of the 
 * {@link PrimesCollection} class.
 * 
 * @author ivan
 *
 */
public class PrimesDemo1 {

	/**
	 * This is the method called when the program
	 * is run.
	 * 
	 * @param args
	 * 		command line arguments.
	 */
	public static void main(String[] args) {
		
		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
		for(Integer prime : primesCollection) {
		    System.out.println("Got prime: "+prime);
		}
	}
}
