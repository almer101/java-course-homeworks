package hr.fer.zemris.java.hw06.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This program demonstrates working with
 * the Stream API on the list of 
 * {@link StudentRecord}
 * 
 * @author ivan
 *
 */
public class StudentDemo {

	private static final int NUMBER_OF_PARAMETERS = 7;
	
	private static final double EPSILON = 1E-08;
	
	private static final int MAX_GRADE = 5;
	
	private static final Comparator<StudentRecord> POINTS_SUM = new Comparator<StudentRecord>() {

		@Override
		public int compare(StudentRecord s1, StudentRecord s2) {
			double result = pointsSum(s2) - pointsSum(s1);
			if(Math.abs(result) < EPSILON) return 0;
			else if(result > 0) return 1;
			else return -1;
		}
	};
	
	private static final Comparator<String> JMBAG_ASC = 
			(j1, j2) -> j1.compareTo(j2);
	
	/**
	 * Method called when the program is run.
	 * 
	 * @param args
	 * 		command line arguments.
	 */
	public static void main(String[] args) {
		
		List<StudentRecord> records = null;
		try {
			List<String> lines = Files.readAllLines(
				    Paths.get("./src/main/resources/studenti.txt"),
				    StandardCharsets.UTF_8
			);
			records = convert(lines);
			
		} catch (IOException e) {
			System.out.println("Unable to read file! The "
					+ "program will now quit!");
			System.exit(0);
		}
		

		long broj = vratiBodovaViseOd25(records);
		printBodovaViseOd25(broj);
		
		long broj5 = vratiBrojOdlikasa(records);
		printBrojOdlikasa(broj5);
		
		
		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
		printOdlikasi(odlikasi);
		
		
		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
		printOdlikasiSortirano(odlikasiSortirano);
		
		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
		printNepolozeniJMBAGovi(nepolozeniJMBAGovi);
		
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		printMapaPoOcjenama(mapaPoOcjenama);
		
		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		printMapaPoOcjenama2(mapaPoOcjenama2);
		
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
		printProlazNeprolaz(prolazNeprolaz);
	}

	/**
	 * This method returns the map which maps the boolean
	 * value <code>true</code> to the list of student records
	 * who passed the subject and boolean value <code>false</code>
	 * to the list of student records who did not pass the subject.
	 * 
	 * @param records
	 * 		the list of student records.
	 * 
	 * @return
	 * 		the map which maps boolean values <code>true</code> and
	 * 		<code>false</code> to the list of student records
	 * 		who passed the subject, that is who did not pass the subject.
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.partitioningBy(s -> s.getGrade() != 1));
	}

	/**
	 * This method returns the list of {@link StudentRecord}s
	 * from the list of {@link String} lines containing 
	 * student information.
	 * 
	 * @param lines	
	 *		list of lines to convert from.
	 *
	 * @return
	 * 		the list of {@link StudentRecord}s with
	 * 		the given information.
	 */
	public static List<StudentRecord> convert(List<String> lines) {
		
		List<StudentRecord> records = new ArrayList<>();
		for(String line : lines) {
			String lineParts[] = line.split("\\t+|\\s+");
			if(lineParts.length < NUMBER_OF_PARAMETERS) continue;
			
			String jmbag = lineParts[0];
			String lastName = lineParts[1];
			String firstName = lineParts[2];
			
			try {
				double pointsMI = Double.parseDouble(lineParts[3]);
				double pointsZI = Double.parseDouble(lineParts[4]);
				double pointsLAB = Double.parseDouble(lineParts[5]);
				int grade = Integer.parseInt(lineParts[6]);
				
				records.add(new StudentRecord(
						jmbag, 
						lastName, 
						firstName, 
						pointsMI, 
						pointsZI, 
						pointsLAB, 
						grade)
				);
			} catch (NumberFormatException e) {
				System.out.println("Unable to parse given numbers!");
			}
		}
		return records;
	}
	
	/**
	 * This method returns the sum of the points of the 
	 * specified {@link StudentRecord}.
	 * 
	 * @param record
	 * 		record whose points to sum.
	 * 
	 * @return
	 * 		the sum of the points.
	 */
	public static double pointsSum(StudentRecord record) {
		return record.getPointsMI() 
				+ record.getPointsZI() 
				+ record.getPointsLAB();
	}
	
	/**
	 * This method returns the number of students from the
	 * specified list that have the sum of points 
	 * greater than 25.
	 * 
	 * @param records
	 * 		list of student records.
	 * 
	 * @return
	 * 		the number of students that have the sum of
	 * 		points greater than 25.
	 */
	public static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream()
				.filter(s -> pointsSum(s) > 25)
				.count();
	}
	
	/**
	 * This method returns the number of students who had the
	 * grade 5 on this subject.
	 * 
	 * @param records
	 * 		the list of student records
	 * 
	 * @return
	 * 		the number of students who had the grade 5.
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream()
				.filter(s -> s.getGrade() == 5)
				.count();
	}
	
	/**
	 * This method returns the list of students who had the 
	 * grade 5 on the subject.
	 * 
	 * @param records
	 * 		the list of student records,
	 * 
	 * @return
	 * 		the list of students who had the grade 5 on the
	 * 		subject.
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream()
				.filter(s -> s.getGrade() == 5)
				.collect(Collectors.toList());
	}
	
	/**
	 * This method returns the sorted list of who had the 
	 * grade 5 on the subject. The list is sorted in such
	 * way that the students with the most scored points
	 * are first in the list.
	 * 
	 * @param records
	 * 		the list of student records.
	 * 
	 * @return
	 * 		the sorted list of the students with the
	 * 		grade 5.
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.stream()
				.filter(s -> s.getGrade() == 5)
				.sorted(POINTS_SUM)
				.collect(Collectors.toList());
	}
	
	/**
	 * This method returns the list of student jmbags
	 * who did not pass the subject. (i.e. students 
	 * who had the grade 1 on the subject)
	 * 
	 * @param records
	 * 		the list of student records.
	 * 
	 * @return
	 * 		the list of student jmbags who did not
	 * 		pass the subject.
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream()
				.filter(s -> s.getGrade() == 1)
				.map(s -> s.getJmbag())
				.sorted(JMBAG_ASC)
				.collect(Collectors.toList());
	}
	
	/**
	 * This method returns the map that maps the grades to
	 * the list of students who scored that grade on 
	 * the subject.
	 * 
	 * @param records
	 * 		the list of student records.
	 * 
	 * @return
	 * 		the map which maps the grade to the list of
	 * 		students who scored that grade on the subject.
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.groupingBy(StudentRecord::getGrade));
	}

	/**
	 * This method returns the map which maps the grade on
	 * the subject to the number of students who scored
	 * that grade.
	 * 
	 * @param records
	 * 		the list of student records.
	 * 
	 * @return
	 * 		the map which maps the grade on the subject
	 * 		to the number of students who scored that 
	 * 		grade.
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.toMap(StudentRecord::getGrade, 
										  s -> 1, 
										  (i1, i2) -> (Integer)(i1 +1)));
	}
	
	/**
	 * Prints the content of the given parameter.
	 * 
	 * @param broj
	 * 		the number of students with the number of
	 * 		points greater than 25.
	 */
	private static void printBodovaViseOd25(long broj) {
		System.out.println("Number of students with more that 25 points is: " + broj);
		printSeparator();
	}

	/**
	 * This method prints the number of students
	 * with the grade 5.
	 * 
	 * @param broj5
	 * 		number of students with the grade 5.
	 */
	private static void printBrojOdlikasa(long broj5) {
		System.out.println("Number of students with grade 5: " + broj5);
		printSeparator();
	}

	/**
	 * This method prints the list of students with the
	 * grade 5.
	 * 
	 * @param odlikasi
	 * 		list of students with the grade 5.
	 */
	private static void printOdlikasi(List<StudentRecord> odlikasi) {
		System.out.println("Odlikasi");
		printList(odlikasi);
		printSeparator();
	}

	/**
	 * This method prints the specified list.
	 * 
	 * @param list
	 * 		list to be printed
	 */
	private static void printList(List<StudentRecord> list) {
		for(StudentRecord s : list) {
			System.out.println(s);
		}
	}

	/**
	 * This method prints the sorted list of students
	 * with the grade 5.
	 * 
	 * @param odlikasiSortirano
	 * 		sorted list of the students with the grade 5.
	 */
	private static void printOdlikasiSortirano(List<StudentRecord> odlikasiSortirano) {
		for(StudentRecord r : odlikasiSortirano) {
			System.out.print(r + "	 bodovi: " + pointsSum(r) +"\n");
		}
		printSeparator();
	}

	/**
	 * This method prints the list of student jmbags
	 * who didn't pass the subject.
	 * 
	 * @param nepolozeniJMBAGovi
	 * 		the list of student jmbags who didn't pass the subject.
	 */
	private static void printNepolozeniJMBAGovi(List<String> nepolozeniJMBAGovi) {
		int i = 0;
		for(String s : nepolozeniJMBAGovi) {
			System.out.print(s + ", ");
			if(i % 5 == 0 && i != 0) System.out.println();
			i++;
		}
		printSeparator();
	}

	/**
	 * This method prints the map which 
	 * maps the grade of the student with the 
	 * list of students with the certain grade.
	 * 
	 * @param mapaPoOcjenama
	 * 		map which maps the grade to the list of students 
	 * 		with that grade.
	 */
	private static void printMapaPoOcjenama(Map<Integer, List<StudentRecord>> mapaPoOcjenama) {
		for(int i = 1; i < MAX_GRADE + 1; i++) {
			List<StudentRecord> list = mapaPoOcjenama.get(i);
			System.out.println("Ocjena: " + i);
			for(StudentRecord r : list) {
				System.out.println(r);
			}
		}
		printSeparator();
	}

	/**
	 * This method prints the map which maps
	 * the grade with the number of students who got
	 * that grade.
	 * 
	 * @param mapaPoOcjenama2
	 * 		the map of student which maps the grade with the
	 * 		number of students with that grade.
	 */
	private static void printMapaPoOcjenama2(Map<Integer, Integer> mapaPoOcjenama2) {
		for(int i = 1; i < MAX_GRADE + 1; i++) {
			int number = mapaPoOcjenama2.get(i);
			System.out.println("Ocjena: " + i + " => " + number);
		}
		printSeparator();
	}

	/**
	 * This method prints the map which maps
	 * the boolean value <code>true</code> with 
	 * the students who passed the subject, i.e
	 * boolean value <code>false</code> with the
	 * students who did not pass the subject.
	 * 
	 * @param prolazNeprolaz
	 * 		the map which maps
	 * 		the boolean value <code>true</code> with 
	 * 		the students who passed the subject, i.e
	 * 		boolean value <code>false</code> with the
	 * 		students who did not pass the subject.
	 */
	private static void printProlazNeprolaz(Map<Boolean, List<StudentRecord>> prolazNeprolaz) {
		System.out.println("False:\n");
		printList(prolazNeprolaz.get(false));
		System.out.println("True:\n");
		printList(prolazNeprolaz.get(true));
		printSeparator();
	}

	/**
	 * This map prints the separator which
	 * separates two different outputs.
	 * 
	 */
	private static void printSeparator() {
		System.out.println("\n====================================================================\n");
	}
}
