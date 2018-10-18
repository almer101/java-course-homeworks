package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a parser which parses a query
 * statement and stores all the expressions in
 * one list. That list can be returned with
 * the dedicated method once the parsing
 * is done.
 * 
 * @author ivan
 *
 */
public class Parser {

	//=================Properties===============================
	/**
	 * The private lexer which returns 
	 * tokens.
	 */
	private Lexer lexer;
	
	/**
	 * The list of conditional expressions .
	 */
	private List<ConditionalExpression> expressions;
	
	//===================Constants==============================
	
	private static final String AND = "and";
	
	private static final String FIRST_NAME = "firstName";
	
	private static final String LAST_NAME = "lastName";
	
	private static final String JMBAG = "jmbag";
	
	private static final String EQUALS = "=";
	
	private static final String NOT_EQUALS = "!=";
	
	private static final String LESS = "<";
	
	private static final String LESS_OR_EQUALS = "<=";
	
	private static final String GREATER = ">";
	
	private static final String GREATER_OR_EQUALS = ">=";
	
	private static final String LIKE = "LIKE";
	
	//==================Constructors===========================
	
	/**
	 * This constructor gets one parameter of type
	 * {@link String} which represents a line 
	 * of a query statement. This is the line to be
	 * parsed.
	 * 
	 * @param line
	 * 		a line to be parsed.
	 */
	public Parser(String line) {
		lexer = new Lexer(line);
		expressions = new ArrayList<ConditionalExpression>();
		try {
			parse();
		} catch (LexerException e) {
			throw new ParserException(e.getMessage());
		}
	}

	//===================Getters===============================
	/**
	 * This method returns the list of expressions.
	 * 
	 * @return
	 * 		the list of expressions.
	 */
	public List<ConditionalExpression> getExpressions() {
		return expressions;
	}
	
	//===========Method implementations==========================
	
	private void parse() {
		Token currentToken = lexer.getToken();
		
		while(currentToken.getType() != TokenType.EOF) {
			String tokenValue = (String)currentToken.getValue();
			
			if(currentToken.getType() == TokenType.FIELD && 
					!tokenValue.equalsIgnoreCase(AND)) {
				parseExpression();
				currentToken = lexer.nextToken();
				continue;
				
			} else if(currentToken.getType() == TokenType.FIELD && 
					tokenValue.equalsIgnoreCase(AND)){
				
				currentToken = lexer.nextToken();
				if(currentToken.getType() != TokenType.FIELD) {
					throw new ParserException("Invalid form "
							+ "of query! After \"and\" must "
									+ "come a FIELD!");
				}
				continue;
				
			} else {
				throw new ParserException("Unrecognized token!");
			}
				
		}
		
	}

	private void parseExpression() {
		IComparisonOperator comparisonOperator;
		IFieldValueGetter fieldGetter;
		String stringLiteral;
		
		fieldGetter = parseField();
		lexer.nextToken();
		comparisonOperator = parseComparisonOperator();
		lexer.nextToken();
		stringLiteral = parseString();

		expressions.add(new ConditionalExpression(
				fieldGetter, 
				stringLiteral, 
				comparisonOperator)
		);
	}

	/**
	 * This method parses a field token.
	 * 
	 * @return
	 * 		an {@link IFieldValueGetter}.
	 */
	private IFieldValueGetter parseField() {
		Token currentToken = lexer.getToken();
		checkEOF();
		
		if(currentToken.getType().equals(TokenType.FIELD)) {
			switch((String)currentToken.getValue()) {
				case JMBAG:
					return FieldValueGetters.JMBAG;
					
				case FIRST_NAME:
					return FieldValueGetters.FIRST_NAME;
					
				case LAST_NAME:
					return FieldValueGetters.LAST_NAME;
					
				default:
					throw new ParserException(
							"Unrecognized field!");
				
			}
		}
		throw new ParserException("This should have "
				+ "been a FIELD token!");
	}

	/**
	 * This method parses a comparisonOperator token.
	 * 
	 * @return
	 * 		a new {@link IComparisonOperator}.
	 * 
	 * @throws ParserException
	 * 		if an error occurs.
	 */
	private IComparisonOperator parseComparisonOperator() {
		Token currentToken = lexer.getToken();
		String tokenValue = (String)lexer.getToken().getValue();
		checkEOF();
		
		if(currentToken.getType().equals(TokenType.FIELD) &&
				tokenValue.equalsIgnoreCase(LIKE)) {
			return ComparisonOperators.LIKE;
		}
		
		if(currentToken.getType().equals(TokenType.SYMBOL)) {
			switch((String)currentToken.getValue()) {
				case EQUALS:
					return ComparisonOperators.EQUALS;
					
				case NOT_EQUALS:
					return ComparisonOperators.NOT_EQUALS;
					
				case LESS:
					return ComparisonOperators.LESS;
					
				case LESS_OR_EQUALS:
					return ComparisonOperators.LESS_OR_EQUALS;
					
				case GREATER:
					return ComparisonOperators.GREATER;
				
				case GREATER_OR_EQUALS:
					return ComparisonOperators.GREATER_OR_EQUALS;
					
				default:
					throw new ParserException("Unrecognized comparison!");
				
			}
		}
		throw new ParserException("This should have been a SYMBOL or "
				+ "FIELD token with value \"LIKE\"!");
	}

	/**
	 * This method parses a string token.
	 * 
	 * @return
	 * 		a string value of the token.
	 * 
	 * @throws ParserException
	 * 		if an error occurs.
	 */
	private String parseString() {
		Token currentToken = lexer.getToken();
		checkEOF();
		
		if(currentToken.getType().equals(TokenType.STRING)) {
			return (String)currentToken.getValue();
		}
		
		throw new ParserException("The token of type STRING was "
				+ "expected!");
	}

	/**
	 * This method checks if the end of file was reached
	 * and if yes throw and exception.
	 *
	 * @throws ParserException
	 * 		if the EOF was reached.
	 * 
	 */
	private void checkEOF() {
		if(lexer.getToken().getType().equals(TokenType.EOF)) {
			throw new ParserException("The end of file was reached!");
		}
		
	}

}
