package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.util.ArgumentProcessor;

/**
 * This command is called with one argument which is
 * a new working directory. The command pushes the
 * current working directory to the stack and sets
 * the specified one as a new current working directory.
 * If the specified directory does not exist the 
 * current working directory is not modified and
 * the command writes the appropriate message to the
 * console.
 * 
 * @author ivan
 *
 */
public class PushDShellCommand implements ShellCommand {

	//=============================Constants=======================================

	/**
	 * The description of the command, i.e. usage instructions.
	 */
	private static List<String> description = new ArrayList<>();
	
	/**
	 * The name of the command.
	 */
	private static final String NAME;
	
	/**
	 * The key under which the stack of the paths is located
	 * in the shared data map in the {@link Environment};
	 */
	private static final String SHARED_DATA_KEYWORD = "cdstack";

	static {
		NAME = "pushd";
		description.add("This command is called with one argument which is the");
		description.add("path to the new working directory. The command pushes");
		description.add("the current working directory to the stack and sets");
		description.add("the specified one as a new working directory. If the");
		description.add("specified directory does not ecist the current working");
		description.add("directory is not modified and the command writes the");
		description.add("appropriate message to the console.");
	}

	//=========================Method implementations==============================
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentProcessor.checkArguments(env, arguments);
		String args[] = ArgumentProcessor.processArguments(arguments);
		
		if(args.length != 1) {
			env.writeln("The pushd command must be called with one argument. "
					+ "See the description for the details.");
			return ShellStatus.CONTINUE;
		}
		
		execute(env, args[0]);	
		return ShellStatus.CONTINUE;
	}

	/**
	 * This method checks if the new path exists and if yes 
	 * pushes the current working directory on the stack
	 * and sets a specified working directory as a new 
	 * working directory of the environment.
	 * 
	 * @param env
	 * 		the environment of the shell.
	 * 
	 * @param pathName
	 * 		the string representation of the path to the 
	 * 		new working directory.
	 */
	private void execute(Environment env, String pathName) {
		Path currentDir = env.getCurrentDirectory();
		Path newDir = currentDir.resolve(Paths.get(pathName));
		
		if(!Files.exists(newDir) || !Files.isDirectory(newDir)) {
			env.writeln("The specified new working directory must exist and must"
					+ " not be a file!");
			return;
		}
		
		if(env.getSharedData(SHARED_DATA_KEYWORD) == null) {
			env.setSharedData(SHARED_DATA_KEYWORD, new Stack<Path>());
		}
		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>)env.getSharedData(SHARED_DATA_KEYWORD);
		stack.push(env.getCurrentDirectory());
		env.setCurrentDirectory(newDir);
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
