package hr.fer.zemris.java.p12.dao;

import hr.fer.zemris.java.p12.dao.sql.SQLDAO;

/**
 * This is the singleton class which knows whom to return as an object which can
 * access the persistent data.
 * 
 * @author ivan
 *
 */
public class DAOProvider {

	/** The singleton object which can access the persistent data. */
	private static DAO dao = new SQLDAO();

	/**
	 * Returns the DAO object which can access the persistent data.
	 * 
	 * @return the object which can access the persistent data.
	 */
	public static DAO getDao() {
		return dao;
	}

}