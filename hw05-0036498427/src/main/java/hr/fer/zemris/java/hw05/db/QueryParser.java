package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * This parser gets one input line which
 * represents a query statement and it gets 
 * that line(String) through the constructor.
 *	
 * @author ivan
 *
 */
public class QueryParser {

	//=================Properties=================================
	
	/**
	 * Parser which does the parsing and after it
	 * is done the method which returns the List
	 * of expressions can be called.
	 */
	private Parser parser;
	
	/**
	 * The list of conditional expressions 
	 */
	private List<ConditionalExpression> expressions;
	
	//=================Constructors===============================
	
	/**
	 * The constructor gets one parameter of type
	 * {@link String} which represents a query
	 * statement.
	 * 
	 * @param line
	 * 		a {@link String} representation
	 * 		of the query statement.
	 */
	public QueryParser(String line) {
		parser = new Parser(line);
		expressions = parser.getExpressions();
	}
	
	//===================Getters==================================
	
	/**
	 * This method returns a list of {@link ConditionalExpression}s
	 * from the query.
	 * 
	 * @return
	 * 		a list of conditional expressions from
	 * 		the query.
	 */
	public List<ConditionalExpression> getQuery() {
		return expressions;
	}
	
	//=================Method implementations======================
	
	/**
	 * This method checks if the query is direct and returns
	 * the boolean value accordingly.
	 * 
	 * @return
	 * 		<code>true</code> if the query is direct.
	 * 		<code>false</code> otherwise.
	 */
	public boolean isDirectQuery() {
		if(expressions.size() != 1) return false;
		
		if(!expressions.get(0).getFieldGetter()
				.equals(FieldValueGetters.JMBAG)) return false;
		
		if(!expressions.get(0).getComparisonOperator()
				.equals(ComparisonOperators.EQUALS)) return false;
		
		return true;
	}
	
	/**
	 * This method returns the queried jmbag only if the 
	 * query is direct. If it is not direct throws an
	 * exception.
	 * 
	 * @return
	 * 		a queried jmbag.
	 * 
	 * @throws IllegalStateException
	 * 		if the query is not direct.
	 */
	public String getQueriedJMBAG() {
		if(!isDirectQuery()) {
			throw new IllegalStateException("The queried jmbag"
					+ " cannot be returned if the query is"
					+ "not direct!");
		}
		return expressions.get(0).getStringLiteral();
	}
}
