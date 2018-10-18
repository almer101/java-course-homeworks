package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This is a demonstration program of the
 * database implementation.
 * 
 * @author ivan
 *
 */
public class StudentDB {
	
	/**
	 * This is the method called then the 
	 * program is run.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		StudentDatabase sdb = null;
		try {
			List<String> lines = Files.readAllLines(
				    Paths.get("./database.txt"),
				    StandardCharsets.UTF_8
			);
			sdb = new  StudentDatabase(lines);
			
		} catch (IOException e) {
			System.out.println("Could not read the file.");
			System.exit(0);
		} catch (ParserException e) {
			System.out.println("The error during parsing: " + 
						e.getMessage() + "\n The system "
								+ "will quit now!");
			System.exit(0);
		}
		
		System.out.print("> ");
		
		while(sc.hasNext()) {
			String input = sc.nextLine().trim();
			if(input.equalsIgnoreCase("exit")) break;
			try {
				doWork(input, sdb);
				
			} catch (IllegalArgumentException e) {
				System.out.println("The first word in the "
						+ "query must be \"query\". Please"
						+ " enter a new valid query!");
				
			} catch (ParserException e) {
				System.out.println("An error occurred "
						+ "during parsing! Error description: "
						+ e.getMessage() + "\nEnter a new "
								+ "valid query!");
			}
			
			System.out.print("> ");
		}
		System.out.println("Goodbye!");
		sc.close();
	}

	/**
	 * This method does all the work, that is: parses a 
	 * query, filters the elements from the specified
	 * 
	 * @param input
	 */
	private static void doWork(String input, StudentDatabase sdb) {
		input = checkFirstWord(input);

		QueryParser qp = new QueryParser(input);
		List<StudentRecord> students;
		
		if(qp.isDirectQuery()) {
			students = new ArrayList<>();
			StudentRecord record = sdb.forJMBAG(qp.getQueriedJMBAG());
			if(record != null) students.add(record);
			System.out.println("Using index for record retrieval.");
			
		} else {
			students = sdb.filter(
					new QueryFilter(qp.getQuery()));
		}
		printStudents(students);
		System.out.println("Records selected: " + 
				students.size()+ "\n");
		
	}

	/**
	 * This method checks if the first word is "query"
	 * and if it is removes it from the string and 
	 * returns the rest of the input.
	 * That way the input is ready for the {@link QueryParser}.
	 * 
	 * @param input
	 * 		an input line
	 * 
	 * @return
	 * 		a string without the first word.
	 * 
	 * @throws IllegalArgumentException
	 * 		if the first word is not query.
	 */
	private static String checkFirstWord(String input) {
		int index = 0;
		for(int i = 0; i < input.length(); i++) {
			if(Character.isLetter(input.charAt(i))) continue;
			index = i;
			break;
		}
		if(!input.substring(0, index).equalsIgnoreCase("query")) {
			throw new IllegalArgumentException("The first "
					+ "word must be query!");
		}
		
		return input.substring(index).trim();
	}

	/**
	 * This method prints the elements of the given list
	 * in shape of a table.
	 * 
	 * @param students
	 * 		list of students to print out.
	 */
	private static void printStudents(List<StudentRecord> students) {
		if(students.size() == 0) return;
		int[] max = maxLength(students);
		printHeaderOrFooter(students, max);
		
		for(StudentRecord r : students) {
			System.out.print("| ");
			System.out.print(r.getJmbag());
			printBlanks(max[0] - r.getJmbag().length() + 1);
			
			System.out.print(r.getLastName());
			printBlanks(max[1] - r.getLastName().length() + 1);
			
			System.out.print(r.getFirstName());
			printBlanks(max[2] - r.getFirstName().length() + 1);
			
			System.out.print(r.getFinalGrade());
			printBlanks(max[3] - String.valueOf(
					r.getFinalGrade()).length() + 1);
			
			System.out.println();
		}
		
		printHeaderOrFooter(students, max);
	}

	/**
	 * This method prints the specified number of blanks and
	 * a | char on the end.
	 * 
	 * @param n
	 * 		number of blank spaces to print.
	 */
	private static void printBlanks(int n) {
		for(int i = 0; i < n; i++) {
			System.out.print(" ");
		}
		System.out.print("| ");
	}

	/**
	 * This method prints header or footer (they are the same)
	 * according to the length of names, last names etc.
	 * 
	 * @param students
	 * 		list of students needed to be able to 
	 * 		determine which property of which 
	 * 		student is the longest.
	 * 
	 */
	private static void printHeaderOrFooter(
			List<StudentRecord> students, int[] max) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("+");
		for(int i = 0; i < max.length; i++) {
			for(int j = 0; j < max[i] + 2; j++) {
				sb.append("=");
			}
			sb.append("+");
		}
		System.out.println(sb.toString());
	}

	/**
	 * This method determines and returns the length of
	 * the longest jmbag, lastName, firstName and grade in the 
	 * students list.
	 * 
	 * @param students
	 * 		a list in which we are looking for the longest
	 * 		values.
	 * 
	 * @return
	 * 		the array of the lengths of the longest
	 * 		properties.
	 * 		indexes:
	 * 			0 -> jmbag; 1 -> lastName; 2 -> firstName;
	 * 			3 -> grade
	 */
	private static int[] maxLength(List<StudentRecord> students) {
		int maxJmbag = students.get(0).getJmbag().length();
		int maxLastName = students.get(0).getLastName().length();
		int maxFirstName = students.get(0).getFirstName().length();
		
		for(StudentRecord r : students) {
			if(r.getJmbag().length() > maxJmbag) {
				maxJmbag = r.getJmbag().length();
			}
			if(r.getLastName().length() > maxLastName) {
				maxLastName = r.getLastName().length();
			}
			if(r.getFirstName().length() > maxFirstName) {
				maxFirstName = r.getFirstName().length();
			}
		}
		int[] array = new int[4];
		array[0] = maxJmbag;
		array[1] = maxLastName;
		array[2] = maxFirstName;
		array[3] = 1;
		return array;
	}
}
