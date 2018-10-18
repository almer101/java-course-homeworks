package hr.fer.zemris.java.webserver;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;
import java.io.IOException; 
import java.io.OutputStream; 
import java.nio.file.Files; 
import java.nio.file.Paths; 
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is the demonstration program, which demonstrates the
 * work of the {@link RequestContext} class.
 * 
 * @author ivan
 *
 */
public class DemoRequestContext {
	
	/**
	 * This is the method called when the program is run.
	 * 
	 * @param args
	 * 		the command line arguments.
	 * 
	 * @throws IOException
	 * 		if writing to the output stream in the 
	 * 		context fails.
	 */
	public static void main(String[] args) throws IOException {
		demo1("primjer1.txt", "ISO-8859-2");
		demo1("primjer2.txt", "UTF-8"); 
		demo2("primjer3.txt", "UTF-8");
	}
	
	/**
	 * This is the demonstration method 1.
	 * 
	 * @param filePath	the path to the file.
	 * @param encoding	the encoding to use.
	 * @throws IOException	
	 * 		if reading of writing to and from the output stream
	 * 		fails.
	 */
	private static void demo1(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, 
				new HashMap<String, String>(), 
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>()); 
		rc.setEncoding(encoding);
        rc.setMimeType("text/plain");
        rc.setStatusCode(205);
        rc.setStatusText("Idemo dalje");
        // Only at this point will header be created and written...
        rc.write("Čevapčići i Šiščevapčići.");
        os.close();
	}
	
	/**
	 * This is the demonstration method 2.
	 * 
	 * @param filePath	the path to the file.
	 * @param encoding	the encoding to use.
	 * @throws IOException	
	 * 		if reading of writing to and from the output stream
	 * 		fails.
	 */
	private static void demo2(String filePath, String encoding) throws IOException {

		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(), new HashMap<String, String>(),
		new ArrayList<RequestContext.RCCookie>()); rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.addRCCookie(new RCCookie("korisnik", "perica", 3600, "127.0.0.1", "/", false));
		rc.addRCCookie(new RCCookie("zgrada", "B4", null, null, "/", false)); 
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}
	
}
