package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.NewEntryForm;

/**
 * This is the servlet which processes the edit request. That is, the servlet
 * receives the text and title from the form, fills the {@link NewEntryForm} with it
 * and validates that everything is all right (both title and text must not be blank.)
 * 
 * @author ivan
 *
 */
@WebServlet("/servleti/editEntry")
public class EditEntryServlet extends HttpServlet {

	/**A default serial version UID*/
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		editEntry(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		editEntry(req, resp);
	}

	/**
	 * This method adds new entry to the list of users entries.
	 * 
	 * @param req
	 *            a HttpServletRequest object that contains the request the client
	 *            has made of the servlet
	 * 
	 * @param respa
	 *            HttpServletResponse object that contains the response the servlet
	 *            sends to the client
	 */
	private void editEntry(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		NewEntryForm f = new NewEntryForm();
		f.fillFromRequest(req);
		f.validate();
		
		if(f.hasErrors()) {
			req.setAttribute("editEntry", f);
			req.getRequestDispatcher("/WEB-INF/pages/Edit.jsp").forward(req, resp);
			return;
		}
		System.out.println(f.getId());
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(f.getId());
		f.fillBlogEntry(entry);
		
		resp.sendRedirect("author/" + req.getSession().getAttribute("current.user.nick"));
	}

}
