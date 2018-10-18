package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ComparisonOperatorsTest {

	@Test
	public void lessTest() {
		IComparisonOperator op = ComparisonOperators.LESS;
		assertTrue(op.satisfied("Boat", "Car"));
		assertTrue(op.satisfied("", "A"));
		assertFalse(op.satisfied("Baka", "Ana"));
		assertFalse(op.satisfied("avion", "avion"));
		assertTrue(op.satisfied("avion", "avion2"));
	}
	
	@Test
	public void lessOrEqualsTest() {
		IComparisonOperator op = ComparisonOperators.LESS_OR_EQUALS;
		assertTrue(op.satisfied("Boat", "Car"));
		assertTrue(op.satisfied("", "A"));
		assertFalse(op.satisfied("Baka", "Ana"));
		assertTrue(op.satisfied("avion", "avion"));
		assertTrue(op.satisfied("avion", "avion2"));
		
		assertTrue(op.satisfied("abcdefghijK", "abcdefghijK"));
		assertFalse(op.satisfied("word", "Word"));
	}
	
	@Test
	public void greaterTest() {
		IComparisonOperator op = ComparisonOperators.GREATER;
		assertTrue(op.satisfied("Jako", "Iznad"));
		assertTrue(op.satisfied("A", ""));
		assertTrue(op.satisfied("Nogomet2", "Nogomet"));
		
		assertFalse(op.satisfied("Nogomet2", "Nogomet2"));
		assertTrue(op.satisfied("radio", "Radio"));
		assertFalse(op.satisfied("", ""));
	}
	
	@Test
	public void greaterOrEqualsTest() {
		IComparisonOperator op = ComparisonOperators.GREATER_OR_EQUALS;
		assertTrue(op.satisfied("Jako", "Iznad"));
		assertTrue(op.satisfied("A", ""));
		assertTrue(op.satisfied("Nogomet2", "Nogomet"));
		
		assertTrue(op.satisfied("Nogomet2", "Nogomet2"));
		assertTrue(op.satisfied("radio", "Radio"));
		
		assertTrue(op.satisfied("", ""));
	}
	
	@Test
	public void equalsNotEqualsTest() {
		IComparisonOperator op1 = ComparisonOperators.EQUALS;
		IComparisonOperator op2 = ComparisonOperators.NOT_EQUALS;
		
		assertTrue(op1.satisfied("Ana", "Ana"));
		assertTrue(op2.satisfied("Ana", "Ana2"));
		
		assertFalse(op1.satisfied("fer", "FER"));
		assertFalse(op2.satisfied("fer", "fer"));
		
		assertTrue(op1.satisfied("ZadACA", "ZadACA"));
		assertTrue(op2.satisfied("ZadACA", "ZadACa"));
	}
	
	@Test
	public void likeTest() {
		IComparisonOperator op = ComparisonOperators.LIKE;
		
		assertTrue(op.satisfied("Ivan", "*va*"));
		assertTrue(op.satisfied("AAAA", "AA*A"));
		assertFalse(op.satisfied("AAA", "AA*AA"));
		
		assertTrue(op.satisfied("Borovo", "B*"));
		assertTrue(op.satisfied("akljfasdjfbksa", "*"));
		assertTrue(op.satisfied("", "*"));
		assertTrue(op.satisfied("BerliN", "B*N"));
		assertTrue(op.satisfied("Berlin", "Berlin"));
		
		assertTrue(op.satisfied("SuperMario", "*Mario*"));
		assertTrue(op.satisfied("SuperMarioSuper", "*Mario*"));
		
		assertTrue(op.satisfied("Tomac", "To*"));
	}
}
