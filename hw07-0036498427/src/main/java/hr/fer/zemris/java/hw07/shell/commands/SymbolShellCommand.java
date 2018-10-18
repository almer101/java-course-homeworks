package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.util.ArgumentProcessor;

/**
 * This command takes one or two arguments, the first argument is
 * mandatory and that is the name of the symbol which the command
 * will be executed on. If called with only one argument the 
 * command prints the value of the wanted symbol. If called with two
 * arguments the second one is the new value of the specified
 * symbol. The command than sets the new value for the specified
 * symbol.
 * 
 * @author ivan
 *
 */
public class SymbolShellCommand implements ShellCommand {
	
	//==============================Constants=====================================

	/**
	 * The description of the command, i.e. usage instructions.
	 */
	private static List<String> description = new ArrayList<>();
	
	/**
	 * The name of the command.
	 */
	private static final String NAME;
	
	/**
	 * The more lines keyword.
	 */
	private static final String MORELINES = "MORELINES";
	
	/**
	 * The multi line keyword.
	 */
	private static final String MULTILINE = "MULTILINE";
	
	/**
	 * The prompt symbol keyword.
	 */
	private static final String PROMPT = "PROMPT";

	static {
		NAME = "symbol";
		description.add("This command is called with one or two arguments - ");
		description.add("the first argument is mandatory and that is the name ");
		description.add("of the symbol which the command will be executed on. ");
		description.add("If called with only one argument the command prints ");
		description.add("the value of the wanted symbol. If called with two ");
		description.add("arguments the second one is the new value of the");
		description.add("specified symbol. The command than sets the new value");
		description.add("for the specified symbol");
	}

	//=========================Method implementations==============================
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentProcessor.checkArguments(env, arguments);
		String args[] = ArgumentProcessor.processArguments(arguments);
		
		if(args.length == 1 && args[0].length() != 0) {
			executeWithOneArgument(args[0], env);
			
		} else if(args.length == 2) {
			executeWithTwoArguments(args[0], args[1], env);
			
		} else {
			env.writeln("The symbol command must be called with one or two "
					+ "arguments. See description of the command for details.");
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * This method prints the value of the symbol whose keyword is
	 * the specified keyword to the environment. If the symbol does
	 * not exist the appropriate message is written to the environment.
	 * 
	 * @param keyword
	 * 		the keyword of the symbol.		
	 * 
	 * @param env
	 * 		environment where to write the output from the method.
	 */
	private void executeWithOneArgument(String keyword, Environment env) {
		keyword = keyword.trim().toUpperCase();
		switch(keyword) {
			case MORELINES: 
				printMessage(keyword, env.getMorelinesSymbol(), env);
				break;
			case MULTILINE: 
				printMessage(keyword, env.getMultilineSymbol(), env);
				break;
			case PROMPT: 
				printMessage(keyword, env.getPromptSymbol(), env);
				break;
			default:
				env.writeln("You entered a keyword for which the symbol "
						+ "does not exist.");
				break;
		}
	}

	/**
	 * This method changes the value of the symbol whose keyword
	 * is specifies by the <code>keyword</code> argument. The new
	 * value is specifies by the parameter <code>newSymbol</code>.
	 * If the newSymbol is not of length one the appropriate 
	 * message is written to the environment.
	 * 
	 * @param keyword
	 * 		the keyword of the symbol.	
	 * 
	 * @param newSymbol
	 * 		the new symbol to set.
	 * 
	 * @param env
	 * 		environment where to write the output from the method.
	 */
	private void executeWithTwoArguments(String keyword, String newSymbol, 
			Environment env) {
		keyword = keyword.trim().toUpperCase();
		newSymbol = newSymbol.trim();
		char oldSymbol;
		if(newSymbol.length() != 1) {
			env.writeln("The specified new symbol must be of length 1.");
			return;
		}
		switch(keyword) {
			case MORELINES: 
				oldSymbol = env.getMorelinesSymbol();
				env.setMorelinesSymbol(newSymbol.charAt(0));
				break;
			case MULTILINE: 
				oldSymbol = env.getMultilineSymbol();
				env.setMultilineSymbol(newSymbol.charAt(0));
				break;
			case PROMPT: 
				oldSymbol = env.getPromptSymbol();
				env.setPromptSymbol(newSymbol.charAt(0));
				break;
			default:
				env.writeln("You entered a keyword for which the symbol "
						+ "does not exist.");
				return;
		}
		printChangeMessage(keyword, oldSymbol, newSymbol, env);
		return;
	}

	/**
	 * This method prints the message when the writing of the 
	 * certain symbol is requested. (e.g. Symbol for PROMPT is '>')
	 * 
	 * @param keyword
	 * 		the name of the symbol.
	 * 
	 * @param symbol
	 * 		the symbol which to write.
	 * 
	 * @param env
	 * 		the environment where to write.
	 */
	private void printMessage(String keyword, char symbol, 
			Environment env) {
		env.writeln("Symbol for " + keyword + " is '" + symbol + "'");
	}

	/**
	 * 	/**
	 * This method prints the message when the writing of the 
	 * certain symbol is requested. (e.g. Symbol for PROMPT is '>')
	 * 
	 * @param keyword
	 * 		the name of the symbol.
	 * 
	 * @param oldSymbol
	 * 		the symbol before change.
	 * 
	 * @param newSymbol
	 * 		the symbol after change.
	 * 
	 * @param env
	 * 		the environment where to write.
	 */
	private void printChangeMessage(String keyword, char oldSymbol, String newSymbol, Environment env) {
		env.writeln("Symbol for " + keyword + " changed from '" + oldSymbol 
				+ "' to '" + newSymbol + "'");
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}
}
