package hr.fer.zemris.java.webserver.workers;

import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This is the worker which generates an HTML table
 * with obtained parameters from the context.
 * 
 * @author ivan
 *
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.setMimeType("text/html");
		
		StringBuilder sb = new StringBuilder(
				"<html>\r\n" + 
				"	<head>\r\n" + 
				"		<title>Parameters in the form of table</title>\r\n" +
				"		<style>\r\n" +
				"			table, th, td {border: 1px solid black; border-collapse: collapse;}\r\n" +
				"			th {text-align: left;}\r\n" + 
 				"		</style>" +
				"	</head>\r\n" + 
				"	<h1>Table of parameters</h1>" +
				"	<body>\r\n" + 
				"		<table style=\"width=100%\">\r\n" 
		);
		Set<String> keys = context.getParameterNames();
		for(String s : keys) {
			String param = context.getParameter(s);
			sb.append(
					"<tr>\r\n" +
					"	<th>"+ s +"</th>\r\n" + 
					"	<th>" + param + "</th>\r\n" +
					"</tr>\r\n"
			);
		}
		sb.append(
				"</table>\r\n" + 
				"</body>\r\n" + 
				"</html>\r\n"
		);
		String s = sb.toString();
		context.write(s);
	}

}
