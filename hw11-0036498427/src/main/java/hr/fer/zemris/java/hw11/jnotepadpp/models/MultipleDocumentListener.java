package hr.fer.zemris.java.hw11.jnotepadpp.models;

/**
 * This is the model of the multiple document listener.
 * The methods of this listener are called when:
 * <ul>
 * 		<li>the tab is changed</li>
 * 		<li>the currently edited file is changed</li>
 * 		<li>when a new file is added to the model.</li>
 * 		<li>when a file is removed from the model</li>  
 * </ul>
 * @author ivan
 *
 */
public interface MultipleDocumentListener {

	/**
	 * This is the method called when the currently active
	 * document is edited (i.e. when it has changed). In that
	 * case only the currentModel parameter is given where the 
	 * previous model is <code>null</code>.<p>
	 * The method is also called when the focus is set to 
	 * another tab, in that case the references to current
	 * and previous model are given to this method.<p>
	 * It can not happen that both parameters are <code>null</code>
	 * 
	 * @param previousModel
	 * 		the previously edited model ("old one") of the 
	 * 		single document.
	 * 
	 * @param currentModel
	 * 		the currently edited model ("new one") of the 
	 * 		single document 
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel,
			SingleDocumentModel currentModel);
	
	/**
	 * This is the method called when the new document is added
	 * to the {@link MultipleDocumentModel}. 
	 * 
	 * @param model
	 * 		the model where new {@link SingleDocumentModel} is
	 * 		added.
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * This is the method called when the document is removed
	 * from the {@link MultipleDocumentModel}.
	 * 
	 * @param model
	 * 		the model from where the document is removed.
	 */
	void documentRemoved(SingleDocumentModel model);
}
