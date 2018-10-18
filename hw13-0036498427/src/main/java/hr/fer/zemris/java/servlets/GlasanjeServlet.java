package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.servlets.util.ServletUtil;

/**
 * This is the servlet which reads the definition file of the voting and
 * prepares the list of all available band which can be voted for.
 * 
 * @author ivan
 *
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/** The default serial version UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Band> choices = ServletUtil.getBands(req);
		Collections.sort(choices, (c1, c2) -> c1.getBandID() - c2.getBandID());

		req.setAttribute("votingChoices", choices);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

	/**
	 * This is the class which models one band. It has the identification number of
	 * the band, name of the band and the link to one of their songs.
	 * 
	 * @author ivan
	 *
	 */
	public static class Band {

		/** The identification of the band */
		private int bandID;

		/** The name of the band */
		private String bandName;

		/** The link to one of their songs. */
		private String songLink;

		/**
		 * The constructor which initializes all the properties of this class.
		 * 
		 * @param bandID
		 *            the identification number of the band.
		 * @param bandName
		 *            the band name.
		 * @param songLink
		 *            the link to on of their songs.
		 */
		public Band(int bandID, String bandName, String songLink) {
			this.bandID = bandID;
			this.bandName = bandName;
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
		 * This method returns the value of the {@link #songLink} property.
		 * 
		 * @return the value of the {@link #songLink} property
		 */
		public String getSongLink() {
			return songLink;
		}
	}
}
