package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This worker checks if the bgcolor parameter was received
 * and if it is and it is a valid hex-encoded color, sets the 
 * new persistent parameter to that value and generates a HTML.
 * 
 * @author ivan
 *
 */
public class BgColorWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String bgcolor = context.getParameter("bgcolor");
		if(bgcolor.trim().length() == 6 &&
				isHexNumber(bgcolor)) {
			context.setPersistentParameter("bgcolor", bgcolor);
			context.setTemporaryParameter("colorChanged", "The color was updated.");
		} else {
			context.setTemporaryParameter("colorChanged", "The color was not updated.");
		}	
		context.getDispatcher().dispatchRequest("/private/colorChanged.smscr");
	}

	/**
	 * This is the method which checks if the specified string is
	 * a hex number (i.e. does it contain only hex digits.)
	 * 
	 * @param bgcolor
	 * 		the string for which to check whether it contains 
	 * 		only hex digits.
	 * @return
	 * 		<code>true</code> if the specified string
	 * 		contains only hex digits, <code>false</code>
	 * 		otherwise.
	 */
	private boolean isHexNumber(String bgcolor) {
		char[] data = bgcolor.toCharArray();
		for(int i = 0; i < data.length; i++) {
			char c = data[i];
			if((c >= '0' && c<='9') || 
					(c >= 'A' && c <= 'F') || 
					(c >= 'a' && c <= 'f')) {
				continue;
			}
			return false;
		}
		return true;
	}

}
