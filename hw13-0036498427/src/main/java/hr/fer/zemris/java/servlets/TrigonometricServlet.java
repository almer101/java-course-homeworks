package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static java.lang.Math.PI;

/**
 * This servlet gets the values of the request parameters a and b, and
 * calculates the values of sine and cosine of the whole numbers between those
 * two defined numbers.
 * 
 * @author ivan
 *
 */
@WebServlet("/trigonometric")
public class TrigonometricServlet extends HttpServlet {

	/** The serial version UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String stringA = req.getParameter("a");
		String stringB = req.getParameter("b");
		int a = (stringA == null || stringA.length() == 0) ? 0 : Integer.parseInt(stringA);
		int b = (stringB == null || stringB.length() == 0) ? 360 : Integer.parseInt(stringB);

		if (a > b) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		b = b > a + 720 ? a + 720 : b;

		List<SinCosEntry> entries = new ArrayList<>();
		for (int i = a; i <= b; i++) {
			entries.add(new SinCosEntry(i));
		}
		req.setAttribute("entryTable", entries);
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}

	/**
	 * This is the list entry which contains of a number, sine of that number and
	 * cosine of that number.
	 * 
	 * @author ivan
	 *
	 */
	public static class SinCosEntry {

		/** The number whose sine and cosine are to be calculated. */
		private int number;

		/** The sine of the number */
		private double sin;

		/** The cosine of the number. */
		private double cos;

		/**
		 * The constructor which gets the number and initializes the {@link #number},
		 * and its sine and cosine.
		 * 
		 * @param number
		 *            the number whose sine and cosine are to be calculated.
		 */
		public SinCosEntry(int number) {
			super();
			this.number = number;
			this.sin = Math.sin((number * PI) / (double) 180);
			this.cos = Math.cos((number * PI) / (double) 180);
		}

		/**
		 * The method which returns the cosine of the {@link #number}.
		 * 
		 * @return the cosine of the {@link #number}
		 */
		public double getCos() {
			return cos;
		}

		/**
		 * The method which returns the number whose sine and cosine this class
		 * contains.
		 * 
		 * @return the value of the {@link #number} property.
		 */
		public int getNumber() {
			return number;
		}

		/**
		 * The method which returns the sine of the {@link #number}.
		 * 
		 * @return the sine of the {@link #number}
		 */
		public double getSin() {
			return sin;
		}
	}
}
