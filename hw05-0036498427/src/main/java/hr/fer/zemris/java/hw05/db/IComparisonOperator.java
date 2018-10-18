package hr.fer.zemris.java.hw05.db;

/**
 * The objects which implement this interface 
 * must implement a method <code>satisfied</code>
 * which checks if the certaion condition is satisfied.
 * 
 * @author ivan
 *
 */
public interface IComparisonOperator {

	/**
	 * This method compares specified values and if
	 * condition  is satisfied and returns a
	 * boolean value accordingly.
	 * 
	 * @param value1	
	 * 		the first parameter of the comparison.
	 * 
	 * @param value2
	 * 		the second parameter of the comparison.
	 * 
	 * @return
	 * 		<code>true</code> if the condition is satisfied;
	 * 		<code>false</code> otherwise.
	 */
	public boolean satisfied(String value1, String value2);
}
