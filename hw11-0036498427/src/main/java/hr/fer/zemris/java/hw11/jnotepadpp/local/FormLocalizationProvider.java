package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * This is the class derived from the {@link LocalizationProviderBridge}
 * and which has a reference to the observed frame and once the frame
 * is opened this provider automatically connects to the main provider
 * and when the frame is closing this provider automatically disconnects
 * from the main provider.
 * 
 * @author ivan
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * The frame which is observes and once it is closed the
	 * connection with the main provider is broken.
	 */
	private JFrame frame;
	
	/**
	 * The constructor which initializes the provider and the frame.
	 * It also sets the window listener to the frame and when the 
	 * frame is opening it connects to the main provider. Once the
	 * frame is closing the provider automatically disconnects from
	 * the provider.
	 * 
	 * @param parent
	 * 		the main provider which translates the requested keys.
	 * 
	 * @param frame
	 * 		the observed frame.
	 */
	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(parent);
		this.frame = frame;
		this.frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				disconnect();
			}
		});
	}
}
