package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * This class represents a whole expression,
 * and consists of {@link IFieldValueGetter}, 
 * String literal and {@link IComparisonOperator}.
 * 
 * @author ivan
 *
 */
public class ConditionalExpression {

	//=======================Properties===================================
	/**
	 * The getter which gets the certain field
	 * of the specified {@link StudentRecord}.
	 */
	private IFieldValueGetter fieldGetter;
	
	/**
	 * The literal which is used in comparison
	 * purposes.
	 */
	private String stringLiteral;
	
	/**
	 * The comparison operator used for comparing
	 * certain arguments.
	 */
	private IComparisonOperator comparisonOperator;
	
	//===========================Constructors================================

	/**
	 * This constructor which gets initial values of
	 * all properties of this class. Neither value
	 * can be null!
	 * 
	 * @param fieldGetter
	 * 		the initial field getter.
	 * 
	 * @param stringLiteral
	 * 		the initial value of string literal.
	 * 
	 * @param comparisonOperator
	 * 		the initial comparison operator
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, 
			String stringLiteral,IComparisonOperator comparisonOperator) {
		super();
		this.fieldGetter = Objects.requireNonNull(fieldGetter);
		this.stringLiteral = Objects.requireNonNull(stringLiteral);
		this.comparisonOperator = Objects.requireNonNull(comparisonOperator);
	}
	
	//=============================Getters==================================

	/**
	 * This method gets and returns the <code>fieldGetter</code>.
	 * 
	 * @return
	 * 		the <code>fieldGetter</code>.
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * This method gets and returns the <code>stringLiteral</code>.
	 * 
	 * @return
	 * 		the <code>stringLiteral</code>.
	 */
	public String getStringLiteral() {
		return String.copyValueOf(stringLiteral.toCharArray());
	}

	/**
	 * This method gets and returns the 
	 * <code>comparisonOperator</code>.
	 * 
	 * @return
	 * 		the <code>comparisonOperator</code>.
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
	
}
