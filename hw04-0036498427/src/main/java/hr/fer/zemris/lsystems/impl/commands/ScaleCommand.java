package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * This class represents a command which scales the 
 * current effective move length with the property
 * <code>factor</code>.
 * 
 * @author ivan
 *
 */
public class ScaleCommand implements Command {

	//===============Properties===============================
	
	/**
	 * This is the factor for scaling the effective length of
	 * the move -> delta.
	 * 
	 */
	private double factor;
	
	//===============Constructors===============================
	
	/**
	 * This constructor receives one parameter and initializes
	 * the value of this classes' property with that specified
	 * value.
	 * 
	 * @param factor
	 * 		the initial value of the property <code>factor</code>
	 */
	public ScaleCommand(double factor) {
		super();
		this.factor = factor;
	}
	
	//===============Method implementations=====================
	
	/**
	 * This method scales the current delta (length of a unit) of
	 * the first state on the stack by multiplying it with this
	 * classes' property <code>factor</code>.
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
		
		double currentDelta = currentState.getDelta();
		currentState.setDelta(currentDelta * factor);
	}
}
