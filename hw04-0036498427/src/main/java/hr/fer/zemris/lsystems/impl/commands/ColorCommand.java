package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * This class represents a command which changes
 * the color of the current state of the turtle.
 * 
 * @author ivan
 *
 */
public class ColorCommand implements Command {

	//=================Properties=============================
	
	/**
	 * The color to which to set a value of the current state
	 * of the turtle. 
	 * 
	 */
	Color color;
	
	//=================Constructors===========================
	
	/**
	 * This constructor receives one parameter and initializes 
	 * the value of the property color to that specified
	 * value
	 * 
	 * @param color
	 * 		initial value of the property <code>color</code>.
	 */
	public ColorCommand(Color color) {
		super();
		this.color = color;
	}
	
	//=================Method implementations=================
	
	/**
	 * This method sets the color of the first state on the
	 * context stack to the value of this classes' property
	 * <code>color</code>.
	 * 
	 * @param ctx
	 * 		context of the fractal visualization process.
	 * 
	 * @param painter
	 * 		an object which can draw a line.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}


}
