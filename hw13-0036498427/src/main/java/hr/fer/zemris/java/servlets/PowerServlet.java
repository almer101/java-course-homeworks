package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This servlet is used for creating an excel file. The servlet gets 3
 * parameters, a, b and n, where n is the maximum power to which to raise the
 * numbers and the [a,b] is an interval of numbers. The servlet then creates an
 * excel file with n sheets where each sheet has two columns, first contains all
 * whole numbers from [a,b] and the second is the number in the first column
 * raised to the power of i, where i is the number of the sheet. (i.e. the
 * sheet1 will contain numbers raised to the power 1, the second to the power of
 * 2 and so on.)
 * 
 * @author ivan
 *
 */
@WebServlet("/powers")
public class PowerServlet extends HttpServlet {

	/** The default serial version UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String stringA = req.getParameter("a");
		String stringB = req.getParameter("b");
		String stringN = req.getParameter("n");

		if (stringA == null || stringB == null || stringN == null) {
			reportError(req, resp);
			return;
		}
		try {
			int a = Integer.parseInt(stringA);
			int b = Integer.parseInt(stringB);
			int n = Integer.parseInt(stringN);
			if (!parametersOK(a, b, n)) {
				reportError(req, resp);
				return;
			}
			HSSFWorkbook hwb = createExcelTable(a, b, n);
			resp.setContentType("application/vnd.ms-excel");
			resp.setHeader("Content-Disposition", "atachment; filename=\"powers.xls\"");
			hwb.write(resp.getOutputStream());
			resp.getOutputStream().flush();

		} catch (NumberFormatException e) {
			reportError(req, resp);
			return;
		}
	}

	/**
	 * This method checks if the specified parameters are of to use.
	 * 
	 * @param a
	 *            parameter a to check.
	 * @param b
	 *            parameter b to check.
	 * @param n
	 *            parameter n to check.
	 * @return <code>true</code> if all the parameters are ok, <code>false</code>
	 *         otherwise.
	 */
	private boolean parametersOK(int a, int b, int n) {
		if (n < 1 || n > 5) return false;
		if (a < -100 || a > 100) return false;
		if (b < -100 || b > 100) return false;
		return true;
	}

	/**
	 * This method reports the error to the user. (i.e. forwards the request to the
	 * page which shows the error.)
	 * 
	 * @param req
	 *            the request.
	 * @param resp
	 *            the response.
	 * @throws IOException
	 *             if the error in the forwarded message occurs.
	 * @throws ServletException
	 *             if the target resource throws this exception
	 */
	private void reportError(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.getRequestDispatcher("/WEB-INF/pages/invalidParameters.jsp").forward(req, resp);
	}

	/**
	 * This method creates the excel table with n sheets, and numbers from a to b,
	 * raised to the power of i, where i is the number of the sheet.
	 * 
	 * @param a
	 *            the starting number
	 * @param b
	 *            the ending number.
	 * @param n
	 *            the number of sheets .
	 * @return the excel table containing n sheets with numbers raised to the sheet
	 *         number power.
	 */
	private HSSFWorkbook createExcelTable(int a, int b, int n) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet[] sheets = createSheets(hwb, n);
		createRowsAndCells(sheets, a, b);

		return hwb;
	}

	/**
	 * This method creates sheets in the specified table. The number of sheets to
	 * create is also specified.
	 * 
	 * @param hwb
	 *            the table where to create the sheets.
	 * @param n
	 *            the number of sheets to create.
	 * @return the sheet array.
	 */
	private HSSFSheet[] createSheets(HSSFWorkbook hwb, int n) {
		HSSFSheet[] sheets = new HSSFSheet[n];
		for (int i = 0; i < n; i++) {
			sheets[i] = hwb.createSheet("Sheet" + (i + 1));
		}
		return sheets;
	}

	/**
	 * This method creates rows and cells for each sheet from the sheet array.
	 * 
	 * @param sheets
	 *            the array of sheets.
	 * @param a
	 *            the starting number.
	 * @param b
	 *            the ending number.
	 */
	private void createRowsAndCells(HSSFSheet[] sheets, int a, int b) {
		for (int i = 0; i < sheets.length; i++) {
			HSSFSheet sheet = sheets[i];
			HSSFRow rowHead = sheet.createRow(0);
			rowHead.createCell(0).setCellValue("number");
			rowHead.createCell(1).setCellValue("number^" + (i + 1));
			createAndFill(sheet, a, b, i + 1);
		}
	}

	/**
	 * This method fills the specified sheet with data (i.e. fills it with the
	 * numbers and their powers.)
	 * 
	 * @param sheet
	 *            the sheet to fill.
	 * @param a
	 *            the starting number.
	 * @param b
	 *            the ending number.
	 * @param power
	 *            the power to which to raise the numbers.
	 */
	private void createAndFill(HSSFSheet sheet, int a, int b, int power) {
		boolean asc = a < b;
		int size = Math.abs(a - b);
		int number = a;
		for (int i = 0; i <= size; i++) {
			HSSFRow row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(number);
			row.createCell(1).setCellValue(Math.pow(number, power));
			number += asc ? 1 : -1;
		}
	}

}
