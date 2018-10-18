package hr.fer.zemris.java.webserver;

/**
 * This is the interface models objects which can process
 * current request. The one method declared gets {@link RequestContext}
 * as a parameter and it is expected to create a content for client.
 * 
 * @author ivan
 *
 */
public interface IWebWorker {

	/**
	 * This is the method which is expected to create
	 * a content for a client given the specified
	 * {@link RequestContext}
	 * 
	 * @param context
	 * 		the request context which has the 
	 * 		output stream where to write the answer 
	 * 		to the user. 
	 * @throws Exception
	 * 		if an error occurs.
	 */
	public void processRequest(RequestContext context) throws Exception;
}
