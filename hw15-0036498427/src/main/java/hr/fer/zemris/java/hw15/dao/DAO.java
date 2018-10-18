package hr.fer.zemris.java.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * This interface models the objects which are in charge of managing persistent
 * data. Some such are SQL DAO objects which manage the database and return the
 * wanted data.
 * 
 * @author ivan
 *
 */
public interface DAO {

	/**
	 * Fetches the blog entry with the specified key if such exists or
	 * <code>null</code> if it does not exist.
	 * 
	 * @param id
	 *            entry key.
	 * @return entry of <code>null</code> if such does not exist.
	 * @throws DAOException
	 *             if an error occurs while fetching the data.s
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * This method returns the user from the database with the specified nickname.
	 * 
	 * @param nick
	 *            the nickname of the wanted user.
	 * @return the blog user with the specified nickname.
	 */
	public BlogUser getUserWithNick(String nick);

	/**
	 * This method returns the list of all registered users.
	 * 
	 * @return the list of all registered users;
	 */
	public List<BlogUser> getRegisteredUsers();

	/**
	 * This method searches for the user with the specified nick and returns his id.
	 * If no such user exists returns <code>null</code>.
	 * 
	 * @param nick
	 *            the nick of the user we are looking for.
	 * @return the id of the wanted user or <code>null</code> if such does not
	 *         exist.
	 */
	public Long getIdForNick(String nick);

	/**
	 * This method searches for the all the entries the user with the specified id
	 * created.
	 * 
	 * @param id
	 *            the id of the user whose entries we want.
	 * @return the list of all the entries this user created.
	 */
	public List<BlogEntry> getEntriesForId(long id);

	/**
	 * This method returns the list of blog entries of the user with the specified
	 * nick.
	 * 
	 * @param nick
	 *            the nick of the user whose entries we are interested in.
	 * @return the list of {@link BlogEntry}ies for the specified nick.
	 */
	public List<BlogEntry> getEntriesForNick(String nick);

	/**
	 * This method returns the user with the specified email or <code>null</code> if
	 * such does not exist.
	 * 
	 * @param email
	 *            email of the user we are looking for.
	 * 
	 * @return the blog user with the specified email.
	 */
	public BlogUser getUserWithEMail(String email);
}