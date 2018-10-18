package hr.fer.zemris.java.p12.dao.sql;

import java.sql.Connection;

/**
 * The class which stores the connections to the database in the ThreadLocal
 * object. The class provides methods for getting the connection and returning
 * one. It also provides the ability to add a new connection to the thread local
 * map
 * 
 * @author ivan
 *
 */
public class SQLConnectionProvider {

	/**
	 * The thread local object which stores the connections under the key which is
	 * the id of the thread
	 */
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();

	/**
	 * Sets the connection for the current thread (or deletes one if the argument is
	 * <code>null</code>)
	 * 
	 * @param con
	 *            the connection to the database.
	 */
	public static void setConnection(Connection con) {
		if (con == null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}

	/**
	 * Returns the connection to the database which the user can use.
	 * 
	 * @return the connection to the database.
	 */
	public static Connection getConnection() {
		return connections.get();
	}

}