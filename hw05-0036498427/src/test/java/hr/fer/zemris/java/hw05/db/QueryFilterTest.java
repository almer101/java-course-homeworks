package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

public class QueryFilterTest {

	@Test
	public void queryFilterTest1() throws IOException{
		List<String> lines = Files.readAllLines(
			    Paths.get("./database.txt"),
			    StandardCharsets.UTF_8
		);
		StudentDatabase sdb = new  StudentDatabase(lines);
		
		QueryParser qp = new QueryParser("jmbag = \"0000000022\"");
		
		List<StudentRecord> students = sdb.filter(new QueryFilter(qp.getQuery()));
		assertTrue(students.size() == 1);
	}	
	
	@Test
	public void queryFilterTest2() throws IOException{
		List<String> lines = Files.readAllLines(
			    Paths.get("./database.txt"),
			    StandardCharsets.UTF_8
		);
		StudentDatabase sdb = new  StudentDatabase(lines);
		
		QueryParser qp = new QueryParser("jmbag >= \"0000000002\"");
		
		List<StudentRecord> students = sdb.filter(new QueryFilter(qp.getQuery()));
		assertTrue(students.size() == 62);
	}	
	
	@Test
	public void queryFilterTest3() throws IOException{
		List<String> lines = Files.readAllLines(
			    Paths.get("./database.txt"),
			    StandardCharsets.UTF_8
		);
		StudentDatabase sdb = new  StudentDatabase(lines);
		
		QueryParser qp = new QueryParser("jmbag > \"0000000019\" and firstName like \"I*\"");
		
		List<StudentRecord> students = sdb.filter(new QueryFilter(qp.getQuery()));
//		for(StudentRecord r : students) {
//			System.out.println(r);
//		}
		assertTrue(students.size() == 7);
	}	
	
	@Test
	public void queryFilterTest4() throws IOException{
		List<String> lines = Files.readAllLines(
			    Paths.get("./database.txt"),
			    StandardCharsets.UTF_8
		);
		StudentDatabase sdb = new  StudentDatabase(lines);
		
		QueryParser qp = new QueryParser(" lastName >= \"K\" and firstName <= \"I\"");
		
		List<StudentRecord> students = sdb.filter(new QueryFilter(qp.getQuery()));
//		for(StudentRecord r : students) {
//			System.out.println(r);
//		}		
		assertTrue(students.size() == 13);
	}	
	
	@Test
	public void queryFilterTest5() throws IOException{
		List<String> lines = Files.readAllLines(
			    Paths.get("./database.txt"),
			    StandardCharsets.UTF_8
		);
		StudentDatabase sdb = new  StudentDatabase(lines);
		
		QueryParser qp = new QueryParser(" jmbag like \"*2\"");
		
		List<StudentRecord> students = sdb.filter(new QueryFilter(qp.getQuery()));
//		for(StudentRecord r : students) {
//			System.out.println(r);
//		}		
		assertTrue(students.size() == 7);
	}	
}
