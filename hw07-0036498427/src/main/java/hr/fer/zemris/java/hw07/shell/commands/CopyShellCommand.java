package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.util.ArgumentProcessor;

/**
 * This command expects two arguments: source file name
 * and destination file name
 * 
 * @author ivan
 *
 */
public class CopyShellCommand implements ShellCommand {
		
	//=============================Constants======================================
		
	/**
	 * The description of the command, i.e. usage instructions.
	 */
	private static List<String> description = new ArrayList<>();
		
	/**
	 * The name of the command.
	 */
	private static final String NAME;
	
	static {
		NAME = "copy";
		description.add("This command takes two arguments. The first ");
		description.add("one is the source file name and the second one ");
		description.add("is the destination file name. If the destination ");
		description.add("file exists the user will be asked if it is ok to ");
		description.add("overwrite it. The source file can not be a ");
		description.add("directory, but the destination can and it is ");
		description.add("than assumed that the user wants to copy the ");
		description.add("source file into that directory.");
	}
	
	//======================Method implementations================================
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentProcessor.checkArguments(env, arguments);
		String args[] = ArgumentProcessor.processArguments(arguments);
		
		if(args.length != 2) {
			env.writeln("The copy command must be called with 2 arguments. For "
					+ "details see the command description.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			executeCopy(env, args[0], args[1]);
		} catch (IOException e) {
			env.writeln("An error occured during copying!");
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * This method checks all necessary properties of the specified
	 * paths (e.g. is any of them a directory, is it a file) and
	 * performs the copy operation if the specified parameters are
	 * valid. The method writes the appropriate message to the 
	 * environment. (e.g. if the specified paths are not valid
	 * "The source file must not be a directory.")
	 * 
	 * @param env
	 * 		the environment where to write the info about the process.
	 * 
	 * @param source
	 * 		the path to the source file.
	 * 
	 * @param dest
	 * 		the path to the destination file.
	 * 
	 * @throws IOException
	 * 		if an error occurs during copying.
	 */
	private void executeCopy(Environment env, String source, String dest) throws IOException{
		Path currentDir = env.getCurrentDirectory();
		Path pSource = currentDir.resolve(Paths.get(source));
		Path pDest = currentDir.resolve(Paths.get(dest));
		
		if(Files.isDirectory(pSource)) {
			env.writeln("The source file must not be a directory.");
			return;
		} 
		if(Files.isDirectory(pDest)) {
			pDest = Paths.get(dest + pSource.getFileName().toString());
		}
		if(Files.exists(pDest)) {
			boolean canOverwrite = askUser(env);
			if(!canOverwrite) return;
		}
		checkDestinationDirectory(pDest, env);
		copy(pSource, pDest, env);
	}

	/**
	 * This method performs the actual copying from the source file
	 * to the destination file. The method prints out the message if
	 * something goes wrong during copying or prints out that the 
	 * copying was completed successfully. 
	 * 
	 * @param pSource
	 * 		the source file path
	 * 
	 * @param pDest
	 * 		the destination file path.
	 * 
	 * @param env
	 * 		the environment where to write the messages about
	 * 		the copying process.
	 */
	private void copy(Path pSource, Path pDest, Environment env) {
		try (InputStream is = new BufferedInputStream(
				Files.newInputStream(pSource, StandardOpenOption.READ))) {
			
			OutputStream os;
			if(pDest.toFile().exists()) {
				os = new BufferedOutputStream(
						Files.newOutputStream(pDest, 
								StandardOpenOption.TRUNCATE_EXISTING));
			} else {
				os = new BufferedOutputStream(
						Files.newOutputStream(pDest, 
								StandardOpenOption.CREATE,
								StandardOpenOption.WRITE));
			}
			byte buff[] = new byte[4096];
			while(true) {
				int numberOfBytes = is.read(buff);
				if(numberOfBytes == -1) break;
				os.write(buff, 0, numberOfBytes);
			}
			os.flush();
			os.close();
		} catch (IOException e) {
			env.writeln("An error occurred during copying.");
			return;
		}
		env.writeln("The copying was completed succesfully.");
	}

	/**
	 * This method asks user if it is all right to overwrite 
	 * an existing file, requesting the user to input Y as yes
	 * or N as no. If the user enters something other than
	 * Y or N, the user is asked again to enter and until 
	 * either Y or N are entered the process is repeated. 
	 * The method returns the boolean value whether
	 * it is all right to overwrite the file.
	 * 
	 * @param env
	 * 		environment where to write and read from.	
	 * 
	 * @return
	 * 		<code>true</code> if the file can be
	 * 		overwritten; <code>false</code> otherwise.
	 */
	private boolean askUser(Environment env) {
		while(true) {
			env.writeln("The destination file already exists. Is it ok to "
					+ "overwrite it? Y/N");
			String yesNo = env.readLine().trim();
			if(yesNo.equalsIgnoreCase("y")) return true;
			else if(yesNo.equalsIgnoreCase("n")) return false;
			env.writeln("Unrecognized input. Please type Y or N");
		}
	}
	
	/**
	 * This method checks if the destination directory exists.
	 * If it does not, creates a such directory structure.
	 * 
	 * @param pDest
	 * 		a file to be checked.
	 */
	private void checkDestinationDirectory(Path pDest, Environment env) {
		String dest = pDest.toString();
		if(dest.charAt(dest.length() - 1) == '/') {
			dest = dest.substring(0, dest.length() - 1);
		}
		int index = dest.lastIndexOf('/');
		if(index == -1) return;
		
		String dir = dest.substring(0, index);
		if(!Files.exists(Paths.get(dir))) {
			ShellCommand mkdir = new MkdirShellCommand();
			mkdir.executeCommand(env, dir);
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
