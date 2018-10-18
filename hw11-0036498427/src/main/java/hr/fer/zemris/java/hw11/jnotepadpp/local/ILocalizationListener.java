package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * This is the model of the localization listener
 * which is informed each time the localization changes.
 * 
 */
public interface ILocalizationListener {

	/**
	 * The method called when the localization changes.
	 */
	public void localizationChanged();
}
