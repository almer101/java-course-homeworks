package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.util.ArgumentProcessor;

/**
 * This command is called with only one argument which is
 * the path to the new current working directory. The path
 * can be either absolute or relative to the current
 * working directory.
 * 
 * @author ivan
 *
 */
public class CdShellCommand implements ShellCommand {

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
		NAME = "cd";
		description.add("This command is called with one argument and the given");
		description.add("argument is the path to the new working directory. The");
		description.add("specified path can be either absolute or relative to ");
		description.add("the current working directory.");
	}
	
	//======================Method implementations==============================
		
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentProcessor.checkArguments(env, arguments);
		String args[] = ArgumentProcessor.processArguments(arguments);
		
		if(args.length != 1) {
			env.write("The cd command must be called with only one argument "
					+ "which is the path to the new current working directory.");
			return ShellStatus.CONTINUE;
		}
		Path currentDir = env.getCurrentDirectory();
		env.setCurrentDirectory(currentDir.resolve(Paths.get(args[0])));
		
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
