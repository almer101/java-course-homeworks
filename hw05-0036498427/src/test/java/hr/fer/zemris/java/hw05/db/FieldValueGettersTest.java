package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FieldValueGettersTest {

	@Test
	public void firstNameTest() {
		IFieldValueGetter getter = FieldValueGetters.FIRST_NAME;
		StudentRecord record1 = new StudentRecord("003624", "Peric", "Pero", 5);
		StudentRecord record2 = new StudentRecord("003625", "Peric", "Ana", 5);
		StudentRecord record3 = new StudentRecord("003628", "Kresic", "Mislav", 5);
		
		assertTrue("Pero".equals(getter.get(record1)));
		assertFalse("pero".equals(getter.get(record1)));
		
		assertTrue("Ana".equals(getter.get(record2)));
		assertFalse("Pero".equals(getter.get(record2)));

		assertTrue("Mislav".equals(getter.get(record3)));
		assertFalse("pero".equals(getter.get(record3)));
	}
	
	@Test
	public void lastNameTest() {
		IFieldValueGetter getter = FieldValueGetters.LAST_NAME;
		StudentRecord record1 = new StudentRecord("003624", "Perić", "Pero", 5);
		StudentRecord record2 = new StudentRecord("003625", "Petrić", "Ana", 5);
		StudentRecord record3 = new StudentRecord("003628", "Krešić Anić", "Mislav", 5);
		
		assertTrue("Perić".equals(getter.get(record1)));
		assertFalse("Peric".equals(getter.get(record1)));
		
		assertTrue("Petrić".equals(getter.get(record2)));
		assertFalse("petrić".equals(getter.get(record2)));
		
		assertTrue("Krešić Anić".equals(getter.get(record3)));
		assertFalse("KrešićAnić".equals(getter.get(record3)));
	}
	
	@Test
	public void jmbagTest() {
		IFieldValueGetter getter = FieldValueGetters.JMBAG ;
		StudentRecord record1 = new StudentRecord("003624", "Perić", "Pero", 5);
		StudentRecord record2 = new StudentRecord("003625", "Petrić", "Ana", 5);
		StudentRecord record3 = new StudentRecord("003628", "Krešić Anić", "Mislav", 5);
		
		assertTrue("003624".equals(getter.get(record1)));
		assertFalse("0036242".equals(getter.get(record1)));
		
		assertTrue("003625".equals(getter.get(record2)));
		assertFalse("003624".equals(getter.get(record2)));
		
		assertTrue("003628".equals(getter.get(record3)));
		assertFalse("0036".equals(getter.get(record3)));
	}
}
