package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LexerTest {

	@Test
	public void nextTokenTest1() {
		
		Lexer lexer = new Lexer(" jmbag       =\"0123456789\"    ");
		assertTrue(lexer.getToken().getType() == TokenType.FIELD);
//		System.out.println(lexer.getToken().getValue());
		lexer.nextToken();
		assertTrue(lexer.getToken().getType() == TokenType.SYMBOL);
//		System.out.println(lexer.getToken().getValue());
		lexer.nextToken();
		assertTrue(lexer.getToken().getType() == TokenType.STRING);
//		System.out.println(lexer.getToken().getValue());
		lexer.nextToken();
//		System.out.println("==========================");
	}
	
	@Test
	public void nextTokenTest2() {
		Lexer lexer = new Lexer("jmbag=\"0123456789\" and lastName>\"J\"");
		assertTrue(lexer.getToken().getType() == TokenType.FIELD);
//		System.out.println(lexer.getToken().getValue());
		
		lexer.nextToken();
		assertTrue(lexer.getToken().getType() == TokenType.SYMBOL);
//		System.out.println(lexer.getToken().getValue());
		
		lexer.nextToken();
		assertTrue(lexer.getToken().getType() == TokenType.STRING);
//		System.out.println(lexer.getToken().getValue());
		
		lexer.nextToken();
		assertTrue(lexer.getToken().getType() == TokenType.FIELD);
//		System.out.println(lexer.getToken().getValue());
		
		lexer.nextToken();
		assertTrue(lexer.getToken().getType() == TokenType.FIELD);
//		System.out.println(lexer.getToken().getValue());
		
		lexer.nextToken();
		assertTrue(lexer.getToken().getType() == TokenType.SYMBOL);
//		System.out.println(lexer.getToken().getValue());
		
		lexer.nextToken();
		assertTrue(lexer.getToken().getType() == TokenType.STRING);
//		System.out.println(lexer.getToken().getValue());
		
	}
}
