package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.util.ArgumentProcessor;

/**
 * This command is called with no arguments and prints
 * the absolute path of the current working directory
 * to the console.
 * 
 * @author ivan
 *
 */
public class PwdShellCommand implements ShellCommand {

	//=============================Constants====================================

	/**
	 * The description of the command, i.e. usage instructions.
	 */
	private static List<String> description = new ArrayList<>();
		
	/**
	 * The name of the command.
	 */
	private static final String NAME;
	
	static {
		NAME = "pwd";
		description.add("This command is called with no arguments and prints");
		description.add("the absolute path of the current working directory");
		description.add("to the console.");
	}
	
	//======================Method implementations==============================
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentProcessor.checkArguments(env, arguments);
		if(arguments.trim().length() != 0) {
			env.writeln("The pwd command must be called with no arguments! See "
					+ "the command description for details.");
			return ShellStatus.CONTINUE;
		}
		env.writeln(env.getCurrentDirectory().toString());
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

}
