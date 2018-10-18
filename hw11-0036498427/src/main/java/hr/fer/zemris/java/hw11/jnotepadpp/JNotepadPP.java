package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJLabel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.models.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;
import javafx.stage.FileChooser;

/**
 * This is the program which starts the notepad++ with
 * some awsome features. The notepad supports the i18n for
 * 3 languages including english, german and croatian.
 * 
 * @author ivan
 *
 */
public class JNotepadPP extends JFrame {
	
	//=======================================Properties=============================================
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The model which manages the files.
	 */
	private MultipleDocumentModel model;

	/**
	 * This is the listener of the selection in
	 * the editor.
	 */
	private SelectionListener selectionListener;
	
	/**
	 * The panel with the information about the file length
	 * and the status of the caret.
	 */
	private JPanel statusPanel;
	
	/**
	 * The listener which is registered or unregistered from and 
	 * current file each time the file changes. The caret listener
	 * calls the method which updates the status bar.
	 */
	private CaretListener caretListener;
	
	/**
	 * This is the listener which tracks the modified
	 * status of the current document.
	 */
	private SingleDocumentListener modifiedListener;
	
	/**
	 * The localization provider which translates the keys.
	 */
	private FormLocalizationProvider flp = 
			new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	
	/**
	 * This is the collator used for comparing the lines in
	 * sorting process.
	 */
	private Collator collator = 
			Collator.getInstance(Locale.forLanguageTag(flp.getString("collator")));;
	
	//========================================Constants=============================================
	
	/**The YES option of the option pane.*/
	private static final int YES = 0;
	
	/**The NO option of the option pane.*/
	private static final int NO = 1;
	
	/**The CANCEL option of the option pane. */
	private static final int CANCEL = 2;
	
	/**The lower case flag.*/
	private static final int LOWER = 0;
	
	/**The upper case flag.*/
	private static final int UPPER = 1;
	
	/**The invert case flag */
	private static final int INVERT = 2;
	
	/**The ascending sort flag*/
	private static final int ASCENDING = 0;
	
	/**The descending sort flag.*/
	private static final int DESCENDING = 1;
	
	/**The flag indicating whether to use only unique lines or not.*/
	private static final boolean UNIQUE = true;
	
	/**This is the indicator for the status bar to show blanks instead of numbers*/
	private static final int BLANK = -1;
	
	//=======================================Constructor============================================
			
	/**
	 * The constructor which initializes the window.
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocation(200, 200);
		setSize(800, 500);
		
		addListeners();
		initGUI();
	}
	
	//=====================================Method implementations===================================
	
	/**
	 * This method sets the window title to the path of the
	 * currently edited document.
	 */
	private void setFrameTitle() {
		if(model.getCurrentDocument() == null) {
			setTitle("JNotepad++");
		} else if(model.getCurrentDocument().getFilePath() == null) {
			setTitle(flp.getString("newdocument") + " - JNotepad++");
		} else {
			setTitle(model.getCurrentDocument().getFilePath()
					.toAbsolutePath().normalize().toString() + " - JNotepad++");
		}
	}
	
	/**
	 * This method adds all necessary listeners.
	 */
	private void addListeners() {
		caretListener = new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				updateStatusBar((JTextArea)e.getSource());
			}
		};
		selectionListener = new SelectionListener() {
			@Override
			public void selectionChanged(int size) {
				setEnableActions(size != 0, uppercaseAction, lowerCaseAction, invertCaseAction);
			}
		};
		modifiedListener = new SingleDocumentListener() {
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				setEnableActions(JNotepadPP.this.model.getCurrentDocument().isModified(), 
						saveDocumentAction);
			}
			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
			}
		};
		flp.addLocalizationListener(() -> {
			setFrameTitle();
			collator = Collator.getInstance(
					Locale.forLanguageTag(flp.getString("collator")));
		});
		model = new DefaultMultipleDocumentModel();
		model.addMultipleDocumentListener(new MultipleDocumentListener() {
			
			@Override
			public void documentRemoved(SingleDocumentModel model) {
				updateFrameStatusBarAndActions();
			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {
				setFrameTitle();
			}
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, 
					SingleDocumentModel currentModel) {
				documentChanged(currentModel, previousModel);
			}
		});
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				checkForUnsavedFiles();
			}
		});
	}
	
	/**
	 * This method sets the enabled flag of some actions 
	 * to the specified value
	 * 
	 * @param b
	 * 		the enabled flag new value.
	 * 
	 * @param actions
	 * 		action which to set to the specified flag.
	 */
	private void setEnableActions(boolean b, Action ... actions) {
		for(Action a : actions) {
			a.setEnabled(b);
		}
	}
	
	/**
	 * This method checks if the number of documents is
	 * zero. And if it is the status bar is updated to show
	 * nothing.
	 * 
	 */
	private void checkStatusBar() {
		if(model.getNumberOfDocuments() == 0) {
			updateLabels(BLANK, BLANK, BLANK, BLANK);
		}
	}
	
	/**
	 * This method updates the frame, status bar and actions 
	 * of the notepad.
	 */
	private void updateFrameStatusBarAndActions() {
		setFrameTitle();
		checkStatusBar();
		setEnableActions(model.getNumberOfDocuments() != 0, 
				saveAsDocumentAction, saveDocumentAction, closeDocumentAction, 
				copyAction, pasteAction, cutAction);
		if(model.getNumberOfDocuments() == 0) return;
		setEnableActions(model.getCurrentDocument().isModified(), 
				saveDocumentAction);
	}

	
	/**
	 * This is the method called when the document changes.
	 * It unregisters the caret listener from the previous model
	 * and registers it to the current. It also changes the title 
	 * of the frame and updates the status bar.
	 * 
	 * @param currentModel
	 * 		the currently active model.
	 * 
	 * @param previousModel
	 * 		the previously active model.
	 */
	private void documentChanged(SingleDocumentModel currentModel, SingleDocumentModel previousModel) {
		setFrameTitle();
		updateStatusBar(currentModel.getTextComponent());
		
		if(previousModel != null) {
			previousModel.removeSingleDocumentListener(modifiedListener);
		}
		currentModel.addSingleDocumentListener(modifiedListener);
		if(previousModel != null) {
			previousModel.getTextComponent().removeCaretListener(caretListener);
		}
		setEnableActions(JNotepadPP.this.model.getNumberOfDocuments() != 0, 
				closeDocumentAction, saveAsDocumentAction ,copyAction, pasteAction, cutAction);
		setEnableActions(JNotepadPP.this.model.getCurrentDocument().isModified(), 
				saveDocumentAction);
		currentModel.getTextComponent().addCaretListener(caretListener);	
	}
	
	/**
	 * This method goes through all the documents in
	 * the model and asks the user if he wishes to save
	 * the modified ones.
	 * 
	 */
	private void checkForUnsavedFiles() {
		Iterator<SingleDocumentModel> it = model.iterator();
		
		while(it.hasNext()) {
			SingleDocumentModel doc = it.next();
			if(doc.isModified()) {
				Path filePath = doc.getFilePath();
				int input = JOptionPane.showConfirmDialog(this, 
						"Do you want to save file : " 
							+ (filePath == null ? "New document" : 
								filePath.getFileName().toString()) + "?"
				);
				if(input == CANCEL) return;
				if(input == NO) continue;
				saveDocument(doc);
			}
		}
		dispose();
		System.exit(0);
	}

	/**
	 * This method saves the specified document. If the 
	 * document does not have the specified path the 
	 * {@link FileChooser} is presented. Otherwise the file
	 * is saved to the path of the file.
	 * 
	 * @param doc
	 * 		the document to save.
	 */
	private void saveDocument(SingleDocumentModel doc) {
		if(doc.getFilePath() != null) {
			model.saveDocument(doc, null);
		} else {
			saveFromFileChooser(doc);
		}
	}

	private void initGUI() {
		setLayout(new BorderLayout());
		
		createActions();
		createMenus();
		createToolbars();
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add((JTabbedPane)model, BorderLayout.CENTER);
		statusPanel = addStatusBar(panel);
		checkStatusBar();
		
		getContentPane().add(panel);
	}
	
	/**
	 * This method creates the actions which are
	 * executed from the menus.
	 * 
	 */
	private void createActions() {
		newDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift N"));
		
		saveDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		
		saveAsDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt S"));
		
		openDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		
		closeDocumentAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		
		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		
		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		
		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt V"));
		
		staticticalInfoAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		
		uppercaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));

		lowerCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		
		invertCaseAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control K"));
		
		sortAscending.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt A"));
		
		sortDescending.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt D"));
		
		uniqueAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt U"));
	}

	/**
	 * This is the method which initializes the menu bar
	 * and its menus.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();
		
		LJMenu fileMenu = new LJMenu("file", flp);
		menuBar.add(fileMenu);
		
		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(closeDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));
		
		LJMenu editMenu = new LJMenu("edit", flp);
		menuBar.add(editMenu);
		
		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(pasteAction));
		editMenu.addSeparator();
		editMenu.add(new JMenuItem(staticticalInfoAction));
		
		LJMenu languagesMenu = new LJMenu("languages", flp);
		menuBar.add(languagesMenu);
		
		JMenuItem en = new JMenuItem("English");
		en.addActionListener(e -> {
			LocalizationProvider.getInstance().setLanguage("en");
		});
		JMenuItem de = new JMenuItem("Deutsch");
		de.addActionListener(e -> {
			LocalizationProvider.getInstance().setLanguage("de");;
		});
		JMenuItem hr = new JMenuItem("Hrvatski");
		hr.addActionListener(e -> {
			LocalizationProvider.getInstance().setLanguage("hr");
		});
		languagesMenu.add(en);
		languagesMenu.add(de);
		languagesMenu.add(hr);
		
		LJMenu toolsMenu = new LJMenu("tools", flp);
		menuBar.add(toolsMenu);
		
		LJMenu changeCase = new LJMenu("changecase", flp);
		toolsMenu.add(changeCase);
		changeCase.add(new JMenuItem(lowerCaseAction));
		changeCase.add(new JMenuItem(uppercaseAction));
		changeCase.add(new JMenuItem(invertCaseAction));
		
		LJMenu sortMenu = new LJMenu("sort", flp);
		toolsMenu.add(sortMenu);
		sortMenu.add(new JMenuItem(sortAscending));
		sortMenu.add(new JMenuItem(sortDescending));
		
		toolsMenu.add(new JMenuItem(uniqueAction));
		this.setJMenuBar(menuBar);
		
	}

	private void createToolbars() {
		JToolBar toolBar = new JToolBar("Tools");
		toolBar.setFloatable(true);
		
		toolBar.add(new JButton(newDocumentAction));
		toolBar.add(new JButton(openDocumentAction));
		toolBar.add(new JButton(saveDocumentAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(copyAction));
		toolBar.add(new JButton(cutAction));
		toolBar.add(new JButton(pasteAction));
		toolBar.add(new JButton(staticticalInfoAction));
		toolBar.addSeparator();
		toolBar.add(new JButton(closeDocumentAction));
		
		getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}
	
	//=========================================Helper methods==============================================
	
	/**
	 * This method adds the status bar to the specified
	 * panel to the end of the page.
	 * 
	 * @return 
	 * 		the panel with the information about the 
	 * 		status of the caret.
	 */
	private JPanel addStatusBar(JPanel panel) {
		JPanel p = new JPanel(new GridLayout(1, 3));
		LJLabel lengthLabel = new LJLabel(flp, "length");
		LJLabel lnColsSelLabel = new LJLabel(flp, new String[] {"ln", "col", "sel"});
		JLabel time = new JLabel("");
		time.setHorizontalAlignment(SwingConstants.RIGHT);
		new Clock(time, this);
		p.add(lengthLabel);
		p.add(lnColsSelLabel);
		p.add(time);
		p.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(150, 150, 150)));
		
		panel.add(p, BorderLayout.PAGE_END);
		return p;
	}

	/**
	 * This is the method which performs the changes on the case 
	 * depending on the specified flag. The actual case changing
	 * is delegated to other method.
	 * 
	 * @param flag
	 * 		the flag specifying whether the upper, lower or invert
	 * 		case action should be performed.
	 */
	private void performChangeCase(int flag) {
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		if(len == 0) return;
		int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		try {
			String newText = changeCase(flag, editor.getDocument().getText(offset, len));
			editor.getDocument().remove(offset, len);
			editor.getDocument().insertString(offset, newText, null);
		} catch (BadLocationException e2) {
			e2.printStackTrace();
		}
	}
	
	/**
	 * This method changes the case of the specified text
	 * depending on the specified flag.
	 * 
	 * @param flag
	 * 		the flag indicating whether the lower, upper
	 * 		or invert case will be performed.
	 * 
	 * @param text
	 * 		the text to change.
	 * 	
	 * @return
	 * 		the changed string.
	 */
	private String changeCase(int flag, String text) {
		if(flag == LOWER) return text.toLowerCase();
		if(flag == UPPER) return text.toUpperCase();
		
		char[] array = text.toCharArray();
		for(int i = 0; i < array.length; i++) {
			if(Character.isLowerCase(array[i])) {
				array[i] = Character.toUpperCase(array[i]);
			} else if (Character.isUpperCase(array[i])){
				array[i] = Character.toLowerCase(array[i]);
			}
		}
		return new String(array);
	}
	
	/**
	 * This method sorts the selected lines of text in
	 * the currently active editor.
	 * 
	 * @param flag
	 * 		the flag indicating whether the ascending
	 * 		or descending sort should be done.
	 */
	private void sort(int flag) {
		if(model.getNumberOfDocuments() == 0) return;
		JTextArea editor = model.getCurrentDocument().getTextComponent();
		try {
			int startingLine = editor.getLineOfOffset(
					Math.min(editor.getCaret().getDot(), editor.getCaret().getMark()));
			int endingLine = editor.getLineOfOffset(
					Math.max(editor.getCaret().getDot(), editor.getCaret().getMark()));
			sortLines(startingLine, endingLine, editor, flag);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method sorts the lines from the starting line to
	 * the ending line. This method also puts the sorted lines
	 * back in the document (editor).
	 * 
	 * @param startingLine
	 * 		the starting line.
	 * 
	 * @param endingLine
	 * 		the ending line.
	 * 
	 * @param editor
	 * 		the editor.
	 * 
	 * @param flag
	 * 		the flag indicating whether the ascending or descending 
	 * 		sort should be done.
	 */
	private void sortLines(int startingLine, int endingLine, JTextArea editor, int flag) {
		Document doc = editor.getDocument();
		List<String> lines = getLines(startingLine, endingLine, editor, doc, !UNIQUE);
		
		lines.sort(collator);
		if(flag == DESCENDING) Collections.reverse(lines);
		addToDocument(startingLine, endingLine, editor, doc, lines);
	}
	
	/**
	 * This method adds the specified lines to the specified document.
	 * 
	 * @param startingLine
	 * 		the first selected line
	 * @param endingLine
	 * 		the last selected line.
	 * @param editor
	 * 		the editor where the text is.
	 * @param doc
	 * 		the document(model) of the editor.
	 * @param lines
	 * 		the lines to add to document.
	 */
	private void addToDocument(int startingLine, int endingLine, JTextArea editor, 
			Document doc, List<String> lines) {
		try {
			int offset = editor.getLineStartOffset(startingLine);
			int end = editor.getLineEndOffset(endingLine);
			doc.remove(offset, end - offset);
			for(String s : lines) {
				doc.insertString(offset, s, null);
				offset += s.length();
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * This method returns the list of lines from the selection.
	 * 
	 * @param startingLine
	 * 		the first selected line.		
	 * @param endingLine
	 * 		the last selected line.		
	 * @param editor
	 * 		the editor of the text.		
	 * @param doc
	 * 		document of the editor.
	 * @param unique
	 * 		if <code>true</code> only unique lines are
	 * 		added (there will be no duplicates), if 
	 * 		<code>false</code> all lines will be added.
	 * @return
	 * 		the list of lines.
	 */
	private List<String> getLines(int startingLine, int endingLine, 
			JTextArea editor, Document doc, boolean unique) {
		List<String> lines = new ArrayList<>();
		for(int i = startingLine; i <= endingLine; i++) {
			try {
				int start = editor.getLineStartOffset(i);
				int end = editor.getLineEndOffset(i);
				String line = doc.getText(start, end - start);
				
				if(unique && lines.contains(line)) continue;
				lines.add(doc.getText(start, end - start));
				
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
		return lines;
	}

	/**
	 * This method removes the duplicate lines from the selected lines
	 * in the specified editor.
	 * 
	 * @param startingLine
	 * 		the first selected line.
	 * 
	 * @param endingLine
	 * 		the last selected line.		
	 * 
	 * @param editor
	 * 		the editor where the text is.
	 */
	private void removeDuplicates(int startingLine, int endingLine, JTextArea editor) {
		Document doc = editor.getDocument();
		List<String> lines = getLines(startingLine, endingLine, editor, doc, UNIQUE);
		addToDocument(startingLine, endingLine, editor, doc, new ArrayList<>(lines));
	}
	
	/**
	 * This method updates the status bar with the new data
	 * using information about the caret position.
	 * 
	 * @param editor
	 * 		the observed editor.
	 */
	private void updateStatusBar(JTextArea editor) {
		int pos = editor.getCaret().getDot();
		try {
			int line = editor.getLineOfOffset(pos);
	        int column = pos - editor.getLineStartOffset(line) + 1;
	        line++;
	        int selection = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
	        selectionListener.selectionChanged(selection);
	        updateLabels(line, column, selection, editor.getText().length());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}		
	}

	/**
	 * This method updates the labels which show the length of the
	 * document, and the status of the caret.
	 * 
	 * @param line
	 * 		the line where the caret is located		
	 * 
	 * @param column
	 * 		the column where the caret is located.
	 * 
	 * @param selection
	 * 		the length of the selected part of text. 
	 * 
	 * @param len
	 * 		the length of the document.
	 */
	private void updateLabels(int line, int column, int selection, int len) {
		((LJLabel)statusPanel.getComponent(0)).update(len);
		((LJLabel)statusPanel.getComponent(1)).update(line, column, selection);
	}
	
	/**
	 * This method launches the file chooser and saves the
	 * specified document to the desired location.
	 * 
	 * @param doc
	 * 		a document to save.
	 */
	private void saveFromFileChooser(SingleDocumentModel doc) {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save Document");
		if(jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(
					JNotepadPP.this, 
					"Nothing was saved!", 
					"Warning", 
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		Path path = jfc.getSelectedFile().toPath();
		model.saveDocument(doc, path);
		
	}
	
	//=============================================Actions=================================================
	
	/**
	 * This is the action which creates a new document.
	 */
	private final Action newDocumentAction = new LocalizableAction("newfile", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			model.createNewDocument();
			model.getCurrentDocument()
				.getTextComponent()
				.addCaretListener(new CaretListener() {
					
					@Override
					public void caretUpdate(CaretEvent e) {
						updateStatusBar((JTextArea)e.getSource());
					}
			});
		}
	};
	
	/**
	 * The action which saves the current document.
	 */
	private final Action saveDocumentAction = new LocalizableAction("save", flp) {
		
		private static final long serialVersionUID = 1L;
		{setEnabled(false);}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel doc = model.getCurrentDocument();
			saveDocument(doc);
		}
	};
	
	/**
	 * The action which saves the current document to the
	 * desired location with the file chooser or informs the
	 * user if he tries to save the existing document.
	 */
	private final Action saveAsDocumentAction = new LocalizableAction("saveas", flp) {
		
		private static final long serialVersionUID = 1L;
		{setEnabled(false);}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel doc = model.getCurrentDocument();
			saveFromFileChooser(doc);
			setFrameTitle();
		}
	};
	
	/**
	 * The action which opens a new document.
	 */
	private final Action openDocumentAction = new LocalizableAction("open", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle(flp.getString("opendocument"));
			if(jfc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			Path filePath = jfc.getSelectedFile().toPath();
			if(!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						flp.getString("filenotexistsmessage"), 
						flp.getString("filenotexists"), 
						JOptionPane.ERROR_MESSAGE);
			}
			model.loadDocument(filePath);
		}
	};
	
	/**
	 * The action which closes the current document.
	 */
	private final Action closeDocumentAction = new LocalizableAction("close", flp) {
		
		private static final long serialVersionUID = 1L;
		{setEnabled(false);}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel doc = model.getCurrentDocument();
			if(doc == null) return;
			if(doc.isModified()) {
				int input = JOptionPane.showConfirmDialog(
						JNotepadPP.this, 
						flp.getString("closingsavemessage"), 
						flp.getString("closingsave"), 
						JOptionPane.INFORMATION_MESSAGE);
				if(input == YES) {
					saveDocument(doc);
				}
			}
			model.closeDocument(doc);
		}
	};
	
	/**
	 * The action which exits the application but first
	 * checks if there are some modified files which user
	 * wants to save.
	 */
	private final Action exitAction = new LocalizableAction("exit", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			checkForUnsavedFiles();
		}
	};
	
	/**
	 * The action which copies the selected part of the 
	 * text to the {@link #clipboard}
	 */
	private final Action copyAction = new LocalizableAction("copy", flp) {
		
		private static final long serialVersionUID = 1L;
		{setEnabled(false);}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			model.getCurrentDocument().getTextComponent().copy();
		}
	};
	
	/**
	 * The action which cuts the selected part of the text
	 * to the {@link #clipboard}
	 */
	private final Action cutAction = new LocalizableAction("cut", flp) {
		
		private static final long serialVersionUID = 1L;
		{setEnabled(false);}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			model.getCurrentDocument().getTextComponent().cut();
		}
	};
	
	/**
	 * The action which pastes the content of the {@link #clipboard}.
	 */
	private final Action pasteAction = new LocalizableAction("paste", flp) {
		
		private static final long serialVersionUID = 1L;
		{setEnabled(false);}

		@Override
		public void actionPerformed(ActionEvent e) {
			model.getCurrentDocument().getTextComponent().paste();
		}
	};
	
	/**
	 * The action which calculates the statistical info of the
	 * currently active file and shows the message to the user.
	 */
	private final Action staticticalInfoAction = new LocalizableAction("info", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel doc = model.getCurrentDocument();
			if(doc == null) {
				JOptionPane.showMessageDialog(
						JNotepadPP.this, 
						flp.getString("noactivefilemessage"), 
						flp.getString("noactivefile"), 
						JOptionPane.WARNING_MESSAGE);
				return;
			};
			char[] text = model.getCurrentDocument().getTextComponent().getText().toCharArray();
			int chars = 0;
			int lines = 0;
			int nonBlanks = 0;
			for(int i = 0; i < text.length; i++) {
				chars++;
				if(!Character.isWhitespace(text[i])) nonBlanks++;
				if(text[i] == '\n') lines++;
			}
			lines = chars > 0 ? lines + 1 : lines;
			JOptionPane.showMessageDialog(
					JNotepadPP.this, 
					String.format(flp.getString("infomessageformat"), chars, nonBlanks, lines), 
					flp.getString("infotitle"), 
					JOptionPane.INFORMATION_MESSAGE);
		}
	};
	
	/**
	 * The action which changes the case of the selected text to upper.
	 */
	private final Action uppercaseAction = new LocalizableAction("uppercase", flp) {
		
		private static final long serialVersionUID = 1L;
		{setEnabled(false);}

		@Override
		public void actionPerformed(ActionEvent e) {
			performChangeCase(UPPER);
		}
	};
	
	/**
	 * The action which changes the case of the selected text to upper.
	 */
	private final Action lowerCaseAction = new LocalizableAction("lowercase", flp) {
		
		private static final long serialVersionUID = 1L;
		{setEnabled(false);}

		@Override
		public void actionPerformed(ActionEvent e) {
			performChangeCase(LOWER);
		}
	};
	
	/**
	 * The action which inverts the case of the selected text.
	 */
	private final Action invertCaseAction = new LocalizableAction("invertcase", flp) {
		
		private static final long serialVersionUID = 1L;
		{setEnabled(false);}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			performChangeCase(INVERT);
		}
	};
	
	/**
	 * This action sorts the selected lines of text in ascending order.
	 */
	private final Action sortAscending = new LocalizableAction("ascending", flp) {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			sort(ASCENDING);
		}
	};
	
	/**
	 * This action sorts the selected lines of text in descending order.
	 */
	private final Action sortDescending = new LocalizableAction("descending", flp) {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			sort(DESCENDING);
		}
	}; 
			
	/**
	 * This action deletes all the repeated occurrences of the lines
	 * in the selection and only the first occurrence remains.
	 */
	private final Action uniqueAction = new LocalizableAction("unique", flp) {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(model.getNumberOfDocuments() == 0) return;
			JTextArea editor = model.getCurrentDocument().getTextComponent();
			try {
				int startingLine = editor.getLineOfOffset(
						Math.min(editor.getCaret().getDot(), editor.getCaret().getMark()));
				int endingLine = editor.getLineOfOffset(
						Math.max(editor.getCaret().getDot(), editor.getCaret().getMark()));
				removeDuplicates(startingLine, endingLine, editor);
			} catch (BadLocationException e2) {
				e2.printStackTrace();
			}
		}
	}; 
	
	//============================================Main method=============================================
	
	/**
	 * This is the method called when the program
	 * is run.
	 * 
	 * @param args
	 * 		command line arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}
}
