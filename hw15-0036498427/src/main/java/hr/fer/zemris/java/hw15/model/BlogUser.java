package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This class models one blog user which has the name, last name, nickname,
 * email and the secure hash value of the password.
 * 
 * @author ivan
 *
 */
@Entity
@Table(name="blog_users")
public class BlogUser {

	/** The id of the user. */
	private long id;

	/** The first name of the user */
	private String firstName;

	/** The last name of the user. */
	private String lastName;

	/** The nickname used on the blog by this user. */
	private String nick;

	/** The email of the user */
	private String email;

	/** The hash value of the user's password. */
	private String passwordHash;
	
	/**The list of blog entries this user has made.*/
	private List<BlogEntry> entries = new ArrayList<>();
	
	/**
	 * Returns the value of the property {@link #entries}.
	 * 
	 * @return
	 * 		the value of the property {@link #entries}.
	 */
	@OneToMany(mappedBy = "created", fetch=FetchType.LAZY, cascade=CascadeType.PERSIST) 
	public List<BlogEntry> getEntries() { 
		return entries; 
	}
	
	/**
	 * Sets the value of the property {@link #entries} to the specified value.
	 * 
	 * @param entries
	 * 		the new value of the property {@link #entries}
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
	}
	
	/**
	 * This is the method which returns the value of the parameter {@link #id}
	 * 
	 * @return the value of the parameter {@link #id}
	 */
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	
	/**
	 * This method sets the value of the parameter {@link #id}
	 * 
	 * @param id
	 * 		the new value of the parameter {@link #id}
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * This is the method which returns the value of the parameter
	 * {@link #firstName}
	 * 
	 * @return the value of the parameter {@link #firstName}
	 */
	@Column(nullable = false, length = 30)
	public String getFirstName() {
		return firstName;
	}

	/**
	 * This method sets the value of the property {@link #firstName} to the specified
	 * value.
	 * 
	 * @param firstName
	 *            the new value of the property {@link #firstName}
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * This is the method which returns the value of the parameter {@link #lastName}
	 * 
	 * @return the value of the parameter {@link #lastName}
	 */
	@Column(nullable = false, length = 50)
	public String getLastName() {
		return lastName;
	}

	/**
	 * This method sets the value of the property {@link #lastName} to the specified
	 * value.
	 * 
	 * @param lastName
	 *            the new value of the property {@link #lastName}
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * This is the method which returns the value of the parameter {@link #nick}
	 * 
	 * @return the value of the parameter {@link #nick}
	 */
	@Column(nullable = false, length = 20, unique = true)
	public String getNick() {
		return nick;
	}

	/**
	 * This method sets the value of the property {@link #nick} to the specified
	 * value.
	 * 
	 * @param nick
	 *            the new value of the property {@link #nick}
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * This is the method which returns the value of the parameter {@link #email}
	 * 
	 * @return the value of the parameter {@link #email}
	 */
	@Column(nullable = false, length = 100)
	public String getEmail() {
		return email;
	}

	/**
	 * This method sets the value of the property {@link #email} to the specified
	 * value.
	 * 
	 * @param email
	 *            the new value of the property {@link #email}
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * This is the method which returns the value of the parameter
	 * {@link #passwordHash}
	 * 
	 * @return the value of the parameter {@link #passwordHash}
	 */
	@Column(nullable = false, length = 150)
	public String getPasswordHash() {
		return passwordHash;
	}
	
	/**
	 * This method sets the value of the property {@link #passwordHash} to the specified
	 * value.
	 * 
	 * @param passwordHash
	 *            the new value of the property {@link #passwordHash}
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

}
