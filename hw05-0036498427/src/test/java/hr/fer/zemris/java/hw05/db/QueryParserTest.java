package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class QueryParserTest {
	
	@Test
	public void queryParserTest1() {
		QueryParser qp1 = new QueryParser(" jmbag       =\"0123456789\"    ");
		assertTrue(qp1.isDirectQuery()); // true
		assertTrue("0123456789".equals(qp1.getQueriedJMBAG())); // 0123456789
		
		assertTrue(qp1.getQuery().size() == 1); // 1
	}
	
	@Test
	public void queryParserTest2() {
		QueryParser qp1 = new QueryParser(" jmbag     like \"0034\" and firstName >= \"A\" "
				+ "and firstName < \"K\"  ");
		assertFalse(qp1.isDirectQuery());
		//assertTrue("0123456789".equals(qp1.getQueriedJMBAG())); //throws an exc
		
		assertTrue(qp1.getQuery().size() == 3);
	}
	
	@Test
	public void queryParserTest3() {
		QueryParser qp1 = new QueryParser(" firstName = \"Ivan\" AnD jmbag >= \"0036\"  ");
		assertFalse(qp1.isDirectQuery());
		//assertTrue("0123456789".equals(qp1.getQueriedJMBAG())); //throws an exc
		
		assertTrue(qp1.getQuery().size() == 2);
	}
	
	@Test
	public void queryPerserTest4() {
		QueryParser qp1 = new QueryParser(" ");
		assertFalse(qp1.isDirectQuery());
		
		assertTrue(qp1.getQuery().size() == 0);
	}
	
	@Test
	public void queryParserTest5() {
		QueryParser qp1 = new QueryParser(" jmbag     like \"0034\" and "
				+ "firstName LIKE \"A*\" and firstName LIKe \"K*\"  ");
		assertFalse(qp1.isDirectQuery());
		
		assertTrue(qp1.getQuery().size() == 3);
	}
}
