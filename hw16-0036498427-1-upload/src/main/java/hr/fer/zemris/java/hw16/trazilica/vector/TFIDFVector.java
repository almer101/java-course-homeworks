package hr.fer.zemris.java.hw16.trazilica.vector;

import java.util.Arrays;

/**
 * This is the implementation of the n-dimensional vector where n is the size of
 * the stop words list. It provides methods for multiplying the vectors,
 * calculating the norm of it etc. It also provides the method which will
 * produce the vector of the specified file. This class has the property which
 * is the list of all the stop words which are necessary when creating the
 * vector representation of the document.
 * 
 * 
 * @author ivan
 *
 */
public class TFIDFVector {

	/** The dimension of the vector */
	private int dimension;

	/** The vector itself, i.e. the values it stores. */
	private double[] vector;

	/**
	 * This is the constructor which gets the dimension of the vector as the
	 * parameter and initializes the vector with that information. The vector is
	 * still not filled with data.
	 * 
	 * @param dimension
	 *            the dimension of the vector.
	 */
	public TFIDFVector(int dimension) {
		this.dimension = dimension;
		vector = new double[dimension];
	}

	/**
	 * This constructor gets the double array as the parameter. This double array
	 * contains the values which will be the values of this vector.
	 * 
	 * @param vector
	 *            the array of double values.
	 */
	public TFIDFVector(double[] vector) {
		this.vector = Arrays.copyOf(vector, vector.length);
		this.dimension = vector.length;
	}

	/**
	 * This method calculates the scalar product of this vector multiplied with the
	 * specified one and returns the result.
	 * 
	 * @param v2
	 *            the vector to multiply with
	 * @return the scalar product.
	 */
	public double scalarProduct(TFIDFVector v2) {
		if (this.dimension != v2.dimension) {
			throw new IllegalArgumentException(
					"Two vectors which do not have the same dimensions can not be multiplied.");
		}
		double sum = 0;
		for (int i = 0; i < dimension; i++) {
			sum += vector[i] * v2.vector[i];
		}
		return sum;
	}

	/**
	 * This method returns the index-th component of this vector.
	 * 
	 * @param index
	 *            the index of the component of the vector.
	 * @return the index-th component of the vector.
	 */
	public double getComponentAtIndex(int index) {
		return vector[index];
	}

	/**
	 * This method returns the norm of the vector.
	 * 
	 * @return the norm of the vector.
	 */
	public double norm() {
		double sum = 0;
		for (int i = 0; i < dimension; i++) {
			sum += vector[i] * vector[i];
		}
		return Math.sqrt(sum);
	}

	/**
	 * This method calculates the similarity between two vectors, this one and the
	 * specified one. The similarity is measured as the cosine of the angle between
	 * the two vectors.
	 * 
	 * @param other
	 * 		the vector to which the similarity is measured.
	 * @return
	 * 		the similarity between two vectors, a number from interval [0, 1]. 1 being the 
	 * 		greatest similarity, 0 being the smallest.
	 */
	public double calculateSimilarity(TFIDFVector other) {
		if (dimension != other.dimension) {
			throw new IllegalArgumentException(
					"The similarity between two vectors which "
					+ "do not have the same dimensions can not be calculated.");
		}
		double sp = scalarProduct(other);
		double normProduct = norm() * other.norm();
		return sp / normProduct;
	}
}
