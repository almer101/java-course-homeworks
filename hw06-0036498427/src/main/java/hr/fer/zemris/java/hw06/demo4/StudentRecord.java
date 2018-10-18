package hr.fer.zemris.java.hw06.demo4;

import java.util.Objects;

/**
 * This class represents an entry for one
 * student containing his information:
 * jmbag, last name, first name, pointsMI,
 * pointsZI, pointsLAB, grade
 * 
 * @author ivan
 *
 */
public class StudentRecord {

	//==========================Properties==============================
	
	/**
	 * The jmbag of the student.
	 */
	private String jmbag;
	
	/**
	 * The last name of the student
	 */
	private String lastName;
	
	/**
	 * The first name of the student.
	 */
	private String firstName;
	
	/**
	 * Points scored on the midterm.
	 */
	private double pointsMI;
	
	/**
	 * Points scored on the finals.
	 */
	private double pointsZI;
	
	/**
	 * Points scored on the Labs.
	 */
	private double pointsLAB;
	
	/**
	 * Final grade for the certain subject.
	 */
	private int grade;
	
	//========================Constructor===============================
	
	/**
	 * The constructor which receives all initial values for
	 * the initialization of the instance of this class.
	 * 
	 * 
	 * @param jmbag
	 * 		initial value of the jmbag
	 * @param lastName
	 * 		initial value of the last name.
	 * @param firstName
	 * 		initial value of the first name.
	 * @param pointsMI
	 * 		points scored at midterm.
	 * @param pointsZI
	 * 		points scored at finals.
	 * @param pointsLAB
	 * 		points scored at labs.
	 * @param grade
	 * 		final grade from the subject.
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, 
			double pointsMI, double pointsZI, double pointsLAB, int grade) {
		super();
		this.jmbag = Objects.requireNonNull(jmbag, "Jmbag cannot be null!");
		this.lastName = Objects.requireNonNull(lastName, "Last name "
				+ "cannot be null!");
		this.firstName = Objects.requireNonNull(firstName, "First name "
				+ "cannot be null!");
		this.pointsMI = pointsMI;
		this.pointsZI = pointsZI;
		this.pointsLAB = pointsLAB;
		this.grade = grade;
	}

	//======================Getters======================================
	
	/**
	 * This method returns the value of the 
	 * <code>jmbag</code> property.
	 * 
	 * @return
	 * 		the value of the <code>jmbag</code>
	 * 		property
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
	 * 		property
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
	 * 		property
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * This method returns the value of the 
	 * <code>pointsMI</code> property.
	 * 
	 * @return
	 * 		the value of the <code>pointsMI</code>
	 * 		property
	 */
	public double getPointsMI() {
		return pointsMI;
	}
	
	/**
	 * This method returns the value of the 
	 * <code>pointsZI</code> property.
	 * 
	 * @return
	 * 		the value of the <code>pointsZI</code>
	 * 		property
	 */
	public double getPointsZI() {
		return pointsZI;
	}

	/**
	 * This method returns the value of the 
	 * <code>pointsLAB</code> property.
	 * 
	 * @return
	 * 		the value of the <code>pointsLAB</code>
	 * 		property
	 */
	public double getPointsLAB() {
		return pointsLAB;
	}

	/**
	 * This method returns the value of the 
	 * <code>grade</code> property.
	 * 
	 * @return
	 * 		the value of the <code>grade</code>
	 * 		property
	 */
	public int getGrade() {
		return grade;
	}
	
	//===============================Method implementations=====================
	
	@Override
	public String toString() {
		return jmbag + "	"
				+ lastName + "	"
				+ firstName + "	"
				+ pointsMI + "	"
				+ pointsZI + "	"
				+ pointsLAB + "	"
				+ grade;
	}	
}
