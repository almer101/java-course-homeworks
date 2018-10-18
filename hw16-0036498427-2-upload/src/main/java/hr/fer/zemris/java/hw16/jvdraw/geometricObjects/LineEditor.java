package hr.fer.zemris.java.hw16.jvdraw.geometricObjects;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 * This is the editor for the line. It enables the editing of the starting and
 * ending coordinates and the line color.
 * 
 * @author ivan
 *
 */
public class LineEditor extends GeometricalObjectEditor {

	/**A default serial version UID*/
	private static final long serialVersionUID = 1L;

	/** The line which is to be edited. */
	private Line line;

	/** The pending color of the editing. */
	private Color pendingColor;

	/** The x coordinate of the start of the line. */
	int x1;

	/** The x coordinate of the end of the line. */
	int x2;

	/** The y coordinate of the start of the line. */
	int y1;

	/** The y coordinate of the end of the line. */
	int y2;

	/** The text area of the x coordinate of the start of the line. */
	JTextArea xStartText = new JTextArea();

	/** The text area of the x coordinate of the end of the line. */
	JTextArea xEndText = new JTextArea();

	/** The text area of the y coordinate of the start of the line. */
	JTextArea yStartText = new JTextArea();

	/** The text area of the y coordinate of the end of the line. */
	JTextArea yEndText = new JTextArea();

	/**
	 * Constructor which gets the line which is to be edited. It then initializes
	 * the panel
	 * 
	 * @param line
	 *            the line to be edited.
	 */
	public LineEditor(Line line) {
		this.line = line;
		pendingColor = line.getColor();
		setLayout(new GridLayout(0, 2));
		add(new JLabel("x-start : "));
		xStartText.setText(String.valueOf(line.getxStart()));
		add(xStartText);
		add(new JLabel("x-end"));
		xEndText.setText(String.valueOf(line.getxEnd()));
		add(xEndText);
		add(new JLabel("y-start : "));
		yStartText.setText(String.valueOf(line.getyStart()));
		add(yStartText);
		add(new JLabel("y-end"));
		yEndText.setText(String.valueOf(line.getyEnd()));
		add(yEndText);
		JButton colors = new JButton("Colors");
		JLabel label = new JLabel();
		label.setBackground(line.getColor());
		label.setOpaque(true);
		colors.addActionListener(e -> {
			Color chosen = JColorChooser.showDialog(LineEditor.this, "Choose the color",
					line.getColor());
			if (chosen == null) return;
			label.setBackground(chosen);
			label.setOpaque(true);
			pendingColor = chosen;
		});
		add(colors);
		add(label);
	}

	@Override
	public void checkEditing() {
		try {
			x1 = Integer.parseInt(xStartText.getText().trim());
			x2 = Integer.parseInt(xEndText.getText().trim());
			y1 = Integer.parseInt(yStartText.getText().trim());
			y2 = Integer.parseInt(yEndText.getText().trim());
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Illegal arguments were given to editor.");
		}
		if (x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0) {
			throw new IllegalArgumentException("Illegal parameters given");
		}
	}

	@Override
	public void acceptEditing() {
		line.setxStart(x1);
		line.setxEnd(x2);
		line.setyStart(y1);
		line.setyEnd(y2);
		line.setColor(pendingColor);
	}

}
