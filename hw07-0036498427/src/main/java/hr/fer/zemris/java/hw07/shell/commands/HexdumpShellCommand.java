package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static hr.fer.zemris.java.hw07.crypto.Util.bytetohex;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.util.ArgumentProcessor;

/**
 * This command takes one argument which is the name of the file.
 * The command than produces a hex output of the specified
 * file.
 * 
 * @author ivan
 *
 */
public class HexdumpShellCommand implements ShellCommand {

	//=============================Constants====================================
		
	/**
	 * The description of the command, i.e. usage instructions.
	 */
	private static List<String> description = new ArrayList<>();
		
	/**
	 * The name of the command.
	 */
	private static final String NAME;
	
	/**
	 * The size of the buffer for reading the files.
	 */
	private static final int BUFFER_SIZE = 16;
	
	/**
	 * The step when printing hex dump.
	 */
	private static final int STEP = 2;
	
	/**
	 * This is the byte value of the dot char.
	 */
	private static final byte DOT = 0x2E;
	
	static {
		NAME = "hexdump";
		description.add("This command takes one argument. The argument");
		description.add("is a file name. The command than produces a hex");
		description.add("output of the specified file.");
	}
	
	//======================Method implementations==============================
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentProcessor.checkArguments(env, arguments);
		String args[] = ArgumentProcessor.processArguments(arguments);
		
		if(args.length != 1 || args[0].length() == 0) {
			env.writeln("The hexdump command must be called with one argument.\n"
					+ "See description of the command for details.");
			return ShellStatus.CONTINUE;
		}
		
		Path currentDir = env.getCurrentDirectory();
		Path source = currentDir.resolve(Paths.get(args[0]));
		
		if(!Files.exists(source)) {
			env.writeln("The specified file does not exist!");
			
		} else if(Files.isDirectory(source)) {
			env.writeln("The specified file must not be a directory.");
			
		} else {
			try {
				execute(env, source);
			} catch (IOException e) {
				env.writeln("The hex dump from the file failed.");
			}
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * This method executes the command after all checks have been
	 * made.
	 * 
	 * @param env
	 * 		environment where to write.
	 * 
	 * @param source
	 * 		the source file from where to read.
	 */
	private void execute(Environment env, Path source) throws IOException {
		byte buff[] = new byte[BUFFER_SIZE];
		int rowCount = 0;
		int readBytes;
		
		InputStream is = new BufferedInputStream(
				Files.newInputStream(source, StandardOpenOption.READ));
		
		while(true) {
			readBytes = is.read(buff);
			if(readBytes == -1) break;
			printRow(Arrays.copyOf(buff, readBytes), env, rowCount);
			rowCount += readBytes;
		}
	}

	/**
	 * This method prints an entire row of the table.
	 * 
	 * @param buff
	 * 		the buffer with bytes of file.
	 * 
	 * @param env
	 * 		environment where to write.
	 * 
	 * @param rowCount
	 * 		the ordinal number of the row.
	 */
	private void printRow(byte[] buff, Environment env, int rowCount) {
		env.write(String.format("%08X: ", rowCount));
		convertBytes(buff);
		String hex = bytetohex(buff);
		printHex(hex, env);
		String text = new String(buff);
		env.writeln(text);
	}

	/**
	 * This method converts those bytes whose value is larger
	 * than 127 or less than 32 to '.'
	 * 
	 * @param buff
	 * 		the array to convert.
	 */
	private void convertBytes(byte[] buff) {
		for(int i = 0; i < buff.length; i++) {
			if(buff[i] < 32 || buff[i] > 127) {
				buff[i] = DOT;
			}
		}
	}

	/**
	 * This method prints the specified hex values
	 * but in upper case. The method also prints delimiters
	 * "|" every 8 hex numbers.
	 * 
	 * @param hex
	 * 		string of hex values.		
	 * 
	 * @param env
	 * 		environment where to write.
	 */
	private void printHex(String hex, Environment env) {
		int index = 0;
		while(index < BUFFER_SIZE * 2) {
			if(index % 16 == 0 && index != 0) env.write("|");
			if(index < hex.length()) {
				env.write(hex.substring(index, index + STEP).toUpperCase() + " ");
			} else {
				env.write(String.format("%3s", ""));
			}
			index += STEP;
		}
		env.write("| ");
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
