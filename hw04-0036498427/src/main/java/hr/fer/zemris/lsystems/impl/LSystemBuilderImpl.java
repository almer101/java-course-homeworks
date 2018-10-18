package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.java.math.Vector2D;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;

import static java.lang.Math.PI;
import static java.lang.Math.pow;
import java.awt.Color;

/**
 * The objects of this class have the ability to configure
 * themselves, and after configuration the method 
 * build() can be called on them and that method returns
 * a new Lindermayer system. Configuration can be done using
 * specified methods or using the method configure from text.
 * 
 * @author ivan
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	
	//=======================Properties===========================
	
	/**
	 * This dictionary stores the pairs (key, value) where
	 * the key is String representation of the command (e.g.
	 * "rotate 60"), and the value is the object of type
	 * {@link Command};
	 * 
	 */
	private Dictionary commands;
	
	/**
	 * This dictionary stores the pairs (key, value) where
	 * the key is String left side of the production. The 
	 * value is also a {@link String} which
	 * represents the sequence on the right 
	 * side of the production.
	 * 
	 * example: "F -> F++F-F"
	 * the left side is: "F"
	 * the right side is: "F++F-F"
	 * 
	 */
	private Dictionary actions;
	
	/**
	 * This is the length of one unit to be drawn.
	 * (i.e. the length of the line drawn with the 
	 * command "draw 1");
	 * 
	 */
	private double unitLength = 0.1; 
	
	/**
	 * This scaler is used to determine the length of
	 * the effective length of the move (delta). Where
	 * delta = unitLength * (unitLengthDegreeScaler ^ d).
	 * d is the depth of the system.
	 * 
	 */
	private double unitLengthDegreeScaler = 1; 
	
	/**
	 * This is the origin position of the turtle.
	 * 
	 */
	private Vector2D origin = new Vector2D(0, 0); 
	
	/**
	 * This is is the angle where the turtle is headed.
	 * It is the angle between the positive x-axis and
	 * the direction of the turtle.
	 * 
	 */
	private double angle = 0;
	
	/**
	 * This is the axiom - the initial value in the
	 * production chain.
	 * 
	 */
	private String axiom = "";
	
	//=====================Constructors========================================
	
	/**
	 * This is a default constructor which initializes the two dictionaries
	 * for storing commands and productions.
	 * 
	 */
	public LSystemBuilderImpl() {
		commands = new Dictionary();
		actions = new Dictionary();
	}
	
	//====================Constants=============================================
	
	private static final String DRAW = "draw";
	
	private static final String ROTATE = "rotate";
	
	private static final String PUSH = "push";
	
	private static final String POP = "pop";

	private static final String COLOR = "color";
	
	private static final String SKIP = "skip";
	
	private static final String SCALE = "scale";
	
	private static final int RED_START_INDEX = 0;
	
	private static final int RED_END_INDEX = 2;
	
	private static final int GREEN_START_INDEX = 2;
	
	private static final int GREEN_END_INDEX = 4;
	
	private static final int BLUE_START_INDEX = 4;
	
	private static final int BLUE_END_INDEX = 6;	
	
	private static final String ORIGIN = "origin";
	
	private static final String ANGLE = "angle";
	
	private static final String UNIT_LENGTH = "unitLength";
	
	private static final String UNIT_LENGTH_DEGREE_SCALER = "unitLengthDegreeScaler";
	
	private static final String COMMAND = "command";
	
	private static final String AXIOM = "axiom";
	
	private static final String PRODUCTION = "production";
	
	private static final Color DEFAULT_TURTLE_COLOR = Color.BLACK;
	
	private static final Vector2D DEFAULT_DIRECTION = new Vector2D(1, 0);
	
	//==========================Helper class==============================================
	
	/**
	 * This inner class is a helper class which implements {@link LSystem}
	 * interface. The object of this class is returned after calling
	 * the method build() from superclass.
	 * 
	 * @author ivan
	 *
	 */
	private class LSystemImpl implements LSystem {

		/**
		 * This method creates a new context, calls the generating of
		 * the final char sequence (using generate() method) and for
		 * each char in the sequence if there is specified command 
		 * the command will be executed, otherwise the char is skipped
		 * and the same process is repeated for the next char.
		 * 
		 * @param level
		 * 		number of levels of production.
		 * 
		 * @param painter
		 * 		an object which knows how to draw lines.
		 * 
		 */
		@Override
		public void draw(int level, Painter painter) {
			
			Context ctx = new Context();
			double delta = unitLength * pow(unitLengthDegreeScaler, level);
			TurtleState startingState = new TurtleState(
										origin, 
										DEFAULT_DIRECTION.rotated(angle), 
										DEFAULT_TURTLE_COLOR, 
										delta);
			ctx.pushState(startingState);
			
			char[] product = generate(level).toCharArray();
			for(char c : product) {
				Command command = (Command)commands.get(c);
				if(command == null) {
					continue;
				}
				command.execute(ctx, painter);
			}
		}

		/**
		 * This method generates a sequence after the 
		 * specified number of levels of productions.
		 * The returned sequence is a {@link String}.
		 * 
		 * @param level
		 * 		number of levels of productions.
		 * 
		 * @return
		 * 		a generated sequence after the specified
		 * 		number of levels of productions.
		 * 
		 */
		@Override
		public String generate(int level) {
			if(level < 0) {
				throw new IllegalArgumentException(
						"The level must be 0 or larger!");
			}
			if(level == 0) return axiom;
			
			String sequence = generate(level - 1);
			return production(sequence);
		}

		/**
		 * This method applies a production to each char
		 * in the sequence if such exists. If not the char 
		 * is skipped.
		 * 
		 * @param sequence
		 * 		the starting sequence from which the product
		 * 		sequence will be made.
		 * 
		 * @return
		 * 		a product sequence after productions were 
		 * 		applied to all the chars in the sequence.
		 */
		private String production(String sequence) {
			StringBuilder sb = new StringBuilder();
			char[] array = sequence.toCharArray();
			
			for(char c : array) {
				String product = (String)actions.get(c);
				if(product == null) {
					sb.append(c);
					continue;
				}
				sb.append(product);
			}
			return sb.toString();
		}
		
	}
	
	//======================Method implementations=================================
	
	/**
	 * This method builds an {@link LSystem} object
	 * and returns it.
	 * 
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	/**
	 * This method receives a String array with all the
	 * configurations to be made. The method than analyzes 
	 * the content of each string and configures this 
	 * builder accordingly.
	 * 
	 * @param data
	 * 		data to be analyzed.
	 */
	@Override
	public LSystemBuilder configureFromText(String[] data) {
		for(String line : data) {
			line = line.trim();
			//check if it is an empty string
			if(line.length() == 0) {
				continue;
			}
			analyzeLine(line);
		}
		return this;
	}

	/**
	 * This method adds a new pair (key, value) to 
	 * the dictionary <code>commands</code>.
	 * The key and value values are given by parameters 
	 * <code>sequence</code> and <code>command</code>.
	 * 
	 * @throws IllegalArgumentException
	 * 		if the command for that sequence already 
	 * 		exists in the dictionary.
	 */
	@Override
	public LSystemBuilder registerCommand(char sequence, String command) {
		if(commands.get(sequence) != null) {
			throw new IllegalArgumentException("The command "
					+ "for the given sequence alread exists, "
					+ "please provide a command for different sequence!");
		}
		
		Command c = getCommand(command);
		commands.put(sequence, c);
		return this;
	}

	/**
	 * This method adds a new pair (key, value) to 
	 * the dictionary <code>actions</code>.
	 * The key and value values are given in arguments 
	 * -> sequence, product.
	 * 
	 * @throws IllegalArgumentException
	 * 		if the production for the given sequence 
	 * 		(key) already exists in the dictionary.
	 */
	public LSystemBuilder registerProduction(char sequence, String product) {
		if(actions.get(sequence) != null) {
			throw new IllegalArgumentException("A production for "
					+ "this sequence alread exists, please provide "
					+ "a production for different sequence!");
			
		}
		actions.put(sequence, product.trim());
		return this;
	}

	/**
	 * This method sets the value of this classes' 
	 * property <code>angle</code> to the specified value.
	 * 
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		//the angle must be converted to radians.
		this.angle = angle * PI/180;
		return this;
	}

	/**
	 * This method sets the value of this classes' 
	 * property <code>axiom</code>
	 * to the specified value.
	 * 
	 * @throws NullPointerException
	 * 		if the given parameter is null.
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		if(axiom == null) {
			throw new NullPointerException("The axiom given in argument "
					+ "must not be null!");
		}
		this.axiom = axiom;
		return this;
	}

	/**
	 * This method sets the this classes' property 
	 * <code>origin</code> to the {@link Vector2D} 
	 * with the specified values of x and y.
	 * 
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		origin = new Vector2D(x, y);
		return this;
	}

	/**
	 * This method sets a new value of this classes' property
	 * <code>unitLength</code> to the specified value.
	 * 
	 * @throws IllegalArgumentException
	 * 		if the given length is less than zero.
	 */
	@Override
	public LSystemBuilder setUnitLength(double length) {
		if(length <= 0) {
			throw new IllegalArgumentException("The length of the unit "
					+ "must be larger than zero! Was " + length);	
		}
		unitLength = length;
		return this;
	}

	/**
	 * This method sets a value of the <code>unitDegreeScaler</code>
	 * to the specified value.
	 * 
	 * @throws IllegalArgumentException
	 * 		if the given scaler is less than zero.
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double scaler) {
		if(scaler <= 0) {
			throw new IllegalArgumentException("The scaler given in argument "
					+ "must be larger than 0! Was " + scaler);
		}
		unitLengthDegreeScaler = scaler;
		return this;
	}

	/**
	 * This method returns a command depending on the key word of the
	 * command. For example "rotate 60" will be returned as a 
	 * {@link RotateCommand} with the value of the angle (60 * PI/180).
	 * Because we want the angle to be in radians.	
	 *  
	 * @param command
	 * 		a {@link String} from which this method creates a new
	 * 		command
	 * 
	 * @return a command which is described by the specified 
	 * 		{@link String}
	 */
	private Command getCommand(String command) {
		String[] commandParts = command.trim().split("\\s+|\\t+");
		
		if(commandParts[0].toLowerCase().equals(ROTATE)) {
			//create a rotate command
			try {
				double angle = Double.parseDouble(commandParts[1]);
				//return a RotateCommand but with the angle in radians.
				return new RotateCommand(angle * PI/180);
				
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				System.out.println("The given form of the command is not "
						+ "valid! Please provide the valid form.");
			}
			
		} else if (commandParts[0].toLowerCase().equals(DRAW)) {
			//create a draw command
			try {
				double step = Double.parseDouble(commandParts[1]);
				return new DrawCommand(step);
				
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				System.out.println("The given form of the command is not "
						+ "valid! Please provide the valid form.");
			}
			
		} else if (commandParts[0].toLowerCase().equals(PUSH)) {
			//create a push command
			if(commandParts.length > 1) {
				throw new IllegalArgumentException("The push command must "
						+ "not have any additional parameters.");
				
			}
			return new PushCommand();
			
		} else if (commandParts[0].toLowerCase().equals(POP)) {
			//create a pop command
			if(commandParts.length > 1) {
				throw new IllegalArgumentException("The pop command must "
						+ "not have any additional parameters.");
				
			}
			return new PopCommand();
			
		} else if (commandParts[0].toLowerCase().equals(COLOR)) {
			//create a color command
			String rgb = commandParts[1].trim();
			
			if(rgb.length() != 6) {
				throw new IllegalArgumentException("The color value must "
						+ "be hex number with 6 numbers! (e.g. 00FF0A)");
				
			}
			String r = rgb.substring(RED_START_INDEX, RED_END_INDEX);
			String g = rgb.substring(GREEN_START_INDEX, GREEN_END_INDEX);
			String b = rgb.substring(BLUE_START_INDEX, BLUE_END_INDEX);
			
			try {
				return new ColorCommand(new Color(
						Integer.parseInt(r, 16),
						Integer.parseInt(g, 16),
						Integer.parseInt(b, 16)
				));
			} catch (NumberFormatException e) {
				System.out.println("Illegal format of the color!");
				
			}
		} else if(commandParts[0].toLowerCase().equals(SKIP)) {
			try {
				double step = Double.parseDouble(commandParts[1]);
				return new SkipCommand(step);
				
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				System.out.println("The given form of the command is not "
						+ "valid! Please provide the valid form.");
			}
		} else if(commandParts[0].toLowerCase().equals(SCALE)) {
			try {
				double factor = Double.parseDouble(commandParts[1]);
				return new ScaleCommand(factor);
				
			} catch (NumberFormatException | IndexOutOfBoundsException e) {
				System.out.println("The given form of the command is not "
						+ "valid! Please provide the valid form.");
			}
		} else {
			throw new IllegalArgumentException("Invalid format of the command!");
		}
		return null;
	}
	
	/**
	 * This method performs analysis of the line and configures
	 * this {@link LSystemBuilderImpl} accordingly (i.e. puts 
	 * new commands in command dictionary, puts new productions 
	 * in actions dictionary, sets the properties of this class, 
	 * such as origin, angle etc.)
	 * 
	 * @param line
	 * 		line to be analyzed.
	 */
	private void analyzeLine(String line) {
		
		String keyword = getFirstWord(line);
		String rest = getRest(line);
		
		if(keyword.equalsIgnoreCase(COMMAND)) {
			registerCommand(rest.charAt(0), rest.substring(1));
			
		} else if (keyword.equalsIgnoreCase(AXIOM)) {
			setAxiom(rest);
			
		} else if(keyword.equalsIgnoreCase(ORIGIN)) {
			String[] parts = rest.split("\\s+|\\t+|\\n+|\\r+");
			
			try {
				setOrigin(Double.parseDouble(parts[0]),
							Double.parseDouble(parts[1]));
				
			} catch (NumberFormatException e) {
				System.out.println("The format of the origin is not valid!");
			}
			
		} else if(keyword.equalsIgnoreCase(PRODUCTION)) {
			registerProduction(rest.charAt(0), rest.substring(1).trim());
			
		} else if(keyword.equalsIgnoreCase(ANGLE)) {
			try {
				setAngle(Double.parseDouble(rest));
				
			} catch (NumberFormatException e) {
				System.out.println("The angle is not a valid number!");
			}
			
		} else if(keyword.equalsIgnoreCase(UNIT_LENGTH)) {
			try {
				setUnitLength(Double.parseDouble(rest));
				
			} catch (NumberFormatException e) {
				System.out.println("The unit length is not a valid number!");
			}
		} else if(keyword.equalsIgnoreCase(UNIT_LENGTH_DEGREE_SCALER)) {
			try {
				analyzeUnitLengthDegreeScalerLine(rest);
				
			} catch (NumberFormatException e) {
				System.out.println("The given argument for length scaler is not "
						+ "valid! Was " + rest);
			}
		}
	}
	
	/**
	 * This method returns the first word from a line. 
	 * 
	 * @param line
	 * 		line from which to extract a first word.
	 * 
	 * @return
	 * 		a first word from the string.
	 */
	private String getFirstWord(String line) {
		int index = firstEmptySpace(line);
		return line.substring(0, index);
	}
	
	/**
	 * This method returns the index of first empty space
	 * in the specified {@link String}. All of these are
	 * considered an empty space:
	 * ' ', '\t', '\n', '\r'
	 * 
	 * @param line
	 * 		line in which we look for an empty space.
	 * 
	 * @return
	 * 		the index of first empty space in the
	 * 		specified {@link String}; if no empty spaces
	 * 		are found, returns the index of the last char + 1;
	 */
	private int firstEmptySpace(String line) {
		int index = 0;
		while(index < line.length() 
				&& !isEmptySpace(line.charAt(index))) {
			index++;
		}
		return index;
	}

	/**
	 * This method returns the part of the line after the keyword.
	 * That part describes parameters for the keyword. This method is used in 
	 * configureFromText() method.
	 * 
	 * @param line
	 * 		line from which to extract the information wanted (i.e. the 
	 * 		part after the keyword)
	 * 
	 * @return
	 * 		the part of the line after the keyword.
	 */
	private String getRest(String line) {
		int index = firstEmptySpace(line);
		return line.substring(index, line.length()).trim();
	}

	/**
	 * This method checks if the specified char is 
	 * an empty space (i.e. '\n', '\r', ' ' or '\t').  
	 * This method is used in getKeyword() method.
	 * 
	 * @param c
	 * 		char to check.
	 * 
	 * @return
	 * 		<code>true</code> if the char is an empty space; 
	 * 		<code>false</code> otherwise
	 */
	private boolean isEmptySpace(char c) {
		if(c == ' ' || c == '\r' || c == '\n' || c == '\t') {
			return true;
		}
		return false;
	}

	/**
	 * This method analyzes the specified line. The line consists of
	 * one "double" number or "double/double" number. This method 
	 * sets the value of the <code>unitLengthDegreeScaler</code> to the
	 * new value this method extracts from the given parameter.
	 * 
	 * @param line
	 * 		the line to analyze.
	 * 
	 * @throws NumberFormatException
	 * 		if the number could not be parsed.
	 */
	private void analyzeUnitLengthDegreeScalerLine(String line) {
		if(!line.contains("/")) {
			unitLengthDegreeScaler = Double.parseDouble(line.trim());
			
		} else {
			String[] parts = line.split("/");
			double a = Double.parseDouble(parts[0].trim());
			double b = Double.parseDouble(parts[1].trim());
			unitLengthDegreeScaler = a/b;
		}
	}

}
