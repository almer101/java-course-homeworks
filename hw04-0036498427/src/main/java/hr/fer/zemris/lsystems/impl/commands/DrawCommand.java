package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.java.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * This command draws a has one property step
 * which represents the length of a line to be
 * drawn. It calculates a new position and moves
 * a turtle to that position leaving a drawn 
 * line behind the turtle. The length of
 * a line is defined by the property step.
 * 
 * @author ivan
 *
 */
public class DrawCommand implements Command {

	//=================Properties==============================
	
	/**
	 * The step of the move (i.e. the length of the move)
	 * 
	 */
	private double step;
	
	//=================Constructors============================
	
	/**
	 * This constructor sets the <code>state</code> property
	 * to the specified value.
	 * 
	 * @param step
	 * 		the value to set the <code>step</code> property to.
	 */
	public DrawCommand(double step) {
		super();
		this.step = step;
	}
	
	//=================Method implementations==================
	
	/**
	 * This method draws a line using a {@link Painter} given
	 * through the parameter <code>painter</code>. The length
	 * of the line is calculated this way:
	 * 		(<code>step</code> * delta)
	 * where delta is the current state's length of a unit.
	 * In that process the current location of the turtle is
	 * changed for the value of the vector 
	 * <i>step * direction</i>, where step is this classes'
	 * property and direction is the current direction of the
	 * turtle (i.e. current direction of the last state 
	 * on the stack).
	 * 
	 * @param ctx
	 * 		context of the fractal visualization process.
	 * 
	 * @param painter
	 * 		an object which can draw a line.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		
		Vector2D v0 = currentState.getCurrentPosition();
		Vector2D direction = currentState.getDirection().copy();
		
		direction.scale(step * currentState.getDelta());
		Vector2D v1 = v0.translated(direction);
		
		painter.drawLine(v0.getX(), v0.getY(), 
						v1.getX(), v1.getY(), 
						currentState.getColor(), 
						1);
		
		currentState.setCurrentPosition(v1);
	}
}
