package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.RegisterForm;

/**
 * This servlet is called when the new users wants to register to the blog. The
 * info from the user is validated and the form is filled, if everything is all
 * right a new user is created and he is now able to login and add his own blog
 * posts.
 * 
 * @author ivan
 *
 */
@WebServlet("/servleti/registerUser")
public class RegisterNewUser extends HttpServlet {

	/**A default serial verions UID*/
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRegistration(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRegistration(req, resp);
	}

	/**
	 * This method actually processes the registration and makes sure the
	 * data user provided is all right. In case nothing is out of order new user is
	 * created. Otherwise the user is informed of the error which happened.
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
	private void processRegistration(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		String method = req.getParameter("method");
		if (!method.equals("Register")) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		}
		RegisterForm f = new RegisterForm();
		f.fillFromRequest(req);
		f.validate();

		if (f.hasErrors()) {
			req.setAttribute("entry", f);
			req.getRequestDispatcher("register").forward(req, resp);
			return;
		}

		EntityManager em = JPAEMProvider.getEntityManager();
		BlogUser user = new BlogUser();
		f.fillBlogUserFromForm(user);

		em.persist(user);
		resp.sendRedirect("success");
	}

}
