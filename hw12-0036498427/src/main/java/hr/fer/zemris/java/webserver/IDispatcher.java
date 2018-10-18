package hr.fer.zemris.java.webserver;

/**
 * This is the request dispatcher interface which
 * models such objects which can dispatch requests.
 * 
 * @author ivan
 *
 */
public interface IDispatcher {
	
	/**
	 * This method dispatches the request depending on the
	 * specified URL. The method determines how the file with
	 * the specified URL will be processed.
	 * 
	 * @param urlPath
	 * 		the string path to the file.
	 * @throws Exception
	 * 		if an error occurs.
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
