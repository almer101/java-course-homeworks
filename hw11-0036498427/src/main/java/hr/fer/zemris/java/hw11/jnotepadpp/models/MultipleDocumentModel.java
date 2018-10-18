package hr.fer.zemris.java.hw11.jnotepadpp.models;

import java.nio.file.Path;

/**
 * This is the model of the multiple document management
 * object. This model is capable of holding zero, one or
 * more documents.
 * 
 * @author ivan
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

	/**
	 * This method creates a new document and adds it to
	 * the model.
	 * 
	 * @return
	 * 		the reference to the newly created document.
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * The method returns the document which user is 
	 * working on.
	 * 
	 * @return
	 * 		the currently edited document.
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * The method which loads the existing document
	 * to the model. The specified path must not be
	 * <code>null</code>
	 *
	 * @param path
	 * 		the path to the file to load.
	 * 
	 * @return
	 * 		the document created for the specified path.
	 */
	SingleDocumentModel loadDocument(Path path);
	
	/**
	 * The method which saves the specified document to the
	 * specified path. <p>
	 * The specified path can be <code>null</code> and in that
	 * case the file is saved to the path associated with the 
	 * document. Otherwise the document is saved to the specified
	 * path and the currentPath of the document is updated
	 * to the value of the <code>newPath</code>
	 * 
	 * @param model
	 * 		the model of the document to save.
	 * 
	 * @param newPath
	 * 		the path where to save the document.
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * The method which closes the specified document but
	 * does not check the modification status or ask any
	 * questions.
	 * 
	 * @param model
	 * 		the model of the document to close.
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * The method which adds the specified listener to the
	 * collection of listeners.
	 * 
	 * @param l
	 * 		a listener to add to the collection.
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * This is the method which removes the specified listener
	 * from the collection of listeners.
	 * 
	 * @param l
	 * 		a listener to remove from the collection.
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l); 
	
	/**
	 * This method returns the number of documents stored
	 * in the {@link MultipleDocumentModel}.
	 * 
	 * @return
	 * 		the number of documents stored in the
	 * 		{@link MultipleDocumentModel}.
	 */
	int getNumberOfDocuments();
	
	/**
	 * This method returns the model on the 
	 * <code>index</code>-th location in the model.
	 * 
	 * @param index
	 * 		the index of the document to return.
	 * 
	 * @return
	 * 		the document which is located at the
	 * 		specified index.
	 */
	SingleDocumentModel getDocument(int index);
}
