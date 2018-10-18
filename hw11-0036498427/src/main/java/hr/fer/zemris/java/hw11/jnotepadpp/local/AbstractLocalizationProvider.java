package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the abstract model of the localization provider
 * which know how to add and remove listeners to and from the
 * collection and knows how to inform all the listeners that the
 * change of the localization occurred.
 * The {@link #getString(String)} method is not implemented.
 * 
 * @author ivan
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	//===========================Properties================================
	
	/**
	 * The collection of listeners which are to be informed
	 * every time the localization changes.
	 */
	private List<ILocalizationListener> listeners = new ArrayList<>();
	
	//=======================Method implementations========================
	
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}

	@Override
	public abstract String getString(String key);

	/**
	 * The method which notifies all the listeners that the
	 * change occurred.
	 */
	public void fire() {
		listeners.forEach(l -> l.localizationChanged());
	}
}
