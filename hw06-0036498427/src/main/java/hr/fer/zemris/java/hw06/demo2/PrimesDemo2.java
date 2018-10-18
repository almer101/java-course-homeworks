package hr.fer.zemris.java.hw06.demo2;

/**
 * This is the program which demonstrates
 * the functionalities of the 
 * {@link PrimesCollection} class.
 * 
 * @author ivan
 *
 */
public class PrimesDemo2 {

	/**
	 * This is the method called when the program
	 * is run.
	 * 
	 * @param args
	 * 		command line arguments.
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		for(Integer prime : primesCollection) {
		  for(Integer prime2 : primesCollection) {
		    System.out.println("Got prime pair: "+prime+", "+prime2);
		  }
		}
	}
}
