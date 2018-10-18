package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOptionEntry;

/**
 * This is the servlet which is called by the jsp and renders the pie chart
 * image on the web page with the results.
 * 
 * @author ivan
 *
 */
@WebServlet("/servleti/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

	/** The default serial version UID */
	private static final long serialVersionUID = 1L;

	/** The title of the chart. */
	private static final String CHART_TITLE = "Voting results";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		long pollID = Long.parseLong((String) req.getParameter("pollID"));
		PieDataset dataset = createDataset(pollID);
		JFreeChart chart = createChart(dataset, CHART_TITLE);

		resp.setContentType("image/png");
		ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, 800, 500);
	}

	/**
	 * This method prepares the data set for the pie chart and returns it. The data
	 * is prepared according to the voting result file.
	 * 
	 * @param pollID
	 *            the identification of the poll for which the results are being
	 *            displayed.
	 * 
	 * @return the prepared data set
	 */
	private PieDataset createDataset(long pollID) throws IOException {
		DefaultPieDataset dataset = new DefaultPieDataset();

		List<PollOptionEntry> options = DAOProvider.getDao().getPollOptionsForPollID(pollID);
		for (PollOptionEntry op : options) {
			dataset.setValue(op.getTitle(), op.getVotesCount());
		}
		return dataset;
	}

	/**
	 * This method creates a chart from the specified data set and returns the
	 * chart.
	 * 
	 * @param dataset
	 *            the data set from which to create a chart.
	 * @param chartTitle
	 *            the chart title.
	 * @return the created chart.
	 */
	public static JFreeChart createChart(PieDataset dataset, String chartTitle) {
		JFreeChart chart = ChartFactory.createPieChart3D(chartTitle, dataset, true, true,
				false);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}
}
