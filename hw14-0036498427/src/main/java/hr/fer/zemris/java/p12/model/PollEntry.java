package hr.fer.zemris.java.p12.model;

/**
 * This class models the entry of the Polls table which has attributes: id - the
 * id of the poll, title - the title of the poll, message - message to be
 * rendered on the page as a link.
 * 
 * @author ivan
 *
 */
public class PollEntry {

	/** The identification number of the poll */
	private long pollID;

	/** The title of the poll */
	private String title;

	/** The message to be displayed on the page as a link. */
	private String message;

	/**
	 * The constructor which initializes all the parameters of this class.
	 * 
	 * @param pollID
	 *            the identification of the poll.
	 * @param title
	 *            the title of the poll
	 * @param message
	 *            the message to be displayed on the page as a link.
	 */
	public PollEntry(long pollID, String title, String message) {
		super();
		this.pollID = pollID;
		this.title = title;
		this.message = message;
	}

	/**
	 * This method gets the value of the parameter {@link #pollID}.
	 * 
	 * @return
	 * 		the value of the parameter {@link #pollID}
	 */
	public long getId() {
		return pollID;
	}

	/**
	 * This method gets the value of the parameter {@link #message}.
	 * 
	 * @return
	 * 		the value of the parameter {@link #message}
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * This method gets the value of the parameter {@link #title}.
	 * 
	 * @return
	 * 		the value of the parameter {@link #title}
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * This method sets the value of the parameter {@link #pollID}
	 * to the specified value.
	 * 
	 * @param pollID
	 * 		the new value of the parameter {@link #pollID}
	 */
	public void setId(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * This method sets the value of the parameter {@link #title}
	 * to the specified value.
	 * 
	 * @param title
	 * 		the new value of the parameter {@link #title}
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * This method sets the value of the parameter {@link #message}
	 * to the specified value.
	 * 
	 * @param message
	 * 		the new value of the parameter {@link #message}
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
