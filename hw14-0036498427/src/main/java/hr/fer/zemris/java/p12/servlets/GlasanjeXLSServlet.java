package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOptionEntry;

/**
 * This is the servlet which creates the excel file with the results of the
 * voting. Only one sheet is created and the file contains the id of the option,
 * title of the option and number of votes for that option.
 * 
 * @author ivan
 *
 */
@WebServlet("/servleti/glasanje-xls")
public class GlasanjeXLSServlet extends HttpServlet {

	/** The default serial version UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		long pollID = Long.parseLong((String)req.getParameter("pollID"));
		String pollName = DAOProvider.getDao().getNameForPollID(pollID);
		HSSFWorkbook hwb = createTable(pollID);
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "atachment; filename=\"" + pollName + ".xls\"");
		hwb.write(resp.getOutputStream());
		resp.getOutputStream().flush();
	}

	/**
	 * This is the method which creates and returns the table with the results from
	 * the voting result file.
	 * 
	 * @param pollID
	 *            the identification of the poll.
	 * @return the created table.
	 */
	private HSSFWorkbook createTable(long pollID) {
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet();
		HSSFRow headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("ID");
		headerRow.createCell(1).setCellValue("Title");
		headerRow.createCell(2).setCellValue("votesCount");
		
		List<PollOptionEntry> options = DAOProvider.getDao().getPollOptionsForPollID(pollID);
		int rowNum = 1;

		for (PollOptionEntry op : options) {
			HSSFRow row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(op.getOptionID());
			row.createCell(1).setCellValue(op.getTitle());
			row.createCell(2).setCellValue(op.getVotesCount());
		}
		return hwb;
	}

}
