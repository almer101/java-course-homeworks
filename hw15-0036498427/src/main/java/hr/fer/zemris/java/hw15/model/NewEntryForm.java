package hr.fer.zemris.java.hw15.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.dao.DAOProvider;

/**
 * This is the class which models the form for a new of edited entry. It
 * contains the id of the entry, title, text, date created, date last modified
 * and the user which created the entry.
 * 
 * @author ivan
 *
 */
public class NewEntryForm {

	/** The identification of the entry. */
	private Long id;

	/** The title of the entry. */
	private String title;

	/** The text of the entry. */
	private String text;

	/** The date when the entry was created. */
	private Date createdAt = new Date();

	/** The date when the entry was last modified. */
	private Date lastModifiedAt = new Date();

	/** The user who created the entry. */
	private BlogUser created;

	/** The map which contains the errors. */
	private Map<String, String> errors = new HashMap<>();

	/**
	 * This method fills the form from the specified user request.
	 * 
	 * @param req
	 *            the user request.
	 */
	public void fillFromRequest(HttpServletRequest req) {
		this.title = req.getParameter("title").trim();
		this.text = req.getParameter("text").trim();
		this.createdAt = new Date();
		this.lastModifiedAt = new Date();
		String id = req.getParameter("id");
		System.out.println("ID ===============> " + id);
		if (id != null && !id.trim().isEmpty()) {
			this.id = Long.valueOf(id.trim());
		}
		String nick = (String) req.getSession().getAttribute("current.user.nick");
		created = DAOProvider.getDAO().getUserWithNick(nick);
	}

	/**
	 * This method fills this form from the specified {@link BlogEntry}.
	 * 
	 * @param entry
	 *            the entry from which to fill the form.
	 */
	public void fillFromBlogEntry(BlogEntry entry) {
		this.title = entry.getTitle();
		this.text = entry.getText();
		this.created = entry.getCreated();
		this.id = entry.getId();
	}

	/**
	 * This method fills the specified {@link BlogEntry} with the data from this
	 * form.
	 * 
	 * @param entry
	 *            the entry to fill.
	 */
	public void fillBlogEntry(BlogEntry entry) {
		entry.setTitle(title);
		System.out.println("title: " + title);
		entry.setText(text);
		System.out.println("text: " + text);
		if (entry.getCreatedAt() == null) {
			entry.setCreatedAt(new Date());
		}
		System.out.println("createdAt: " + createdAt);
		entry.setLastModifiedAt(lastModifiedAt);
		System.out.println("last mod: " + lastModifiedAt);
		entry.setCreated(created);
		System.out.println("creted:" + created);
	}

	/**
	 * This method validates the properties of this form and if some are not ok,
	 * puts the errors in the errors map.
	 * 
	 */
	public void validate() {
		if (title.isEmpty()) {
			errors.put("title", "You must provide the title.");
		}
		if (text.isEmpty()) {
			errors.put("text", "You must provide non empty text.");
		}
	}

	/**
	 * Returns the value of the property {@link #id}
	 * 
	 * @return the value of the property {@link #id}
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the value of the property {@link #id} to the specified value.
	 * 
	 * @param id
	 * 		the new value of the property {@link #id}
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method checks if errors are present and returns the boolean value
	 * accordingly.
	 * 
	 * @return <code>true</code> if errors are present, <code>false</code>
	 *         otherwise.
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	/**
	 * This method checks if the error with the specified key is present in the map
	 * of errors.
	 * 
	 * @param key
	 *            the key of the error.
	 * @return <code>true</code> if error with the specified key exists,
	 *         <code>false</code> otherwise.
	 */
	public boolean hasError(String key) {
		return errors.containsKey(key);
	}

	/**
	 * This method returns the error text for the specified key, where the key is
	 * the name of specific variable such as, e.g. "nick" , "email" etc.
	 * 
	 * @param key
	 *            the key of the error.
	 * @return the message of the error for the specified key.
	 */
	public String getError(String key) {
		return errors.get(key);
	}

	/**
	 * Returns the value of the property {@link #title}
	 * 
	 * @return the value of the property {@link #title}
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the value of the property {@link #title} to the specified value.
	 * 
	 * @param title
	 * 		the new value of the property {@link #title}
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the value of the property {@link #text}
	 * 
	 * @return the value of the property {@link #text}
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the value of the property {@link #text} to the specified value.
	 * 
	 * @param text
	 * 		the new value of the property {@link #text}
	 */
	public void setText(String text) {
		this.text = text;
	}

}
