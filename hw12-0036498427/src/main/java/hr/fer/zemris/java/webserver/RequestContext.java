package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * This is the context which manages cookies and parameters
 * and writes to the output stream given in the constructor.
 * The encoding can be changed but only before the header was
 * generated. 
 * 
 * @author ivan
 *
 */
public class RequestContext {

	//===============================Constants======================================
	
	/**The default encoding of the context.*/
	private String DEFAULT_ENCODING = "UTF-8";
	
	/**The default status code.*/
	private int DEFAULT_STATUS_CODE = 200;
	
	/**The default status text*/
	private String DEFAULT_STATUS_TEXT = "OK";
	
	/**The default mime type*/
	private String DEFAULT_MIME_TYPE = "text/html";
	
	//===============================Properties======================================
	
	/**The output stream where to write.*/
	private OutputStream outputStream;
	
	/**The char-set to use when writing.*/
	private Charset charset;
	
	/**The write-only property which is the encoding to use.*/
	private String encoding = DEFAULT_ENCODING;
	
	/**The write-only property which represents the status code.*/
	private int statusCode = DEFAULT_STATUS_CODE;
	
	/**The write-only property which represents the text matched with
	 * status.
	 */
	private String statusText = DEFAULT_STATUS_TEXT;
	
	/**The default mime type.*/
	private String mimeType = DEFAULT_MIME_TYPE;
	
	/**The read-only map of parameters.*/
	private Map<String,String> parameters;
	
	/**The read-write map of temporary parameters.*/
	private Map<String,String> temporaryParameters;
	
	/**The read-write map of persistent parameters.*/
	private Map<String,String> persistentParameters;
	
	/**The list of all the output cookies.*/
	private List<RCCookie> outputCookies;
	
	/**The flag indicating whether the header was generated.*/
	private boolean headerGenerated = false;
	
	/**The work dispatcher.*/
	private IDispatcher dispatcher;
	
	/**The length of the content which is shown to the user (in bytes)*/
	private Long contentLength = null;
	
	//===============================Constructor=====================================
	
	/**
	 * The constructor with initial values of the maps and output stream. The 
	 * specified output stream must not be <code>null</code> while other
	 * parameters if <code>null</code> are treated as empty maps/lists.
	 * 
	 * @param outputStream
	 * 		the output stream where to write.
	 * @param parameters
	 * 		the parameters map.
	 * @param persistentParameters
	 * 		the persistent parameters map.
	 * @param outputCookies
	 * 		the output cookies list.
	 */
	public RequestContext(OutputStream outputStream,  Map<String,String> parameters, 
			Map<String,String> persistentParameters, List<RCCookie> outputCookies) {
		this(outputStream, parameters, 
				persistentParameters, outputCookies, null, null);
	}
	
	/**
	 * The constructor with initial values of the maps and output stream. The 
	 * specified output stream must not be <code>null</code> while other
	 * parameters if <code>null</code> are treated as empty maps/lists.
	 * Additionally the constructor gets the reference to the work dispatcher
	 * and the temporary parameters map.
	 * 
	 * @param outputStream
	 * 		the output stream where to write.
	 * @param parameters
	 * 		the parameters map.
	 * @param persistentParameters
	 * 		the persistent parameters map.
	 * @param outputCookies
	 * 		the output cookies list.
	 * @param dispatcher
	 * 		the work dispatcher
	 * @param temporaryParameters
	 * 		the temporary parameters map.
	 */
	public RequestContext(OutputStream outputStream,  Map<String,String> parameters, 
			Map<String,String> persistentParameters, List<RCCookie> outputCookies, 
			IDispatcher dispatcher, Map<String, String> temporaryParameters) {
		
		this.outputStream = Objects.requireNonNull(outputStream);
		this.parameters = parameters == null ? new HashMap<>() : parameters;
		this.persistentParameters = persistentParameters == null ?
				new HashMap<>() : persistentParameters;
		this.outputCookies = outputCookies == null ? 
				new ArrayList<>() : outputCookies;
		this.dispatcher = dispatcher;
		this.temporaryParameters = temporaryParameters == null ? 
				new HashMap<>() : temporaryParameters;
	}
	
	//===========================Getters and setters=================================
	
	/**
	 * This method sets the value of the encoding
	 * parameter but only if the header is not yet generated. 
	 * Otherwise throws a {@link RuntimeException}.
	 * 
	 * @param encoding
	 * 		the encoding to use.
	 */
	public void setEncoding(String encoding) {
		checkHeaderGenerated();
		this.encoding = Objects.requireNonNull(encoding);
		charset = Charset.forName(encoding);
	}

	/**
	 * This method sets the value of the {@link #statusCode}
	 * parameter but only if the header is not yet generated. 
	 * Otherwise throws a {@link RuntimeException}.
	 * 
	 * @param statusCode
	 * 		the status code.
	 */
	public void setStatusCode(int statusCode) {
		checkHeaderGenerated();
		this.statusCode = statusCode;
	}

	/**
	 * This method sets the value of the {@link #statusText}
	 * parameter but only if the header is not yet generated. 
	 * Otherwise throws a {@link RuntimeException}.
	 * 
	 * @param statusText
	 * 		the status text
	 */
	public void setStatusText(String statusText) {
		checkHeaderGenerated();
		this.statusText = Objects.requireNonNull(statusText);
	}

	/**
	 * This method sets the value of the {@link #mimeType}
	 * parameter but only if the header is not yet generated. 
	 * Otherwise throws a {@link RuntimeException}.
	 * 
	 * @param mimeType
	 * 		the mime type
	 */
	public void setMimeType(String mimeType) {
		checkHeaderGenerated();
		this.mimeType = Objects.requireNonNull(mimeType);
	}
	
	/**
	 * This method returns the value of the property
	 * {@link #dispatcher}.
	 * 
	 * @return
	 * 		the value of the property {@link #dispatcher}.
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}
	
	/**
	 * This method sets the value of the {@link #contentLength}
	 * parameter but only if the header is not yet generated.
	 * Otherwise throws a {@link RuntimeException}.
	 * 
	 * @param contentLength
	 * 		the length of the content
	 */
	public void setContentLength(Long contentLength) {
		checkHeaderGenerated();
		this.contentLength = contentLength;
	}
	
	//=========================Method implementations================================
	
	/**
	 * This method checks if the header is generated and throws 
	 * an exception if it is.
	 */
	private void checkHeaderGenerated() {
		if(headerGenerated) {
			throw new RuntimeException("The header is already generated!");
		}
	}
	
	/**
	 * This method adds the specified cookie to the list of cookies.
	 * If the header is already generated and the user tries to 
	 * change the cookies (i.e. add another cookie) the method will
	 * throw {@link RuntimeException}.
	 * 
	 * @param cookie
	 * 		a cookie to add.
	 */
	public void addRCCookie(RCCookie cookie) {
		checkHeaderGenerated();
		outputCookies.add(cookie);
	}
	
	/**
	 * This method returns the value from the map under the specified key
	 * or <code>null</code> if no value under that key exists.
	 * 
	 * @param name
	 * 		the key of the entry in the map.
	 * 
	 * @return
	 * 		the value under the specified key.
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	/**
	 * This method retrieves all the names of all parameters in the
	 * {@link #parameters} map. The returned set is unmodifiable.
	 * 
	 * @return
	 * 		the set of all the names of all the parameters in the
	 * 		{@link #parameters} map.
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(
				new HashSet<>(parameters.keySet()));
	}
	
	/**
	 * This method retrieves the value from the {@link #persistentParameters}
	 * map under the specified key and returns it. The method returns 
	 * <code>null</code> if no association exists.
	 * 
	 * @param name
	 * 		the key of the entry in the map.		
	 * 
	 * @return
	 * 		the value under the specified key of <code>null</code> if
	 * 		no association under the specified key exists.
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}
	
	/**
	 * This method retrieves all the names of all parameters in the
	 * {@link #persistentParameters} map. The returned set is unmodifiable.
	 * 
	 * @return
	 * 		the set of all the names of all the parameters in the
	 * 		{@link #persistentParameters} map.
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(
				new HashSet<>(persistentParameters.values()));
	}
	
	/**
	 * This method stores the new entry to the {@link #persistentParameters}
	 * map. The entry has the specified key and value.
	 * 
	 * @param name
	 * 		the key of the entry to store in the map.
	 * 
	 * @param value
	 * 		the value under that key.
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}
	
	/**
	 * This method removes the value under the specified key 
	 * from the {@link #persistentParameters} map. 
	 * 
	 * @param name
	 * 		the key of the entry to be removed from the
	 * 		{@link #persistentParameters} map.
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}
	
	/**
	 * This method retrieves the value from the {@link #temporaryParameters}
	 * map under the specified key and returns it. The method returns 
	 * <code>null</code> if no association exists.
	 * 
	 * @param name
	 * 		the key of the entry in the map.		
	 * 
	 * @return
	 * 		the value under the specified key of <code>null</code> if
	 * 		no association under the specified key exists.
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}
	
	/**
	 * This method retrieves all the names of all parameters in the
	 * {@link #temporaryParameters} map. The returned set is unmodifiable.
	 * 
	 * @return
	 * 		the set of all the names of all the parameters in the
	 * 		{@link #temporaryParameters} map.
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(
				new HashSet<>(temporaryParameters.values()));
	}
	
	/**
	 * This method stores the new entry to the {@link #temporaryParameters}
	 * map. The entry has the specified key and value.
	 * 
	 * @param name
	 * 		the key of the entry to store in the map.
	 * 
	 * @param value
	 * 		the value under that key.
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}
	
	/**
	 * This method removes the value under the specified key 
	 * from the {@link #temporaryParameters} map. 
	 * 
	 * @param name
	 * 		the key of the entry to be removed from the
	 * 		{@link #temporaryParameters} map.
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}
	
	/**
	 * This method writes the specified data to the 
	 * {@link #outputStream} but first checks if the header is
	 * generated and if not then firstly the header is generated
	 * and then the data is written to the {@link #outputStream}.
	 * 
	 * @param data
	 * 		the data to write to {@link #outputStream} 
	 * @return
	 * 		the reference to this object.
	 * @throws IOException
	 * 		if the exception occurs during writing to the
	 * 		{@link #outputStream}.
	 */
	public RequestContext write(byte[] data) throws IOException {
		return write(data, 0, data.length);
	}
	/**
	 * This method writes the specified data to the 
	 * {@link #outputStream} but first checks if the header is
	 * generated and if not then firstly the header is generated
	 * and then the data is written to the {@link #outputStream}.
	 * Only the byte at the index <code>offset</code> and next
	 * <code>len - 1</code> bytes are written to the output stream
	 * 
	 * @param data
	 * 		the data to write to {@link #outputStream} 
	 * @param offset 
	 * 		offset from where in array to write.
	 * @param len 
	 * 		the number of bytes to write.
	 * @return
	 * 		the reference to this object.
	 * @throws IOException
	 * 		if the exception occurs during writing to the
	 * 		{@link #outputStream}.
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if(!headerGenerated) {
			generateHeader();
			headerGenerated = true;
		}
		outputStream.write(data, offset, len);
		outputStream.flush();
		return this;
	}
	
	/**
	 * This method writes the specified text to the 
	 * {@link #outputStream} but first checks if the header is
	 * generated and if not then firstly the header is generated
	 * and then the text is converted to bytes using {@link #charset} 
	 * and once we have a byte array the content of it is written 
	 * to the {@link #outputStream}.
	 * 
	 * @param text
	 * 		the text to write to {@link #outputStream}
	 * @return
	 * 		the reference to this object.
	 * @throws IOException
	 * 		if the exception occurs during writing to the
	 * 		{@link #outputStream}.
	 */
	public RequestContext write(String text) throws IOException {
		if(charset == null) setEncoding(encoding);
		byte[] bytes = text.getBytes(charset);
		return write(bytes);
	}
	
	/**
	 * This method generates a header and writes it to the
	 * {@link #outputStream}.
	 * 
	 * @throws IOException
	 * 		if the an exception occurs during writing to
	 * 		the {@link #outputStream}.
	 */
	private void generateHeader() throws IOException {
		charset = Charset.forName(encoding);
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		appendContentType(sb);
		appendCookies(sb);
		sb.append(contentLength == null ? "" : String.valueOf(contentLength) + "\r\n");
		sb.append("\r\n");
		
		String header = sb.toString();
		byte[] bytes = header.getBytes(StandardCharsets.ISO_8859_1);
		outputStream.write(bytes, 0, bytes.length);
	}
	
	/**
	 * This method appends the content type and used charset
	 * if needed to the specified {@link StringBuilder}
	 * 
	 * @param sb
	 * 		the {@link StringBuilder} where to append the 
	 * 		text.
	 */
	private void appendContentType(StringBuilder sb) {
		sb.append("Content-Type: " + mimeType);
		if(mimeType.startsWith("text/")) {
			sb.append("; charset=" + encoding);
		}
		sb.append("\r\n");
	}

	/**
	 * This method appends the cookie info to the
	 * specified {@link StringBuilder}. 
	 * 
	 * @param sb
	 */
	private void appendCookies(StringBuilder sb) {
		for(RCCookie c : outputCookies) {
			sb.append("Set-Cookie: " + c.getName() + "=\"" + c.getValue() + "\"");
			if(c.getDomain() != null) sb.append("; Domain=" + c.getDomain());
			if(c.getPath() != null) sb.append("; Path=" + c.getPath());
			if(c.getMaxAge() != null) sb.append("; Max-Age=" + c.getMaxAge());
			if(c.isHttpOnly()) sb.append("; HttpOnly");
			sb.append("\r\n");
		}
	}
	
	//===============================Helper class====================================
	
	/**
	 * The static class which represents one cookie. The cookie consists
	 * of 4 read only {@link String} properties and one private read-only
	 * {@link Integer} property.
	 * 
	 * @author ivan
	 *
	 */
	public static class RCCookie {

		/**The name of the cookie.*/
		private String name;
		
		/**The value of the cookie.*/
		private String value;
		
		/**The domain of the cookie.*/
		private String domain;
		
		/**The path*/
		private String path;
		
		/**The maximum amount of seconds cookie can live.*/
		private Integer maxAge;
		
		/**The flag indicating that the cookie is http only.*/
		private boolean httpOnly = false;
		
		/**
		 * The constructor with initial values of all parameters.
		 * 
		 * @param name 	    the name of the cookie. Can't be null
		 * @param value		the value of the cookie. Can't be null
		 * @param domain	the domain of the cookie. 	
		 * @param path		the path of the cookie.
		 * @param maxAge	the {@link #maxAge} of the cookie.
		 * @param httpOnly  the initial value of the {@link #httpOnly} flag.
		 */
		public RCCookie(String name, String value, Integer maxAge,
				String domain, String path, boolean httpOnly) {
			super();
			this.name = Objects.requireNonNull(name);
			this.value = Objects.requireNonNull(value);
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
			this.httpOnly = httpOnly;
		}
		
		/**
		 * This method returns the value of the parameter
		 * {@link #name};
		 * @return the value of the parameter {@link #name}
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * This method returns the value of the parameter
		 * {@link #value}
		 * @return the value of the parameter {@link #value}.
		 */
		public String getValue() {
			return value;
		}
		
		/**
		 * This method returns the value of the parameter
		 * {@link #domain}
		 * @return the value of the parameter {@link #domain}.
		 */
		public String getDomain() {
			return domain;
		}
		
		/**
		 * This method returns the value of the parameter
		 * {@link #path}
		 * @return the value of the parameter {@link #path}.
		 */
		public String getPath() {
			return path;
		}
		
		/**
		 * This method returns the value of the parameter
		 * {@link #maxAge}
		 * @return the value of the parameter {@link #maxAge}.
		 */
		public Integer getMaxAge() {
			return maxAge;
		}
		
		/**
		 * This method returns the boolean value indicating
		 * whether this cookie is http only cookie.
		 * 
		 * @return
		 * 		the boolean value indicating whether this 
		 * 		cookie is http only cookie or not. 
		 */
		public boolean isHttpOnly() {
			return httpOnly;
		}
	}
	
}
