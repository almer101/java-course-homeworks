package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class StudentDatabaseTest {

	private IFilter filter1 = r -> true;
	private IFilter filter2 = r -> false;
	
	@Test
	public void filterTest() throws IOException {
		List<String> lines = Files.readAllLines(
			    Paths.get("./database.txt"),
			    StandardCharsets.UTF_8
		);
		StudentDatabase sdb = new  StudentDatabase(lines);
		
		List<StudentRecord> studenti = sdb.filter(filter2);
		for(StudentRecord r : studenti) {
			System.out.println(r);
		}
		assertTrue(studenti.size() == 0);
		
		studenti = sdb.filter(filter1);
		assertTrue(studenti.size() == 63);
	}
	
	@Test
	public void forJmbagTest() throws IOException {
		List<String> lines = Files.readAllLines(
			    Paths.get("./database.txt"),
			    StandardCharsets.UTF_8
		);
		StudentDatabase sdb = new  StudentDatabase(lines);
		
		assertTrue(sdb.forJMBAG("0000000001")
				.getFirstName()
				.equals("Marin")
		);
		
		assertTrue(sdb.forJMBAG("0000000010")
				.getLastName()
				.equals("Dokleja")
		);
		
		assertNull(sdb.forJMBAG("0036498427"));

	}
}
