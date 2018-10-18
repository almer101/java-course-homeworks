package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This is the worker which accepts two parameters,
 * <code>a</code> and <code>b</code> and treats their
 * values as integers.
 * 
 * @author ivan
 *
 */
public class SumWorker implements IWebWorker {

	/**This is the name of the first variable.*/
	private static final String NAME_A = "a";
	
	/**This is the name of the second variable.*/
	private static final String NAME_B = "b";
	
	/**The default value for the parameter a.*/
	private static final int DEFAULT_A = 1;
	
	/**The default value for the parameter b.*/
	private static final int DEFAULT_B = 2;
	
	@Override
	public void processRequest(RequestContext context) throws Exception {
		String stringA = context.getParameter(NAME_A);
		String stringB = context.getParameter(NAME_B);
		int a = getNumber(stringA, DEFAULT_A);
		int b = getNumber(stringB, DEFAULT_B);;
	
		int result = a + b;
		context.setTemporaryParameter("zbroj", String.valueOf(result));
		context.setTemporaryParameter(NAME_A, String.valueOf(a));
		context.setTemporaryParameter(NAME_B, String.valueOf(b));
		
		context.getDispatcher().dispatchRequest("/private/calc.smscr");
	}

	/**
	 * This method tries to parse the given string into
	 * an integer and returns the parsed value if parsing
	 * succeeds. Otherwise returns the default value.
	 * 
	 * @param string
	 * 		string to parse
	 * @param defaultNumber
	 * 		the default value to return.
	 * @return
	 * 		the value of the number (parsed or default)
	 */
	private int getNumber(String string, int defaultNumber) {
		try {
			return string == null ? defaultNumber : Integer.parseInt(string);
		} catch (NumberFormatException e) {
			return defaultNumber;
		}
	}
}
