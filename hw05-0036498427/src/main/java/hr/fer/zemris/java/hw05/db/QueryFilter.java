package hr.fer.zemris.java.hw05.db;

import java.util.List;
import java.util.Objects;

/**
 * This class represents a filter which checks 
 * all the expressions and if record does not 
 * satisfy only one of them the record is not
 * accepted. 
 * 
 * @author ivan
 *
 */
public class QueryFilter implements IFilter {

	//====================Properties===================================
	
	/**
	 * A list of expressions which have to be checked.
	 */
	private List<ConditionalExpression> expressions;
	
	//=====================Constructors================================
	
	/**
	 * This constructor gets one argument and that is 
	 * the list of expressions which have to be checked
	 * before accepting the record.
	 * 
	 * @param expressions	
	 * 		list of expressions.
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = Objects.requireNonNull(expressions);
	}
	
	//===============Method implementations============================
	
	@Override
	public boolean accepts(StudentRecord record) {
		for(ConditionalExpression c : expressions) {
			boolean conditionMet = c.getComparisonOperator().satisfied(
				c.getFieldGetter().get(record), 
				c.getStringLiteral()
			);
			if(!conditionMet) return false;
		}
		return true;
	}

}
