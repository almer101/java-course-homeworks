package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.util.ArgumentProcessor;

/**
 * This command takes two arguments. The first one is the path
 * to the source file tree and the second is the destination
 * file tree. The command copies the source tree to the 
 * destination tree. Example:
 * source: "name1/t.txt"
 * destination: "name2/name3"
 * 
 * @author ivan
 *
 */
public class CpTreeShellCommand implements ShellCommand {

	//=============================Constants=======================================

	/**
	 * The description of the command, i.e. usage instructions.
	 */
	private static List<String> description = new ArrayList<>();
	
	/**
	 * The name of the command.
	 */
	private static final String NAME;

	static {
		NAME = "cptree";
		description.add("This command takes two arguments. The first one is the");
		description.add("path to the source file tree and the second is the");
		description.add("destination file tree. The command copies the source tree");
		description.add("to the destination tree.");
	}

	//=========================Method implementations==============================
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentProcessor.checkArguments(env, arguments);
		String args[] = ArgumentProcessor.processArguments(arguments);
		
		if(args.length != 2) {
			env.writeln("The cptree command must be called with two arguments. See"
					+ " the description of the command for details.");
		}
		Path currentDir = env.getCurrentDirectory();
		Path source = currentDir.resolve(Paths.get(args[0]));
		Path dest = currentDir.resolve(Paths.get(args[1]));
		
		if(!Files.exists(source)) {
			env.writeln("The source file does not exist!");
			return ShellStatus.CONTINUE;
		}
		if(!Files.exists(dest)) {
			
			String destString = dest.toAbsolutePath().normalize().toString();
			Path oneDirLess = Paths.get(dest.toString()
										.substring(0, destString.lastIndexOf("/"))
			);
			if(!Files.exists(oneDirLess)) {
				env.writeln("The destination is not existent!");
				return ShellStatus.CONTINUE;
			}
			copyDirectory(source ,destString, env);
			
		} else if(Files.exists(dest)) {
			String destString = dest.toAbsolutePath().normalize().toString();
			if(Files.isDirectory(source)) {
				destString += "/" + source.getFileName();
			}
			copyDirectory(source, destString, env);
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * This method copies the whole source directory to the
	 * specified destination.
	 * 
	 * @param source
	 * 		the source directory to copy.		
	 * 
	 * @param destString
	 * 		the string representation of the destination directory.		
	 * 
	 * @param env
	 * 		the environment of the shell
	 */
	private void copyDirectory(Path source, String destString, Environment env) {
		try {
			Files.walkFileTree(source, new CopyDirectory(destString, env));
		} catch (IOException e) {
			env.writeln("An error occurred while walking the tree!");
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
	
	//=========================Helper class========================================
	
	private static class CopyDirectory implements FileVisitor<Path> {

		/**
		 * The pattern of the destination path. 
		 * Only file names are added to this path pattern.
		 */
		private String destPattern;
		
		/**
		 * The environment of the shell.
		 */
		private Environment env;
		
		/**
		 * This is the command to copy files.
		 */
		private ShellCommand copy;
		
		/**
		 * This is the command to create new directories.
		 */
		private ShellCommand mkDir;
		
		/**
		 * The flag indicating that the file walker is in entering the
		 * root directory.
		 */
		private boolean enteringRootDirectory = true;
		
		/**
		 * The number of entered directories, not counting
		 * the root directory.
		 */
		private int enteredDirectories = 0;
		
		/**
		 * This constructor initializes the patter of the destination
		 * path.
		 * 
		 * @param destPattern
		 * 		the initial value of the destination directory pattern.
		 * 
		 * @param env 
		 * 		the environment of the shell.
		 */
		public CopyDirectory(String destPattern, Environment env) {
			super();
			this.destPattern = destPattern;
			this.env = env;
			this.copy = new CopyShellCommand();
			this.mkDir = new MkdirShellCommand();
		}
		
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			if(!enteringRootDirectory) {
				destPattern += "/" + dir.getFileName();
				enteredDirectories++;
			}
			enteringRootDirectory = false;
			mkDir.executeCommand(env, destPattern);
			
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			String arguments = "\"" + file.toAbsolutePath().normalize().toString() + "\"" + 
								" \"" + destPattern + "/" + file.getFileName() + "\""; 
			copy.executeCommand(env, arguments);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			env.writeln("The visit of the file failed.");
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			if(enteredDirectories != 0) {
				destPattern = destPattern.substring(0, destPattern.lastIndexOf("/"));
			}
			return FileVisitResult.CONTINUE;
		}
	}
}
