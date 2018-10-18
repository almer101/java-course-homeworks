package hr.fer.zemris.java.p12.model;

/**
 * This is the class which model one entry in the table PollOptions. It has the
 * id of the poll the option belongs to, the id of the option itself, the title
 * of the option, the link which somehow references this option and the number
 * of votes this option has got.
 * 
 * @author ivan
 *
 */
public class PollOptionEntry {

	/** The identification of the poll this option belongs to. */
	private long pollID;

	/** The identification of this option. */
	private long optionID;

	/** The title of this option. */
	private String title;

	/** The link which references this option. */
	private String link;

	/** The number of votes this option has got. */
	private long votesCount;

	/**
	 * This method returns the identification of the poll this option belongs to.
	 * 
	 * @return the identification of the poll this option belongs to.
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * This method sets the value of the {@link #pollID} to the specified value.
	 * 
	 * @param pollID
	 *            the new value of the {@link #pollID}
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * This method returns the identification of this option.
	 * 
	 * @return the identification of this poll.
	 */
	public long getOptionID() {
		return optionID;
	}

	/**
	 * This method sets the value of the {@link #optionID} to the specified value.
	 * 
	 * @param optionID
	 *            the new value of the {@link #optionID}
	 */
	public void setOptionID(long optionID) {
		this.optionID = optionID;
	}

	/**
	 * This method returns the title of this poll option.
	 * 
	 * @return the title of this poll option.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * This method sets the value of the {@link #title} to the specified value.
	 * 
	 * @param title
	 *            the new value of the {@link #title}
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * This method returns the link which references the poll option.
	 * 
	 * @return the link which references the poll option.
	 */
	public String getLink() {
		return link;
	}

	/**
	 * This method sets the value of the {@link #link} to the specified value.
	 * 
	 * @param link
	 *            the new value of the {@link #link}
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * This method returns the number of votes this option has got.
	 * 
	 * @return the number of votes this option has got.
	 */
	public long getVotesCount() {
		return votesCount;
	}

	/**
	 * This method sets the value of the {@link #votesCount} to the specified value.
	 * 
	 * @param votesCount
	 *            the new value of the {@link #votesCount}
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}

}
