package hr.fer.zemris.java.hw07.shell;

import java.nio.file.Path;
import java.util.SortedMap;

/**
 * This interface defines a contract through which
 * the user communicates with the shell implementation.
 * 
 * @author ivan
 *
 */
public interface Environment {
	
	/**
	 * This method reads a line from the console and
	 * returns the read line.
	 * 
	 * @return
	 * 		the read line.
	 * 
	 * @throws ShellIOException
	 * 		if the reading fails.
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * This method writes the specified text to the
	 * user (console) without writing a new line 
	 * character at the end of the text.
	 * 
	 * @param text
	 * 		the text to be written to the user.		
	 * 
	 * @throws ShellIOException
	 * 		if the writing to the user fails.
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * This method writes the specified text to the
	 * user (console) and writes a new line 
	 * character at the end of the text.
	 * 
	 * @param text
	 * 		the text to be written to the user.
	 * 
	 * @throws ShellIOException
	 * 		if the writing to the user fails.
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * This method returns the unmodifiable map of 
	 * command names and commands. User cannot delete commands
	 * by clearing the returned map,
	 * 
	 * @return
	 * 		the unmodifiable map of commands and their names.
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * This method gets and returns the current
	 * multi-line symbol.
	 * 
	 * @return
	 * 		the current value of the multi-line
	 * 		symbol.
	 */
	Character getMultilineSymbol();
	
	/**
	 * This method sets the value of the multi-line
	 * symbol to the specified value.
	 * 
	 * @param symbol
	 * 		the new multi-line symbol.
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * This method returns the value of the current
	 * prompt symbol.
	 * 
	 * @return
	 * 		the current prompt symbol.
	 */
	Character getPromptSymbol();
	
	/**
	 * This method sets the current prompt symbol to
	 * the specified value.
	 * 
	 * @param symbol
	 * 		the new value of the prompt symbol.
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * This method gets and returns the current
	 * more-lines symbol.
	 * 
	 * @return
	 * 		the current more-lines symbol
	 */
	Character getMorelinesSymbol();
	
	/**
	 * This method sets the value of the current
	 * more-lines symbol to the specified value.
	 * 
	 * @param symbol
	 * 		the new value of the more lines symbol.
	 */
	void setMorelinesSymbol(Character symbol);
	
	/**
	 * This method returns the normalized path to the 
	 * current directory. Upon the running of the program
	 * the method will transform the "." into an absolute and
	 * normalized path and return such.
	 * 
	 * @return
	 * 		the absolute normalized path to the current directory.
	 */
	Path getCurrentDirectory();
	
	/**
	 * This method sets the current working directory to the
	 * specified one if such exists. If the specified path
	 * does not exist the method throws and Exception. 
	 * 
	 * @param path
	 * 		the path to the directory which is about to
	 * 		become a working directory after the execution
	 * 		of this method.
	 */
	void setCurrentDirectory(Path path);
	
	/**
	 * This method returns the shared data for the
	 * specified key. The key is needed because the
	 * implementation of this interface must use a map
	 * to store shared data. If the key which does not
	 * exist is provided method returns null.
	 * 
	 * @param key
	 * 		the key for the wanted shared data.
	 * 
	 * @return
	 * 		the value for the specified key if such
	 * 		exists. <code>null</code> otherwise.
	 */
	Object getSharedData(String key);
	
	/**
	 * This method adds a new shared data entry to
	 * the map. The key must not be null.
	 * 
	 * @param key
	 * 		the key of the entry.	
	 * 
	 * @param value
	 * 		the value of the entry.
	 */
	void setSharedData(String key, Object value);
}
