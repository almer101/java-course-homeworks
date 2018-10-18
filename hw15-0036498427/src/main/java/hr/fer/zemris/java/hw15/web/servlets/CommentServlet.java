package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * This servlet is called each time a new comment arrives. It processes the
 * comment and if something is not all right informs the user to input the comment
 * again.
 * 
 * @author ivan
 *
 */
@WebServlet("/servleti/comment")
public class CommentServlet extends HttpServlet {

	/** The default serial version UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processComment(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processComment(req, resp);
	}

	/**
	 * This method processes the new comment, checks if it is empty and if the user
	 * is registered, if something is wrong the servlet informs the user about it
	 * and asks him to enter a comment again.
	 * 
	 * @param req
	 *            the request from the user.
	 * @param resp
	 *            the respond to the user.
	 * @throws ServletException
	 *             if the target resource throws this exception
	 * @throws IOException
	 *             if the target resource throws this exception
	 */
	private void processComment(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long id = Long.valueOf(req.getParameter("entryID"));
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
		String comment = (String) req.getParameter("comment");
		if (comment.trim().isEmpty()) {
			req.setAttribute("commentError", "The comment must not be empty.");
			req.getRequestDispatcher(
					"author/" + entry.getCreated().getNick() + "/" + entry.getId())
					.forward(req, resp);
			return;
		}
		BlogUser currentUser = DAOProvider.getDAO()
				.getUserWithNick((String) req.getSession().getAttribute("current.user.nick"));
		String email = req.getParameter("email");
		if (currentUser == null && email.trim().isEmpty()) {
			req.setAttribute("commented", comment);
			req.setAttribute("emailError", "To leave a comment please enter the email first.");
			req.getRequestDispatcher(
					"author/" + entry.getCreated().getNick() + "/" + entry.getId())
					.forward(req, resp);
			return;
		} else if (currentUser != null) {
			email = currentUser.getEmail();
		}
		if (!isEMailValid(email)) {
			System.out.println("Provjeravam mail");
			req.setAttribute("commented", comment);
			req.setAttribute("emailError", "The email is invalid");
			req.getRequestDispatcher(
					"author/" + entry.getCreated().getNick() + "/" + entry.getId())
					.forward(req, resp);
			return;
		}
		BlogComment c = new BlogComment();
		c.setBlogEntry(entry);
		c.setMessage(comment);
		c.setPostedOn(new Date());
		c.setUsersEMail(email);
		entry.getComments().add(c);
		resp.sendRedirect("author/" + entry.getCreated().getNick() + "/" + entry.getId());
	}

	/**
	 * This method checks if the provided mail is valid. The valid mail is such
	 * which has [something]@[something].[something]
	 * 
	 * @param email
	 * 		the email to validate.
	 * @return
	 * 		<code>true</code> if the email is valid, <code>false otherwise.</code>
	 */
	private boolean isEMailValid(String email) {
		String pattern = "[^@]+@[^.@]+.[^.@]+";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(email);
		return m.matches();
	}

}
