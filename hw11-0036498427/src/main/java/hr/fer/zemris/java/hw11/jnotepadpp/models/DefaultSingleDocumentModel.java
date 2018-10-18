package hr.fer.zemris.java.hw11.jnotepadpp.models;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * This is the default implementation of the {@link SingleDocumentModel}.
 * 
 * @author ivan
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	//=================================Properties=======================================
	
	/**
	 * The path to the opened file.
	 */
	private Path openedFilePath;
	
	/**
	 * The modified status flag of the document.
	 */
	private boolean modified;
	
	/**
	 * The editor (i.e. the swing component) where the text is displayed.
	 */
	private JTextArea editor;
	
	/**
	 * The collection of listeners of this model.
	 */
	private List<SingleDocumentListener> listeners;
	
	//================================Constructor=======================================
	
	/**
	 * The constructor which gets the path to the file and the 
	 * 
	 * @param openedFilePath
	 * @param textContent
	 */
	public DefaultSingleDocumentModel(Path openedFilePath, String textContent) {
		super();
		this.openedFilePath = openedFilePath;
		listeners = new ArrayList<>();
		editor = new JTextArea(textContent);
		
		editor.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}
		});
	}

	//============================Method implementations=================================

	@Override
	public JTextArea getTextComponent() {
		return editor;
	}

	@Override
	public Path getFilePath() {
		return openedFilePath;
	}

	@Override
	public void setFilePath(Path path) {
		openedFilePath = Objects.requireNonNull(path);
		listeners.forEach(l -> l.documentFilePathUpdated(this));
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		if(modified == this.modified) return;
		this.modified = modified;
		listeners.forEach(l -> l.documentModifyStatusUpdated(this));
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}

}
