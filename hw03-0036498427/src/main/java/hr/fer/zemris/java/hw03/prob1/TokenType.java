package hr.fer.zemris.java.hw03.prob1;

/**
 * This enumeration represents all the possible
 * types of tokens in our lexical analysis.
 * 
 * @author ivan
 *
 */
public enum TokenType {
	
	/**
	 * The token type which represents
	 * the End Of File. EOF is generated as
	 * the last <code>Token</code> in 
	 * lexical analysis.
	 * 
	 */
	EOF,
	
	/**
	 * A token type representing a word.
	 * The series of characters for which the
	 * method <code>Character.isLetter</code>
	 * returns the value <code>true</code>
	 * 	 
	 */
	WORD,
	
	/**
	 * The token type which represents
	 * a number in the lexical analysis.
	 * The token of type number must be able
	 * to be casted to <code>Long</code>.
	 * 
	 */
	NUMBER,
	
	/**
	 * Any single char which stays when we
	 * remove all the words, numbers and 
	 * blank spaces (including '\n', '\t' etc.)
	 * 
	 */
	SYMBOL;
}
