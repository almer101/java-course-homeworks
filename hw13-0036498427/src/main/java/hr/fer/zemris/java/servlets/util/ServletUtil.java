package hr.fer.zemris.java.servlets.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.servlets.GlasanjeRezultatiServlet.VotingResult;
import hr.fer.zemris.java.servlets.GlasanjeServlet.Band;

/**
 * This is the class which contains some static methods which are helpful when
 * managing the results of the voting, calculating the time this web application
 * has been running, etc.
 * 
 * @author ivan
 *
 */
public class ServletUtil {

	/**
	 * This method gets the number of milliseconds and formats that to the number of
	 * days, hours, minutes, seconds and milliseconds.
	 * 
	 * @param timeElapsed
	 *            the number of milliseconds which is to be formatted.
	 * @return the formated time as a {@link String}
	 */
	public static String getElapsedString(long timeElapsed) {
		StringBuilder sb = new StringBuilder();
		long millis = 24 * 60 * 60 * 1000;

		long days = timeElapsed / millis;
		sb.append(days + (days == 1 ? " day " : " days "));
		timeElapsed %= millis;
		millis /= 24;

		long hours = timeElapsed / millis;
		sb.append(hours + (hours == 1 ? " hour " : " hours "));
		timeElapsed %= millis;
		millis /= 60;

		long minutes = timeElapsed / millis;
		sb.append(minutes + (minutes == 1 ? " minute " : " minutes "));
		timeElapsed %= millis;
		millis /= 60;

		long seconds = timeElapsed / millis;
		sb.append(seconds + (seconds == 1 ? " second " : " seconds "));
		timeElapsed %= millis;
		millis /= 1000;

		sb.append("and " + timeElapsed + " milliseconds ");
		return sb.toString();
	}

	/**
	 * This method loads the file with the definition of the bands and returns the
	 * list of the {@link Band} objects.
	 * 
	 * @param req
	 *            the request to the servlet which can return the path to the wanted
	 *            file.
	 * @return the list of all the bands from the definition file.
	 * @throws IOException
	 *             if reading from the file fails
	 */
	public static List<Band> getBands(HttpServletRequest req) throws IOException {
		String fileName = req.getServletContext()
				.getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> lines = Files.readAllLines(Paths.get(fileName));

		List<Band> choices = new ArrayList<>();
		for (String l : lines) {
			String[] parts = l.split("\t+");
			choices.add(new Band(Integer.parseInt(parts[0].trim()), parts[1].trim(),
					parts[2].trim()));
		}
		return choices;
	}

	/**
	 * This method returns the name of the band with the specified id. If no such
	 * band is found <code>null</code> is returned.
	 * 
	 * @param choices
	 *            the list of all the bands.
	 * @param ID
	 *            the identification number of the wanted band.
	 * @return the name of the band with the specified identification number.
	 */
	public static String getNameForID(List<Band> choices, int ID) {
		for (Band c : choices) {
			if (c.getBandID() == ID) {
				return c.getBandName();
			}
		}
		return null;
	}

	/**
	 * This method checks if the specified id is present in the specified list of
	 * bands and returns boolean value accordingly.
	 * 
	 * @param choices
	 *            the list of all the bands.
	 * @param ID
	 *            the identification number of the wanted band.
	 * @return <code>true</code> if the list contains the band with the specified
	 *         id, <code>false</code> otherwise
	 */
	public static boolean conatinsID(List<Band> choices, int ID) {
		for (Band c : choices) {
			if (c.getBandID() == ID) {
				return true;
			}
		}
		return false;
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

	/**
	 * This is the method which returns the voting results after reading them from
	 * the file with results. The results are sorted by number of votes for easier
	 * manipulation afterwards.
	 * 
	 * @param lines
	 *            the lines of the text file with the results.
	 * @param req
	 *            the request for this servlet
	 * @return the list of voting results.
	 * @throws IOException
	 *             if reading from the voting definition file fails.
	 */
	public static List<VotingResult> getVotingRestults(List<String> lines,
			HttpServletRequest req) throws IOException {
		List<Band> choices = ServletUtil.getBands(req);

		List<VotingResult> results = new ArrayList<>();
		for (String l : lines) {
			String[] parts = l.split("\t+");
			int bandID = Integer.parseInt(parts[0].trim());
			int votes = Integer.parseInt(parts[1].trim());
			Band c = getNameAndSongLinkForID(bandID, choices);
			results.add(new VotingResult(bandID, c.getBandName(), votes, c.getSongLink()));
		}
		Collections.sort(results, (r1, r2) -> r2.getVotes() - r1.getVotes());
		return results;
	}

	/**
	 * This is the method which finds the the band with the specified id and returns
	 * it. If none such is found <code>null</code> is returned.
	 * 
	 * @param bandID
	 *            the band identification.
	 * @param bands
	 *            the list of all the bands
	 * @return
	 */
	private static Band getNameAndSongLinkForID(int bandID, List<Band> bands) {
		for (Band c : bands) {
			if (c.getBandID() != bandID)
				continue;
			return c;
		}
		return null;
	}
}
