package hr.fer.zemris.java.hw03.prob1;

/**
 * Lexer is a class which performs lexical analysis
 * of the input text.
 * 
 * @author ivan
 * @version 1.0
 */
public class Lexer {

	/**
	 * The array which consists of each char from
	 * input text.
	 * 
	 */
	private char[] data;
	
	/**
	 * The current token this {@link Lexer} 
	 * is analyzing.
	 * 
	 */
	private Token token;
	
	/**
	 * The index of the first char which is yet to
	 * be analyzed.
	 * 
	 */
	private int currentIndex;
	
	private LexerState state = LexerState.BASIC;
	
	/**
	 * The constructor which gets input text as argument
	 * and puts that text to <code>data</code> char array.
	 * 
	 * @param text 
	 * 		the input text which has to be analyzed.
	 */
	public Lexer(String text) {
		if(text == null) {
			throw new IllegalArgumentException("The argument must not be null!");
			
		}
		data = text.toCharArray();
		currentIndex = 0;
	}
	
	/**
	 * This method generates and returns the next 
	 * <code>Token</code>.
	 * 
	 * @return 
	 * 		the next <code>Token</code> from the
	 * 		input text.
	 * 
	 * @throws LexerException if the error occurs.
	 */
	public Token nextToken() {
		
		//if the method nextToken() is called and the last token
		//before that was EOF, then this method will throw LexerException.
		if(token != null && token.type == TokenType.EOF) {
			throw new LexerException("There are no more tokens afeter EOF.");
					
		}
		
		skipBlankSpaces();
		
		if(currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		if(state == LexerState.BASIC) {
			//checking if it is a word
			if(Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {
				token = basicNextWordToken();
				return token;
			}
			
			//checking if it is a number
			if(data[currentIndex] >= '1' && data[currentIndex] <= '9') {	
				try {
					token = basicNextNumberToken();
					return token;
					
				} catch (NumberFormatException e) {
					throw new LexerException("The number could not be parsed to long!");	
				}
			}
		} else {
			//it is in the EXTENDED state
			if(data[currentIndex] != '#') {
				token = extendedNextWordToken();
				return token;
			}
		}
		
		//if it is not a word or a number then it is a symbol.
		
		token = new Token(TokenType.SYMBOL, data[currentIndex]);
		currentIndex++;
		
		return token;
	}
	
	/**
	 * This method skips all the blank spaces (including 
	 * \n, \t, \r, ' ')
	 * 
	 */
	 private void skipBlankSpaces() {
		while(currentIndex < data.length) {
			char c = data[currentIndex];
			if(c == ' ' || c == '\t' || c =='\n' || c == '\r') {
				currentIndex++;
				
			} else {
				break;
				
			}
		}
	}
	 
	/**
	 * This method generates and returns the next WORD
	 * Token. This is the method called when the 
	 * Lexer is working in <code>BASIC</code> state.
	 * 
	 * @return
	 * 		a <code>Token</code> of type WORD.
	 */
	 private Token basicNextWordToken() {
		 
		StringBuilder sb = new StringBuilder();
		
		while(currentIndex < data.length) {
				
			if( Character.isLetter(data[currentIndex]) ) {
				sb.append(data[currentIndex]);
				
			} else if (data[currentIndex] == '\\') {
					
				//the following condition is here to ensure that the '\' char is
				//not the last element in the data array, or that the char after
				//the '\' is not a letter because that is the invalid escape
				if(currentIndex + 1 >= data.length || 
						Character.isLetter(data[currentIndex + 1])) {
					
					throw new LexerException("Invalid escape sequence!");	
				}
				
				currentIndex++;
				sb.append(data[currentIndex]);
				
			} else {
				break;
					
			}
			currentIndex++;
		}
		
		return new Token(TokenType.WORD, sb.toString());
	 }

	 /**
	  * This method generates and returns the next NUMBER 
	  * Token. This is the method called when the 
	 * Lexer is working in <code>BASIC</code> state.
	  * 
	  * @return
	  * 		a <code>Token</code> of type NUMBER.
	  */
	 private Token basicNextNumberToken() {
		StringBuilder sb = new StringBuilder();
			
		while(currentIndex < data.length) {
				
			char c = data[currentIndex];
			if(c >= '1' && c <= '9') {
				sb.append(c);
				currentIndex++;
					
			} else break;	
		}
		
		return new Token(TokenType.NUMBER, Long.parseLong(sb.toString()));
	 }
	 
	 /**
	  * This method generates and returns the next WORD 
	  * Token. This is the method called when the 
	 * Lexer is working in <code>EXTENDED</code> state.
	  * 
	  * @return
	  * 		the next <code>Token</code> of type WORD.
	  */
	 private Token extendedNextWordToken() {
		 StringBuilder sb = new StringBuilder();
		 
		 while(currentIndex < data.length) {
			 char c = data[currentIndex];
			 
			 if(c == '#') break;
			 if(c == ' ' || c == '\n' || c== '\t' || c == '\r') break;
			 
			 sb.append(c);
			 currentIndex++;
		 }
		 return new Token(TokenType.WORD, sb.toString());
	 }
	 
	/**
	  * This method returns a current <code>Token</code>.
	  * Method can be called more than once, and does not
	  * start the generate the next token.
	  * 
	  * @return
	  * 		the current token.
	  */
	public Token getToken() {
		return token;
	}
	
	/**
	 * This method sets state of the {@link Lexer} to the
	 * given parameter <code>state</code>
	 * 
	 * @param state
	 * 		this will be the lexer state after this method
	 * 
	 * @throws IllegalArgumentException 
	 * 		if the given state is null!
	 */
	public void setState(LexerState state) {
		if(state == null) {
			throw new IllegalArgumentException("The given Lexer state "
					+ "must not be null!");
			
		}
		this.state = state;
	}
}
