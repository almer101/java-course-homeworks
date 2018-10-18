package hr.fer.zemris.java.hw03.prob1;

/**
 * This enumeration defines two modes in which
 * the {@link Lexer} can work.
 * 
 * @author ivan
 * @version 1.0
 *
 */
public enum LexerState {

	/**
	 * When in this state {@link Lexer} will perform the basic
	 * be doing normal lexical analysis. Where numbers, words and
	 * symbols can be a Token.
	 * 
	 */
	BASIC, 
	
	/**
	 * When in this state, {@link Lexer} will have the extended
	 * duty, i.e. it will use only blanks as delimiters 
	 * between 2 expressions and everything will be interpreted 
	 * as a word.
	 * 
	 */
	EXTENDED;
}
