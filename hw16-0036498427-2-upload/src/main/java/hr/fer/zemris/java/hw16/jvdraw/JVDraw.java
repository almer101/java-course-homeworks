package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import hr.fer.zemris.java.hw16.jvdraw.geometricObjects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometricObjects.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.geometricObjects.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometricObjects.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw16.jvdraw.util.DrawingModelUtil;

/**
 * This is the program which enables the user to draw shapes on the canvas and 
 * for those shapes to be saved or exported to the png, gif or jpg image format.
 * 
 * @author ivan
 *
 */
public class JVDraw extends JFrame {

	/**A default serial version UID*/
	private static final long serialVersionUID = 1L;
	
	/**The current state*/
	private static Tool toolState;
	
	/**The model of the drawings which contains all the important data.*/
	private DrawingModel model;
	
	/**The modified flag used when closing the program and when saving files.*/
	private boolean modified = false;
	
	/**The path to the current shown picture if such exists.*/
	private Path currentFile = null;

	/**
	 * The constructor which initializes the GUI and registers an anonymous listener
	 * to the model which tracks if the model has been edited or not.
	 */
	public JVDraw() {
		setTitle("JVDraw");
		setLocation(400, 200);
		setSize(1000, 600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		model = new MyDrawingModel();
		model.addDrawingModelListener(new DrawingModelListener() {

			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				modified = true;
			}

			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
				modified = true;
			}

			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				modified = true;
			}
		});
		initGUI();
	}

	/**
	 * This method initializes the GUI of the {@link JVDraw} frame.
	 */
	private void initGUI() {
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		addMenuItems(file);
		menubar.add(file);
		setJMenuBar(menubar);
		// add toolbar

		JColorArea fgColorProvider = new JColorArea(Color.BLUE);
		JColorArea bgColorProvider = new JColorArea(Color.RED);

		JToolBar toolbar = new JToolBar();
		fgColorProvider.setMaximumSize(new Dimension(20, 20));
		bgColorProvider.setMaximumSize(new Dimension(20, 20));
		toolbar.add(fgColorProvider);
		toolbar.add(bgColorProvider);

		toolState = new LineTool(fgColorProvider, model);
		JDrawingCanvas canvas = new JDrawingCanvas(toolState, model);

		JToggleButton lineButton = new JToggleButton("Line");
		JToggleButton circleButton = new JToggleButton("Circle");
		JToggleButton filledCircleButton = new JToggleButton("Filled Circle");
		lineButton.setSelected(true);

		toolbar.add(lineButton);
		toolbar.add(circleButton);
		toolbar.add(filledCircleButton);
		ButtonGroup toolsGroup = new ButtonGroup();
		toolsGroup.add(lineButton);
		toolsGroup.add(circleButton);
		toolsGroup.add(filledCircleButton);

		lineButton.addActionListener(e -> {
			toolState = new LineTool(fgColorProvider, model);
			canvas.setToolState(toolState);
		});
		circleButton.addActionListener(e -> {
			toolState = new CircleTool(fgColorProvider, model);
			canvas.setToolState(toolState);
		});
		filledCircleButton.addActionListener(e -> {
			toolState = new FilledCircleTool(fgColorProvider, bgColorProvider, model);
			canvas.setToolState(toolState);
		});

		c.add(toolbar, BorderLayout.PAGE_START);

		c.add(canvas, BorderLayout.CENTER);
		ColorsLabel label = new ColorsLabel(fgColorProvider, bgColorProvider);
		c.add(label, BorderLayout.PAGE_END);

		JList<GeometricalObject> list = new JList<>();
		DrawingObjectListModel listModel = new DrawingObjectListModel(model);
		list.setModel(listModel);
		c.add(list, BorderLayout.LINE_END);

		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				offerEditOption(e);
			}
		});

		list.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				processKeyPressed(e, list);
			}

		});
	}

	/**
	 * This is the method called when the key is pressed when the list has focus.
	 * 
	 * @param e
	 * 		the key event.
	 * @param list
	 * 		the list where the key was pressed.
	 */
	private void processKeyPressed(KeyEvent e, JList<GeometricalObject> list) {
		char pressed = e.getKeyChar();
		int index = list.getSelectedIndex();
		if (pressed == '\b') {
			model.remove(list.getSelectedValue());
		} else if (pressed == '+') {
			model.changeOrder(list.getSelectedValue(), 1);
			if (index + 1 < model.getSize()) {
				list.setSelectedIndex(index + 1);
			} else {
				list.setSelectedIndex(index);
			}
		} else if (pressed == '-') {
			model.changeOrder(list.getSelectedValue(), -1);
			if (index - 1 >= 0) {
				list.setSelectedIndex(index - 1);
			} else {
				list.setSelectedIndex(index);
			}
		}
	}
	
	/**
	 * This method adds menu items to the file menu.
	 * 
	 * @param file
	 * 		the file menu where to add the menu items
	 */
	private void addMenuItems(JMenu file) {
		JMenuItem open = new JMenuItem(openAction);
		file.add(open);
		JMenuItem save = new JMenuItem(saveAction);
		file.add(save);
		JMenuItem saveAs = new JMenuItem(saveAsAction);
		file.add(saveAs);
		JMenuItem export = new JMenuItem(exportAction);
		file.add(export);
		JMenuItem exit = new JMenuItem(exitAction);
		file.add(exit);
	}

	/**
	 * The action which opens the .jvd file from the disk and loads the image. 
	 * 
	 */
	Action openAction = new AbstractAction("Open") {

		/**A default serial version UID*/
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Open document");
			if (jfc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			Path filePath = jfc.getSelectedFile().toPath();
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(JVDraw.this, "Not an existing file.",
						"The file does not exist.", JOptionPane.ERROR_MESSAGE);
			}
			model = DrawingModelUtil.loadFromFile(filePath, model);
			JVDraw.this.currentFile = filePath;
			modified = false;
		}
	};

	/**
	 * The action which saves the current drawing, if the file does not already exist
	 * the save as action is performed.
	 */
	Action saveAction = new AbstractAction("Save") {

		/**A default serial version UID*/
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (currentFile != null) {
				// the file already exists
				DrawingModelUtil.makeFileForModel(currentFile, model);
				modified = false;
			} else {
				saveAsAction.actionPerformed(e);
			}
		}
	};

	/**
	 * The action which saves the current drawing at the desired location on the
	 * disk.
	 */
	Action saveAsAction = new AbstractAction("Save As") {

		/**A default serial version UID*/
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save Document");
			if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(JVDraw.this, "Nothing was saved!", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			Path path = jfc.getSelectedFile().toPath();
			DrawingModelUtil.makeFileForModel(path, model);
			currentFile = path;
			modified = false;
		}
	};

	/**
	 * The action which exits the program but first checks if the current drawing
	 * is modified and if not the program is closed, but if yes the save option is 
	 * provided to the user.
	 */
	Action exitAction = new AbstractAction("Exit") {
		
		/**A default serial version UID*/
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (modified) {
				if (JOptionPane.showConfirmDialog(JVDraw.this, "Do you wish to save?",
						"Save before exit",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					saveAction.actionPerformed(e);
				}
			}
			JVDraw.this.dispose();

		}
	};

	/**
	 * The action which exports the current drawing to the file of desired format,
	 * png, jpg of gif.
	 */
	Action exportAction = new AbstractAction("Export") {

		/**A default serial version UID*/
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (model.getSize() == 0) {
				JOptionPane.showMessageDialog(JVDraw.this, "Cannot export an empty image", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JPanel extensions = new JPanel();
			JComboBox<String> comboBox = new JComboBox<>();
			comboBox.addItem("jpg");
			comboBox.addItem("png");
			comboBox.addItem("gif");
			extensions.add(comboBox);
			String extension = null;
			if (JOptionPane.showConfirmDialog(JVDraw.this, comboBox, "Choose extension",
					JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
				extension = (String) comboBox.getSelectedItem();
			} else {
				return;
			}
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save Document");
			if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(JVDraw.this, "Nothing was saved!", "Warning",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
			int size = model.getSize();
			for (int i = 0; i < size; i++) {
				model.getObject(i).accept(bbcalc);
			}
			Rectangle box = bbcalc.getBoundingBox();
			System.out.println(box);
			BufferedImage image = new BufferedImage(box.width, box.height,
					BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g = image.createGraphics();
			g.translate(-box.x, -box.y);
			GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
			g.setColor(Color.WHITE);
			g.setBackground(Color.WHITE);
			g.fillRect(box.x, box.y, image.getWidth(), image.getHeight());
			for (int i = 0; i < size; i++) {
				model.getObject(i).accept(painter);
			}
			g.dispose();

			String fileName = jfc.getSelectedFile().toPath().toAbsolutePath().normalize()
					.toString() + "." + extension;
			Path filePath = Paths.get(fileName);
			try {
				Files.createFile(filePath);
				ImageIO.write(image, extension, filePath.toFile());
			} catch (IOException e2) {
				JOptionPane.showMessageDialog(JVDraw.this, "Export not successful", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(JVDraw.this, "Export successful", "Success",
					JOptionPane.INFORMATION_MESSAGE);
		}
	};

	/**
	 * This method shows the edit dialog to the user. The edit dialog depends on the
	 * component which was clicked.
	 * 
	 * @param e
	 *            the mouse event used to find out which component was double
	 *            clicked.
	 */
	private void offerEditOption(MouseEvent e) {
		@SuppressWarnings("unchecked")
		JList<GeometricalObject> theList = (JList<GeometricalObject>) e.getSource();
		if (e.getClickCount() == 2) {
			int index = theList.locationToIndex(e.getPoint());
			if (index >= 0) {
				showEditDialog(theList, index);
			}
		}
	}

	/**
	 * This message shows the edit dialog to the user. The user is then able to edit
	 * the selected element of the list.
	 * 
	 * @param theList
	 *            the list of elements.
	 * @param index
	 *            the index of the double clicked element.
	 */
	private void showEditDialog(JList<GeometricalObject> theList, int index) {
		while (true) {
			GeometricalObject o = theList.getModel().getElementAt(index);
			GeometricalObjectEditor editor = o.createGeometricalObjectEditor();
			if (JOptionPane.showConfirmDialog(JVDraw.this, editor, "Editor",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
				try {
					editor.checkEditing();
					editor.acceptEditing();
					break;
				} catch (Exception e2) {
					System.out.println("The input was invalid!");
					continue;
				}
			}
			break;
		}
	}

	/**
	 * This is the method called when the program is run.
	 * 
	 * @param args
	 *            the command line arguments.
	 */
	public static void main(String[] args) {
		JVDraw draw = new JVDraw();
		draw.setVisible(true);
	}
}
