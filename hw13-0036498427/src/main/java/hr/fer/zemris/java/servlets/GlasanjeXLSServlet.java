package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.servlets.GlasanjeServlet.Band;
import hr.fer.zemris.java.servlets.util.ServletUtil;

/**
 * This is the servlet which creates the excel file with the results of the
 * voting. Only one sheet is created and the file contains the id of the band,
 * name of the band and number of votes for that band.
 * 
 * @author ivan
 *
 */
@WebServlet("/glasanje-xls")
public class GlasanjeXLSServlet extends HttpServlet {

	/** The default serial version UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HSSFWorkbook hwb = createTable(req);
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "atachment; filename=\"band_votes.xls\"");
		hwb.write(resp.getOutputStream());
		resp.getOutputStream().flush();
	}

	/**
	 * This is the method which creates and returns the table with the results from
	 * the voting result file.
	 * 
	 * @param req
	 *            the request for the servlet.
	 * @return the created table.
	 * @throws IOException
	 *             if reading from the file with results fails.
	 */
	private HSSFWorkbook createTable(HttpServletRequest req) throws IOException {
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet();
		HSSFRow headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Band ID");
		headerRow.createCell(1).setCellValue("Band name");
		headerRow.createCell(2).setCellValue("Votes");

		String fileName = req.getServletContext()
				.getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Path p = Paths.get(fileName);
		List<String> resultLines = Files.readAllLines(p);
		List<Band> choices = ServletUtil.getBands(req);
		int rowNum = 1;

		for (String l : resultLines) {
			String[] parts = l.split("\t+");
			int id = Integer.parseInt(parts[0].trim());
			int votes = Integer.parseInt(parts[1].trim());
			String name = ServletUtil.getNameForID(choices, id);
			HSSFRow row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(id);
			row.createCell(1).setCellValue(name);
			row.createCell(2).setCellValue(votes);
		}
		return hwb;
	}

}
