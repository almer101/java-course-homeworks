package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.util.BlogUtil;

/**
 * This is the main page which offers the login form for existing users, link to
 * the registration page for new users and the list of registered authors.
 * 
 * @author ivan
 *
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

	/** A default serial version UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		BlogUser user = new BlogUser();
		req.setAttribute("user", user);
		redirectUser(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processLogin(req, resp);
	}

	/**
	 * This method sets the list of users as an attribute and redirects the user to
	 * the main page.
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
	private void redirectUser(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<BlogUser> users = DAOProvider.getDAO().getRegisteredUsers();
		req.setAttribute("userList", users);
		req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
	}
	
	/**
	 * This method actually processes the login from the user. If user has provided
	 * valid nickname and password then he is redirected to his own page of posts.
	 * If that is not the case he is informed of failed login attempt and asked to
	 * enter the info again.
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
	private void processLogin(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		String nick = req.getParameter("nick");
		String password = req.getParameter("password");

		BlogUser user = DAOProvider.getDAO().getUserWithNick(nick);
		if (user == null) {
			// no user with the specified nick exists
			req.setAttribute("user", new BlogUser());
			req.setAttribute("loginError", "You entered a non existing username");
			req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
			return;
		}
		String pwHash = BlogUtil.getHexEncodedHashOfPassword(password);
		if (!user.getPasswordHash().equals(pwHash)) {
			req.setAttribute("user", user);
			req.setAttribute("loginError",
					"Incorrect password, please enter a valid password.");
			req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
			return;
		} else {
			req.getSession().setAttribute("current.user.id", user.getId());
			req.getSession().setAttribute("current.user.fn", user.getFirstName());
			req.getSession().setAttribute("current.user.ln", user.getLastName());
			req.getSession().setAttribute("current.user.nick", user.getNick());
			resp.sendRedirect("author/" + req.getSession().getAttribute("current.user.nick"));
			return;
		}
	}
}
