package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.Assert.assertTrue;

import java.io.EOFException;

import org.junit.Test;

import hr.fer.zemris.java.hw03.prob1.LexerException;

public class SmartScriptLexerTest {

	String document1 = "{$  FOR i334v__s3 1 \"-12\"-1$}";
	
	String document2 = "{$FOR ivan 0 10 2 $}";
	
	String document3 = "{$$}";
	
	String document4 = "{$= i i @fja \"str\\\\in\\\"g\" @decfmt ja $}";
	
	String document5 = "{$= \"Joe \\\"Long\\\" Smith\"$}";
	
	String document6 = "This is sample text.\n" + 
			"{$ FOR i 1 10 1 $}" + 
			"  This is {$= i $}-th time this message is generated.\n" + 
			"{$END$}" + 
			"{$FOR i 0 10 2 $}" + 
			"  sin({$=i$}^2) = " +
			"{$= i i * @sin  \"0.000\" @decfmt $}" + 
			"{$END$}";
	
	@Test//(expected = LexerException.class)
	public void nextTokenTest() {
		SmartScriptLexer lexer = new SmartScriptLexer(document1);
		
		assertTrue(lexer.getToken().getType() == SmartScriptTokenType.OPENED_PARENTHESES && 
					lexer.getToken().getValue() == null);
		lexer.nextToken();
		
		assertTrue(lexer.getToken().getType() == SmartScriptTokenType.DOLLAR && 
				lexer.getToken().getValue() == null);
		lexer.setState(SmartScriptLexerState.TAG_ENTER_STATE);
		lexer.nextToken();
		
		assertTrue(lexer.getToken().getType() == SmartScriptTokenType.KEYWORD && 
				lexer.getToken().getValue().equals("FOR"));
		lexer.setState(SmartScriptLexerState.FOR_STATE);
		lexer.nextToken();
		
		assertTrue(lexer.getToken().getType() == SmartScriptTokenType.ID && 
				lexer.getToken().getValue().equals("i334v__s3"));
		lexer.nextToken();
		
		assertTrue(lexer.getToken().getType() == SmartScriptTokenType.NUMBER && 
				lexer.getToken().getValue().equals(1));
		lexer.nextToken();
		
		assertTrue(lexer.getToken().getType() == SmartScriptTokenType.STRING && 
				lexer.getToken().getValue().equals("-12"));
		lexer.nextToken();
		
		assertTrue(lexer.getToken().getType() == SmartScriptTokenType.NUMBER && 
				lexer.getToken().getValue().equals(-1));
		lexer.nextToken();
		
		assertTrue(lexer.getToken().getType() == SmartScriptTokenType.DOLLAR && 
				lexer.getToken().getValue() == null);
		lexer.nextToken();
		
		assertTrue(lexer.getToken().getType() == SmartScriptTokenType.CLOSED_PARENTHESES && 
				lexer.getToken().getValue() == null);
	}
	
	@Test
	public void nextTokenTest2() {
		SmartScriptLexer lexer = new SmartScriptLexer(document2);
		
		lexer.nextToken();
		lexer.setState(SmartScriptLexerState.TAG_ENTER_STATE);
		lexer.nextToken();
		lexer.setState(SmartScriptLexerState.FOR_STATE);
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		
		assertTrue(lexer.getToken().getType() == SmartScriptTokenType.EOF && 
				lexer.getToken().getValue() == null);
		
	}
	
	@Test
	public void nextTokenTest3() {
		SmartScriptLexer lexer = new SmartScriptLexer(document4);
		
		lexer.nextToken();
		lexer.setState(SmartScriptLexerState.TAG_ENTER_STATE);
		lexer.nextToken();
		lexer.setState(SmartScriptLexerState.ECHO_STATE);
		lexer.nextToken();
		assertTrue(lexer.getToken().getType() == SmartScriptTokenType.ID && 
				lexer.getToken().getValue().equals("i"));
		lexer.nextToken();
		assertTrue(lexer.getToken().getType() == SmartScriptTokenType.ID && 
				lexer.getToken().getValue().equals("i"));
		
		lexer.nextToken();
		assertTrue(lexer.getToken().getType() == SmartScriptTokenType.FUNCTION_ID && 
				lexer.getToken().getValue().equals("fja"));
		lexer.nextToken();
		
		assertTrue(lexer.getToken().getType() == SmartScriptTokenType.STRING && 
				lexer.getToken().getValue().equals("str\\in\"g"));
		lexer.nextToken();
		
		assertTrue(lexer.getToken().getType() == SmartScriptTokenType.FUNCTION_ID && 
				lexer.getToken().getValue().equals("decfmt"));
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		assertTrue(lexer.getToken().getType() == SmartScriptTokenType.EOF && 
				lexer.getToken().getValue() == null);
	}
	
	@Test
	public void nextTokenTest4() {
		SmartScriptLexer lexer = new SmartScriptLexer(document5);
		
		lexer.nextToken();
		lexer.setState(SmartScriptLexerState.TAG_ENTER_STATE);
		lexer.nextToken();
		assertTrue(lexer.getToken().getType() == SmartScriptTokenType.KEYWORD && 
				lexer.getToken().getValue().equals("="));
		lexer.setState(SmartScriptLexerState.ECHO_STATE);
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		assertTrue(lexer.getToken().getType() == SmartScriptTokenType.EOF && 
				lexer.getToken().getValue() == null);
	}
	
	@Test (expected = LexerException.class)
	public void nextTokenTest5() {
		SmartScriptLexer lexer = new SmartScriptLexer("this is text!");
		lexer.nextToken();
		lexer.nextToken();
	}
	
}
