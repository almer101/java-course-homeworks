package hr.fer.zemris.java.hw07.shell.subcommands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw07.shell.parser.NameBuilderParserException;
import hr.fer.zemris.java.hw07.shell.util.ArgumentProcessor;
import hr.fer.zemris.java.hw07.shell.util.FilesProcessor;

/**
 * This command when executed moves or renames the files
 * which match the mask. The new name of the files is
 * determined by the expression which is the regular 
 * expression of renaming scheme.
 * 
 * @author ivan
 *
 */
public class ExecuteSubShellCommand implements ShellCommand {

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
		NAME = "execute";
		description.add("This command when executed moves or renames the files");
		description.add("which match the mask. The new name of the files is");
		description.add("determined by the expression which is the regular ");
		description.add("expression of renaming scheme.");
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
		Path dir2 = env.getCurrentDirectory().resolve(Paths.get(args[1]));
		
		if(!Files.isDirectory(dir1)) {
			env.writeln("Source path must be a directory.");
			return ShellStatus.CONTINUE;
		} 
		if(!Files.exists(dir2)) {
			//create a directory if user entered a directory which
			//is not existing
			ShellCommand mkdir = new MkdirShellCommand();
			mkdir.executeCommand(env, dir2.toAbsolutePath().normalize().toString());
		}
		if(!Files.isDirectory(dir2)) {
			env.writeln("Destination path must be a directory.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			FilesProcessor processor = 
					new FilesProcessor(env, args[3], args[4], dir1, dir2);
			//						(env, mask, expression, source, dest)
			processor.moveFiles();
			
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
