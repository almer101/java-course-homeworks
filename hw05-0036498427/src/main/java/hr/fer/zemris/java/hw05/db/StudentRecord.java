package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * This class represents a record for each
 * student. The class has attributes that
 * describe the student.
 * 
 * @author ivan
 *
 */
public class StudentRecord {

	//==================Properties========================
	/**
	 * The jmbag of the student.
	 */
	private String jmbag;
	
	/**
	 * The last name of the student.
	 */
	private String lastName;
	
	/**
	 * The first name of the student.
	 */
	private String firstName;
	
	/**
	 * The final grade of the student.
	 */
	private int finalGrade;
	
	//==================Constructors==============================
	
	/**
	 * This constructor initializes the properties
	 * of this class with the specified values.
	 * 
	 * @param jmbag
	 * 		the initial value of jmbag
	 * 
	 * @param lastName
	 * 		the initial value of last name
	 * 
	 * @param firstName
	 * 		the initial value of first name
	 * 
	 * @param finalGrade
	 * 		the initial value of the final grade.
	 */
	public StudentRecord(String jmbag, String lastName, 
			String firstName, int finalGrade) {
		super();
		this.jmbag = Objects.requireNonNull(jmbag);
		this.lastName = Objects.requireNonNull(lastName);
		this.firstName = Objects.requireNonNull(firstName);
		this.finalGrade = finalGrade;
	}

	//==================Getters=====================================
	
	/**
	 * This method returns the value of the 
	 * <code>jmbag</code> property.
	 * 
	 * @return
	 * 		the value of the <code>jmbag</code> 
	 * 		property.
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * This method returns the value of the 
	 * <code>lastName</code> property.
	 * 
	 * @return
	 * 		the value of the <code>lastName</code> 
	 * 		property.
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * This method returns the value of the 
	 * <code>firstName</code> property.
	 * 
	 * @return
	 * 		the value of the <code>firstName</code> 
	 * 		property.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * This method returns the value of the 
	 * <code>finalGrade</code> property.
	 * 
	 * @return
	 * 		the value of the <code>finalGrade</code> 
	 * 		property.
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
	
	//===================Method implementations=======================
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + 
				((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return jmbag + " " + 
				lastName + " " + 
				firstName + " " + 
				finalGrade + " ";
	}
}
