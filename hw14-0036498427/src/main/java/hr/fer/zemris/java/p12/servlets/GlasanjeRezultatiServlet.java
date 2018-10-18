package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOptionEntry;


/**
 * This is the servlet which is called to prepare the results for the page to
 * render them.
 * 
 * @author ivan
 *
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/** The default serial version UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		long pollID = Long.parseLong((String)req.getParameter("pollID"));
		List<PollOptionEntry> options = DAOProvider.getDao().getPollOptionsForPollID(pollID);
		List<PollOptionEntry> winners = DAOProvider.getDao().getWinnersForPollID(pollID);

		req.setAttribute("options", options);
		req.setAttribute("winners", winners);

		req.setAttribute("pollID", pollID);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
