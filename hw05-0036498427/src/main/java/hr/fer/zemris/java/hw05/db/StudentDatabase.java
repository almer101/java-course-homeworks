package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

/**
 * This class represents a database of
 * {@link StudentRecord}s. 
 * 
 * @author ivan
 *
 */
public class StudentDatabase {

	//====================Properties=============================
	/**
	 * The list of all the student records from 
	 * the input text file.
	 */
	private List<StudentRecord> students;
	
	/**
	 * The table of the student records used for 
	 * faster access to the record if the jmbag is
	 * known.
	 */
	private SimpleHashtable<String, StudentRecord> table;
	
	//=====================Constructors==========================
	/**
	 * This constructor receives the list of
	 * all the rows from the input file and
	 * initializes the <code>students</code>
	 * property with {@link StudentRecord}s 
	 * made from the specified list
	 * 
	 * @param rows
	 * 		the list of rows of the input
	 * 		file.
	 */
	public StudentDatabase(List<String> rows) {
		students = new ArrayList<StudentRecord>();
		table = new SimpleHashtable<String, StudentRecord>();
		
		for(String s : rows) {
			StudentRecord record = getRecord(s);
			students.add(record);
			table.put(record.getJmbag(), record);
		}
	}

	//===================Method implementations===================
	/**
	 * This method gets 1 parameter which is a 
	 * {@link String} representation of the 
	 * {@link StudentRecord} and transforms that 
	 * string into an actual record.
	 * 
	 * @param recordString
	 *		the {@link String} representation of the
	 *		{@link StudentRecord};
	 * 		
	 * @return
	 * 		the {@link StudentRecord} with the values
	 * 		from the specified string.
	 */
	private StudentRecord getRecord(String recordString) {
		String[] parts = recordString.trim().split("\\t+");
		try {
			int grade = Integer.parseInt(parts[3]);
			StudentRecord newRecord = new StudentRecord(
					parts[0].trim(), 
					parts[1].trim(), 
					parts[2].trim(), 
					grade);
			
			return newRecord;
		} catch (NumberFormatException e) {
			System.out.println("The invalid grade.");
			return null;
		}
	}
	
	/**
	 * This method returns a {@link StudentRecord}
	 * with the specified jmbag. The complexity of
	 * this method is O(1).
	 * 
	 * @param jmbag
	 * 		the jmbag of the wanted student.
	 * 
	 * @return
	 * 		the {@link StudentRecord} of the student
	 * 		with the specified jmbag.
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return table.get(jmbag);
	}
	
	/**
	 * This method loops through all the elements in
	 * the students list and calls <i>accepts</i>
	 * method for each element. In case the <i>accepts</i> 
	 * method returns true the element is added to the
	 * temporary list which is returned after the method
	 * looped through all the elements.
	 * 
	 * @param filter
	 * 		the object which has the needed <i>accepts</i>
	 * 		method.
	 * 
	 * @return
	 * 		the new list of the filtered 
	 * 		{@link StudentRecord}s.
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> list = new ArrayList<>();
		
		for(StudentRecord record : students) {
			if(filter.accepts(record)) {
				list.add(record);
			}
		}
		return list;
	}
}
