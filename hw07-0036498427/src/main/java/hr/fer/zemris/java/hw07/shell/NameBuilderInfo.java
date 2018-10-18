package hr.fer.zemris.java.hw07.shell;


/**
 * The objects of this type contain a {@link StringBuilder}
 * which the {@link NameBuilder} objects are using for
 * writing pieces of name in. When all writings have 
 * finished, the stringBuilder contains the new name of
 * the file.
 * 
 * @author ivan
 *
 */
public interface NameBuilderInfo {

	/**
	 * This method returns the {@link StringBuilder}
	 * which is a property of this class.
	 * 
	 * @return
	 * 		a {@link StringBuilder}.
	 */
	StringBuilder getStringBuilder();
	
	/**
	 * This method returns the value of the matcher
	 * group for the specified index.
	 * 
	 * @param index
	 * 		the index of the group
	 * 
	 * @return
	 * 		the group from the matcher
	 */
	String getGroup(int index);
}
