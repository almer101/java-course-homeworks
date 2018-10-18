package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * This is a demonstration program for 
 * visualization of Lindermayer's system.
 * 
 * @author ivan
 *
 */
public class Glavni1 {

	/**
	 * This is the method called when the program
	 * is run.
	 * 
	 * @param args
	 * 		command line arguments.
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));
	}
	
	/**
	 * A method which configures the {@link LSystemBuilderImpl} and
	 * returns a Lindermayer's system.
	 * 
	 * @param provider
	 * 		an object which has the ability to configure
	 * 		itself and create a {@link LSystem}.
	 * 
	 * @return
	 * 		a complete Lindermayer's system.
	 */
	private static LSystem createKochCurve(LSystemBuilderProvider provider) { 
		
		return provider.createLSystemBuilder()
				.registerCommand('F', "draw 1")
				.registerCommand('+', "rotate 60")
				.registerCommand('-', "rotate -60")
				.setOrigin(0.05, 0.4)
				.setAngle(0)
				.setUnitLength(0.9)
				.setUnitLengthDegreeScaler(1.0/3.0)
				.registerProduction('F', "F+F--F+F")
				.setAxiom("F")
				.build();
	}
}
