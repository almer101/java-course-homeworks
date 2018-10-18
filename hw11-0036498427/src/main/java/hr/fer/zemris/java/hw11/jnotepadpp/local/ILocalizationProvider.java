package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * The interface defining the localization providers
 * which can register and unregister listeners and 
 * provide the {@link #getString(String)} method which translates
 * the specified key.
 * 
 * @author ivan
 *
 */
public interface ILocalizationProvider {

	/**
	 * This method adds the specified listener to the
	 * collection of listeners.
	 * 
	 * @param l
	 * 		a listener to add.
	 */
	public void addLocalizationListener(ILocalizationListener l);
	
	/**
	 * This method removes the specified listener from 
	 * the collection of listeners.
	 * 
	 * @param l
	 * 		the listener to remove from the collection.
	 */
	public void removeLocalizationListener(ILocalizationListener l);
	
	/**
	 * This method translates the requested key and
	 * returns the translation.
	 * 
	 * @param key
	 * 		a key to translate.
	 * 
	 * @return
	 * 		the translation of the key.
	 */
	public String getString(String key);
}
