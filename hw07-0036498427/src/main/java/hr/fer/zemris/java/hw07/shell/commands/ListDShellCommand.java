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
 * The command is called without any arguments and 
 * prints the string representation of the paths 
 * stored on the stack in the environment shared data.
 * If the stack is empty the such message is written 
 * to the environment.
 * 
 * @author ivan
 *
 */
public class ListDShellCommand implements ShellCommand {

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
		NAME = "listd";
		description.add("This command is called without any arguments and lists");
		description.add("the string representations of the paths on the stack");
		description.add("in the Environment shared data. If the stack is empty");
		description.add("or non-existent such message is written to the console.");
	}

	//=========================Method implementations==============================
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentProcessor.checkArguments(env, arguments);
		if(arguments.trim().length() != 0) {
			env.writeln("The listd command must be called without any arguments!");
			return ShellStatus.CONTINUE;
		}
		execute(env);
		return ShellStatus.CONTINUE;
	}

	private void execute(Environment env) {
		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData(SHARED_DATA_KEYWORD);
		
		if(stack == null || stack.isEmpty()) {
			env.writeln("There are no stored directories!");
			
		} else {
			List<Path> paths = new ArrayList<>(stack);
			paths.forEach((p) -> env.writeln(p.toString()));
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
