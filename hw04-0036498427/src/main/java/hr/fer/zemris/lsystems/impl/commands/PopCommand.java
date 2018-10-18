package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * This class represents a command which removes one sate from
 * the top of the stack.
 * 
 * @author ivan
 *
 */
public class PopCommand implements Command {

	/**
	 * This method removes the last state from the context
	 * stack of states.
	 * 
	 * @param ctx
	 * 		context of the fractal visualization process.
	 * 
	 * @param painter
	 * 		an object which can draw a line.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}

}
