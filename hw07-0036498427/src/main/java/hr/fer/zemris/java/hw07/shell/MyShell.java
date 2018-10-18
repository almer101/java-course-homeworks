package hr.fer.zemris.java.hw07.shell;

import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw07.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CpTreeShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.ListDShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.MassRenameShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.PopDShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.PushDShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.PwdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.RmTreeShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.TreeShellCommand;
import hr.fer.zemris.java.hw07.shell.util.ArgumentProcessor;

/**
 * This is the implementation of the shell.
 * 
 * @author ivan
 *
 */
public class MyShell {
	
	/**
	 * The sorted map of command names and {@link ShellCommand}s
	 */
	private static SortedMap<String, ShellCommand> commands = new TreeMap<>();
	
	static {
		commands.put("help", new HelpShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("charset", new CharsetsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("pwd", new PwdShellCommand());
		commands.put("cd", new CdShellCommand());
		commands.put("pushd", new PushDShellCommand());
		commands.put("popd", new PopDShellCommand());
		commands.put("listd", new ListDShellCommand());
		commands.put("cptree", new CpTreeShellCommand());
		commands.put("rmtree", new RmTreeShellCommand());
		commands.put("massrename", new MassRenameShellCommand());
	}
	
	/**
	 * This is the method called when the program is run.
	 * 
	 * @param args
	 * 		the command line arguments.
	 */
	public static void main(String[] args) {
		ShellStatus status = ShellStatus.CONTINUE;		
		Environment env = new MyEnvironment(commands);
		
		try {
			while(status != ShellStatus.TERMINATE) {
				env.write(env.getPromptSymbol() + " ");
				String line = readLineOrLines(env);
				String parts[] = ArgumentProcessor.splitInputLine(line);
				ShellCommand command = commands.get(parts[0].toLowerCase());
				String arguments = parts[1];
				
				if(command != null) {
					status = command.executeCommand(env, arguments);
					continue;
				}
				env.writeln("Unrecognized command: " + parts[0]);
			}
		} catch (ShellIOException e) {
			//the program will now finish since the communication with
			//the user is no more possible.
		}
	}

	/**
	 * This method reads a line (or lines) from the environment.
	 * In case the line is finished with morelines symbol the 
	 * reading continues until the line no more ends with morelines 
	 * symbol.
	 * 
	 * @param env
	 * 		environment from where to read.
	 * 
	 * @return
	 * 		the concatenated line (if the input spanned across multiple
	 * 		lines)
	 */
	private static String readLineOrLines(Environment env) {
		String line = env.readLine();
		StringBuilder sb = new StringBuilder();
		
		while(isMultiline(line, env.getMorelinesSymbol())) {
			String actualLine = line.substring(0, 
					line.lastIndexOf(env.getMorelinesSymbol()));
			sb.append(actualLine);
			env.write(env.getMultilineSymbol() + " ");
			line = env.readLine();
		}
		sb.append(line);
		
		return sb.toString();
	}

	/**
	 * This method checks if the line ends with morelines symbol,
	 * i.e. is it a multiline input.
	 * 
	 * @param line
	 * 		line to be checked.		
	 * 
	 * @param morelinesSymbol
	 * 		the symbol which represents that input will span across
	 * 		multiple lines.
	 * 
	 * @return
	 * 		<code>true</code> if the input is multiline input.
	 *		<code>false</code> otherwise.
	 */
	private static boolean isMultiline(String line, char morelinesSymbol) {
		return line.trim().endsWith(String.valueOf(morelinesSymbol));
	}
}
