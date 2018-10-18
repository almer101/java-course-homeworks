package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.dao.jpa.JPADAOImpl;

/**
 * This is the singleton class which knows whom to return as an object which can
 * access the persistent data.
 * 
 * @author ivan
 *
 */
public class DAOProvider {

	/** The singleton object which can access the persistent data. */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Returns the DAO object which can access the persistent data.
	 * 
	 * @return the object which can access the persistent data.
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}