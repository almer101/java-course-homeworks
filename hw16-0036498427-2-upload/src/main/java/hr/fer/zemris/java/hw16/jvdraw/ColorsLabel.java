package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * This is the label which shows the current state of the colors of the foreground 
 * and background.
 * 
 * @author ivan
 *
 */
public class ColorsLabel extends JLabel {

	/**A default serial version UID*/
	private static final long serialVersionUID = 1L;

	/**The color of the foreground*/
	private Color foregroundColor;
	
	/**The color of the background*/
	private Color backgroundColor;

	/**
	 * This constructor gets the needed color areas on which it registers listeners,
	 * and sets the values of the {@link #foregroundColor} and {@link #backgroundColor}.
	 * 
	 * @param fgColorProvider
	 * 		the color of the foreground.
	 * @param bgColorProvider
	 * 		the color of the background.
	 */
	public ColorsLabel(IColorProvider fgColorProvider, 
			IColorProvider bgColorProvider) {
		foregroundColor = fgColorProvider.getCurrentColor();
		backgroundColor = bgColorProvider.getCurrentColor();

		fgColorProvider.addColorChangeListener(new ColorChangeListener() {

			@Override
			public void newColorSelected(IColorProvider source, Color oldColor,
					Color newColor) {
				foregroundColor = newColor;
				setLabelText();

			}
		});

		bgColorProvider.addColorChangeListener(new ColorChangeListener() {

			@Override
			public void newColorSelected(IColorProvider source, Color oldColor,
					Color newColor) {
				backgroundColor = newColor;
				setLabelText();
			}
		});
		setLabelText();
	}

	/**
	 * This method sets the value of the text of the label based on the current colors.
	 * 
	 * @param fgColor
	 * 		the color of the foreground
	 * @param bgColor
	 * 		the color of the background
	 */
	private void setLabelText() {
		String text = String.format(
				"Foreground color: (%d,%d,%d), background color: (%d,%d,%d)",
				foregroundColor.getRed(), foregroundColor.getGreen(),
				foregroundColor.getBlue(), 
				backgroundColor.getRed(), backgroundColor.getGreen(),
				backgroundColor.getBlue()
		);
		setText(text);
	}
}
