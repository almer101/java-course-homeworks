package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet gets the value of the parameter color and sets the background
 * color session attribute to that value. Then forwards the work to the jsp page
 * which renders the web page with that background color.
 * 
 * @author ivan
 *
 */
@WebServlet("/setcolor")
public class SetColorServlet extends HttpServlet {

	/** The serial version UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String color = req.getParameter("color");
		req.getSession().setAttribute("pickedBgColor", color);
		req.getRequestDispatcher("/colors.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}
}
