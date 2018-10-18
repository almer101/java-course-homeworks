package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollEntry;

/**
 * This is the servlet which prepares all the polls to be rendered
 * by the jsp file. The polls are then rendered and the user can choose
 * in which poll he would like to participate.
 * 
 * @author ivan
 *
 */
@WebServlet("/servleti/index.html")
public class IndexServlet extends HttpServlet {

	/**The default serial version UID*/
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<PollEntry> entries = DAOProvider.getDao().getPollEntries();
		req.setAttribute("pollEntries", entries);
		
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}
	
}
