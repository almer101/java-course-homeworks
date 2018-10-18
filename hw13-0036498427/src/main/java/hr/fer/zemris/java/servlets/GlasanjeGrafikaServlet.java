package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.servlets.GlasanjeServlet.Band;
import hr.fer.zemris.java.servlets.util.ServletUtil;

/**
 * This is the servlet which is called by the jsp and renders the pie chart
 * image on the web page with the results.
 * 
 * @author ivan
 *
 */
@WebServlet("/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

	/** The default serial version UID */
	private static final long serialVersionUID = 1L;

	/** The title of the chart. */
	private static final String CHART_TITLE = "Voting results";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PieDataset dataset = createDataset(req);
		JFreeChart chart = ServletUtil.createChart(dataset, CHART_TITLE);

		resp.setContentType("image/png");
		ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, 800, 500);
	}

	/**
	 * This method prepares the data set for the pie chart and returns it.
	 * The data is prepared according to the voting result file.
	 * 
	 * @return the prepared data set
	 */
	private PieDataset createDataset(HttpServletRequest req) throws IOException {
		DefaultPieDataset dataset = new DefaultPieDataset();
		String fileName = req.getServletContext()
							.getRealPath("/WEB-INF/glasanje-rezultati.txt");
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		List<Band> choices = ServletUtil.getBands(req);

		for (String l : lines) {
			String[] parts = l.split("\t+");
			String name = ServletUtil.getNameForID(choices, Integer.parseInt(parts[0].trim()));
			dataset.setValue(name, Integer.parseInt(parts[1].trim()));
		}

		return dataset;
	}
}
