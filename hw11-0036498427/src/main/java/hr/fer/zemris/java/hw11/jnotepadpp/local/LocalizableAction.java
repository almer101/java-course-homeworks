package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

/**
 * The action which reacts to the change of language
 * by changing the content it shows. (e.g. if the button
 * has this action the text on the button will change.)
 * 
 * @author ivan
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	/**
	 * The key of the action (i.e. the key under which
	 * the translation can be found in the {@link #lp}
	 */
	private String key;
	
	/**
	 * The provider which translates the requested key.
	 */
	private ILocalizationProvider lp;
	
	/**
	 * The constructor which gets the key of the action and
	 * the {@link ILocalizationProvider} which translates
	 * the requested key.
	 * 
	 * @param key
	 * 		the key of the action.
	 * 
	 * @param lp
	 * 		the localization provider which translates
	 * 		the keys.
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		this.key = key;
		this.lp = lp;
		
		putValue(NAME, lp.getString(this.key));
		putValue(SHORT_DESCRIPTION, lp.getString(this.key + ".sd"));
		putValue(MNEMONIC_KEY, Integer.parseInt(lp.getString(this.key + ".mn")));
		this.lp.addLocalizationListener(() -> {
			putValue(NAME, lp.getString(this.key));
			putValue(SHORT_DESCRIPTION, lp.getString(this.key + ".sd"));
			putValue(MNEMONIC_KEY, Integer.parseInt(lp.getString(this.key + ".mn")));
		});
	}
	
	@Override
	public abstract void actionPerformed(ActionEvent e);
}
