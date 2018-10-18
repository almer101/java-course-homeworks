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
import hr.fer.zemris.java.hw07.shell.util.ArgumentProcessor;
import hr.fer.zemris.java.hw07.shell.util.FilesProcessor;

/**
 * This command is called by the massrename command and
 * it filters through the files in the specified directory
 * and writes all files which match the specified mask.
 * 
 * @author ivan
 *
 */
public class FilterSubShellCommand implements ShellCommand {

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
		NAME = "filter";
		description.add("This command is called by the massrename command and");
		description.add("it filters through the files in the specified directory");
		description.add("and writes all files which match the specified mask.");
	}
	
	//======================Method implementations====================================

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		//the arguments don't have to be checked if null because only
		//massrename command can call this command and it has checked
		//the arguments
		String args[] = ArgumentProcessor.processArguments(arguments);
		if(args.length != 4) {
			env.writeln("The filter subcommand does not expect any additional "
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
			writeNames(processor.select(), env);
			
		} catch (IOException e) {
			env.writeln("An error occurred during listing the files in "
					+ "the directory.");
			
		} catch (PatternSyntaxException e) {
			env.writeln("The mask syntax is not valid!");
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * This method selects the files from the specified stream
	 * which are selected by the specified mask.
	 * 
	 * @param files
	 * 		stream of files in the directory.
	 * 	
	 * @param mask
	 * 		mask to select files.
	 */
	private void writeNames(List<String> list, Environment env) {
		list.forEach(name -> env.writeln(name));
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
