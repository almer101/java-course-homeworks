package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ConditionalExpressionTest {

	@Test
	public void conditionalExpressionTest() {
		ConditionalExpression expr = new ConditionalExpression(
				FieldValueGetters.LAST_NAME,
				"To*",
				ComparisonOperators.LIKE
		);
		StudentRecord record = new StudentRecord("0036", "Toljaga", "Pero", 5);
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record),  
				expr.getStringLiteral()           
		);
		assertTrue(recordSatisfies);
		
		//=====================================================================
		expr = new ConditionalExpression(
				FieldValueGetters.JMBAG,
				"*444*",
				ComparisonOperators.LIKE
		);
		record = new StudentRecord("0036444444", "Prozor", "Mirko", 4);
		recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record), 
				expr.getStringLiteral()             
		);
		assertTrue(recordSatisfies);
		
		//=====================================================================
		expr = new ConditionalExpression(
				FieldValueGetters.FIRST_NAME,
				"*a",
				ComparisonOperators.LIKE
		);
		record = new StudentRecord("0036444444", "Prozorcic", "Mirka", 4);
		recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record), 
				expr.getStringLiteral()             
		);
		assertTrue(recordSatisfies);
		
		//=====================================================================
		expr = new ConditionalExpression(
				FieldValueGetters.LAST_NAME,
				"Bosnic",
				ComparisonOperators.GREATER
		);
		record = new StudentRecord("0036444444", "Brzic", "Zvucnik", 1);
		recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record), 
				expr.getStringLiteral()             
		);
		assertTrue(recordSatisfies);
		
		
		//=====================================================================
		expr = new ConditionalExpression(
				FieldValueGetters.JMBAG,
				"0036555555",
				ComparisonOperators.LESS
		);
		record = new StudentRecord("0036444454", "Peric", "Perislav", 3);
		recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record), 
				expr.getStringLiteral()             
		);
		assertTrue(recordSatisfies);
		
		
		//=====================================================================
		expr = new ConditionalExpression(
				FieldValueGetters.FIRST_NAME,
				"Legolas",
				ComparisonOperators.EQUALS
		);
		record = new StudentRecord("0036444454", "Peric", "Legolas", 3);
		recordSatisfies = expr.getComparisonOperator().satisfied(
				expr.getFieldGetter().get(record), 
				expr.getStringLiteral()             
		);
		assertTrue(recordSatisfies);
	}
}
