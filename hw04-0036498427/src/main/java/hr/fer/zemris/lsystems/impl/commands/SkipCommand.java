package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.java.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * This command calculates a new position and moves
 * a turtle to that position without leaving a drawn 
 * line behind the turtle. 
 * 
 * @author ivan
 *
 */
public class SkipCommand implements Command {

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
	public SkipCommand(double step) {
		super();
		this.step = step;
	}
		
	//=================Method implementations==================
	
	/**
	 * This method changes the current location of the turtle
	 * without drawing a line. The current location vector is
	 * changed for the value of the vector
	 * <i>(step * direction)</i>, where step is this classes'
	 * property and direction is the current direction of the
	 * turtle (i.e. direction of the last state 
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

		currentState.setCurrentPosition(v1);
	}

}
