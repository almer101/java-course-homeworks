package hr.fer.zemris.java.hw07.shell;

import java.util.List;

/**
 * This is the interface each shell command will implement
 * and defines the way to communicate with the commands.
 * 
 * @author ivan
 *
 */
public interface ShellCommand {

	/**
	 * This method executes the command.
	 * 
	 * @param env
	 * 		the reference to the environment.
	 * 
	 * @param arguments
	 * 		this represents everything that the user entered
	 * 		after the command name.
	 * 		
	 * @return
	 * 		the status command returns to shell with.
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * This method returns the name of the command.
	 * 
	 * @return
	 * 		the name of the command.
	 */
	String getCommandName();
	
	/**
	 * This method returns the description of the command.
	 * (i.e. usage instructions)
	 * 
	 * @return
	 * 		the usage instructions.
	 */
	List<String> getCommandDescription();
}
