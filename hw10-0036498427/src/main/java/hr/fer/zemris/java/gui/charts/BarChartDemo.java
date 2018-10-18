package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * The frame which displays a bar chart across
 * the whole area of the frame. 
 * 
 * @author ivan
 *
 */
public class BarChartDemo extends JFrame {
	
	private static final long serialVersionUID = 1L;

	/**
	 * The constructor which gets the model of the chart
	 * and the name of the file from which the configuration
	 * of the chart is read.
	 * 
	 * @param model
	 * 		the model of the chart.
	 * 
	 * @param path
	 * 		the path to the file from which the 
	 * 		configuration is read. 
	 */
	public BarChartDemo(BarChart model, String path) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(100, 200);
		setSize(1000, 600);
		setLayout(new BorderLayout());
		
		BarChartComponent comp = new BarChartComponent(model);
		getContentPane().add(new JLabel(path, SwingConstants.CENTER), BorderLayout.PAGE_START);
		getContentPane().add(comp, BorderLayout.CENTER);
	}

	/**
	 * The method called when the program is run.
	 * 
	 * @param args
	 * 		command line arguments.
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("One file was expected!");
			System.exit(0);
		}
		try {
			BarChart model = getModel(args[0].trim());
			
			SwingUtilities.invokeLater(() -> {
				Path p = Paths.get(args[0].trim()).toAbsolutePath().normalize();
				BarChartDemo chart = new BarChartDemo(model, p.toString());
				chart.setVisible(true);
			}); 
		} catch (Exception e) {
			System.out.println("The format of the file is not valid!");
			System.exit(0);
		}
	}

	/**
	 * This method reads the file whose path is specified
	 * and creates a {@link BarChart} model accordingly.
	 * The method then returns that object
	 * 
	 * @param path
	 * 		the path to the file with the description
	 * 		of the model.
	 * 
	 * @return
	 * 		the model of the chart.
	 */
	private static BarChart getModel(String path) {
		try(BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
			String xDescription = reader.readLine().trim();
			String yDescription = reader.readLine().trim();
			List<XYValue> values = readValues(reader.readLine().trim());
			int yMin = Integer.parseInt(reader.readLine().trim());
			int yMax = Integer.parseInt(reader.readLine().trim());
			int step = Integer.parseInt(reader.readLine().trim());
			
			return new BarChart(values, xDescription, yDescription, yMin, yMax, step);
			
		} catch (RuntimeException | IOException e) {
			throw new IllegalArgumentException("Invalid format of the file!");
		}
	}

	/**
	 * This method parses the specified line and returns
	 * the list of {@link XYValue} objects.
	 * 
	 * @param line
	 * 		the line to parse.
	 * 
	 * @return
	 * 		the list of {@link XYValue} objects.
	 */
	private static List<XYValue> readValues(String line) {
		List<XYValue> values = new ArrayList<>();
		String parts[] = line.split("\\s+");
		
		for(int i = 0; i < parts.length; i++) {
			String valueParts[] = parts[i].split(",");
			int x = Integer.parseInt(valueParts[0].trim());
			int y = Integer.parseInt(valueParts[1].trim());
			values.add(new XYValue(x, y));
		}
		return values;
	}
}
