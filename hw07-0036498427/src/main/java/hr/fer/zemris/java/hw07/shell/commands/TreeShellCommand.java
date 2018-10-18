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
 * This command is called with 1 argument 
 * (directory name) and prints the tree
 * recursively.
 * 
 * 
 * @author ivan
 *
 */
public class TreeShellCommand implements ShellCommand {
	
	//=============================Properties======================================
	
	/**
	 * The current environment. This is used by the {@link PrintTree}
	 * helper class which prints the tree.
	 */
	private Environment env;
	
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
		NAME = "tree";
		description.add("This command is called with one argument ");
		description.add("and that is the name of the directory. ");
		description.add("The command than prints the tree of that directory.");
	}

	//=========================Method implementations==============================
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentProcessor.checkArguments(env, arguments);
		this.env = env;
		String args[] = ArgumentProcessor.processArguments(arguments);
		
		if(args.length != 1 || args[0].length() == 0) {
			env.writeln("The tree command must be called with one "
					+ "argument!");
			return ShellStatus.CONTINUE;
		}
		
		try {
			Path currentDir = env.getCurrentDirectory();
			Files.walkFileTree(currentDir.resolve(Paths.get(args[0])), new PrintTree());
		} catch (IOException e) {
			env.writeln("The error occurred during walking the tree. " + 
						e.getMessage());
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

	//===============================Helper class==================================
	
	/**
	 * This is the implementation of the {@link FileVisitor}
	 * which prints the tree of the given directory.
	 * 
	 * @author ivan
	 *
	 */
	private class PrintTree implements FileVisitor<Path> {

		/**
		 * This is the number of indents to write before printing
		 * the name of the file or directory.
		 */
		private int indent = 0;
		
		/**
		 * The number of indents to increment for each deeper level
		 * of the tree.
		 */
		private static final int STEP = 2;
		
		@Override
		public FileVisitResult preVisitDirectory(Path dir, 
				BasicFileAttributes attrs) throws IOException {
			printBlanks();
			env.writeln(dir.getFileName().toString());
			indent += STEP;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) 
				throws IOException {
			printBlanks();
			env.writeln(file.getFileName().toString());
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) 
				throws IOException {
			throw new IOException("The visit of the file \"" + 
							file.getFileName() + "\" failed!"
			);
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) 
				throws IOException {
			indent -= STEP;
			return FileVisitResult.CONTINUE;
		}

		/**
		 * This method prints <b><i>indent</i></b> number of blanks.
		 * 
		 */
		private void printBlanks() {
			for(int i = 0; i < indent; i++) {
				env.write(" ");
			}
		}
	}
}
