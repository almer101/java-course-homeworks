package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is the servlet which redirects the user to the main servlet. This
 * servlet is reached if the user only enters the url with the name of the app
 * and then the servlet mapped to index.jsp is reached and that is this servlet
 * which then redirects the user to the main servlet.
 * 
 * @author ivan
 *
 */
@WebServlet(urlPatterns = {"/index.jsp", "/"})
public class IndexServlet extends HttpServlet {

	/**A default serial version UID*/
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.sendRedirect("servleti/main");
	}
}
