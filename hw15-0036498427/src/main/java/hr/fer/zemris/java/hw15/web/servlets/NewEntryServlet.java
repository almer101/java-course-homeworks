package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.NewEntryForm;

/**
 * This servlet is called when the user wants to enter a new entry.
 * It processes the text and title it got and if something is not all right 
 * informs the user about it. Otherwise a new entry will be created.
 * 
 * @author ivan
 *
 */
@WebServlet("/servleti/newEntry")
public class NewEntryServlet extends HttpServlet {

	/**A default serial version UID*/
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		addNewEntry(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		addNewEntry(req, resp);
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
	private void addNewEntry(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		NewEntryForm f = new NewEntryForm();
		f.fillFromRequest(req);
		f.validate();
		
		if(f.hasErrors()) {
			req.setAttribute("newEntry", f);
			req.getRequestDispatcher("/WEB-INF/pages/New.jsp").forward(req, resp);
			return;
		}
		EntityManager em = JPAEMProvider.getEntityManager();
		BlogEntry entry = new BlogEntry();
		f.fillBlogEntry(entry);
		em.persist(entry);
		
		System.out.println(req.getContextPath().toString());
		resp.sendRedirect("author/" + req.getSession().getAttribute("current.user.nick"));
	}

}
