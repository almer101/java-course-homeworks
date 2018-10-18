package hr.fer.zemris.java.hw05.db;

/**
 * The classes which implement this interface
 * must provide the <i>accept</i> method which
 * accepts or declines the given argument.
 * 
 * @author ivan
 *
 */
public interface IFilter {

	/**
	 * This method performs the analysis of
	 * the specified record and returns true if
	 * the record is accepted and false otherwise.
	 * 
	 * @param record
	 * 		record to be checked.
	 * 
	 * @return
	 * 		<code>true</code> if the record is 
	 * 		accepted. <code>false</code> otherwise.
	 */
	public boolean accepts(StudentRecord record);
}
