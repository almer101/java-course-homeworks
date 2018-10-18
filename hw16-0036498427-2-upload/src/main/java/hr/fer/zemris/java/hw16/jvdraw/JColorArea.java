package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JColorChooser;
import javax.swing.JComponent;


/**
 * This is the component which is used for choosing colors, i.e. to choose the
 * color of foreground and background.
 * 
 * @author ivan
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

	/** A default serial version UID */
	private static final long serialVersionUID = 1L;

	/** The selected color of this color area. */
	private Color selectedColor;

	/** The list of listeners. */
	private List<ColorChangeListener> listeners = new ArrayList<>();

	/**
	 * This is the constructor which gets the initial value of the property
	 * {@link #selectedColor}.
	 * 
	 * @param selectedColor
	 *            the initial value of the property {@link #selectedColor}.
	 */
	public JColorArea(Color selectedColor) {
		setSelectedColor(selectedColor);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Color chosen = JColorChooser.showDialog(JColorArea.this, "Choose the color.",
						selectedColor);
				if (chosen == null)
					return;
				setSelectedColor(chosen);
			}
		});
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}

	/**
	 * This method sets the value of the property {@link #selectedColor} to the
	 * specified value.
	 * 
	 * @param selectedColor
	 *            the new value of the property {@link #selectedColor}.
	 */
	public void setSelectedColor(Color selectedColor) {
		Objects.requireNonNull(selectedColor);
		Color oldColor = this.selectedColor;
		Color newColor = selectedColor;
		this.selectedColor = selectedColor;
		repaint();
		listeners.forEach(l -> l.newColorSelected(this, oldColor, newColor));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(this.selectedColor);
		g.fillRect(0, 0, 15, 15);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(l);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}

}
