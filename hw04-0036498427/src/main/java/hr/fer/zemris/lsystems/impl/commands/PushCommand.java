package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * This class represents a command which copies an
 * instance of a state from the stack, and pushes
 * that copy on the stack.
 * 
 * @author ivan
 *
 */
public class PushCommand implements Command {

	/**
	 * This method copies the last state from the stack
	 * and pushes that copy on the stack without removing
	 * the existing element on the stack whose copy it took.
	 * 
	 * @param ctx
	 * 		context of the fractal visualization process.
	 * 
	 * @param painter
	 * 		an object which can draw a line.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState().copy();
		ctx.pushState(state);
	}

}
