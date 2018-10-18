package hr.fer.zemris.java.hw05.db;

/**
 * This class contains of public static
 * final {@link IFieldValueGetter}s and every
 * such obtains a certain field from the 
 * specified record.
 * 
 * @author ivan
 *
 */
public class FieldValueGetters {

	//=====================Public static final variables====================
	
	/**
	 * This getter returns the value of the 
	 * student's first name.
	 * 
	 */
	public static final IFieldValueGetter FIRST_NAME = r -> r.getFirstName();
	
	/**
	 * This getter returns the value of the 
	 * student's last name.
	 * 
	 */
	public static final IFieldValueGetter LAST_NAME = r -> r.getLastName();
	
	/**
	 * This getter returns the value of the
	 * student's jmbag.
	 * 
	 */
	public static final IFieldValueGetter JMBAG = r -> r.getJmbag();
}
