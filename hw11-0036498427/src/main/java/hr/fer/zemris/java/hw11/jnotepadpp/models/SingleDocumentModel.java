package hr.fer.zemris.java.hw11.jnotepadpp.models;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * This is the model of the single document. It holds the 
 * information about the file path, document modification status
 * and reference to the swing component which is used for editing.
 * (each document has its own editor component)
 * 
 * @author ivan
 *
 */
public interface SingleDocumentModel {

	/**
	 * This method returns the swing component which
	 * is used for editing.
	 * 
	 * @return
	 * 		the swing component which is used for editing.
	 */
	JTextArea getTextComponent();
	
	/**
	 * Method which returns the path to the file of
	 * this model.
	 * 
	 * @return
	 * 		the path to the file of this model.
	 */
	Path getFilePath();
	
	/**
	 * This method sets the current file path to the 
	 * specified one.
	 * 
	 * @param path
	 * 		the new path to set.
	 */
	void setFilePath(Path path);
	
	/**
	 * This method checks if the file is modified and
	 * returns the boolean value accordingly.
	 * 
	 * @return
	 * 		<code>true</code> if the file is modified, 
	 * 		<code>false</code> otherwise.
	 */
	boolean isModified();
	
	/**
	 * The method which sets the <code>modified</code> flag
	 * to the specified value.
	 * 
	 * @param modified
	 * 		the new value of the <code>modified</code> flag.
	 */
	void setModified(boolean modified);
	
	/**
	 * This method adds the specified listener to the 
	 * collection of listeners of this model.
	 * 
	 * @param l
	 * 		the listener to add to the collection.
	 */
	void addSingleDocumentListener(SingleDocumentListener l); 
	
	/**
	 * This method removes the specified listener from
	 * the collection of listeners of this model.
	 * 
	 * @param l
	 * 		a listener to remove.
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
