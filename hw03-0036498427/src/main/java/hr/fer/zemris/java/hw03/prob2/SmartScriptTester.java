package hr.fer.zemris.java.hw03.prob2;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

/**
 * This program tests functionalities of
 * the {@link SmartScriptParser} class.
 * 
 * @author ivan
 *
 */
public class SmartScriptTester {

	/**
	 * This is the method called when 
	 * the user runs this program.
	 * 
	 * @param args
	 * 		command line arguments
	 */
	public static void main(String[] args) {
		
		String docBody[] = new String[3];
		
		try {
			docBody[0] = new String(
	                Files.readAllBytes(Paths.get("./examples/doc1.txt")),
	                StandardCharsets.UTF_8);
			docBody[1] = new String(
	                Files.readAllBytes(Paths.get("./examples/doc2.txt")),
	                StandardCharsets.UTF_8);
			docBody[2] = new String(
	                Files.readAllBytes(Paths.get("./examples/doc3.txt")),
	                StandardCharsets.UTF_8);
		} catch (Exception ignorable) {
		}
		
		SmartScriptParser parser[] = new SmartScriptParser[3];
		
		try {
 		  parser[0] = new SmartScriptParser(docBody[0]);
 		  parser[1] = new SmartScriptParser(docBody[1]);
 		  parser[2] = new SmartScriptParser(docBody[2]);
 		  
		} catch(SmartScriptParserException e) {
		  System.out.println("Unable to parse document!");
		  System.exit(-1);
		} catch(IllegalArgumentException e) {
		  System.out.println("If this line ever executes, you have failed this class!");
		  System.exit(-1);
		}
		
		for(int i = 0; i < 3 ; i++) {
			System.out.println("--------------------------------------------------\n"
					+ "original text : \n"
					+ "--------------------------------------------------\n");
			System.out.println(docBody[i]);
			
			System.out.println("--------------------------------------------------\n"
					+ "After parsing and printing the tree values : \n"
					+ "--------------------------------------------------\n");
			DocumentNode document = parser[i].getDocumentNode();
			String originalDocumentBody = SmartScriptParser.createOriginalDocumentBody(document);
			System.out.println(originalDocumentBody); // should write something like original
			                                          // content of docBody
			if(i != 2) {
				System.out.println("--------------------------------------------------\n"
						+ "New document : \n"
						+ "--------------------------------------------------\n");
			}
		}
	}

}
