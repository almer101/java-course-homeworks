package hr.fer.zemris.java.hw16.jvdraw.geometricObjects;

import javax.swing.JPanel;

/**
 * This is the editor which enables editing of geometric objects. For each object
 * other editor is produced.
 * 
 * @author ivan
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {

	/**The default serial version UID*/
	private static final long serialVersionUID = 1L;

	/**
	 * Checks if the entered data is valid and if not the exception
	 * is thrown.
	 */
	public abstract void checkEditing();

	/**
	 * This method writes all the entered data back to the geometric object.
	 */
	public abstract void acceptEditing();
}
