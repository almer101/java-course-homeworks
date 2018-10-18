package hr.fer.zemris.java.hw07.shell.subcommands;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.commands.MassRenameShellCommand;
import hr.fer.zemris.java.hw07.shell.parser.NameBuilderParserException;
import hr.fer.zemris.java.hw07.shell.util.ArgumentProcessor;
import hr.fer.zemris.java.hw07.shell.util.FilesProcessor;

/**
 * This command is executed by the {@link MassRenameShellCommand},
 * it takes 5 arguments, where the first 4 are as described in 
 * {@link MassRenameShellCommand}. The fifth one is the expression
 * (i.e. the naming scheme).
 * 
 * @author ivan
 *
 */
public class ShowSubShellCommand implements ShellCommand {
	
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
		NAME = "show";
		description.add("This command is executed by the MassRenameShellCommand,");
		description.add("it takes 5 arguments, where the first 4 are as described in");
		description.add("MassRenameShellCommand. The fifth one is the expression");
		description.add("(i.e. the naming scheme).");
	}
	
	//======================Method implementations====================================
		
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String args[] = ArgumentProcessor.processArguments(arguments);
		if(args.length != 5) {
			env.writeln("The show command expects 5 arguments. See the "
					+ "description of the command for details.");
			return ShellStatus.CONTINUE;
		}
		
		Path dir1 = env.getCurrentDirectory().resolve(Paths.get(args[0]));
		
		try {
			FilesProcessor processor = new FilesProcessor(env, args[3], args[4], dir1, null);
			processor.writeNames();
		} catch (IOException e) {
			env.writeln("An error occurred while listing the files.");
			
		} catch (PatternSyntaxException e) {
			env.writeln("The mask is invalid.");
			
		} catch (NameBuilderParserException e) {
			env.writeln("An error occured during parsing the "
					+ "expression: " + e.getMessage());
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
