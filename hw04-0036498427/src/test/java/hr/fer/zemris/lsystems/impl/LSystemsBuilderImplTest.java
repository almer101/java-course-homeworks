package hr.fer.zemris.lsystems.impl;

import org.junit.Test;

/**
 * These tests were just here so i can follow the states
 * of variables to check if everything is fine.
 * There are no assertions in these tests and nothing
 * is printed out on system.out. 
 * 
 * @author ivan
 *
 */
public class LSystemsBuilderImplTest {

	@Test
	public void registerCommandTest() {
		LSystemBuilderImpl builder = new LSystemBuilderImpl();
		builder.registerCommand('+', "rotate 60");
		builder.registerCommand('-', "rotate -60");
		builder.registerCommand('F', "draw 1");
		
		builder.registerCommand('G', "color   	ff00ff");
		builder.setAxiom("F");
	}
	
	@Test
	public void registerProductionTest() {
		LSystemBuilderImpl builder = new LSystemBuilderImpl();
		builder.registerProduction('F', "F+F--F+F");
		try {
			builder.registerProduction('F', "F+F--");
		} catch (IllegalArgumentException e) {
			System.out.println("Good job!");
		}
	}
	
	@Test
	public void configureFromTextTest() {
		LSystemBuilderImpl builder = new LSystemBuilderImpl();
		String[] data = {
				"origin 0.05 0.4", 
				"angle 0", 
				"unitLength 0.9", 
				"unitLengthDegreeScaler 1.0 / 3.0", 
				"",
				"command F draw 1",
				"command + rotate 60",
				"command - rotate -60",
				"",
				"axiom F",
				"",
				"production F F+F--F+F"
		};
		builder.configureFromText(data);
	}
}
