package hr.fer.zemris.java.hw15.model;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.util.BlogUtil;

/**
 * This is the form filled by the user when he wants to register as a new user.
 * The form contains: first and last name of the user, his associated id, his
 * nick, email, password and a password hash.
 * 
 * @author ivan
 *
 */
public class RegisterForm {

	/** The identification of the user. */
	private String id;

	/** The first name of the user. */
	private String firstName;

	/** The last name of the user. */
	private String lastName;

	/** The nick of the user. */
	private String nick;

	/** The email of the user. */
	private String email;

	/** The hash value of the user's password. */
	private String passwordHash;

	/** The user's password. */
	private String password;

	/** The map of errors where the key is the name of the property with error. */
	private Map<String, String> errors = new HashMap<>();

	/**
	 * This method fills the properties of this form from the specified request. The
	 * data is firstly prepared and then stored.
	 * 
	 * @param req
	 *            the request from the client.
	 */
	public void fillFromRequest(HttpServletRequest req) {
		this.id = prepare(req.getParameter("id"));
		this.firstName = prepare(req.getParameter("firstName"));
		this.lastName = prepare(req.getParameter("lastName"));
		this.nick = prepare(req.getParameter("nick"));
		this.email = prepare(req.getParameter("email"));
		this.password = prepare(req.getParameter("password"));
		String encoded = BlogUtil.getHexEncodedHashOfPassword(password);
		this.passwordHash = encoded;

	}

	/**
	 * Fills the specified user with the data from this form. The user is given the
	 * password hash and not the password.
	 * 
	 * @param user
	 *            the user to fill with data.
	 */
	public void fillBlogUserFromForm(BlogUser user) {
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setNick(nick);
		user.setPasswordHash(passwordHash);
	}

	/**
	 * Prepares the parameter to be the initial value of some property. If parameter
	 * is null and empty string is returned, if not the trimmed value of the
	 * parameter is returned.
	 * 
	 * @param parameter
	 *            the parameter to prepare.
	 * @return the prepared parameter.
	 */
	private String prepare(String parameter) {
		return parameter == null ? "" : parameter.trim();
	}

	/**
	 * Validates the data from this form and in case some criteria for validation
	 * are not met the error with the name of the property as the key is put in the
	 * {@link #errors} map
	 */
	public void validate() {
		errors.clear();

		if (!id.isEmpty()) {
			try {
				Long.parseLong(id);
			} catch (NumberFormatException e) {
				errors.put("id", "The entered id is not valid");
			}
		}
		if (firstName.isEmpty()) {
			errors.put("firstName", "The first name must not be blank.");
		}
		if (lastName.isEmpty()) {
			errors.put("lastName", "The last name must not be blank.");
		}
		if (email.isEmpty()) {
			errors.put("email", "The email must not be blank.");
		} else {
			String pattern = "[^@]+@[^.@]+.[^.@]+";
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(email);
			if (!m.matches()) {
				errors.put("email", "The email is not valid.");
			}
		}
		if (nick.isEmpty()) {
			errors.put("nick", "The nickname must not be blank.");
		} else {
			checkExistingNickname();
		}
		if (password.isEmpty()) {
			errors.put("password", "The password field must not remain blank.");
		}
	}

	/**
	 * This method checks if the user with the specified nickname already exists and
	 * writes an error to the user if so.
	 * 
	 */
	private void checkExistingNickname() {
		BlogUser check = DAOProvider.getDAO().getUserWithNick(nick);
		if (check != null) {
			// user with specified nick already exists
			errors.put("nick", "The user with specified nickname already exists, "
					+ "please provide some other nickname.");
			nick = "";
		}
	}

	/**
	 * This method checks if errors are present and returns the boolean value
	 * accordingly.
	 * 
	 * @return <code>true</code> if errors are present, <code>false</code>
	 *         otherwise.
	 */
	public boolean hasErrors() {
		return errors.size() != 0;
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
	 * Returns the value of the property {@link #id}
	 * 
	 * @return
	 * 		the value of the property {@link #id}
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the value of the property {@link #firstName}
	 * 
	 * @return
	 * 		the value of the property {@link #firstName}
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns the value of the property {@link #lastName}
	 * 
	 * @return
	 * 		the value of the property {@link #lastName}
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns the value of the property {@link #nick}
	 * 
	 * @return
	 * 		the value of the property {@link #nick}
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Returns the value of the property {@link #email}
	 * 
	 * @return
	 * 		the value of the property {@link #email}
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns the value of the property {@link #passwordHash}
	 * 
	 * @return
	 * 		the value of the property {@link #passwordHash}
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Returns the value of the property {@link #errors}
	 * 
	 * @return
	 * 		the value of the property {@link #errors}
	 */
	public Map<String, String> getErrors() {
		return errors;
	}

	/**
	 * Sets the value of the property {@link #firstName} to the specified value.
	 * 
	 * @param firstName
	 * 		the new value of the property {@link #firstName}
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Sets the value of the property {@link #nick} to the specified value.
	 * 
	 * @param nick
	 * 		the new value of the property {@link #nick}
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Sets the value of the property {@link #email} to the specified value.
	 * 
	 * @param email
	 * 		the new value of the property {@link #email}
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Sets the value of the property {@link #lastName} to the specified value.
	 * 
	 * @param lastName
	 * 		the new value of the property {@link #lastName}
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
