package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * This interface defines that all the objects
 * which implement this interface must have
 * the ability to execute themselves.
 * 
 * @author ivan
 *
 */
public interface Command {

	/**
	 * A method which executes this command.
	 * 
	 * @param ctx
	 * 		A context of the fractal visualization 
	 * 		process.		
	 * 
	 * @param painter
	 * 		an object which has the ability to
	 * 		draw lines.
	 */
	void execute(Context ctx, Painter painter);
}
