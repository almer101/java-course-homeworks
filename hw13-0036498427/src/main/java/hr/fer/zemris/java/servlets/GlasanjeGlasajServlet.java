package hr.fer.zemris.java.servlets;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.servlets.GlasanjeServlet.Band;
import hr.fer.zemris.java.servlets.util.ServletUtil;

/**
 * This is the servlet which is called each time the new vote is added. The
 * servlet than updates the file with voting results and forwards the work to
 * the result visualization servlet.
 * 
 * @author ivan
 *
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/** A default serial version UID */
	private static final long serialVersionUID = 1L;

	/** The semaphore used to synchronize the threads. */
	private static final Object semaphore = new Object();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String fileName = req.getServletContext()
				.getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path p = Paths.get(fileName);
		int votedID = Integer.parseInt(req.getParameter("id"));

		synchronized (semaphore) {
			if (!Files.exists(p)) {
				updateVotingFile(null, p, votedID, req);
			} else {
				List<String> lines = Files.readAllLines(p);
				updateVotingFile(lines, p, votedID, req);
			}
		}
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}

	/**
	 * This is the method which updates the voting result file. If no file exists, a
	 * new one is created and results are stored in it. The bands which are not
	 * voted for, not even once, they have vote count zero.
	 * 
	 * @param lines
	 *            the lines from the file with previous results
	 * @param p
	 *            the path to the resulting file.
	 * @param votedID
	 *            the id which was voted for.
	 * @param req
	 *            the request sent to this servlet.
	 * @throws IOException
	 *             if writing to the file fails.
	 */
	private void updateVotingFile(List<String> lines, Path p, int votedID,
			HttpServletRequest req) throws IOException {
		if (!Files.exists(p)) {
			Files.createFile(p);
		}
		BufferedWriter bw = Files.newBufferedWriter(p, StandardOpenOption.WRITE,
				StandardOpenOption.TRUNCATE_EXISTING);
		List<Band> choices = ServletUtil.getBands(req);
		List<String> linesToWrite = new ArrayList<>();

		if (lines == null) {
			// there was no votes and this is the first one.
			linesToWrite.add(votedID + "\t" + 1 + "\n");
			addChoicesWithNoVotes(choices, linesToWrite, votedID);
		} else {
			boolean written = false;
			List<Integer> addedIDs = new ArrayList<>();
			for (String l : lines) {
				String[] parts = l.split("\t");
				addedIDs.add(Integer.parseInt(parts[0].trim()));
				if (String.valueOf(votedID).equals(parts[0].trim())) {
					int votes = Integer.parseInt(parts[1].trim());
					votes++;
					linesToWrite.add(votedID + "\t" + votes + "\n");
					written = true;
					continue;
				}
				linesToWrite.add(l + "\n");
			}
			addMissingVotes(choices, addedIDs, linesToWrite);
			if (!written) {
				linesToWrite.add(votedID + "\t" + 1 + "\n");
			}
		}
		Collections.sort(linesToWrite);
		for (String l : linesToWrite) {
			bw.write(l);
		}
		bw.flush();
		bw.close();
	}

	/**
	 * This is the method which adds the band id numbers which are still not voted
	 * for and sets the number of their votes to zero.
	 * 
	 * @param choices
	 *            all the choices available for voting.
	 * @param linesToWrite
	 *            the lines to write to the result file.
	 * @param votedID
	 *            the id which was voted for.
	 */
	private void addChoicesWithNoVotes(List<Band> choices, List<String> linesToWrite,
			int votedID) {
		for (Band c : choices) {
			if (c.getBandID() == votedID)
				continue;
			linesToWrite.add(c.getBandID() + "\t" + 0 + "\n");
		}
	}

	/**
	 * This method adds the missing votes to the result file. The missing votes
	 * (i.e. band ids and number of votes) are added so that if the user
	 * additionally changes the content of the voting definition file, the results
	 * will still be displayed.
	 * 
	 * @param choices
	 *            all the choices available for voting
	 * @param addedIDs
	 *            the list of all added band ids.
	 * @param linesToWrite
	 *            the lines which are to be written to the resulting file.
	 */
	private void addMissingVotes(List<Band> choices, List<Integer> addedIDs,
			List<String> linesToWrite) {
		for (Band c : choices) {
			if (addedIDs.contains(c.getBandID()))
				continue;
			linesToWrite.add(c.getBandID() + "\t" + 0 + "\n");
		}
	}

}
