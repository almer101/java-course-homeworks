package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;


/**
 * This is the servlet which is called each time the new vote is added. The
 * servlet than updates the file with voting results and forwards the work to
 * the result visualization servlet.
 * 
 * @author ivan
 *
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/** A default serial version UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		long pollID = Long.parseLong(req.getParameter("pollID"));
		long optionID = Long.parseLong(req.getParameter("optionID"));
		DAOProvider.getDao().incrementVote(pollID, optionID);;
		
		req.getServletContext().setAttribute("pollID", pollID);
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + pollID);
	}

}
