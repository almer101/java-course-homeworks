package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.util.ArgumentProcessor;

/**
 * This class represents a shell command which, when
 * executed, terminates the work of the shell.
 * 
 * @author ivan
 *
 */
public class ExitShellCommand implements ShellCommand {
	
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
		NAME = "exit";
		description.add("This command when executed terminates");
		description.add("the work of the shell.");
	}
		
	//======================Method implementations==============================
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentProcessor.checkArguments(env, arguments);
		if(argumentsOK(env, arguments)) {
			return ShellStatus.TERMINATE;
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
	 * This method checks if the given arguments are empty 
	 * as they should be (i.e. makes sure that the command
	 * is called with no arguments). If that is not the
	 * case than writes the appropriate message to the 
	 * environments output.
	 * 
	 * @param arguments
	 * 		arguments to be checked.
	 * @param env
	 * 		the environment of the shell.
	 */
	private boolean argumentsOK(Environment env, String arguments) {
		arguments = arguments.trim();
		if(arguments.length() != 0) {
			env.writeln("The exit command must be called with no arguments.");
			return false;
		}
		return true;
	}
}
