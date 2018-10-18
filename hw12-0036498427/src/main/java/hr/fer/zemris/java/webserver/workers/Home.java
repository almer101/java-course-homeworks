package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This worker checks if the entry with the key 
 * <code>bgcolor</code> exists and if yes copies its
 * value into temporary parameters. If it does not
 * exist it should put value ""
 * 
 * @author ivan
 *
 */
public class Home implements IWebWorker {

	/**
	 * The default color to put in temporary parameters
	 * if the bgcolor entry does not exist in persistent
	 * parameters.
	 */
	private static final String DEFAULT_COLOR = "7F7F7F";
	
	@Override
	public void processRequest(RequestContext context) throws Exception {
		String color = context.getPersistentParameter("bgcolor");
		if(color == null) {
			color = DEFAULT_COLOR;
		}
		context.setTemporaryParameter("background", color);
		context.getDispatcher().dispatchRequest("/private/home.smscr");
	}

}
