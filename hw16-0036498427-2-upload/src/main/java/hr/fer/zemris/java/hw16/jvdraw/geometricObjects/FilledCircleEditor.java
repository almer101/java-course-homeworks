package hr.fer.zemris.java.hw16.jvdraw.geometricObjects;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


/**
 * This editor enables user to edit filled circle geometric objects. It enables
 * the radius and x,y coordinates of the circle to be changed as well as 
 * outline and fill color of the circle.
 * 
 * @author ivan
 *
 */
public class FilledCircleEditor extends GeometricalObjectEditor {

	/**The default serial version UID*/
	private static final long serialVersionUID = 1L;

	/**The circle to be edited.*/
	private FilledCircle filledCircle;
	
	/**The pending background color from the chooser.*/
	private Color pendingFillColor;
	
	/**The pending foreground color from the chooser.*/
	private Color pendingOutlineColor;
	
	/**The pending radius of the edited circle.*/
	private int radius;
	
	/**The pending x coordinate of the circle center.*/
	private int x;
	
	/**The pending y coordinate of the circle center.*/
	private int y;
	
	/**
	 * Constructor which gets the reference to the circle which is to be edited and
	 * initializes this {@link JPanel}.
	 * 
	 * @param filledCircle
	 * 		the circle to be edited.
	 */
	public FilledCircleEditor(FilledCircle filledCircle) {
		this.filledCircle = filledCircle;
		setLayout(new GridLayout(0, 2));
		add(new JLabel("x-center : "));
		JTextArea xCenter = new JTextArea();
		xCenter.setText(String.valueOf(filledCircle.getCenterX()));
		add(xCenter);
		add(new JLabel("y-center"));
		JTextArea yCenter = new JTextArea();
		yCenter.setText(String.valueOf(filledCircle.getCenterY()));
		add(yCenter);
		add(new JLabel("radius"));
		JTextArea radius = new JTextArea();
		radius.setText(String.valueOf(filledCircle.getRadius()));
		add(radius);
		JButton colorBG = new JButton("Fill");
		pendingFillColor = filledCircle.getFillColor();
		JLabel bgLabel = new JLabel();
		bgLabel.setBackground(filledCircle.getFillColor());
		bgLabel.setOpaque(true);
		colorBG.addActionListener(e -> {
			Color chosen = JColorChooser.showDialog(FilledCircleEditor.this, "Choose the background color",
					filledCircle.getFillColor());
			if (chosen == null) return;
			bgLabel.setBackground(chosen);
			pendingFillColor = chosen;
		});
		add(colorBG);
		add(bgLabel);
		
		JButton colorFG = new JButton("Outline");
		pendingOutlineColor = filledCircle.getOutlineColor();
		JLabel fgLabel = new JLabel();
		fgLabel.setBackground(filledCircle.getOutlineColor());
		fgLabel.setOpaque(true);
		colorFG.addActionListener(e -> {
			Color chosen = JColorChooser.showDialog(FilledCircleEditor.this, "Choose the outline color",
					filledCircle.getOutlineColor());
			if (chosen == null) return;
			fgLabel.setBackground(chosen);
			pendingOutlineColor = chosen;
		});
		add(colorFG);
		add(fgLabel);
	}
	
	@Override
	public void checkEditing() {
		try {
			x = Integer.parseInt(((JTextArea)getComponent(1)).getText().trim());
			y = Integer.parseInt(((JTextArea)getComponent(3)).getText().trim());
			radius = Integer.parseInt(((JTextArea)getComponent(5)).getText().trim());
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Illegal arguments were given to editor.");
		}
		if (radius < 0 || x < 0 || y < 0) {
			throw new IllegalArgumentException("Illegal parameters given");
		}
	}

	@Override
	public void acceptEditing() {
		filledCircle.setCenterX(x);
		filledCircle.setCenterY(y);
		filledCircle.setRadius(radius);
		filledCircle.setFillColor(pendingFillColor);
		filledCircle.setOutlineColor(pendingOutlineColor);
	}

}
