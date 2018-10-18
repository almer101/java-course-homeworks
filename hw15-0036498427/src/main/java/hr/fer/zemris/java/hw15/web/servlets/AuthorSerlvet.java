package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.NewEntryForm;

/**
 * This servlet is used for showing the authors posts and also knows how to
 * handle requests which are e.g. create a new entry, edit an existing one
 * 
 * @author ivan
 *
 */
@WebServlet("/servleti/author/*")
public class AuthorSerlvet extends HttpServlet {

	/** A default serial version UID */
	private static final long serialVersionUID = 1L;

	/** The constant of the keyword new. */
	private static final String NEW = "new";

	/** The constant of the keyword edit. */
	private static final String EDIT = "edit";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req, resp);
	}

	/**
	 * This method processes the request from the client. The requests can be such
	 * that listing of the users posts is required, adding a new post to the
	 * collection of posts and editing of the existing post.
	 * 
	 * @param req
	 *            the clients request
	 * @param resp
	 *            the response to the client
	 * @throws ServletException
	 *             if the target resource throws this exception
	 * @throws IOException
	 *             if the target resource throws this exception
	 */
	private void processRequest(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String info = req.getPathInfo().substring(1);
		String[] parts = info.split("/");
		req.setAttribute("author", parts[0]);
		if (parts.length == 1) {
			List<BlogEntry> authorPosts = DAOProvider.getDAO().getEntriesForNick(parts[0]);
			req.setAttribute("authorPosts", authorPosts);
			req.getRequestDispatcher("/WEB-INF/pages/Author.jsp").forward(req, resp);

		} else if (parts.length == 2) {
			String currentNick = (String) req.getSession().getAttribute("current.user.nick");
			System.out.println(currentNick);

			if (parts[1].equals(NEW)) {
				req.setAttribute("edit", false);
				req.getRequestDispatcher("/WEB-INF/pages/New.jsp").forward(req, resp);

			} else if (parts[1].equals(EDIT)) {
				req.setAttribute("edit", true);
				String s = req.getParameter("id");
				Long id = Long.valueOf(s);
				if(currentNick ==  null || !currentNick.equals(parts[0])) {
					System.out.println("Tu sam dosao");
					req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req, resp);
					return;
				}
				BlogEntry be = DAOProvider.getDAO().getBlogEntry(id);
				NewEntryForm f = new NewEntryForm();
				f.fillFromBlogEntry(be);
				req.setAttribute("editEntry", f);
				req.getRequestDispatcher("/WEB-INF/pages/Edit.jsp").forward(req, resp);

			} else {
				String idString = parts[1];
				Long id = Long.parseLong(idString);
				BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
				req.setAttribute("blogEntry", entry);
				req.setAttribute("newEntry", entry);
				req.getRequestDispatcher("/WEB-INF/pages/ShowEntry.jsp").forward(req, resp);
			}
		}
	}
}
