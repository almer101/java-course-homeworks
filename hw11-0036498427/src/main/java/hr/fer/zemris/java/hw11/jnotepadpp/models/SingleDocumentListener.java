package hr.fer.zemris.java.hw11.jnotepadpp.models;

/**
 * This listener of the single document model which is notified
 * each time the change is made to the document.
 * 
 * @author ivan
 *
 */
public interface SingleDocumentListener {

	/**
	 * This is the method called when the modify status of the
	 * file is updated (i.e. when the file is modified or when
	 * it is saved)
	 * 
	 * @param model
	 * 		the model of the file where the change happened.
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * This is the method called when the path of the file
	 * is updated, i.e. when the file is saved and it was not
	 * saved before.
	 * 
	 * @param model
	 * 		the model of the file where the change happened.
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
