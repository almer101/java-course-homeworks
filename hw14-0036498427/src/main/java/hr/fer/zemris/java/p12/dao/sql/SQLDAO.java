package hr.fer.zemris.java.p12.dao.sql;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.model.PollEntry;
import hr.fer.zemris.java.p12.model.PollOptionEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the implementation of the DAO using the SQL technology. This
 * implementation expects the connection to be provided (using
 * {@link SQLConnectionProvider} class). The class provides methods for
 * "talking" to the database, i.e. getting the wandted entries from the
 * database.
 * 
 * @author ivan
 */
public class SQLDAO implements DAO {

	@Override
	public List<PollEntry> getPollEntries() {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		List<PollEntry> entries = new ArrayList<>();

		try {
			pst = con.prepareStatement("select * from Polls");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						long id = rs.getLong(1);
						String title = rs.getString(2);
						String message = rs.getString(3);
						entries.add(new PollEntry(id, title, message));
					}
				} finally {
					rs.close();
				}
			} finally {
				pst.close();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return entries;
	}

	@Override
	public String getNameForPollID(long pollID) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;

		try {
			pst = con.prepareStatement("select * from Polls where id = ?");
			pst.setLong(1, pollID);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						String title = rs.getString(2);
						return title;
					}
				} finally {
					rs.close();
				}
			} finally {
				pst.close();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return null;
	}

	@Override
	public List<PollOptionEntry> getPollOptionsForPollID(long pollID) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		List<PollOptionEntry> entries = new ArrayList<>();

		try {
			pst = con.prepareStatement(
					"select * from PollOptions where pollID = ? order by votescount desc");
			pst.setLong(1, pollID);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					addOptions(rs, entries);
				} finally {
					rs.close();
				}
			} finally {
				pst.close();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return entries;
	}

	@Override
	public void incrementVote(long pollID, long optionID) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("update PollOptions "
					+ "set votesCount = votesCount + 1 " + "where pollID = ? and id = ?");
			pst.setLong(1, pollID);
			pst.setLong(2, optionID);
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public List<PollOptionEntry> getWinnersForPollID(long pollID) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		List<PollOptionEntry> winners = new ArrayList<>();

		try {
			pst = con.prepareStatement("select * from PollOptions where pollID = ? and "
					+ "votesCount = (select max(votesCount) " + "from PollOptions "
					+ "where pollID = ?) ");
			pst.setLong(1, pollID);
			pst.setLong(2, pollID);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					addOptions(rs, winners);
				} finally {
					rs.close();
				}
			} finally {
				pst.close();
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}

		return winners;
	}

	/**
	 * This method adds all the options from the result set to the list of
	 * {@link PollOptionEntry}s. Assuming that the result set will contain rows
	 * which correspond the {@link PollOptionEntry} class.
	 * 
	 * @param rs
	 *            the set of results
	 * @param entries
	 *            the list in which the entries are to be added.
	 * @throws SQLException
	 *             if a database access error occurs or this method is called on a
	 *             closed result set.
	 */
	private void addOptions(ResultSet rs, List<PollOptionEntry> entries) throws SQLException {
		while (rs != null && rs.next()) {
			PollOptionEntry option = new PollOptionEntry();
			option.setOptionID(rs.getLong(1));
			option.setTitle(rs.getString(2));
			option.setLink(rs.getString(3));
			option.setPollID(rs.getLong(4));
			option.setVotesCount(rs.getLong(5));
			entries.add(option);
		}
	}

}