package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * This class models the calculator display which is
 * also a listener and each time the value changes the 
 * display is updated.
 * 
 * @author ivan
 *
 */
public class CalcDisplay extends JLabel implements CalcValueListener {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The constructor which sets the initial alignment
	 * of the text in the label.
	 * 
	 */
	public CalcDisplay() {
		setHorizontalAlignment(SwingConstants.RIGHT);
		setOpaque(true);
		setBackground(Color.YELLOW);
		setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

	}

	@Override
	public void valueChanged(CalcModel model) {
		SwingUtilities.invokeLater(() -> {
			setText(model.toString());
		});
	}
}
