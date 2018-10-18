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
 * This command takes one argument which is a directory.
 * The command than deletes the content of the specified
 * directory and the directory itself.
 * 
 * @author ivan
 *
 */
public class RmTreeShellCommand implements ShellCommand {
	
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
		NAME = "rmtree";
		description.add("This command takes one argument which is a directory.");
		description.add("The command than deletes the content of the specified");
		description.add("directory and the directory itself.");
	}

	//=========================Method implementations==============================
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentProcessor.checkArguments(env, arguments);
		String args[] = ArgumentProcessor.processArguments(arguments);
		
		if(args.length != 1) {
			env.writeln("The specified path to the directory to be "
					+ "removed must exist.");
			return ShellStatus.CONTINUE;
		}
		deleteDirectory(env, args[0]);
		
		env.writeln("The directory was successfully removed!");
		return ShellStatus.CONTINUE;
	}

	/**
	 * This method checks if the specified directory is valid and if it is
	 * walks the file tree and deletes the content of that directory 
	 * and the directory itself.
	 * 
	 */
	private void deleteDirectory(Environment env, String pathName) {
		Path dirToRemove = env.getCurrentDirectory().resolve(Paths.get(pathName));
		if(!Files.isDirectory(dirToRemove) || !Files.exists(dirToRemove))  {
			env.writeln("The directory you wish to be removed is non-existent or"
					+ " it is not a directory.");
			return;
		}
		try {
			Path currentDir = env.getCurrentDirectory();
			Files.walkFileTree(currentDir.resolve(dirToRemove), new RemoveDir(env));
		} catch (IOException e) {
			env.writeln("An error occurred while walking the tree.");
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

	//==============================Helper class====================================
	
	private static class RemoveDir implements FileVisitor<Path> {
		
		/**
		 * The environment of the shell.
		 */
		private Environment env;
		
		/**
		 * The constructor which initializes the private property 
		 * of this object -> environment of the shell.
		 * 
		 * @param env 
		 * 		the environment of the shell.
		 */
		public RemoveDir(Environment env) {
			super();
			this.env = env;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			Files.delete(file);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			env.writeln("The visit of the file failed!");
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			Files.delete(dir);
			return FileVisitResult.CONTINUE;
		}
		
	}
	
}
