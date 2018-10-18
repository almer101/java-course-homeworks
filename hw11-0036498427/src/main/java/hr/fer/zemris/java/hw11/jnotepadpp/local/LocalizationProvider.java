package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This is a singleton which has its own resource bundle
 * and translates the keys.
 * 
 * @author ivan
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	//==============================Properties====================================
	
	/**
	 * The language tag.
	 */
	private String language;
	
	/**
	 * The bundle which translates the requested key.
	 */
	private ResourceBundle bundle;
	
	/**
	 * The single instance of this class.
	 */
	private static LocalizationProvider instance = new LocalizationProvider();
	
	/**
	 * The default language tag.
	 */
	private static final String DEFAULT = "en";
	
	//===========================Private constructor==============================
	
	/**
	 * A private constructor which initializes the language to
	 * the default language and initializes the {@link #bundle}
	 * for that language.
	 */
	private LocalizationProvider() {
		language = DEFAULT;
		bundle = ResourceBundle.getBundle(
				"hr.fer.zemris.java.hw11.jnotepadpp.local.translations", 
				Locale.forLanguageTag(language));
	}
	
	//==========================Method implementations============================
	
	/**
	 * The method which returns an instance of {@link LocalizationProvider}. 
	 * 
	 * @return
	 * 		the instance of {@link LocalizationProvider}.
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	/**
	 * The method which sets the value of the property {@link #language}
	 * to the specified value. The value must not be <code>null</code>.
	 * 
	 * @param language
	 * 		the new language to set.
	 */
	public void setLanguage(String language) {
		this.language = Objects.requireNonNull(language);
		bundle = ResourceBundle.getBundle(
				"hr.fer.zemris.java.hw11.jnotepadpp.local.translations", 
				Locale.forLanguageTag(language));
		fire();
	}
	
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}
}
