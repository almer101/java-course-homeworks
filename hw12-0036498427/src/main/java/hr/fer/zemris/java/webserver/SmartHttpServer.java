package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * This is the server which handles the requests from the user
 * and can execute scripts, preview files from the web root folder,
 * it is also capable of storing client information in form of cookies
 * so that the client sessions are saved but only for the specified 
 * amount of time.
 * 
 * @author ivan
 *
 */
public class SmartHttpServer {

	//====================================Properties==========================================
	
	/**The address of the server.*/
	@SuppressWarnings("unused")
	private String address;
	
	/**The name of the domain of the server.*/
	private String domainName;
	
	/**The port where the server listens.*/
	private int port;
	
	/**The number of working threads.*/
	private int workerThreads;
	
	/**The time the session will work (in seconds)*/
	private int sessionTimeout;
	
	/**The map of the mime types.*/
	private Map<String,String> mimeTypes = new HashMap<String, String>();
	
	/**The server thread which accepts the clients.*/
	private ServerThread serverThread;
	
	/**The pool of threads.*/
	private ExecutorService threadPool;
	
	/**The path to the root folder where the resources are.*/
	private Path documentRoot;
	
	/**The map of workers.*/
	private Map<String,IWebWorker> workersMap = new HashMap<>();
	
	/**The map of sessions.*/
	private Map<String, SessionMapEntry> sessions =
			new HashMap<String, SmartHttpServer.SessionMapEntry>();
	
	/**The generator of random numbers. Used for generating the
	 * session identifications.*/
	private Random sessionRandom = new Random();
	
	/**This is the semaphore used for exclusion of threads
	 * which operate on the {@link #sessions} map*/
	private Object semaphore = new Object();
	
	//====================================Constants==========================================
	
	/**This is the extension of the script which has to be executed.*/
	private static final String EXTENSION_SCRIPT = "smscr";
	
	/**The length of the session identifier.*/
	private static final int SID_LENGTH = 20;
	
	/**The number of minutes the cleaning thread has to wait
	 * between cleanings.*/
	private static final int WAIT_MINUTES = 5;
	
	//====================================Main method=========================================
	
	/**
	 * This is the method called when the program is run.
	 * 
	 * @param args
	 * 		command line arguments.
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("One argument was expected. The argument is"
					+ "the config file name which is in the root directory"
					+ "of this project.");
			return;
		}
		SmartHttpServer server = new SmartHttpServer(args[0]);
		server.start();
	}
	
	//==============================Constructor and its methods===============================
	
	/**
	 * This is the default constructor which gets the string
	 * of the path to the configuration file for the server.
	 * 
	 * @param configFileName
	 * 		the string path to the configuration
	 * 		file for the server.
	 */
	public SmartHttpServer(String configFileName) { 
		Properties p = new Properties();
		try {
			p.load(Files.newInputStream(Paths.get(configFileName)));
			initializeVariables(p);
		} catch (IOException | ClassNotFoundException | 
				IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
		}
		startCleaningThread();
	}
	
	/**
	 * This is the method which starts the cleaning thread.
	 * The cleaning thread periodically cleans the session 
	 * map from out-dated sessions
	 */
	private void startCleaningThread() {
		Thread t = new Thread(() -> {
			while(true) {
				try {
					Thread.sleep(WAIT_MINUTES * 60 * 1000);
				} catch (InterruptedException e) {
					continue;
				}
				synchronized (semaphore) {
					cleanSessions();
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}

	/**
	 * This is the method called by the cleaning thread which
	 * actually cleans the out-dated sessions from the 
	 * {@link #sessions} map.
	 */
	private void cleanSessions() {
		Set<String> keys = new HashSet<>(sessions.keySet());
		for(String s : keys) {
			SessionMapEntry entry = sessions.get(s);
			if(entry.validUntil < System.currentTimeMillis()/1000) {
				sessions.remove(s);
			}
		}	
	}

	/**
	 * This method initializes the variables of this 
	 * class from the specified {@link Properties} 
	 * parameter
	 * 
	 * @param p
	 * 		the object which contains properties.
	 */
	private void initializeVariables(Properties p) 
			throws IOException, ClassNotFoundException, 
			IllegalAccessException, InstantiationException {
		address = p.getProperty("server.address").trim();
		domainName = p.getProperty("server.domainName").trim();
		port = Integer.parseInt(p.getProperty("server.port"));
		workerThreads = Integer.parseInt(p.getProperty("server.workerThreads"));
		documentRoot = Paths.get(p.getProperty("server.documentRoot"));
		sessionTimeout = Integer.parseInt(p.getProperty("session.timeout"));
		initializeMimeTyoes(p);
		initializeWorkers(p);
	}

	/**
	 * This method initializes the {@link #mimeTypes}
	 * property from the specified {@link Properties}
	 * object.
	 * 
	 * @param p
	 * 		the object which contains properties.
	 */
	private void initializeMimeTyoes(Properties p) throws IOException {
		Path mimeConfigPath = Paths.get(p.getProperty("server.mimeConfig"));
		List<String> lines = Files.readAllLines(mimeConfigPath);
		for(String l : lines) {
			if(l.trim().startsWith("#")) continue;
			String[] nameValue = l.split("=");
			mimeTypes.put(nameValue[0].trim(), nameValue[1].trim());
		}
	}
	
	/**
	 * This method reads the workers properties file and 
	 * initializes workers with it.
	 * 
	 * @param p
	 * 		the properties object which contains the 
	 * 		path to the workers properties file.
	 * @throws IllegalAccessException 
	 * 		if the constructor is not accessible
	 * @throws InstantiationException 
	 * 		if the class is abstract class.
	 */
	private void initializeWorkers(Properties p) 
			throws IOException, ClassNotFoundException, 
			InstantiationException, IllegalAccessException {
		String path = p.getProperty("server.workers");
		Path workersConfig = Paths.get(path);
		List<String> lines = Files.readAllLines(workersConfig);
		for(String l : lines) {
			if(l.trim().startsWith("#")) continue;
			String[] pathName = l.split("=");
			String fqcn = pathName[1].trim();
			String name = pathName[0].trim();
			if(workersMap.containsKey(name)) {
				throw new IllegalArgumentException("The same property "
						+ "can not be defined twice.");
			}
			IWebWorker iww = getWebWorker(fqcn);
			workersMap.put(pathName[0].trim(), iww);
		}
	}

	/**
	 * This method returns the {@link IWebWorker} object which
	 * whose fully qualified class name is specified.
	 * 
	 * @param fqcn
	 * 		the fully qualified class name.
	 * @return
	 * 		the {@link IWebWorker} which has the fqcn name.
	 * @throws ClassNotFoundException
	 * 		if class is not found by the name.
	 * @throws InstantiationException
	 * 		if the class is abstract.
	 * @throws IllegalAccessException
	 * 		if the constructor is not accessible.
	 */
	private IWebWorker getWebWorker(String fqcn) 
			throws ClassNotFoundException, 
			InstantiationException, IllegalAccessException {
		Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
		@SuppressWarnings("deprecation")
		Object newObject = referenceToClass.newInstance();
		return (IWebWorker)newObject;
	}

	//===================================Method implementations===============================
	
	/**
	 * This method starts the {@link #serverThread} if 
	 * it is not already running.
	 */
	protected synchronized void start() {
		if(serverThread != null && serverThread.isAlive()) return;
		serverThread = new ServerThread();
		threadPool = Executors.newFixedThreadPool(workerThreads);
		serverThread.start();
	}
	
	/**
	 * This method stops the {@link #serverThread} and 
	 * shuts down the thread pool.
	 */
	protected synchronized void stop() {
		serverThread.stopRequested = true;
		threadPool.shutdown();
	}
	
	/**
	 * This is the implementation of the server thread
	 * which accepts clients and submits work to the pool. 
	 * 
	 * @author ivan
	 *
	 */
	protected class ServerThread extends Thread {
		
		/**The flag indicating that the stop is requested.*/
		private boolean stopRequested = false;
		
		@Override
		public void run() {
			ServerSocket serverSocket;
			try {
				serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress((InetAddress)null, port));
				serverSocket.setSoTimeout(1000);
				while(true) {
					try {
						Socket client = serverSocket.accept();
						ClientWorker cw = new ClientWorker(client);
						threadPool.submit(cw);
					} catch (SocketTimeoutException e) {
						if(stopRequested) break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 	
	}	
	
	//==================================ClientWorker class======================================
	
	/**
	 * This is the class representing the worker which serves
	 * the clients request.
	 * 
	 * @author ivan
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		
		/**The client socket.*/
		private Socket csocket;
		
		/**The socket input stream.*/
		private PushbackInputStream istream;
		
		/**The socket output stream*/
		private OutputStream ostream;
		
		/**The version of the protocol used.*/
		private String version;
		
		/**The method from the header.*/
		private String method;
		
		/**The host name.*/
		private String host;
		
		/**The parameters map.*/
		private Map<String,String> params = new HashMap<String, String>();
		
		/**The temporary parameters map.*/
		private Map<String,String> tempParams = new HashMap<String, String>();
		
		/**The persistent parameters map.*/
		private Map<String,String> permPrams = new HashMap<String, String>(); 
		
		/**The list of the cookies.*/
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		
		/**The context whose default value is null*/
		private RequestContext context = null;
		
		/**Session identification.*/
		private String SID;
				
		/**
		 * This is the constructor which gets the client
		 * socket as a parameter and initializes the {@link #csocket}
		 * property with it.
		 * 
		 * @param csocket
		 * 		the client socket.
		 */
		public ClientWorker(Socket csocket) { 
			super();
			this.csocket = csocket; 
		}
		
		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
			
				List<String> request = readRequest();
				if(request.size() < 1) {
					sendError(400, "Bad request");
					return;
				}
				String[] firstLine = request.get(0).split("\\s+");
				version = firstLine[2];
				method = firstLine[0];
				host = getHost(request);
				if(!method.equalsIgnoreCase("GET") || 
						(!firstLine[2].equalsIgnoreCase("HTTP/1.0") &&
						!firstLine[2].equalsIgnoreCase("HTTP/1.1"))) {
					sendError(400, "Bad request");
					return;
				}
				
				SessionMapEntry entry = null;
				synchronized (semaphore) {
					entry = checkSession(request);
				}
				permPrams = entry.map;
				
				String path = null;
				String l = firstLine[1];
				if(l.contains("?")) {
					int index = l.indexOf("?");
					parseParameters(l.substring(index + 1));
					path = l.substring(0, index);
				} else {
					path = l;
				}
				try {
					internalDispatchRequest(path, true);
				} catch (Exception e) {
					sendError(404, "File not found");
				}
			} catch (IOException e) {
				System.out.println("Error: " + e.getMessage());
				
			} finally {
				try {
					csocket.close();
				} catch (Exception ignorable) {}
			}
		}

		/**
		 * This method check the request and if the existing 
		 * cookies are present in the sessions map of the server
		 * those cookies are used for restoring the session of the 
		 * client(user). If no such cookie exists it is obviously a
		 * new client and the new client is registered and new cookie
		 * is added to the {@link #outputCookies} list.
		 * 
		 * @param request
		 * 		the request containing info about the cookies.
		 * @return
		 * 		the entry for the current client.
		 */
		private SessionMapEntry checkSession(List<String> request) {
			String sidCandidate = null;
			for(String l : request) {
				if(!l.startsWith("Cookie:")) continue;
				int index = l.indexOf(":");
				l = l.substring(index + 1);
				String[] cookies = l.split(";");
				sidCandidate = getCandidate(cookies);
				SessionMapEntry entry = null;
				if(sidCandidate != null) {
					entry = candidateFound(sidCandidate);
				}
				if(entry == null) {
					return registerClient();
				} else {
					entry.validUntil = 
							System.currentTimeMillis()/1000 + 
							sessionTimeout;
					return entry;
				}
			}
			return registerClient();
		}

		/**
		 * This method returns the value of the session id cookie 
		 * if such exists, otherwise <code>null</code> is returned.
		 * 
		 * @param cookies
		 * 		the string array of the cookies entries.(i.e. pairs
		 * 		name=value)
		 * @return
		 * 		the session id of the client if he is present in the
		 * 		sessions map.
		 */
		private String getCandidate(String[] cookies) {
			for(String c : cookies) {
				String[] parts = c.split("=");
				if(parts[0].trim().equals("sid")) {
					return parts[1].replace('"', ' ').trim();
				}
			}
			return null;
		}
		
		/**
		 * This method returns the entry from the the sessions map
		 * if the entry for the specified session id exists. 
		 * Otherwise <code>null</code> is returned.
		 * 
		 * @param sidCandidate
		 * 		the session id of the entry.
		 * @return
		 * 		the entry for the specified session id, or 
		 * 		<code>null</code> if such does not exist.
		 */
		private SessionMapEntry candidateFound(String sidCandidate) {
			SessionMapEntry entry = sessions.get(sidCandidate);
			if(entry == null) return null;
			if(!entry.host.equals(host)) return null;
			if(entry.validUntil < System.currentTimeMillis()/1000) {
				sessions.remove(sidCandidate);
				return null;
			}
			return entry;
		}

		/**
		 * This method registers the client into the
		 * sessions map of the {@link SmartHttpServer} class.
		 */
		private SessionMapEntry registerClient() {
			SID = generateSID();
			SessionMapEntry entry = new SessionMapEntry(SID, host, 
					System.currentTimeMillis()/1000 + sessionTimeout, 
					new ConcurrentHashMap<>()
			);
			sessions.put(SID, entry);
			RCCookie cookie = new RCCookie("sid", SID, null, host, "/", true);
			outputCookies.add(cookie);
			return entry;
		}

		/**
		 * This method generates and returns the SID.
		 * The SID is generated with SID_LENGTH upper case
		 * letters.
		 * 
		 * @return the generated SID.
		 */
		private String generateSID() {
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < SID_LENGTH; i++) {
				char c = (char)(sessionRandom.nextInt(26) + 65);
				sb.append(c);
			}
			return sb.toString();
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}
		
		/**
		 * This method determines how to process the file which
		 * is under the specified path string.
		 * 
		 * @param urlPath
		 * 		the string of the path to the file.
		 * @param directCall
		 * 		the flag indicating that the method is called directly.
		 * @throws Exception
		 * 		if an error occurs.
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall)
			     throws Exception {
			
			if(urlPath.startsWith("/ext/")) {
				checkContext();
				String className = urlPath.substring("/ext/".length()).trim();
				IWebWorker iww = getWebWorker(
						"hr.fer.zemris.java.webserver.workers." + className);
				executeWorker(iww);
				return;
			}
			
			if((urlPath.startsWith("/private/") || urlPath.startsWith("/private")) &&
					directCall) {
				sendError(404, "File not found.");
				return;
			}
			
			IWebWorker iww = workersMap.get(urlPath.trim());
			if(iww != null) {
				checkContext();
				executeWorker(iww);
				return;
			}
			
			Path requestedFile = documentRoot.resolve(urlPath.substring(1));
			if(!requestedFile.startsWith(documentRoot)) {
				sendError(403, "Forbidden");
				return;
			}
			if(!Files.exists(requestedFile) || !Files.isReadable(requestedFile)) {
				sendError(404, "File not found");
				return;
			}
			String fileName = requestedFile.getFileName().toString();
			String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
			String type = mimeTypes.get(extension);
			String mimeType = type == null ? "application/octet-stream" : type;
			
			if(extension.equals(EXTENSION_SCRIPT)) {
				parseSmartScript(requestedFile, mimeType);
			} else {
				returnStaticContent(requestedFile, mimeType);
			}
		}
		
		/**
		 * This method executes the specified worker but
		 * such that the execution is thread-safe.
		 * 
		 * @param iww a worker to execute
		 * @throws Exception
		 * 		if an error in the request processing occurs.
		 */
		private void executeWorker(IWebWorker iww) throws Exception {
			//explicit synchronization is needed for more threads
			//can at the same time call IWebWorker#processRequest method.
			synchronized (this) {
				iww.processRequest(context);
			}
			return;
		}

		/**
		 * This method parses the file with the specified path
		 * and parses it to create a {@link DocumentNode} which
		 * is then passed to the {@link SmartScriptEngine} which
		 * executes the script.
		 * 
		 * @param requestedFile
		 * 		the script which has to be executed.
		 * 				
		 * @param mimeType
		 * 		the mime type of the file.
		 * 
		 * @throws IOException
		 * 		if reading or writing to or from the file fails.
		 */
		private void parseSmartScript(Path requestedFile, String mimeType) 
				throws IOException {
			String docBody = new String(Files.readAllBytes(requestedFile));
			checkContext();
			context.setMimeType(mimeType);
			
			new SmartScriptEngine(
					new SmartScriptParser(docBody).getDocumentNode(), 
					context
			).execute();;
		}

		/**
		 * This method gives user the requested answer when the file is
		 * not a smart script. The requested answers are e.g. a picture,
		 * html file etc.
		 * 
		 * @param requestedFile
		 * 		the requested resource (file) to be displayed to user.		
		 * 
		 * @param mimeType
		 * 		the mime type of the file.
		 * 
		 * @throws IOException
		 * 		if reading or writing fails.
		 */
		private void returnStaticContent(Path requestedFile, String mimeType) 
				throws IOException {
			checkContext();
			context.setMimeType(mimeType);
			context.setContentLength(requestedFile.toFile().length());
			
			try(InputStream fis = Files.newInputStream(requestedFile)) {
				byte[] buf = new byte[1024];
				while(true) {
					int r = fis.read(buf);
					if(r<1) break;
					context.write(buf, 0, r);
				}
			}
		}

		/**
		 * This method checks if the context is initialized
		 * and if it is not initializes it. Otherwise no action
		 * is performed.
		 */
		private void checkContext() {
			if(context == null) {
				context = new RequestContext(ostream, params, 
						permPrams, outputCookies, this, tempParams);
			}
		}

		/**
		 * This method parses the parameter string and fills
		 * the map {@link #params} with it.
		 * 
		 * @param paramString
		 * 		the string to parse.
		 */
		private void parseParameters(String paramString) {
			String[] parts = paramString.split("&");
			for(String s : parts) {
				String[] nameValue = s.split("=");
				params.put(nameValue[0], nameValue.length == 1 ? "" : nameValue[1]);
			}
		}

		/**
		 * This method goes through the request and searches for
		 * the line with "Host: yyy" and if such exists returns the 
		 * "yyy". If such line does not exist returns the domain name
		 * of the server.
		 * 
		 * @param request the request from the user. 
		 * @return 
		 * 		the host.
		 */
		private String getHost(List<String> request) {
			for(String line : request) {
				if(line.trim().startsWith("Host:")) {
					int index = line.indexOf(":") + 1;
					String host = line.substring(index).trim();
					if(!host.contains(":")) return host;
					return host.substring(0, host.indexOf(":"));
				}
			}
			return domainName;
		}

		/**
		 * This method sends the error to the output stream.
		 * The status code of the error is given by the first
		 * parameter and the status text is given by the second parameter.
		 * 
		 * @param statusCode
		 * 		the status code of the error
		 * @param statusText
		 * 		the status text of the error
		 * @throws IOException
		 * 		if writing to the output stream fails.
		 */
		private void sendError(int statusCode, String statusText) 
				throws IOException {
			ostream.write(
					(version + " " + statusCode+" "+statusText+"\r\n"+
					"Server: simple java server\r\n"+
					"Content-Type: text/plain;charset=UTF-8\r\n"+
					"Content-Length: 0\r\n"+
					"Connection: close\r\n"+
					"\r\n").getBytes(StandardCharsets.US_ASCII)
				);
			ostream.flush();			
		}

		/**
		 * This method reads from the input stream and
		 * creates the byte array of the header so that
		 * {@link #getHeaderList(byte[])} can turn that byte
		 * array into list of header lines.
		 * 
		 * @return
		 * 		the list of header lines.
		 * 
		 * @throws IOException
		 * 		if reading or writing to the files fails.
		 */
		private List<String> readRequest() throws IOException {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				int state = 0;
		l:		while(true) {
					int b = istream.read();
					if(b==-1) return null;
					if(b!=13) {
						bos.write(b);
					}
					switch(state) {
					case 0: 
						if(b==13) { state=1; } else if(b==10) state=4;
						break;
					case 1: 
						if(b==10) { state=2; } else state=0;
						break;
					case 2: 
						if(b==13) { state=3; } else state=0;
						break;
					case 3: 
						if(b==10) { break l; } else state=0;
						break;
					case 4: 
						if(b==10) { break l; } else state=0;
						break;
					}
				}
				byte[] array = bos.toByteArray();
				if(array == null) return null;
				
				return getHeaderList(array);
			}

		/**
		 * This method returns the list of the header lines
		 * for easier analyzing. The specified parameter is the 
		 * byte representation of the lines from the header.
		 * 
		 * @param array
		 * 		the byte array of the header.
		 * @return
		 * 		the list of the header lines.
		 */
		private List<String> getHeaderList(byte[] array) {
			String requestStr = new String(
					array, 
					StandardCharsets.US_ASCII
			);
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for(String s : requestStr.split("\n")) {
				if(s.isEmpty()) break;
				char c = s.charAt(0);
				if(c==9 || c==32) {
					currentLine += s;
				} else {
					if(currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if(!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}
	}	
	
	//==================================SessionMapEntry Class==================================
	
	/**
	 * This class represents a session map entry.
	 * It holds the session id, the host and the value until
	 * this entry is valid.
	 * 
	 * @author ivan
	 *
	 */
	private static class SessionMapEntry {
		
		/**The session id*/
		private String sid;
		
		/**The host.*/
		private String host;
		
		/**The time until the session is valid.*/
		private long validUntil;
		
		/**The thread-safe implementation of map, 
		 * it is used for clients entries.*/
		private Map<String, String> map;
		
		/**
		 * The constructor which initializes all the parameters of this class.
		 * 
		 * @param sid
		 * 		the session id.
		 * @param host
		 * 		the host.
		 * @param validUntil
		 * 		the time until the session is valid.
		 * @param map
		 * 		the map for client entries.
		 */
		public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {
			this.sid = Objects.requireNonNull(sid);
			this.host = Objects.requireNonNull(host);
			this.validUntil = validUntil;
			this.map = map;
		}
		
		@Override
		public String toString() {
			return "sid = " + sid + 
					"; Host: " + host + 
					"; valid for: " + (validUntil - System.currentTimeMillis()/1000) + ";";
		}
	}
}
