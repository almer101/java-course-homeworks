package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.dao.DAOException;

/**
 * This is the class which provides entity managers. All used managers are
 * stored in the {@link ThreadLocal} object. This class also offers a method for closing
 * the opened entity manager. 
 * 
 * @author ivan
 *
 */
public class JPAEMProvider {

	/**The map of the entity managers where the key is the thread id*/
	private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();

	/**
	 * Returns an entity manager from the {@link ThreadLocal} map or creates a new one
	 * if such does not already exist and returns it.
	 * 
	 * @return
	 * 		the entity manager from the {@link ThreadLocal} map or a new one.
	 */
	public static EntityManager getEntityManager() {
		EntityManager em = locals.get();
		if (em == null) {
			em = JPAEMFProvider.getEmf().createEntityManager();
			em.getTransaction().begin();
			locals.set(em);
		}
		return em;
	}

	/**
	 * Closes the current entity manager. The current manager is determined
	 * by the thread which is calling this method.
	 * 
	 * @throws DAOException
	 */
	public static void close() throws DAOException {
		EntityManager em = locals.get();
		if (em == null) {
			return;
		}
		DAOException dex = null;
		try {
			em.getTransaction().commit();
		} catch (Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			em.close();
		} catch (Exception ex) {
			if (dex != null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if (dex != null)
			throw dex;
	}

}