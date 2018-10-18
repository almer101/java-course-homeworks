package hr.fer.zemris.java.hw16.trazilica;

import java.nio.file.Path;
import java.util.Comparator;

/**
 * This is the result of the query. It contains the path to the file and the similarity 
 * with the user's query. 1 being the biggest similarity and 0 being the smallest.
 * 
 * @author ivan
 *
 */
public class Result implements Comparable<Result> {
	
	/**The path to the result file.*/
	private Path path;
	
	/**The similarity of the file with the user's query.*/
	private double similarity;
	
	/**
	 * This comparator will compare the results such that the results with the
	 * largest similarity are the first in the list which is being sorted.
	 */
	public static final Comparator<Result> DESC = 
			(r1, r2) -> -Double.compare(r1.similarity, r2.similarity);

	/**
	 * The constructor which gets the initial values of all the parameters of this class.
	 * 
	 * @param path
	 * 		the path to the file.
	 * @param similarity
	 * 		the similarity to the user's query.
	 */
	public Result(Path path, double similarity) {
		super();
		this.path = path;
		this.similarity = similarity;
	}

	/**
	 * This method returns the value of the {@link #path} property.
	 * 
	 * @return
	 * 		the value of the {@link #path} property.
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * This method returns the value of the {@link #similarity} property.
	 * 
	 * @return
	 * 		the value of the {@link #similarity} property.
	 */
	public double getSimilarity() {
		return similarity;
	}

	@Override
	public int compareTo(Result other) {
		return -Double.compare(similarity, other.similarity);
	}
}
