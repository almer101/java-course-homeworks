package hr.fer.zemris.java.hw15.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This class models the blog entry. Each entry has associated identification,
 * list of comments left on that entry, date when the comment was created, date
 * when it was last modified, title of the entry, text of the entry and the user
 * who created the entry.
 * 
 * @author ivan
 *
 */
@NamedQueries({
		@NamedQuery(name = "BlogEntry.upit1", query = "select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when") })
@Entity
@Table(name = "blog_entries")
@Cacheable(true)
public class BlogEntry {

	/** The identification of the entry. */
	private Long id;

	/** The list of comments on the entry. */
	private List<BlogComment> comments = new ArrayList<>();

	/** The date when the entry was created. */
	private Date createdAt;

	/** The date when the date was last modified. */
	private Date lastModifiedAt;

	/** The title of the entry. */
	private String title;

	/** The text of the entry. */
	private String text;

	/** The user who created the entry. */
	private BlogUser created;

	/**
	 * This method returns the user which created this entry.
	 * 
	 * @return the user which created this entry.
	 */
	@ManyToOne
	public BlogUser getCreated() {
		return created;
	}

	/**
	 * This method sets the value of the property {@link #created} to the specified
	 * value.
	 * 
	 * @param created
	 *            the new value of the property {@link #created}.
	 */
	public void setCreated(BlogUser created) {
		this.created = created;
	}

	/**
	 * Returns the value of the property {@link #id}.
	 * 
	 * @return the value of the property {@link #id}.
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Sets the value of the property {@link #id} to the specified value.
	 * 
	 * @param id
	 *            the new value of the property {@link #id}.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the value of the property {@link #comments}.
	 * 
	 * @return the value of the property {@link #comments}.
	 */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Sets the value of the property {@link #comments} to the specified value.
	 * 
	 * @param comments
	 *            the new value of the property {@link #comments}.
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Returns the value of the property {@link #createdAt}.
	 * 
	 * @return the value of the property {@link #createdAt}.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the value of the property {@link #createdAt} to the specified value.
	 * 
	 * @param createdAt
	 *            the new value of the property {@link #createdAt}.
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Returns the value of the property {@link #lastModifiedAt}.
	 * 
	 * @return the value of the property {@link #lastModifiedAt}.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Sets the value of the property {@link #lastModifiedAt} to the specified value.
	 * 
	 * @param lastModifiedAt
	 *            the new value of the property {@link #lastModifiedAt}.
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Returns the value of the property {@link #title}.
	 * 
	 * @return the value of the property {@link #title}.
	 */
	@Column(length = 200, nullable = false)
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the value of the property {@link #title} to the specified value.
	 * 
	 * @param title
	 *            the new value of the property {@link #title}.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the value of the property {@link #text}.
	 * 
	 * @return the value of the property {@link #text}.
	 */
	@Column(length = 4096, nullable = false)
	public String getText() {
		return text;
	}

	/**
	 * Sets the value of the property {@link #text} to the specified value.
	 * 
	 * @param text
	 *            the new value of the property {@link #text}.
	 */
	public void setText(String text) {
		this.text = text;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}