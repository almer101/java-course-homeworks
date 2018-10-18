package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.List;

import javax.persistence.NoResultException;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * This is the implementation of the DAO using the JPA technology. Provides
 * methods which are used for accessing the persistent data by their keys or
 * such.
 * 
 * @author ivan
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager()
				.createQuery("Select e from BlogEntry as e where e.id=:id", BlogEntry.class)
				.setParameter("id", id).getSingleResult();
		if (blogEntry == null) {
			System.out.println("Entitiy does not exist!");
		} else {
			System.out.println("Entitiy exists!");
			System.out.println(blogEntry.getTitle());
			System.out.println(blogEntry.getText());
		}
		return blogEntry;
	}

	@Override
	public BlogUser getUserWithEMail(String email) {
		try {
			BlogUser user = JPAEMProvider.getEntityManager()
					.createQuery("SELECT u FROM BlogUser as u where u.email=:e",
							BlogUser.class)
					.setParameter("e", email).getSingleResult();
			return user;
		} catch (NoResultException ignorable) {
			// if the code reaches here it means
			// no user with the specified id was found
		}
		return null;
	}

	@Override
	public BlogUser getUserWithNick(String nick) throws DAOException {
		try {
			BlogUser user = JPAEMProvider.getEntityManager()
					.createQuery("SELECT u FROM BlogUser as u where u.nick=:n", BlogUser.class)
					.setParameter("n", nick).getSingleResult();
			return user;
		} catch (NoResultException ignorable) {
			// if the code reaches here it means
			// no user with the specified nick was found
		}
		return null;
	}

	@Override
	public List<BlogUser> getRegisteredUsers() {
		List<BlogUser> users = JPAEMProvider.getEntityManager()
				.createQuery("select b from BlogUser as b", BlogUser.class).getResultList();
		return users;
	}

	@Override
	public Long getIdForNick(String nick) {
		BlogUser user = getUserWithNick(nick);
		if (user == null)
			return null;
		return user.getId();
	}

	@Override
	public List<BlogEntry> getEntriesForId(long id) {
		List<BlogEntry> userEntries = JPAEMProvider.getEntityManager()
				.createQuery("select e from BlogEntry as e, BlogUser as u "
						+ "where e.created=u and u.id=:id", BlogEntry.class)
				.setParameter("id", id).getResultList();
		return userEntries;
	}

	@Override
	public List<BlogEntry> getEntriesForNick(String nick) {
		BlogUser user = getUserWithNick(nick);
		if (user == null)
			return null;
		return getEntriesForId(user.getId());
	}

}