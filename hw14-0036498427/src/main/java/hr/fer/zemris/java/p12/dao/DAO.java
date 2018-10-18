package hr.fer.zemris.java.p12.dao;

import java.util.List;

import hr.fer.zemris.java.p12.model.PollEntry;
import hr.fer.zemris.java.p12.model.PollOptionEntry;

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
	 * This method returns all the poll entries from the table which contains data
	 * about polls.
	 * 
	 * @return the list of poll entries which contain data about the poll.
	 */
	public List<PollEntry> getPollEntries();

	/**
	 * This method returns the name of the poll with the specified poll
	 * identification number.
	 * 
	 * @param pollID
	 *            the identification number of the wanted poll.
	 * @return the name of the poll for which the identification is provided.
	 */
	public String getNameForPollID(long pollID);

	/**
	 * This method returns the list of options from the Poll options table with the
	 * specified pollID, others are ignored and are not returned.
	 * 
	 * @param pollID
	 *            the identification of the poll we are interested in.
	 * @return the list of options from the poll options table with the specified
	 *         pollID.
	 */
	public List<PollOptionEntry> getPollOptionsForPollID(long pollID);

	/**
	 * This method returns the list of winners from the Poll options table with the
	 * specified pollID, others are ignored and are not returned. Winners are those
	 * entries with maximum number of votes. (e.g. if two entries have the same
	 * votes count both are returned in this list.)
	 * 
	 * @param pollID
	 *            the identification of the poll we are interested in.
	 * @return the list of winners from the poll options table with the specified
	 *         pollID.
	 */
	public List<PollOptionEntry> getWinnersForPollID(long pollID);

	/**
	 * This method increments the votesCount of the entry specified by the pollID
	 * and the optionID. This method is used every time a user votes for some
	 * option.
	 * 
	 * @param pollID
	 *            the identification of the poll.
	 * @param optionID
	 *            the identification of the option.
	 */
	public void incrementVote(long pollID, long optionID);

}