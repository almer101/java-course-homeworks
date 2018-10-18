package hr.fer.zemris.java.hw07.shell.commands;

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
 * This command is called with no arguments. And pops the 
 * directory from the top of the stack and discards it. 
 * (i.e. the current working directory is not changed)
 * 
 * @author ivan
 *
 */
public class DropDShellCommand implements ShellCommand {

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
		NAME = "dropd";
		description.add("This command is called without any arguments and pops");
		description.add("the directory from the rop of the stack and discards it.");
		description.add("(i.e. the current working directory is not changed)");
	}

	//=========================Method implementations==============================
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentProcessor.checkArguments(env, arguments);
		if(arguments.trim().length() != 0) {
			env.writeln("The command dropd must be called without any arguments! "
					+ "See the description of the command for details.");
			return ShellStatus.CONTINUE;
		}
		execute(env);
		return ShellStatus.CONTINUE;
	}

	/**
	 * This method executes checks if the stack is existent and if it is 
	 * is it not empty. If both are true the stack is popped but the 
	 * current working directory is not changed. (i.e. the popped Path is
	 * discarded)
	 * 
	 * @param env
	 * 		the environment of the shell.
	 * 
	 */
	private void execute(Environment env) {
		@SuppressWarnings("unchecked")
		Stack<Path> paths = (Stack<Path>) env.getSharedData(SHARED_DATA_KEYWORD);
		
		if(paths == null || paths.isEmpty()) {
			env.writeln("The stack is empty or non-existent");
		} else {
			paths.pop();
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
