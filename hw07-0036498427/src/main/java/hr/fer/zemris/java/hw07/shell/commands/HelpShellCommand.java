package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.util.ArgumentProcessor;

/**
 * This class represents a help method which, if
 * called with no arguments lists names of all
 * supported commands.
 * 
 * @author ivan
 *
 */
public class HelpShellCommand implements ShellCommand{
	
	//=============================Constants===================================

	/**
	 * The description of the command, i.e. usage instructions.
	 */
	private static List<String> description = new ArrayList<>();
		
	/**
	 * The name of the command.
	 */
	private static final String NAME;
	
	static {
		NAME = "help";
		description.add("This command when executed without arguments ");
		description.add("lists the names of all available commands. ");
		description.add("If called with a single argument it prints the ");
		description.add("name and the description of the selected command.");
	}
		
	//======================Method implementations==============================

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentProcessor.checkArguments(env, arguments);
		arguments = arguments.trim();
		
		if(arguments.length() == 0) {
			env.commands().forEach((k,v) -> env.writeln(k));
		} else {
			executeWithArguments(env, arguments);
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

	/**
	 * This method executes the command in case the command
	 * is called with more than 0 arguments.
	 * 
	 * @param env	
	 *		the current environment.
	 *
	 * @param arguments
	 * 		the arguments, this command is called with.
	 */
	private void executeWithArguments(Environment env, String arguments) {
		String parts[] = ArgumentProcessor.processArguments(arguments);
		
		if(parts.length > 1) {
			env.writeln("The command must be called with "
					+ "0 or 1 arguments! Called with: " + parts.length);
		} else {
			ShellCommand command = env.commands()
								.get(parts[0].toLowerCase());
			
			if(command == null) {
				env.writeln("Error: Such command does not exist!");
			} else {
				command.getCommandDescription().forEach(l -> env.writeln(l));
			}
		}
	}

}
