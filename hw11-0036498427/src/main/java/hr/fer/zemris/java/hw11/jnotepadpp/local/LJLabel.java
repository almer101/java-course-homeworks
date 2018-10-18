package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JLabel;

/**
 * This is the localizable label which can hold keys to multiple
 * keys and show multiple values in combination with numbers.
 * 
 * @author ivan
 *
 */
public class LJLabel extends JLabel {
	
	//===========================Properties=============================
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The localization provider which translates
	 * the requested key.
	 */
	private ILocalizationProvider lp;
	
	/**
	 * The keys of the label.
	 */
	private String[] keys;
	
	/**
	 * The texts to show on the label. It is here because
	 * maybe the updates are made frequently and we don't
	 * want it to always ask the provider, but only then when the 
	 * language changes.
	 */
	private String[] texts;
	
	/**
	 * The values which are shown with the text.
	 */
	private int[] values;
	
	/**
	 * This is the indicator for the status bar to show blanks 
	 * instead of numbers
	 */
	private static final int BLANK = -1;
	
	//===========================Constructor============================
	
	/**
	 * The constructor which gets the initial values of
	 * the {@link #keys} and the {@link #lp}.
	 * 
	 * @param lp
	 * 		the localization provider which translates
	 * 		the requested key.
	 * 
	 * @param keys
	 * 		the keys of the label.
	 */
	public LJLabel(ILocalizationProvider lp, String ... keys) {
		super();
		this.lp = lp;
		this.keys = keys;
		texts = new String[keys.length];
		updateTexts();
		
		this.lp.addLocalizationListener(() -> {
			updateTexts();
			update(values);
		});
	}
	
	//=======================Method implementations=====================
	
	/**
	 * This method updates the texts of the label in
	 * communication with the {@link #lp}.
	 * 
	 */
	private void updateTexts() {
		for(int i = 0; i < keys.length; i++) {
			texts[i] = lp.getString(keys[i]);
		}
	}

	/**
	 * The method which updates the current label text to
	 * the {@link #text} + the string value of the specified
	 * value
	 * 
	 * @param values
	 * 		the values which are going to be a part
	 * 		of the label text.
	 */
	public void update(int ... values) {
		if(values == null) return;
		if(values.length != keys.length) {
			throw new IllegalArgumentException("The number of the keys and"
					+ "values must be equal!");
		}
		this.values = values;
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < keys.length; i++) {
			sb.append(texts[i] + " : " + (values[i] == BLANK ? "" : values[i]) + "\t\t");
		}
		setText(sb.toString());
	}
}
