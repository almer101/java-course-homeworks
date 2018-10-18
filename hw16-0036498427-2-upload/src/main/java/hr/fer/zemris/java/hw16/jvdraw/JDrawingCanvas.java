package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Objects;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.geometricObjects.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jvdraw.geometricObjects.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;

/**
 * This is the canvas on which the objects are painted. Delegates the mouse events to the
 * {@link Tool} state.
 * 
 * @author ivan
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/**The default serial version UID*/
	private static final long serialVersionUID = 1L;
	
	/**The current tool state*/
	private Tool currentState;
	
	/**The model which contains all the drawings.*/
	private DrawingModel model;
	
	/**
	 * Constructor which gets the starting state and the model as parameters.
	 * 
	 * @param state
	 * 		the initial state.
	 * @param model
	 * 		the model of the drawings.
	 */
	public JDrawingCanvas(Tool state, DrawingModel model) {
		this.currentState = state;
		this.model = model;
		model.addDrawingModelListener(this);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				currentState.mousePressed(e);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				currentState.mouseReleased(e);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				currentState.mouseClicked(e);
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				currentState.mouseMoved(e);
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				currentState.mouseDragged(e);
			}
		});
		
		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				currentState.mouseMoved(e);
			}			
			@Override
			public void mouseDragged(MouseEvent e) {
				currentState.mouseDragged(e);
			}
		});
	}
	
	/**
	 * This method sets the state of the tool.
	 * 
	 * @param toolState	
	 * 		the new value of the {@link #currentState} property.
	 */
	public void setToolState(Tool toolState) {
		currentState = Objects.requireNonNull(toolState);
	}
	
	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		int size = model.getSize();
		GeometricalObjectVisitor painter = new GeometricalObjectPainter((Graphics2D)g);
		for(int i = 0; i < size; i++) {
			model.getObject(i).accept(painter);
		}
		currentState.paint((Graphics2D)g);
	}
}
