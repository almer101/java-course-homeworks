package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * This is a simple implementation of
 * a lexer used by the {@link QueryParser}.
 * 
 * @author ivan
 *
 */
public class Lexer {

	//====================Properties===========================
	/**
	 * A char array of the line from which tokens will 
	 * be extracted.
	 */
	private char[] data;
	
	/**
	 * A current token this lexer is on.
	 */
	private Token currentToken;
	
	/**
	 * A current index the lexer is on in the data array.
	 */
	private int currentIndex;
	
	//=======================Constants=========================
	
	private static final char QUOTE = '"';
	
	private static final String SYMBOLS = "<>=";
	
	//========================Constructors=====================
	/**
	 * A constructor which gets one string
	 * argument which represents a new 
	 * 
	 * @param line
	 * 		the line from which the tokens have 
	 * 		to be extracted.
	 */
	public Lexer(String line) {
		if(line == null) {
			throw new LexerException("The specified line String"
					+ " must not be null!");
		}
		this.data = Objects.requireNonNull(line)
						.trim()
						.toCharArray();
		currentIndex = 0;
		nextToken();
	}
	
	//======================Getters============================

	/**
	 * This method gets and returns the 
	 * <code>currentToken</code>.
	 * 
	 * @return
	 * 		the value of the <code>currentToken</code>.
	 */
	public Token getToken() {
		return currentToken;
	}
	
	//=================Method implementations===================
	
	/**
	 * This method extracts and returns the next token
	 * from the <code>line</code>.
	 * 
	 * @return
	 * 		the value of the <code>currentToken</code>
	 * 		property.
	 */
	public Token nextToken() {
		extractNextToken();
		return getToken();
	}
	
	/**
	 * This method extracts the next token from the line
	 * and sets the <code>currentToken</code> to that new
	 * value.
	 * 
	 */
	private void extractNextToken() {
		if(currentToken != null && 
				currentToken.getType() == TokenType.EOF) {
			throw new LexerException("The end of file was reached!");
			
		}
		if(currentIndex > data.length - 1) {
			currentToken = new Token(TokenType.EOF, null);
			return;
		}
		skipBlankSpaces();
		char c = data[currentIndex];
		
		if(Character.isLetter(c)) {
			extractField();
			
		} else if(c == QUOTE) {
			extractString();
			
		} else {
			extractSymbol();
			
		}
	}

	/**
	 * This method analyzes and extracts the field token
	 * from the input line. Used in <i>extractToken()</i>
	 * method.
	 * 
	 */
	private void extractField() {
		char c = data[currentIndex];
		StringBuilder sb = new StringBuilder();
		
		while(Character.isLetter(c) && 
				currentIndex < data.length - 1) {
			sb.append(c);
			currentIndex++;
			c = data[currentIndex];
		}
		
		currentToken = new Token(TokenType.FIELD, sb.toString());
	}

	/**
	 * This method analyzes and extracts the String token
	 * from the input line. Used in <i>extractToken()</i>
	 * method.
	 * 
	 */
	private void extractString() {
		//skip the starting quotation mark
		char c = data[++currentIndex];
		StringBuilder sb = new StringBuilder();
		
		while(c != QUOTE && 
				currentIndex < data.length - 1) {
			
			sb.append(c);
			c = data[++currentIndex];
		}
		
		//skip the ending quotation mark
		currentIndex++;
		currentToken = new  Token(TokenType.STRING, sb.toString());
	}

	/**
	 * This method analyzes and extracts the Symbol token
	 * from the input line. Used in <i>extractToken()</i>
	 * method.
	 * 
	 */
	private void extractSymbol() {
		char c = data[currentIndex]; 
		StringBuilder sb = new StringBuilder();
		
		while(SYMBOLS.contains(String.valueOf(c))) {
			sb.append(c);
			c = data[++currentIndex];
		}
		
		currentToken = new Token(TokenType.SYMBOL, sb.toString());
	}

	/**
	 * This method skips all the blank spaces and sets
	 * <code>currentIndex</code> to the first index
	 * where there is something other than ' ' or '\t'.
	 * 
	 */
	private void skipBlankSpaces() {
		while(currentIndex < data.length) {
			char c = data[currentIndex];
			
			if( c == ' ' || c == '\t') {
				currentIndex++;
			} else {
				break;
			}
		}
	}
}
