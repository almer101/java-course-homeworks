package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.util.ArgumentProcessor;

/**
 * This command takes only one argument and that is the
 * directory whose content the command will list.
 * Lists the names of the files and directories in the
 * directory along with some additional information
 * about the files.
 * 
 * @author ivan
 *
 */
public class LsShellCommand implements ShellCommand {

	//=============================Properties===================================
	
	/**
	 * The current environment. This is used by the {@link PrintTree}
	 * helper class which prints the tree.
	 */
	private Environment env;
		
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
		NAME = "ls";
		description.add("This command takes only one argument and");
		description.add("that is the name of the directory whose content ");
		description.add("the command is about to list. Lists the names of ");
		description.add("all files and directories in the given directory ");
		description.add("along with some other additional useful information.");
	}
	
	//======================Method implementations==============================
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentProcessor.checkArguments(env, arguments);
		this.env = env;
		String args[] = ArgumentProcessor.processArguments(arguments);
		if(args.length != 1) {
			env.writeln("The ls command must be called with one "
					+ "argument. Was " + args.length);
			return ShellStatus.CONTINUE;
		}
		
		try {
			Path currentDir = env.getCurrentDirectory();
			Files.walkFileTree(currentDir.resolve(Paths.get(args[0])), new ListDirectory());
			
		} catch (IOException e) {
			env.writeln("An error occured while printing the directory "
					+ "content : " + e.getMessage());
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

	//===========================Helper class====================================
	
	/**
	 * This is the implementation of the {@link FileVisitor}
	 * which goes through the files in the specified folder
	 * and prints the names and some additional info about them.
	 * 
	 * @author ivan
	 *
	 */
	private class ListDirectory implements FileVisitor<Path> {

		/**
		 * This variable indicates if the root directory is
		 * already processed, since nothing should be written
		 * for the root directory and only for the directories 
		 * inside it.
		 */
		private boolean isRootDirectory = true;
		
		@Override
		public FileVisitResult preVisitDirectory(Path dir, 
				BasicFileAttributes attrs) throws IOException {
			if(isRootDirectory) {
				isRootDirectory = false;
				return FileVisitResult.CONTINUE;
			} 
			printFileInfo(dir);
			return FileVisitResult.SKIP_SUBTREE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) 
				throws IOException {
			printFileInfo(file);
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
			return FileVisitResult.CONTINUE;
		}
		
		/**
		 * This method prints the info of the specified path
		 * (can be a directory or a file).
		 * 
		 * @param path
		 * 		the path to write the info for.
		 */
		private void printFileInfo(Path path) throws IOException {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			BasicFileAttributeView faView = Files.getFileAttributeView(
					path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS );
			BasicFileAttributes attributes = faView.readAttributes();
			FileTime fileTime = attributes.creationTime();
			
			String formattedDateTime = sdf.format(new Date(fileTime.toMillis())); 
			String drwx = fileDescription(path.toFile());
			String size = String.valueOf(path.toFile().length());
			String name = path.getFileName().toString();
			
			String complete = String.format("%s %10s %s %s", drwx, size, 
												formattedDateTime, name);
			env.writeln(complete);
		}

		/**
		 * This method checks if the specified file is
		 * directory, readable, writable and executable and
		 * returns the {@link String} of format "drwx" accordingly. 
		 * (e.g. if the file is writable and a directory then returns
		 * d-w-)
		 * 
		 * @return
		 * 		the {@link String} of format "drwx".
		 */
		private String fileDescription(File file) {
			StringBuilder sb = new StringBuilder();
			
			sb.append(file.isDirectory() ? "d" : "-");
			sb.append(file.canRead() ? "r" : "-");
			sb.append(file.canWrite() ? "w" : "-");
			sb.append(file.canExecute() ? "x" : "-");
			return sb.toString();
		}

	}
}
