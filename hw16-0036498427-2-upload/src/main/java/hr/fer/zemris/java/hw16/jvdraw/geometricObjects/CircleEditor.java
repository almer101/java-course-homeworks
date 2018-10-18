package hr.fer.zemris.java.hw16.jvdraw.geometricObjects;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * This is the editor of the circle geometric object. It enables user to edit radius, 
 * center x coordinate and center y coordinate.
 * 
 * @author ivan
 *
 */
public class CircleEditor extends GeometricalObjectEditor {

	/**The default serial version UID*/
	private static final long serialVersionUID = 1L;

	/**The circle to be edited.*/
	private Circle circle;
	
	/**The pending color from the chooser.*/
	private Color pendingColor;
	
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
	 * @param circle
	 * 		the circle to be edited.
	 */
	public CircleEditor(Circle circle) {
		this.circle = circle;
		setLayout(new GridLayout(0, 2));
		add(new JLabel("x-center : "));
		JTextArea xCenter = new JTextArea();
		xCenter.setText(String.valueOf(circle.getCenterX()));
		add(xCenter);
		add(new JLabel("y-center"));
		JTextArea yCenter = new JTextArea();
		yCenter.setText(String.valueOf(circle.getCenterY()));
		add(yCenter);
		add(new JLabel("radius"));
		JTextArea radius = new JTextArea();
		radius.setText(String.valueOf(circle.getRadius()));
		add(radius);
		JButton colors = new JButton("Colors");
		JLabel label = new JLabel();
		label.setBackground(circle.getColor());
		label.setOpaque(true);
		colors.addActionListener(e -> {
			Color chosen = JColorChooser.showDialog(CircleEditor.this, "Choose the color",
					circle.getColor());
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
		circle.setCenterX(x);
		circle.setCenterY(y);
		circle.setRadius(radius);
		circle.setColor(pendingColor);
	}

}
