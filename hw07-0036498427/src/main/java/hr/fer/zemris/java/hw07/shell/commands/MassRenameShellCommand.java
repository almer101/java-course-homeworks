package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.subcommands.ExecuteSubShellCommand;
import hr.fer.zemris.java.hw07.shell.subcommands.FilterSubShellCommand;
import hr.fer.zemris.java.hw07.shell.subcommands.GroupsSubShellCommand;
import hr.fer.zemris.java.hw07.shell.subcommands.ShowSubShellCommand;
import hr.fer.zemris.java.hw07.shell.util.ArgumentProcessor;

/**
 * This method is used for mass renaming/moving of files
 * (not directories!) which are in the directory given in
 * the first argument. The second argument is the directory 
 * where the renamed files are to be moved (it can be the 
 * same as the first directory). The fourth argument is a regular
 * expression which selects which files are to be renamed/moved.
 * The third argument is a sub command which 
 * 
 * @author ivan
 *
 */
public class MassRenameShellCommand implements ShellCommand {

	//=============================Constants======================================
	
	/**
	 * The description of the command, i.e. usage instructions.
	 */
	private static List<String> description = new ArrayList<>();
		
	/**
	 * The name of the command.
	 */
	private static final String NAME;
	
	/**
	 * The keyword for the filter command.
	 */
	private static final String FILTER = "filter";
	
	/**
	 * The keyword for the groups command.
	 */
	private static final String GROUPS = "groups";
	
	/**
	 * The keyword for the show command.
	 */
	private static final String SHOW = "show";
	
	/**
	 * The keyword for the execute command.
	 */
	private static final String EXECUTE = "execute";
	
	static {
		NAME = "massrename";
		description.add("This method is used for mass renaming/moving of files");
		description.add("(not directories!) which are in the directory given in");
		description.add("the first argument. The second argument is the directory ");
		description.add("where the renamed files are to be moved (it can be the ");
		description.add("same as the first directory). The fourth argument is a regular");
		description.add("regular expression which selects which files are to be ");
		description.add("renamed/moved. The third argument is a sub command which ");
	}
	
	//======================Method implementations================================
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentProcessor.checkArguments(env, arguments);
		String args[] = ArgumentProcessor.processArguments(arguments);
		
		if(args.length < 4) {
			env.writeln("The command must be called with at least 4 arguments.");
			return ShellStatus.CONTINUE;
		}
		String cmd = args[2];
		
		switch(cmd) {
			case FILTER:
				ShellCommand filter = new FilterSubShellCommand();
				filter.executeCommand(env, arguments);
				break;
				
			case GROUPS:
				ShellCommand groups = new GroupsSubShellCommand();
				groups.executeCommand(env, arguments);
				break;
				
			case SHOW:
				ShellCommand show = new ShowSubShellCommand();
				show.executeCommand(env, arguments);
				break;
				
			case EXECUTE:
				ShellCommand execute = new ExecuteSubShellCommand();
				execute.executeCommand(env, arguments);
				break;
	
			default:
				env.writeln("Unrecognized subcommand!");
				break;
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
