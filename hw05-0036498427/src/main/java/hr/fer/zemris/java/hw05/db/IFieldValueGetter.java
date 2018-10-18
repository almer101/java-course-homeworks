package hr.fer.zemris.java.hw05.db;

/**
 * The objects which implement this interface
 * implement the method which obtains the 
 * certain field from the given {@link StudentRecord}
 * 
 * @author ivan
 *
 */
public interface IFieldValueGetter {

	/**
	 * This method obtains and returns the certain
	 * field from the specified record. 
	 * 
	 * @param record
	 * 		the record from which the certain field 
	 * 		has to be obtained.
	 * 
	 * @return
	 * 		the wanted field
	 */
	public String get(StudentRecord record);
}
