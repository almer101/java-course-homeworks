package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * This is the localizable {@link JMenu} which changes
 * its text every time the localization changes.
 * 
 * @author ivan
 *
 */
public class LJMenu extends JMenu {
	
	private static final long serialVersionUID = 1L;

	/**
	 * The provider which translates the specified
	 * key to the language which is set.
	 */
	private ILocalizationProvider lp;
	
	/**
	 * The key of the menu
	 */
	private String key;
	
	/**
	 * The constructor which gets the {@link ILocalizationProvider}
	 * which how to translate the specified key of the menu.
	 * 
	 * @param lp
	 * 		the provider which translates the requested key.
	 * 
	 * @param key
	 * 		the key of the menu.
	 */
	public LJMenu(String key, ILocalizationProvider lp) {
		this.lp = lp;
		this.key = key;
		
		setText(this.lp.getString(this.key));
		this.lp.addLocalizationListener(() -> {
			setText(this.lp.getString(this.key));
		});
	}

}
