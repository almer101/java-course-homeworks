package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is the servlet called when the user wants to logout.
 * The servlet invalidates the current session and redirects the user to the main page.
 * 
 * @author ivan
 *
 */
@WebServlet("/servleti/logout")
public class LogoutServlet extends HttpServlet {

	/**The default serial version UID*/
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logoutUser(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logoutUser(req, resp);
	}

	/**
	 * This method invalidates the current session and redirects the user to the main page.
	 * 
	 * @param req
	 * 		the http request
	 * @param resp
	 * 		the http response
	 * @throws IOException
	 * 		If an input or output exception occurs
	 */
	private void logoutUser(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		req.getSession().invalidate();
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
	}
}
