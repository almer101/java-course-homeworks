package hr.fer.zemris.java.hw07.shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.SortedMap;

/**
 * This is the implementation of the environment which
 * is used when the shell is operated through 
 * {@link System}.in. The inputs are scanned using
 * {@link Scanner} and the output is printed to the 
 * {@link System}.out.
 * 
 * @author ivan
 *
 */
public class MyEnvironment implements Environment {
	
	//================================Properties==================================
	
	/**
	 * This is the source where to read from.
	 */
	private BufferedReader br;
	
	/**
	 * This is the destination, where to write the output.
	 */
	private BufferedWriter bw;
	
	/**
	 * This is the sorted map of the commands.
	 */
	private SortedMap<String, ShellCommand> commands;
	
	/**
	 * The symbol written on the beginning of every
	 * multi line command.
	 */
	private Character multilineSymbol;
	
	/**
	 * The symbol written on the beginning of every 
	 * new command line.
	 */
	private Character promptSymbol;
	
	/**
	 * This is the symbol written by user when 
	 * the command line spans across multiple lines.
	 */
	private Character morelinesSymbol;
	
	/**
	 * The current working directory.
	 */
	private Path currentDirectory;
	
	/**
	 * The map of the shared data. The data is shared among
	 * the commands.
	 */
	private Map<String, Object> sharedData;
	
	//==============================Constants====================================
	
	private static final Character MULTILINE_SYMBOL_DEFAULT = '|';
	private static final Character MORELINES_SYMBOL_DEFAULT = '\\' ;
	private static final Character PROMPT_SYMBOL_DEFAULT = '>' ;
	private static final InputStream STANDARD_INPUT = System.in;
	private static final OutputStream STANDARD_OUTPUT = System.out;
	
	//==============================Constructors==================================
	
	/**
	 * This constructor gets initial values for all properties
	 * except scanner which is initialized in the constructor.
	 * (i.e. outside {@link Scanner} cannot be sent to this 
	 * object)
	 * 
	 * @param commands
	 * 		the initial commands map.
	 * 
	 * @param multilineSymbol
	 * 		the initial multiline symbol.
	 * 
	 * @param promptSymbol
	 * 		the initial prompt symbol.		
	 * 
	 * @param morelinesSymbol
	 * 		the initial morelines symbol.
	 * 
	 * @param is
	 * 		the input stream from where to read.
	 * 
	 * @param os
	 * 		the output stream where to write.
	 */
	public MyEnvironment(SortedMap<String, ShellCommand> commands, 
			Character multilineSymbol, Character promptSymbol,
			Character morelinesSymbol, InputStream is, OutputStream os) {
		
		this.commands = commands;
		this.multilineSymbol = multilineSymbol;
		this.promptSymbol = promptSymbol;
		this.morelinesSymbol = morelinesSymbol;
		this.currentDirectory = Paths.get(".")
									.toAbsolutePath()
									.normalize();
		this.sharedData = new HashMap<>();
		br = new BufferedReader(new InputStreamReader(is));
		bw = new BufferedWriter(new OutputStreamWriter(os));
	}

	/**
	 * This constructor gets the sorted map of commands for 
	 * the initial value of the such property of this class.
	 * Values of other properties are set to default values.
	 * 
	 * @param commands
	 * 		the initial commands map.
	 */
	public MyEnvironment(SortedMap<String, ShellCommand> commands) {
		this(commands, MULTILINE_SYMBOL_DEFAULT, 
				PROMPT_SYMBOL_DEFAULT, MORELINES_SYMBOL_DEFAULT, 
				STANDARD_INPUT, STANDARD_OUTPUT);
	}

	//=========================Method implementations=============================
	
	@Override
	public String readLine() throws ShellIOException {
		try {
			return br.readLine();
		} catch (IOException e) {
			throw new ShellIOException("An error occurred during "
					+ "reading!");
		}
	}

	@Override
	public void write(String text) throws ShellIOException {
		try {
			bw.write(text);
			bw.flush();
		} catch (IOException e) {
			throw new ShellIOException("An error occurred during "
					+ "writing!");
		}
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		write(text + "\n");
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		Objects.requireNonNull(symbol, "The specified Multiline symbol "
				+ "must not be null!");
		multilineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		Objects.requireNonNull(symbol ,"The specified Prompt symbol "
				+ "must not be null!");
		promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		Objects.requireNonNull(symbol, "The specified Morelines "
				+ "symbol must not be null!");
		morelinesSymbol = symbol;
	}

	@Override
	public Path getCurrentDirectory() {
		return Paths.get(currentDirectory.toString());
	}

	@Override
	public void setCurrentDirectory(Path path) {
		Objects.requireNonNull(path, 
				"The specified path must not be null!"
		);
		if(!Files.exists(path)) {
			throw new IllegalArgumentException("Specified directory does "
					+ "not exist!");
		}
		currentDirectory = path.toAbsolutePath().normalize();
	}

	@Override
	public Object getSharedData(String key) {
		return sharedData.get(key);
	}

	@Override
	public void setSharedData(String key, Object value) {
		Objects.requireNonNull(key);
		sharedData.put(key, value);
	}
}
