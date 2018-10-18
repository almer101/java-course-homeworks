package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
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
 * This command takes one or two arguments. The first argument
 * is mandatory and it is the path to some file. The second 
 * argument is charset name which should be used to interpret 
 * chars from bytes.
 * 
 * @author ivan
 *
 */
public class CatShellCommand implements ShellCommand {
		
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
		NAME = "cat";
		description.add("This command takes one or two arguments. The first ");
		description.add("one is mandatory and it is the path to some file. The ");
		description.add("second one is charset name which sould be uset to ");
		description.add("interpret chars from bytes.");
	}
	
	//======================Method implementations==============================
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentProcessor.checkArguments(env, arguments);
		String args[] = ArgumentProcessor.processArguments(arguments);
		
		if(args.length == 1 && args[0].length() == 0) {
			env.writeln("The cat command must be called with one argument.");
			
		}else if(args.length == 2) {
			executeWithTwoArguments(args[0], args[1], env);
			
		} else if(args.length == 1) {
			executeWithTwoArguments(args[0], Charset.defaultCharset().name(), env);
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
	
	/**
	 * This method reads the content from the file with specified
	 * path and writes it to the environment.
	 * 
	 * @param pathName
	 * 		the path name of the file which we want to write.
	 * 
	 * @param charset
	 * 		the charset to use.
	 */
	private void executeWithTwoArguments(String pathName, String charset, Environment env) {
		try {
			Path currentDir = env.getCurrentDirectory();
			Path file = currentDir.resolve(Paths.get(pathName));
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
							new BufferedInputStream(
									new FileInputStream(file.toString())),charset)
			);
			
			char buff[] = new char[4096];
			while(br.read(buff) != -1) {
				env.writeln(String.copyValueOf(buff));
			}
			br.close();
		} catch (IOException e) {
			env.writeln("The reading of the file failed.");
		}
	}

}
