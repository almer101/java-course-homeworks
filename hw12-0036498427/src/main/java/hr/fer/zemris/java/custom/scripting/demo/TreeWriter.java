package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * This is the program which accepts the file name
 * as a single command line argument. The program
 * will open the specified file, parse it into a 
 * tree and reproduce it.
 * 
 * @author ivan
 *
 */
public class TreeWriter {

	/**
	 * This is the method called when the program is run.
	 * 
	 * @param args
	 * 		the command line arguments.
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("One argument was expected!");
			System.exit(0);
		}
		Path path = Paths.get(args[0]);
		try {
			String docBody = new String(Files.readAllBytes(path));
			SmartScriptParser p = new SmartScriptParser(docBody);
			WriterVisitor visitor = new WriterVisitor(); p.getDocumentNode().accept(visitor);
			
		} catch (IOException e) {
			System.out.println("Unable to read the file!");
			System.exit(0);
		}
	}
}
