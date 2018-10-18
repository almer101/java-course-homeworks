package hr.fer.zemris.java.hw07.shell.subcommands;

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
import hr.fer.zemris.java.hw07.shell.util.FilesProcessor;

/**
 * This command will writes all groups for each selects
 * file from the specified directory.
 * 
 * @author ivan
 *
 */
public class GroupsSubShellCommand implements ShellCommand {

	//=============================Constants==========================================

	/**
	 * The description of the command, i.e. usage instructions.
	 */
	private static List<String> description = new ArrayList<>();
		
	/**
	 * The name of the command.
	 */
	private static final String NAME;
	
	static {
		NAME = "groups";
		description.add("This command will writes all groups for each selects");
		description.add("file from the specified directory.");
	}
	
	//======================Method implementations====================================
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		//the arguments don't have to be checked if null because only
		//massrename command can call this command and it has checked
		//the arguments
		String args[] = ArgumentProcessor.processArguments(arguments);
		if(args.length != 4) {
			env.writeln("The groups subcommand does not expect any additional "
					+ "arguments but the basic 4 argument construction.");
			return ShellStatus.CONTINUE;
		}
		
		Path currentDir = env.getCurrentDirectory();
		Path dir1 = currentDir.resolve(Paths.get(args[0]));
		
		if(!Files.exists(dir1)) {
			env.writeln("The source directory does not exist!");
			return ShellStatus.CONTINUE;
		}
		try {
			FilesProcessor processor = new FilesProcessor(env, args[3], dir1);
			//											(env, mask, sourceDir)
			processor.groupFileNames();
			
		} catch (IOException e) {
			env.writeln("An error occurred while listing the files.");
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
