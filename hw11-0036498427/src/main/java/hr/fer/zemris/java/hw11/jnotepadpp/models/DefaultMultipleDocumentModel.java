package hr.fer.zemris.java.hw11.jnotepadpp.models;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * This is the implementation of the {@link MultipleDocumentModel} which 
 * inherits from {@link JTabbedPane} and holds the collection of the 
 * {@link SingleDocumentModel}s. The class also supports the listeners
 * management.
 * 
 * @author ivan
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	//===================================Properties===========================================
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The collection of the documents in the model.
	 */
	private List<SingleDocumentModel> documents = new ArrayList<>();
	
	/**
	 * The current document user is working on.
	 */
	private SingleDocumentModel currentDocument = null;
	
	/**
	 * The collection of listeners.
	 */
	private List<MultipleDocumentListener> listeners = new ArrayList<>();
	
	/**
	 * The icon showed when the file is not modified.
	 */
	private ImageIcon notModifiedIcon;
	
	/**
	 * The icon showed when the file is modified.
	 */
	private ImageIcon modifiedIcon;
	
	/**
	 * The localization provider which translates the 
	 * requested key.
	 */
	private LocalizationProvider lp = LocalizationProvider.getInstance();
	
	/**
	 * This is the listener set to the the current 
	 * {@link SingleDocumentModel} and updates the gui 
	 * dynamically.
	 */
	private SingleDocumentListener changeListener;
	
	//======================================Constructor========================================
	
	/**
	 * This constructor initializes the values of the {@link #modifiedIcon} 
	 * and {@link #notModifiedIcon}, and also initializes the 
	 * behavior of the listener. 
	 */
	public DefaultMultipleDocumentModel() {
		this.notModifiedIcon = getImage("icons/blankDisk.png");
		this.modifiedIcon = getImage("icons/blueDisk.png");
		addChangeListener(e -> {
			changeCurrentDocument(getSelectedIndex() == -1 ? 
					null : documents.get(getSelectedIndex()));
		});
		this.changeListener = new SingleDocumentListener() {
			
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				if(getNumberOfDocuments() == 0) return;
				if(model.isModified()) {
					setIconAt(getSelectedIndex(), modifiedIcon);
				} else {
					setIconAt(getSelectedIndex(), notModifiedIcon);
				}
			}
			
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				setToolTipTextAt(getSelectedIndex(),
						model.getFilePath().toAbsolutePath().normalize().toString());
				setTitleAt(getSelectedIndex(), 
						model.getFilePath().getFileName().toString());
				
			}
		};
	}
	
	/**
	 * This method returns the {@link ImageIcon} object of the
	 * image which is located in the src/main/resources and in
	 * the same package as this class.
	 * 
	 * @param pathString
	 * 		the string of the path to the image.	
	 * 
	 * @return
	 * 		the {@link ImageIcon} object of the image
	 * 		with the specified path.
	 */
	private ImageIcon getImage(String pathString) {
		InputStream is = this.getClass().getResourceAsStream(pathString);
		if(is == null) {
			throw new IllegalArgumentException("No resources under the"
					+ "specified name were found.");
		}
		try {
			byte[] bytes = is.readAllBytes();
			is.close();
			return new ImageIcon(bytes);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
	}

	//==================================Method implementations====================================

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel newDoc = new DefaultSingleDocumentModel(null, null);
		addDocument(newDoc);
		return newDoc;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path);
		String text = null;
		
		int index = indexOfPath(path);
		if(index != -1) {
			setSelectedIndex(index);
			return currentDocument;
		}
		if(Files.exists(path)) {
			try {
				byte[] bytes = Files.readAllBytes(path);
				text = new String(bytes, StandardCharsets.UTF_8);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(
						this,
						lp.getString("loaderrormessage"), 
						lp.getString("loaderror"), 
						JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
		SingleDocumentModel newCurrent = new DefaultSingleDocumentModel(path, text);
		addDocument(newCurrent);
		return newCurrent;
	}
	
	/**
	 * This method returns the index of the tab with the 
	 * specified file path if it is already active in the model 
	 * of the notepad. Returns <code>-1</code> if the path 
	 * is not present in the model
	 * 
	 * @param filePath
	 * 		the path for which to check if it is in the model.
	 * 		
	 * @return
	 * 		the index of the tab with the file path.
	 */
	private int indexOfPath(Path filePath) {
		int len = getNumberOfDocuments();
		for(int i = 0; i < len; i++) {
			Path p = documents.get(i).getFilePath();
			if(p == null) continue;
			if(p.equals(filePath)) return i;
		}
		return -1;
	}

	/**
	 * This method adds the specified document to the
	 * collection of documents and adds the proper tab for it.
	 * The current document is set to the newly created tab.
	 * The method also notifies all the interested listeners.
	 * 
	 * @param doc
	 * 		the document to add to the collection of documents.
	 */
	private void addDocument(SingleDocumentModel doc) {
		int index = documents.size();
		documents.add(doc);
		String title = doc.getFilePath() == null ? 
				lp.getString("newdocument") : 
				doc.getFilePath().getFileName().toString();
		JPanel panel = new JPanel(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(doc.getTextComponent());
		panel.add(scrollPane, BorderLayout.CENTER);
		
		Path filePath = doc.getFilePath();
		if(filePath == null) {
			addTab(title, 
					modifiedIcon, 
					panel, 
					lp.getString("filenotsaved"));
			doc.setModified(true);
			changeCurrentDocument(doc);
		} else {
			addTab(title, 
					notModifiedIcon, 
					panel, 
					doc.getFilePath().toAbsolutePath().normalize().toString());
			changeCurrentDocument(doc);
			doc.setModified(false);
		}
		
		setSelectedIndex(index);
		listeners.forEach(l -> l.documentAdded(doc));
	}

	/**
	 * This method changes the current document to the 
	 * specified one and informs the listeners that the change happened.
	 * 
	 * @param newCurrent
	 * 		the new current document.
	 */
	private void changeCurrentDocument(SingleDocumentModel newCurrent) {	
		if(newCurrent == null) {
			currentDocument = null;
			return;
		}
		if(currentDocument == null) {
			currentDocument = newCurrent;	
			listeners.forEach(l -> l.currentDocumentChanged(null, newCurrent));
		} else {
			SingleDocumentModel previous = currentDocument;
			previous.removeSingleDocumentListener(changeListener);
			currentDocument = newCurrent;
			listeners.forEach(l -> l.currentDocumentChanged(previous, newCurrent));
		}	
		currentDocument.addSingleDocumentListener(changeListener);
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if(newPath == null) {
			newPath = model.getFilePath();
		} else if(Files.exists(newPath)) {
			JOptionPane.showMessageDialog(
					this,
					lp.getString("existingfilemessage"), 
					lp.getString("existingfile"), 
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		byte[] bytes = model.getTextComponent()
							.getText().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(newPath, bytes);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(
					this,
					lp.getString("saveerrormessage"),
					lp.getString("saveerror"),
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(
				this,
				lp.getString("savesuccessmessage"), 
				lp.getString("savesuccess"), 
				JOptionPane.INFORMATION_MESSAGE);
		model.setModified(false);
		if(model.getFilePath() == null) model.setFilePath(newPath);
		changeListener.documentFilePathUpdated(model);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		documents.remove(model);
		remove(getSelectedIndex());
		if(documents.size() == 0) {
			currentDocument = null;
		}
		listeners.forEach(l -> l.documentRemoved(model));
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}
}
