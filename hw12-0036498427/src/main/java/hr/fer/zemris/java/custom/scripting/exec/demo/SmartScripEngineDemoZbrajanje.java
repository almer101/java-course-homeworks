package hr.fer.zemris.java.custom.scripting.exec.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * This is the demonstration program for the 
 * script zbrajanje.smscr
 * 
 * @author ivan
 *
 */
public class SmartScripEngineDemoZbrajanje {
 
	/**
	 * This is the method called when the program is run.
	 * 
	 * @param args
	 * 		command line arguments.
	 */
	public static void main(String[] args) {
		String documentBody = null;
		try {
			documentBody = new String(Files.readAllBytes(
					Paths.get(
							"./src/main/resources/hr/fer/zemris/java/custom/scripting/demo/zbrajanje.smscr")));
		} catch (IOException e) {
			System.out.println("Unable to read file.");
			System.exit(0);
		}
		
		Map<String,String> parameters = new HashMap<String, String>(); 
		Map<String,String> persistentParameters = new HashMap<String, String>(); 
		List<RCCookie> cookies = new ArrayList<>();
		parameters.put("a", "4");
		parameters.put("b", "2");
		
		// create engine and execute it 
		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
		

	}

	
}
