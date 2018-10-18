package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * This is a demonstration program for 
 * visualization of Lindermayer's system.
 * 
 * @author ivan
 *
 */
public class Glavni3 {

	/**
	 * This is the method called when the program
	 * is run. 
	 * 
	 * @param args
	 * 		command line arguments.
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}
}
