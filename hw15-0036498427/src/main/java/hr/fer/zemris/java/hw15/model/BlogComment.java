package hr.fer.zemris.java.hw15.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This class models one blog comment. Each comment has associated identification, blog entry
 * on which the comment was left, the email of the user who left the comment, the message he left,
 * the date and time when the comment was posted and the 
 * 
 * @author ivan
 *
 */
@Entity
@Table(name="blog_comments")
public class BlogComment {

	/**The identification of the comment.*/
	private Long id;
	
	/**The blog entry where the comment was left.*/
	private BlogEntry blogEntry;
	
	/**The email of the user who left the comment.*/
	private String usersEMail;
	
	/**The message the user left.*/
	private String message;
	
	/**The date when the comment was posted.*/
	private Date postedOn;
	
	/**
	 * Returns the {@link #id} property of the object.
	 * 
	 * @return
	 * 		the value of the {@link #id} property of the object.
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the value of the {@link #id} property to the specified value.
	 * 
	 * @param id
	 * 		the new value of the {@link #id} property.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Returns the {@link #blogEntry} property of the object.
	 * 
	 * @return
	 * 		the value of the {@link #blogEntry} property of the object.
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * Sets the value of the {@link #blogEntry} property to the specified value.
	 * 
	 * @param blogEntry
	 * 		the new value of the {@link #blogEntry} property.
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Returns the {@link #usersEMail} property of the object.
	 * 
	 * @return
	 * 		the value of the {@link #usersEMail} property of the object.
	 */
	@Column(length=100,nullable=false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Sets the value of the {@link #usersEMail} property to the specified value.
	 * 
	 * @param usersEMail
	 * 		the new value of the {@link #usersEMail} property.
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Returns the {@link #message} property of the object.
	 * 
	 * @return
	 * 		the value of the {@link #message} property of the object.
	 */
	@Column(length=4096,nullable=false)
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the value of the {@link #message} property to the specified value.
	 * 
	 * @param message
	 * 		the new value of the {@link #message} property.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Returns the {@link #postedOn} property of the object.
	 * 
	 * @return
	 * 		the value of the {@link #postedOn} property of the object.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Sets the value of the {@link #postedOn} property to the specified value.
	 * 
	 * @param postedOn
	 * 		the new value of the {@link #postedOn} property.
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}