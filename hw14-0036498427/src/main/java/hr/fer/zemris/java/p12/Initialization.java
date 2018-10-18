package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.p12.dao.sql.SQLConnectionProvider;

/**
 * This is the listener which initializes the connection pool and creates the
 * tables with Polls and Poll information if they do not already exist.
 * 
 * @author ivan
 *
 */
@WebListener
public class Initialization implements ServletContextListener {

	/** The name of the table with the information about the polls. */
	private static String POLLS = "Polls";

	/**
	 * The name of the table with the information about the poll options, number of
	 * votes for the option etc.
	 */
	private static String POLL_OPTIONS = "PollOptions";

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		Properties prop = new Properties();
		String fileName = sce.getServletContext()
				.getRealPath("/WEB-INF/dbsettings.properties");
		Path p = Paths.get(fileName);
		if (!Files.exists(p)) {
			throw new RuntimeException("The properties file does not exist!");
		}

		try {
			prop.load(Files.newInputStream(p));
		} catch (IOException e) {
			throw new RuntimeException("The properties file could not be loaded!");
		}

		String connectionURL = "jdbc:derby://" + prop.getProperty("host") + ":"
				+ prop.getProperty("port") + "/" + prop.getProperty("name") + ";user="
				+ prop.getProperty("user") + ";password=" + prop.getProperty("password");

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException(
					"An error occured during the initialization of the pool", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);

		Connection con = null;
		try {
			con = cpds.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException("The connection could not have have be established.");
		}
		SQLConnectionProvider.setConnection(con);
		try {
			createTables(cpds);
			populateTables(sce.getServletContext());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("The connection could not be established.");
		}
		SQLConnectionProvider.setConnection(null);
	}

	/**
	 * This method creates the two tables. One is the table which contains the data
	 * about the Polls themselves, and the other is the table which contains more
	 * info about the polls such as link, votes count etc.
	 * 
	 * @throws SQLException
	 *             if the error occurs while getting the connection from the pool.
	 * 
	 */
	private void createTables(ComboPooledDataSource pool) throws SQLException {
		Connection con = SQLConnectionProvider.getConnection();
		createTable(con,
				"CREATE TABLE Polls(" + "id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
						+ " title VARCHAR(150) NOT NULL,  message CLOB(2048) NOT NULL)",
				POLLS);
		createTable(con,
				"CREATE TABLE PollOptions("
						+ "id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
						+ "optionTitle VARCHAR(100) NOT NULL,"
						+ "optionLink VARCHAR(150) NOT NULL," + "pollID BIGINT,"
						+ "votesCount BIGINT," + "FOREIGN KEY (pollID) REFERENCES Polls(id))",
				POLL_OPTIONS);
	}

	/**
	 * This method executes the statement given by the parameter, the statement is
	 * meant to be a create table statement and this method will then create the
	 * table if such not already exists.
	 * 
	 * @param con
	 *            the connection to database.
	 * @param statement
	 *            the statement to execute(meant to be a CREATE TABLE statement)
	 * @param tableName
	 *            the name of the table to be created
	 */
	private void createTable(Connection con, String statement, String tableName) {
		PreparedStatement pst = null;
		if (tableExists(con, tableName)) return;
		try {
			pst = con.prepareStatement(statement);
			pst.executeUpdate();
		} catch (SQLException ignorable) {
		} finally {
			try {
				pst.close();
			} catch (SQLException ignorable) {
			}
		}
	}

	/**
	 * This method checks if the table with the specified name exists and returns
	 * the boolean value accordingly.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param tableName
	 *            the name of the table which we are interested in.
	 * @return <code>true</code> if the table exists, <code>false</code> otherwise.
	 */
	private boolean tableExists(Connection con, String tableName) {
		try {
			DatabaseMetaData meta = con.getMetaData();
			ResultSet rs = meta.getTables(null, null, tableName, null);
			return rs.next();
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * This method populates the tables Polls and PollOptions but only if the table
	 * Polls does not exist or is empty.
	 * 
	 * @param context
	 *            the context of the servlet used for accessing the file with
	 *            initial values.
	 * @throws SQLException
	 *             if a database access error occurs or this method is called on a
	 *             closed connection
	 */
	private void populateTables(ServletContext context) throws SQLException {
		if (!isTableEmpty(POLLS)) {
			return;
		}
		String fileName = context.getRealPath("/WEB-INF/PollsInit.txt");
		try {
			List<String> lines = Files.readAllLines(Paths.get(fileName));
			for (String l : lines) {
				Connection con = SQLConnectionProvider.getConnection();
				String[] parts = l.split("\t+");
				PreparedStatement pst = con.prepareStatement(
						"insert into Polls (title, message) values (?,?)",
						Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, parts[0].trim());
				pst.setString(2, parts[1].trim());
				pst.executeUpdate();
				System.out.println("1 row inserted in POLLS");
				long pollReference = Long.parseLong(parts[2].trim());
				ResultSet rs = pst.getGeneratedKeys();
				try {
					if (rs != null) {
						rs.next();
						long id = rs.getLong(1);
						populatePollOptionsTable(context, id, pollReference);
					}
				} finally {
					rs.close();
				}
			}
		} catch (IOException e) {
			throw new SQLException(e);
		}
		return;
	}

	/**
	 * This method populates the options table with the options from the file with
	 * initial values to be stored in the table. Only the rows which have the
	 * pollrReference equal to the one specified will be added to the table. Each of
	 * the will have the pollID specified by the parameter.
	 * 
	 * @param context
	 *            the context of the servlet used for accessing the file with
	 *            initial values.
	 * @param pollID
	 *            the identification of the poll which will be used for later
	 *            operations on the relation.
	 * @param pollReference
	 *            the reference to the poll (id which is not in the tables but is
	 *            used to determine which poll the option belongs to)
	 * @throws SQLException
	 *             if a database access error occurs or this method is called on a
	 *             closed connection
	 */
	private void populatePollOptionsTable(ServletContext context, long pollID,
			long pollReference) throws SQLException {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = con.prepareStatement(
				"insert into PollOptions (optionTitle, optionLink, pollID, votesCount) "
						+ "values(?,?,?,?)");
		String fileName = context.getRealPath("/WEB-INF/PollOptionsInit.txt");
		try {
			List<String> lines = Files.readAllLines(Paths.get(fileName));

			for (String l : lines) {
				try {
					if (l.trim().length() == 0)
						continue;
					String[] parts = l.split("\t+");
					long parsed = Long.parseLong(parts[3].trim());
					if (parsed != pollReference)
						continue;
					pst.setString(1, parts[0].trim());
					pst.setString(2, parts[1].trim());
					pst.setLong(3, pollID);
					pst.setLong(4, Long.parseLong(parts[2].trim()));
					pst.executeUpdate();
					System.out.println("1 row inserted in POLLS OPTIONS");
				} catch (SQLException e) {
					// the row already exists in the table!
					e.printStackTrace();
					continue;
				}
			}
		} catch (IOException e) {
			throw new SQLException();
		}

	}

	/**
	 * This method checks if the table with the specified name is empty and returns
	 * the boolean value accordingly.
	 * 
	 * @param table
	 *            the name of the table for which to check whether it is empty
	 * @return <code>true</code> if the table is empty, <code>false</code>
	 *         otherwise.
	 * @throws SQLException
	 *             if a database access error occurs or this method is called on a
	 *             closed connection
	 */
	private boolean isTableEmpty(String table) throws SQLException {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = con.prepareStatement("select count(*) from " + table);

		boolean empty = false;
		try {
			ResultSet rs = pst.executeQuery();
			try {
				long i = 0;
				while (rs != null && rs.next()) {
					i = rs.getLong(1);
				}
				empty = (i == 0);

			} finally {
				rs.close();
			}
		} finally {
			pst.close();
		}
		return empty;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}