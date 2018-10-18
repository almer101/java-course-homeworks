package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the bridge between the singleton provider and the
 * frame. The class offers some additional methods and those
 * are 
 * 
 * @author ivan
 *
 */
public class LocalizationProviderBridge implements ILocalizationProvider {

	//===============================Properties======================================
	
	/**
	 * The {@link LocalizationProvider} which translates the
	 * requested key.
	 */
	private ILocalizationProvider parent;
	
	/**
	 * The flag indicating that the bridge is connected.
	 */
	private boolean connected = false;
	
	/**
	 * The listeners which are informed every time the localization
	 * changes.
	 */
	private List<ILocalizationListener> listeners = new ArrayList<>();
	
	/**
	 * The listener which is registered and unregistered to and
	 * from the provider.
	 */
	private ILocalizationListener l = () -> {
		listeners.forEach(l -> l.localizationChanged());
	};
	
	//================================Constructor===================================

	/**
	 * The constructor which gets an instance of {@link ILocalizationProvider}
	 * and initializes the property {@link #parent} with it.
	 * 
	 * @param provider
	 * 		the initial value of the property {@link #parent}
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		super();
		this.parent = provider;
	}
	
	//===========================Method implementations=============================
	
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

	/**
	 * This method registers a listener to the provider and
	 * sets the {@link #connected} flag to <code>true</code>
	 * 
	 */
	public void connect() {
		if(connected) return;
		parent.addLocalizationListener(l);
		connected = true;
	}
	
	/**
	 * This method unregisters the listener from the provider
	 * and sets the {@link #connected} flag to <code>false</code>.
	 */
	public void disconnect() {
		if(!connected) return;
		parent.removeLocalizationListener(l);
		connected = false;
	}
	
}
