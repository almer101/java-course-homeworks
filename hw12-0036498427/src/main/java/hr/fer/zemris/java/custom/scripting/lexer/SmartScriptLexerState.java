package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * This enumeration defines {@link SmartScriptLexer}
 * working states. The states are changed according 
 * to the content lexer is analyzing.
 * 
 * @author ivan
 *
 */
public enum SmartScriptLexerState {

	/**
	 * This is the state lexer is working in
	 * when analyzing text.
	 * 
	 */
	TEXT_STATE,
	
	/**
	 * This is the state lexer is working in
	 * when it is enters a tag.
	 * 
	 */
	TAG_ENTER_STATE,
	
	/**
	 * State of the lexer when it is inside
	 * for-loop tag.
	 * 
	 */
	FOR_STATE,
	
	/**
	 * In this state lexer is inside the =-tag
	 * 
	 */
	ECHO_STATE;
}
