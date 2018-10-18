package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * This is the model which listens to the selection in the 
 * text area. Each time something or nothing is selected the
 * listener is informed and the size of the selected
 * part is sent as a parameter.
 * 
 * @author ivan
 *
 */
public interface SelectionListener {

	/**
	 * This is the method called each time something or 
	 * nothing is selected the listener is informed and 
	 * the size of the selected part is sent as a parameter.
	 * 
	 * @param size
	 * 		the size of the selection.
	 */
	public void selectionChanged(int size);
}
