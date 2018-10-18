package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import static java.lang.Character.isLetter;
import static java.lang.Character.isDigit;

/**
 * This class is a lexer which generates tokens
 * from the input text. It is used by 
 * {@link SmartScriptParser}.
 * 
 * @author ivan
 *
 */
public class SmartScriptLexer {

	/**
	 * The array which consists of each char from
	 * input text.
	 * 
	 */
	private char data[];
	
	/**
	 * The index of the first char which is
	 * to be analyzed.
	 * 
	 */
	private char currentIndex;
	
	/**
	 * This is the last generated token.
	 * 
	 */
	private SmartScriptToken currentToken;
	
	/**
	 * This is the state this lexer is working in.
	 * 
	 */
	private SmartScriptLexerState state;
	
	/**
	 * These are all the accepted operators.
	 * 
	 */
	private static final String OPERATORS = "+-*/^";

	/**
	 * A constructor which gets on parameter
	 * <code>text</code> and initializes the
	 * data array with chars from the {@link String}
	 * <code>text</code>
	 * 
	 * @param text
	 * 		text to be analyzed
	 */
	public SmartScriptLexer(String text) {
		if(text == null) {
			throw new NullPointerException("The given text "
					+ "must not be null!!");
			
		}
		this.data = text.toCharArray();
		currentIndex = 0;
		state = SmartScriptLexerState.TEXT_STATE;
		nextToken();
	}
	
	/**
	 * This method extracts the next token and returns
	 * its value	.
	 * 
	 * @return
	 * 		the value of the last generated token.
	 */
	public SmartScriptToken nextToken() {
		extractNextToken();
		return getToken();
	}
	
	/**
	 * This method returns the value of the
	 * <code>token</code> property.
	 * 
	 * @return
	 * 		value of the <code>token</code> property
	 */
	public SmartScriptToken getToken() {
		return currentToken;
	}
	
	/**
	 * This method sets the value of the property
	 * <code>state</code> to the value of the parameter
	 * <code>state</code>.
	 * 
	 * @param state
	 * 		the value to set the property 
	 * 		<code>state</code> to.
	 */
	public void setState(SmartScriptLexerState state) {
		if(state == null) {
			throw new NullPointerException("The state must not be null!");
		}
		this.state = state;
	}
	
	/**
	 * This method skips all the blank spaces and sets
	 * <code>currentIndex</code> to the first index
	 * where there is something other than ' ', '\n',
	 * '\r', '\t'.
	 * 
	 */
	private void skipBlankSpaces() {
		while(currentIndex < data.length) {
			char c = data[currentIndex];
			
			if( c == ' ' || c == '\r' || c == '\t' || c == '\n') {
				currentIndex++;
				
			} else {
				break;
				
			}
		}
	}
	
	/**
	 * This method extracts a next token from the
	 * data array and updates the <code>token</code>
	 * property.
	 * 
	 */
	private void extractNextToken() {
		
		//if end of file is already reached and this method is called.
		if(currentToken != null && 
				currentToken.getType() == SmartScriptTokenType.EOF) {
			
			throw new LexerException("The end of file is reached and no"
					+ " more tokens are available for extracting!");
		}
		
		//if the currentIndex is now at the end of file. 
		//generate new token and set token type to EOF.
		if(currentIndex >= data.length) {
			this.currentToken = new SmartScriptToken(
									SmartScriptTokenType.EOF, null);
			return;
			
		}
		
		if(state == SmartScriptLexerState.TEXT_STATE) {
			textStateAnalysis();
		}
		
		if(state == SmartScriptLexerState.TAG_ENTER_STATE) {
			tagEnterStateAnalysis();
		}
		
		if(state == SmartScriptLexerState.FOR_STATE) {
			forStateAnalysis();
		}
		
		if(state == SmartScriptLexerState.ECHO_STATE) {
			echoStateAnalysis();
		}
	}
	
	/**
	 * This method performs analysis when lexer is in
	 * <code>TAG_ENTER_STATE</code>. Method generates 
	 * new {@link Token} and sets the value of the 
	 * <code>currentToken</code> property to that value. 
	 * 
	 */
	private void textStateAnalysis() {
		StringBuilder sb = new StringBuilder();
		char c = data[currentIndex];;
		
		if(checkBeginTag() == true) {
			//the token was generated in the method.
			return;
		}
		
		while( currentIndex < data.length ) {
			c = data[currentIndex];
			
			if(c == '{') break;
			
			//if the \ char is on the current index
			//what comes next must be specially 
			//taken care of so it goes to the
			//separate method
			if(c == '\\') {
				c = escapeAnalysis();	
			}
			
			sb.append(c);
			currentIndex++;	
		}
		
		currentToken = new SmartScriptToken(
				SmartScriptTokenType.TEXT, sb.toString());
		return;
	}

	/**
	 * This method performs analysis when lexer is in
	 * <code>TAG_ENTER_STATE</code>. Method generates 
	 * new {@link Token} and sets the value of the 
	 * <code>currentToken</code> property to that value. 
	 * 
	 */
	private void tagEnterStateAnalysis() {
		skipBlankSpaces();
		
		//must check for end tag in case this is 
		//an empty tag.
		if(checkEndTag() == true) {
			//the token was generated in the method.
			return;
		}
		
		char c = data[currentIndex];
		StringBuilder sb = new StringBuilder();
		
		while( currentIndex < data.length ) {
			if(c == '$' || c == ' ') break;	
			
			//checking the special case where = is immediately
			//before a letter so to make sure it is interpreted
			//correctly
			if(c == '=') {
				sb.append(c);
				currentIndex++;
				break;
			}
			sb.append(c);
			currentIndex++;
			c = data[currentIndex];
		}
		
		String word = sb.toString().toUpperCase();
		
		//creating a token of type KEYWORD
		//the value of the keyword is in upper case
		currentToken = new SmartScriptToken(
				SmartScriptTokenType.KEYWORD, word);
		return;
	}

	/**
	 * This method performs analysis when lexer is in
	 * <code>FOR_STATE</code>. Method generates 
	 * new {@link Token} and sets the value of the 
	 * <code>currentToken</code> property to that value. 
	 * 
	 */
	private void forStateAnalysis() {
		skipBlankSpaces();
		
		if(checkEndTag() == true) {
			//the token was generated in the method.
			return;
		}
		char c = data[currentIndex];
		
		if(Character.isLetter(c)) {
			String name = analyzeName();
			
			currentToken = new SmartScriptToken(
					SmartScriptTokenType.ID, name);
			return;
		}
		
		if(Character.isDigit(c) || 
				(c == '-' && Character.isDigit(data[currentIndex + 1]) ||
						(c == '+' && Character.isDigit(data[currentIndex + 1])))) {
			
			analyzeNumber();
			return;
		}
		
		if(c == '"') {
			String s = parseString();
			currentToken = new SmartScriptToken(
					SmartScriptTokenType.STRING, s);
			return;
		}
		
		//if it is none of the above than it is something
		//lexer can not recognize!
		throw new LexerException("Unrecognized sign: " + c);
		
	}


	/**
	 * This method performs analysis when lexer is in
	 * <code>ECHO_STATE</code>. Method generates 
	 * new {@link Token} and sets the value of the 
	 * <code>currentToken</code> property to that value. 
	 * 
	 */
	private void echoStateAnalysis() {
		skipBlankSpaces();
		char c = data[currentIndex];
		
		if( c == '}' ) {
			currentToken = new SmartScriptToken(
					SmartScriptTokenType.CLOSED_PARENTHESES, null);
			currentIndex++;
			return;
		}
		
		if( c == '$' ) {
			currentToken = new SmartScriptToken(
					SmartScriptTokenType.DOLLAR, null);	
			currentIndex++;
			return;
		}	
		
		if(Character.isLetter(c)) {
			String name = analyzeName();
			
			currentToken = new SmartScriptToken(
					SmartScriptTokenType.ID, name);
			return;
		}
		
		if(Character.isDigit(c) || 
				(c == '-' && Character.isDigit(data[currentIndex + 1]) ||
						(c == '+' && Character.isDigit(data[currentIndex + 1])))) {
			
			analyzeNumber();
			return;
		}
		
		if(OPERATORS.contains(String.valueOf(c))) {
			//it is an operator
			currentToken = new SmartScriptToken(
					SmartScriptTokenType.SYMBOL, String.valueOf(c));
			currentIndex++;
			return;
		}
		
		if(c == '@') {
			//it is a function.
			currentIndex++;
			c = data[currentIndex];
			
			if(Character.isLetter(c)) {
				String name = analyzeName();
				
				currentToken = new SmartScriptToken(
						SmartScriptTokenType.FUNCTION_ID, name);
				return;
			}	
		}
		
		if(c == '"') {
			String text = parseString();	
			currentToken = new SmartScriptToken(
					SmartScriptTokenType.STRING, text);
			return;
		}
		
		//if it is none of the above than it is something
		//lexer can not recognize!
		throw new LexerException("Unrecognized sign: " + c);
		
	}

	/**
	 * This method checks if the current index is on
	 * the beginning of the tag. In case it is, this method 
	 * generates a token and then sets the value of the
	 * property <code>currentToken</code> to that generated
	 * value. It also returns <code>true</code> if the
	 * beginning of the tag was reached.
	 * 
	 * In case the beginning of tag is not reached yet,
	 * method returns <code>false</code> and does
	 * not change the value of the property 
	 * <code>currentToken</code>
	 * This method is used when lexer is working 
	 * in <code>TEXT_STATE</code>.
	 * 
	 * @return
	 * 		<code>true</code> if the end of tag is reached;
	 * 		<code>false</code> otherwise.
	 */
	private boolean checkBeginTag() {
		char c = data[currentIndex];
		
		if( c == '{' ) {
			currentToken = new SmartScriptToken(
					SmartScriptTokenType.OPENED_PARENTHESES, null);
			currentIndex++;
			return true;
		}
		
		if( c == '$' ) {
			currentToken = new SmartScriptToken(
					SmartScriptTokenType.DOLLAR, null);	
			currentIndex++;
			return true;
		}
		return false;
	}

	/**
	 * This method checks if the current index is on
	 * the end of the tag. In case it is, this method 
	 * generates a token and then sets the value of the
	 * property <code>currentToken</code> to that generated
	 * value. It also returns <code>true</code> if the
	 * end of tag was reached.
	 * 
	 * In case the end of tag is not reached yet,
	 * method returns <code>false</code> and does
	 * not change the value of the property 
	 * <code>currentToken</code>
	 * This method is used when lexer is working in 
	 * <code>TAG_ENTER_STATE</code>, 
	 * <code>FOR_STATE</code> and <code>ECHO_STATE</code>.
	 * 
	 * @return
	 * 		<code>true</code> if the end of tag is reached;
	 * 		<code>false</code> otherwise.
	 */
	private boolean checkEndTag() {
		char c = data[currentIndex];
		
		if( c == '}' ) {
			currentToken = new SmartScriptToken(
					SmartScriptTokenType.CLOSED_PARENTHESES, null);
			currentIndex++;
			return true;
		}
		
		if( c == '$' ) {
			currentToken = new SmartScriptToken(
					SmartScriptTokenType.DOLLAR, null);	
			currentIndex++;
			return true;
		}	
		return false;
	}
	
	/**
	 * This method analyzes number, whether it is an integer
	 * or double and returns a token accordingly. This method 
	 * generates a new token and sets the value of the
	 * property <code>currentToken</code> to that 
	 * generated value
	 * 
	 */
	private void analyzeNumber() {
		String numberString = stringOfNumber();
		
		if(numberString.contains(".")) {
			//it is a decimal number
			double number = parseDoubleNumber(numberString);
			currentToken = new SmartScriptToken(
					SmartScriptTokenType.NUMBER, number);
			
		} else {
			//it is an integer
			int number = parseIntegerNumber(numberString);
			currentToken = new SmartScriptToken(
					SmartScriptTokenType.NUMBER, number);
			
		}	
	}

	/**
	 * This method analyzes name of either
	 * variable or a function, it
	 * generates and sets the value 
	 * of the <code>currentToken</code> property. 
	 * This method is used in <code>ECHO_STATE</code> 
	 * and <code>FOR_STATE</code> of lexer.
	 * 
	 */
	private String analyzeName() {
		char c = data[currentIndex];
		StringBuilder sb = new StringBuilder();
		
		while(c != ' ' && c != '\t' && c != '\n' && c != '$') {
			sb.append(c);
			currentIndex++;
			c = data[currentIndex];
		}
		
		String id = sb.toString();
		checkId(id);
		
		return id;
	}

	
	/**
	 * This method returns the {@link String} from the 
	 * data array which starts and ends 
	 * with quotes (") .
	 * 
	 * @return
	 * 		analyzed and parsed string. Quotes will be excluded
	 * 		from the returned value
	 */
	private String parseString() {
		StringBuilder sb = new StringBuilder();
		
		currentIndex++;
		char c = data[currentIndex];
		
		while(c != '"') {			
			if(c == '$') {
				//the quotes were not closed
				throw new LexerException("The quotes must be closed!");
			}
			//taking care of escaping!
			if(c == '\\') {
				if(data[currentIndex + 1] == 'n') {
					sb.append("\n");
					currentIndex +=2;
					c = data[currentIndex];
					continue;
				} else if (data[currentIndex + 1] == 'r') {
					sb.append("\r");
					currentIndex +=2;
					c = data[currentIndex];
					continue;
				} else if(data[currentIndex + 1] != '\"' && data[currentIndex + 1] != '\\') {
					throw new LexerException("Invalid escaping!");
				}
				currentIndex++;
			}
			
			sb.append(data[currentIndex]);
			currentIndex++;
			c = data[currentIndex];
		}
		
		currentIndex++;
		return sb.toString();
	}

	/**
	 * This method checks if the name of the variable
	 * is valid. Used in FOR and ECHO state of lexer.
	 * 
	 * @param id
	 * 		the name to be checked
	 * 
	 * @throws LexerException
	 * 		if the id is not valid.
	 */
	private void checkId(String id) {
		for(int i = 0, len = id.length(); i < len; i++) {
			char c = id.charAt(i);
			if(c == '_' || isDigit(c) || isLetter(c)) {
				continue;
			}
			throw new LexerException("The id is not valid.");
		}
		return;
	}
	
	/**
	 * This method returns a number as a String, starting from
	 * the <code>currentIndex</code> in order to determine if 
	 * the given number was double or integer.
	 * Double will contain "." and integer not.
	 * 
	 * @return
	 * 		string representation of a number.
	 */
	private String stringOfNumber() {
		
		int startIndex = currentIndex;
		currentIndex++;
		
		while(currentIndex < data.length) {
			
			char c = data[currentIndex];
			if(Character.isDigit(c) || 
					(c == '.' && Character.isDigit(data[currentIndex + 1]))) {
				currentIndex++;
			} else {
				break;
			}
		}
		
		return String.valueOf(data, startIndex, currentIndex - startIndex);
	}
	
	/**
	 * The method parses the number from the 
	 * given {@link String} <code>s</code>
	 * and returns its value. If the parsing
	 * can not be done {@link LexerException}
	 * is thrown.
	 * 
	 * @param s
	 * 		{@link String} to be parsed.
	 * 
	 * @return
	 * 		the parsed number
	 * 
	 * @throws LexerException
	 * 		if the number could not be parsed
	 */
	private double parseDoubleNumber(String s) throws LexerException {
		
		try {
			double number = Double.parseDouble(s);
			return number;
			
		} catch (NumberFormatException e) {
			throw new LexerException("Invalid expression in FOR tag!");
			
		}
	}
	
	/**
	 * This method parses the number from the 
	 * given {@link String} <code>s</code>
	 * and returns its value. If the parsing
	 * can not be done {@link LexerException}
	 * is thrown.
	 * 
	 * @param s
	 * 		{@link String} to be parsed
	 * 
	 * @return
	 * 		parsed value of the <code>s</code>
	 * 
	 */
	private int parseIntegerNumber(String s) {
		try {
			int number = Integer.parseInt(s);
			return number;
			
		} catch (NumberFormatException e) {
			throw new LexerException("Invalid expression in FOR tag!");
			
		}
	}

	/**
	 * This method takes care of the chars which are after
	 * the '\' char.
	 * (e.g. \{ will be interpreted as text "{")
	 * (e.g. \\ will be interpreted as text "\")
	 * These two are the only accepted escaping.
	 * 
	 * This method returns the char after the '\'
	 * char.
	 * 
	 * @return
	 * 		the char after the '\' sign
	 * 
	 * @throws LexerException 
	 * 		if the char after the '\' is something
	 * 		other than '{' and '\'
	 */
	private char escapeAnalysis() throws LexerException {
		
		//the following condition is here to ensure that the '\' char is
		//not the last element in the data array
		if(currentIndex + 1 >= data.length) {
			throw new LexerException("Escape char \\ can not be the last char "
					+ "in the text!");
			
		}
		currentIndex++;
		char c = data[currentIndex];
		
		//if the char after the '\' is something other than { and \
		//throw exception.
		if(c != '{' && c != '\\') {
			throw new LexerException("Invalid escape sequence, the only accepted "
					+ "two are \\\\ and \\{. Was " + c);
			
		}
		
		return c;
	}
}