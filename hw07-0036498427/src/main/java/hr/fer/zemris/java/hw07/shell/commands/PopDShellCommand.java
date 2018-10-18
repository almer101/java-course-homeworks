package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.util.ArgumentProcessor;

/**
 * This command is called without arguments and pops
 * the path from the stack and sets that popped path
 * as a new working directory. If such does not exist
 * (maybe it was deleted in the meantime) the current
 * directory is not modified but the popped directory is 
 * still popped from the stack. If the stack is empty 
 * the appropriate message is written to the console.
 * 
 * @author ivan
 *
 */
public class PopDShellCommand implements ShellCommand {

	
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
		NAME = "popd";
		description.add("This command is called without any arguments and pops the");
		description.add("path from the stack and sets that popped path as a new");
		description.add("working directory. If such does not exist, the current");
		description.add("directory is not modified but the popped directory still");
		description.add("remains popped from the stack.");
	}

	//=========================Method implementations==============================
		
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentProcessor.checkArguments(env, arguments);
		if(arguments.trim().length() != 0) {
			env.writeln("The popd command must be called without any arguments!");
			return ShellStatus.CONTINUE;
		}
		execute(env);
		return ShellStatus.CONTINUE;
	}

	/**
	 * This method checks if the stack under the key
	 * <code>SHARED_DATA_KEYWORD</code> exists and if it is not empty.
	 * If both are true gets pops the path from the stack and 
	 * if the path still exists (if it was not removed in the meantime)
	 * sets the new working directory of the environment to the
	 * popped directory.
	 * 
	 * @param env
	 * 		environment where to write and where to change a directory.
	 */
	private void execute(Environment env) {
		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>)env.getSharedData(SHARED_DATA_KEYWORD);
		
		if(stack == null) {
			env.writeln("The stack of paths is not existing!");
			return;
		}
		if(stack.isEmpty()) {
			env.writeln("The empty stack cannot be popped!");
			return;
		}
		Path newDir = stack.pop();
		if(Files.exists(newDir)) {
			env.setCurrentDirectory(newDir);
		}
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
