package hr.fer.zemris.java.servlets;

import java.io.IOException;
import org.jfree.chart.ChartUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.servlets.util.ServletUtil;

/**
 * This servlet creates a pie chart with some specific values and posts its
 * picture on the jsp page
 * 
 * @author ivan
 *
 */
@WebServlet("/reportImage")
public class ChartServlet extends HttpServlet {

	/** The default serial version UID */
	private static final long serialVersionUID = 1L;

	/** The title of the chart. */
	private static final String CHART_TITLE = "OS Usage";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PieDataset dataset = createDataset();
		JFreeChart chart = ServletUtil.createChart(dataset, CHART_TITLE);

		resp.setContentType("image/png");
		ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, 800, 500);
	}

	/**
	 * This method prepares the data set for the pie chart and returns it
	 * 
	 * @return the prepared data set
	 */
	private PieDataset createDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Windows", 100);
		dataset.setValue("Linux", 120);
		dataset.setValue("MacOS", 70);
		dataset.setValue("Other", 3);

		return dataset;
	}

}
