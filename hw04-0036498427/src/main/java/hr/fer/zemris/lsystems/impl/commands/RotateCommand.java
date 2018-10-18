package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * This class represents a command which rotates
 * the direction of a turtle for the value
 * of <code>angle</code> property of this class.
 * 
 * @author ivan
 *
 */
public class RotateCommand implements Command {

	//=================Properties===========================
	/**
	 * The angle to rotate the direction vector for.
	 * 
	 */
	private double angle;
	
	//=================Constructors=========================
	
	/**
	 * This constructor initializes an object of this
	 * instance with the specified angle.
	 * 
	 * @param angle
	 * 		the initial value of the angle of rotation.
	 */
	public RotateCommand(double angle) {
		super();
		this.angle = angle;
	}
	
	//==============Method implementations==================
	
	/**
	 * This method rotates the direction of the first state
	 * on the <code>ctx</code> stack for the value of
	 * this classes' property <code>angle</code>.
	 * 
	 * @param ctx
	 * 		context of the fractal visualization process.
	 * 
	 * @param painter
	 * 		an object which can draw a line.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		state.getDirection().rotate(angle);

	}


}
