package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is the servlet which is mapped to /index.html and redirects
 * the user to the {@link IndexServlet}.
 * 
 * @author ivan
 *
 */
@WebServlet(urlPatterns = { "/index.html" , "/" })
public class HomeServlet extends HttpServlet {

	/**The default serial version UID*/
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.sendRedirect("servleti/index.html");
	}
}
