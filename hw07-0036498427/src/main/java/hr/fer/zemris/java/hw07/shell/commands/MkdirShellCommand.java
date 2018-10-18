package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
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
 * This command takes one argument and creates a
 * directory structure specified by that argument.
 * 
 * @author ivan
 *
 */
public class MkdirShellCommand implements ShellCommand {

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
		NAME = "mkdir";
		description.add("This command is called with one argument ");
		description.add("and that is the name of the directory ");
		description.add("to be created. The command than creates the");
		description.add("specified directory structure.");
	}

	//========================Method implementations============================
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentProcessor.checkArguments(env, arguments);
		
		String args[] = ArgumentProcessor.processArguments(arguments);
		if(args.length != 1 || args[0].length() == 0) {
			env.writeln("Command accepts only one argument. The mkdir command \n"
					+ "must be provided with the directory name to create");
			return ShellStatus.CONTINUE;
		}
		try {
			Path currentDir = env.getCurrentDirectory();
			Files.createDirectories(currentDir.resolve(Paths.get(args[0])));
			env.writeln("The directory structure was succesfully created.");	
			
		} catch (IOException e) {
			env.writeln("The directory could not be created.");
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

}
