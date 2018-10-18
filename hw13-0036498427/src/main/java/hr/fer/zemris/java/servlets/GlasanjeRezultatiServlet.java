package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.servlets.util.ServletUtil;

/**
 * This is the servlet which is called to prepare the results for the page to
 * render them.
 * 
 * @author ivan
 *
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/** The default serial version UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String fileName = req.getServletContext()
				.getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path p = Paths.get(fileName);
		if (!Files.exists(p)) {
			Files.createFile(p);
		}
		List<String> lines = Files.readAllLines(p);
		List<VotingResult> results = ServletUtil.getVotingRestults(lines, req);
		List<VotingResult> winners = getWinners(results);

		req.setAttribute("votingResults", results);
		req.setAttribute("winners", winners);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

	/**
	 * This method returns the winner from the resulting list of voting results. The
	 * winner is a band (or bands) with the maximum number of votes.
	 * 
	 * @param results
	 *            the results of the voting.
	 * @return the resulting list of winners
	 */
	private List<VotingResult> getWinners(List<VotingResult> results) {
		int maxVotes = results.get(0).votes;
		List<VotingResult> winners = new ArrayList<>();
		winners.add(results.get(0));
		int size = results.size();

		for (int i = 1; i < size; i++) {
			VotingResult r = results.get(i);
			if (r.votes < maxVotes)
				break;
			winners.add(r);
		}
		return winners;
	}

	/**
	 * This class models the voting result for one band which has the name of the
	 * band, number of votes and the link to their song
	 * 
	 * @author ivan
	 *
	 */
	public static class VotingResult {

		/** The identification of the band. */
		private int bandID;

		/** The name of the band */
		private String bandName;

		/** The number of votes for this band */
		private int votes;

		/** The link to the song of this band */
		private String songLink;

		/**
		 * The constructor which initializes all the properties of this class.
		 * 
		 * @param bandID
		 *            the identification of the band
		 * @param bandName
		 *            the name of the band
		 * @param votes
		 *            the number of votes for the band
		 * @param songLink
		 *            the link to the song of the band.
		 */
		public VotingResult(int bandID, String bandName, int votes, String songLink) {
			super();
			this.bandID = bandID;
			this.bandName = bandName;
			this.votes = votes;
			this.songLink = songLink;
		}

		/**
		 * This method returns the value of the {@link #bandID} property.
		 * 
		 * @return the value of the {@link #bandID} property
		 */
		public int getBandID() {
			return bandID;
		}

		/**
		 * This method returns the value of the {@link #bandName} property.
		 * 
		 * @return the value of the {@link #bandName} property
		 */
		public String getBandName() {
			return bandName;
		}

		/**
		 * This method returns the value of the {@link #votes} property.
		 * 
		 * @return the value of the {@link #votes} property
		 */
		public int getVotes() {
			return votes;
		}

		/**
		 * This method returns the value of the {@link #songLink} property.
		 * 
		 * @return the value of the {@link #songLink} property
		 */
		public String getSongLink() {
			return songLink;
		}
	}

}
