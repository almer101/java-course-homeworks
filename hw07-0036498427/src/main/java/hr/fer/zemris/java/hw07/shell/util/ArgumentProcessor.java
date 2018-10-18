package hr.fer.zemris.java.hw07.shell.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.Environment;

/**
 * This class provides a few static methods
 * for splitting and processing the arguments
 * of the commands.
 * 
 * @author ivan
 *
 */
public class ArgumentProcessor {
	
	//============================Constants=================================
	
	private static final char QUOTE = '\"';
	private static final char ESCAPE_SIGN = '\\';
	private static final char SPACE = ' ';
	private static final char TAB = '	';

	//=======================Method implementations=========================
	
	/**
	 * This method processes the specified arguments.
	 * It splits them and returns the array of 
	 * individual arguments.
	 * 
	 * @param line
	 * 		a line of arguments.
	 * 
	 * @return
	 * 		the string array of split arguments.
	 */
	public static String[] processArguments(String line) {
		if(line.contains("\"")) {
			return processArgumentsWithQuotes(line);
		} else {
			return processNormalArguments(line);
		}
	}
	
	/**
	 * The method checks if the specified arguments are null and
	 * throws the {@link NullPointerException} if either of the 
	 * two is.
	 * 
	 * @param env
	 * 		{@link Environment} to be checked if null.		
	 * 
	 * @param arguments
	 * 		arguments {@link String} to be checked if null.
	 */
	public static void checkArguments(Environment env, String arguments) {
		Objects.requireNonNull(env, "The given environment can not be null!");
		Objects.requireNonNull(arguments, "The given arguments can not be null!");
	}
	
	/**
	 * This method splits the input line from the user into command name
	 * and command arguments.
	 * 
	 * @param line
	 * 		line to be split
	 * 
	 * @return
	 * 		the {@link String} array of command name and command arguments.
	 */
	public static String[] splitInputLine(String line) {
		line = line.trim();
		String parts[] = new String[2];
		int index = 0;
		while(index < line.length()) {
			if(line.charAt(index) == SPACE || line.charAt(index) == TAB) break;
			index++;
		}
		if(index >= line.length()) {
			parts[0] = line;
			parts[1] = "";
		} else {
			parts[0] = line.substring(0, index);
			parts[1] = line.substring(index, line.length()).trim();
		}
		return parts;
	}

	/**
	 * This method processes the normal type of line of 
	 * arguments, those are the arguments which do not contain quotes.
	 * 
	 * @param arguemnts
	 * 		a line of arguments.
	 *  
	 * @return
	 * 		the {@link String} array of individual
	 * 		arguments.
	 */
	private static String[] processNormalArguments(String arguemnts) {
		return arguemnts.trim().split("\\s+|\\t+");
		
	}

	/**
	 * This method processes a line of arguments which 
	 * contain quotes.
	 * 
	 * @param arguments
	 * 		a line of arguments.
	 * 
	 * @return
	 * 		a {@link String} array of individual
	 * 		arguments.
	 */
	private static String[] processArgumentsWithQuotes(String arguments) {
		int index = 0;
		char data[] = arguments.toCharArray();
		List<String> listOfArgs = new ArrayList<>();
		
		while(index < data.length) {
			String arg;
			if(data[index] == QUOTE) {
				arg = processQuotes(data, index + 1);
				int numOfEscapes = countEscapes(data, index + 1);
				index += arg.length() + 2 + numOfEscapes;
				checkIfValid(data, index);
				
			} else {
				arg = processNormal(data, index);
				index += arg.length();
			}
			listOfArgs.add(arg);
			index = skipBlanks(data, index);
		}
		String result[] = new String[listOfArgs.size()];
		listOfArgs.toArray(result);
		return result;
	}

	/**
	 * This method checks if the whitespace comes after
	 * the ending of quotation marks. If not throw an exception
	 * 
	 * @param data
	 * 		char array in which it is checked whether
	 * 		it is valid or not.		
	 * 
	 * @param index
	 * 		the current index.
	 */
	private static void checkIfValid(char[] data, int index) {
		if(index < data.length && !Character.isWhitespace(data[index])) {
			throw new IllegalArgumentException("After quotes must "
					+ "come a whitespace, otherwise it is an illegal string.");
		}	
	}

	/**
	 * This method skips the blank spaces (i.e. \s and \t)
	 * and returns the index of the first non-blank char.
	 * 
	 * @param data
	 * 		char array to skip blanks in.
	 * 
	 * @param index
	 * 		the current index in char array.		
	 * 
	 * @return
	 * 		the index of the first occurrence of the 
	 * 		non-blank char
	 */
	private static int skipBlanks(char[] data, int index) {
		while(index < data.length && 
				(data[index] == SPACE || data[index] == TAB)) {
			index++;
		}
		return index;
	}

	/**
	 * This method count how many valid escapes does
	 * the argument line have to be able to increment
	 * the index properly. The escapes are searched from
	 * the current index to the first occurrence of the
	 * quote mark.
	 * 
	 * @param data
	 * 		char array where escapes are searched for.
	 * 
	 * @param i
	 * 		the current index.
	 * 	
	 * @return
	 * 		the number of escapes.
	 */
	private static int countEscapes(char[] data, int i) {
		int numOfEscapes = 0;
		while(i < data.length && data[i] != QUOTE) {
			if(data[i] == ESCAPE_SIGN && (i + 1) < data.length) {
				if(data[i + 1] == ESCAPE_SIGN || data[i + 1] == QUOTE) {
					i += 2;
					numOfEscapes++;
				}
			}
			i++;
		}
		return numOfEscapes;
	}

	/**
	 * This method processes an individual argument which
	 * is inside the quotes.
	 * 
	 * @param data
	 * 		the char array of the argument line.
	 * 
	 * @param i
	 * 		the current index in the data array.
	 * 
	 * @return
	 * 		the argument inside the quotes which
	 * 		was processed.
	 */
	private static String processQuotes(char[] data, int i) {
		//we are now in quotes
		StringBuilder sb = new StringBuilder();
		while(i < data.length && data[i] != QUOTE) {
			//check for escape sign
			if(data[i] == ESCAPE_SIGN && (i + 1) < data.length) {
				if(data[i + 1] == ESCAPE_SIGN || data[i + 1] == QUOTE) {
					sb.append(data[i+1]);
					i += 2;
					continue;
				}
			}
			sb.append(data[i++]);
		}
		if(i >= data.length) {
			throw new IllegalArgumentException("Illegal argument passed."
					+ " The quotes must closed!");
		}
		return sb.toString();
	}
	
	/**
	 * This method processes an individual argument which
	 * is not inside the quotes.
	 * 
	 * @param data
	 * 		the char array of the argument line.
	 * 
	 * @param i
	 * 		the current index in the data array.
	 * 
	 * @return
	 * 		the argument which was processed.
	 */
	private static String processNormal(char data[], int i) {
		StringBuilder sb = new StringBuilder();
		while(i < data.length && data[i] != SPACE && data[i] != TAB) {
			sb.append(data[i++]);
		}
		return sb.toString();
	}
}
