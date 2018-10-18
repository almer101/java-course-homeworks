package hr.fer.zemris.java.hw16.jvdraw.util;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.geometricObjects.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometricObjects.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometricObjects.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometricObjects.Line;

/**
 * Utility class which offers methods for loading the model from the jvd file and creating
 * the jvd file according to the specified model. 
 * 
 * @author ivan
 *
 */
public class DrawingModelUtil {

	/**
	 * This method loads the specified model from the specified file. The
	 * meethod than returns the loaded model.
	 * 
	 * @param file
	 * 		the file from where to load the model.
	 * @param model
	 * 		the model which is to be loaded.
	 * @return
	 * 		the loaded model
	 */
	public static DrawingModel loadFromFile(Path file, DrawingModel model) {
		if (!file.getFileName().toString().endsWith(".jvd")) {
			throw new IllegalArgumentException("The file is not compatible for the JVDraw");
		}
		int size = model.getSize();
		for (int i = 0; i < size; i++) {
			model.remove(model.getObject(0));
		}
		try {
			List<String> lines = Files.readAllLines(file);
			loadLinesToModel(lines, model);
		} catch (IOException e) {

		}
		return model;
	}

	/**
	 * This method loads the specified list of lines to the model. First splits
	 * the components of each line and then according to the object type the model
	 * is filled with one {@link GeometricalObject}
	 * 
	 * @param lines
	 * 		the lines with data about the objects to be filled.
	 * @param model
	 * 		the model which is to be filled.
	 */
	private static void loadLinesToModel(List<String> lines, DrawingModel model) {
		for (String line : lines) {
			if(line.isEmpty()) continue;
			String[] parts = line.split("\\s+|\\t+");
			if (parts[0].trim().equals("LINE")) {
				addLineToModel(parts, model);
			} else if (parts[0].trim().equals("CIRCLE")) {
				addCircleToModel(parts, model);
			} else if (parts[0].trim().equals("FCIRCLE")) {
				addFCircleToModel(parts, model);
			}
		}
	}

	/**
	 * This method adds the line with data from the parts array to the model.
	 * 
	 * @param parts
	 * 		the data about the line which is to be added.
	 * @param model
	 * 		the model where to add the line.
	 */
	private static void addLineToModel(String[] parts, DrawingModel model) {
		int x0 = Integer.parseInt(parts[1].trim());
		int y0 = Integer.parseInt(parts[2].trim());
		int x1 = Integer.parseInt(parts[3].trim());
		int y1 = Integer.parseInt(parts[4].trim());
		int r = Integer.parseInt(parts[5].trim());
		int g = Integer.parseInt(parts[6].trim());
		int b = Integer.parseInt(parts[7].trim());
		Color c = new Color(r, g, b);
		model.add(new Line(x0, y0, x1, y1, c));
	}

	/**
	 * This method adds the circle with data from the parts array to the model.
	 * 
	 * @param parts
	 * 		the data about the circle which is to be added.
	 * @param model
	 * 		the model where to add the circle.
	 */
	private static void addCircleToModel(String[] parts, DrawingModel model) {
		int x = Integer.parseInt(parts[1].trim());
		int y = Integer.parseInt(parts[2].trim());
		int radius = Integer.parseInt(parts[3].trim());
		;
		int r = Integer.parseInt(parts[4].trim());
		int g = Integer.parseInt(parts[5].trim());
		int b = Integer.parseInt(parts[6].trim());
		Color c = new Color(r, g, b);
		model.add(new Circle(x, y, radius, c));
	}

	/**
	 * This method adds the filled circle with data from the parts array to the model.
	 * 
	 * @param parts
	 * 		the data about the filled circle which is to be added.
	 * @param model
	 * 		the model where to add the filled circle.
	 */
	private static void addFCircleToModel(String[] parts, DrawingModel model) {
		int x = Integer.parseInt(parts[1].trim());
		int y = Integer.parseInt(parts[2].trim());
		int radius = Integer.parseInt(parts[3].trim());
		;
		int r1 = Integer.parseInt(parts[4].trim());
		int g1 = Integer.parseInt(parts[5].trim());
		int b1 = Integer.parseInt(parts[6].trim());
		Color c1 = new Color(r1, g1, b1);
		int r2 = Integer.parseInt(parts[7].trim());
		int g2 = Integer.parseInt(parts[8].trim());
		int b2 = Integer.parseInt(parts[9].trim());
		Color c2 = new Color(r2, g2, b2);
		model.add(new FilledCircle(x, y, radius, c1, c2));
	}

	/**
	 * This method fills the specified file with the info
	 * from the model.
	 * 
	 * @param file
	 * 		the file which is to be filled.
	 * @param model
	 * 		the model from where the info is to be extracted.
	 */
	public static void makeFileForModel(Path file, DrawingModel model) {
		try {
			if (!Files.exists(file)) {
				Files.createFile(file);
			}
			BufferedWriter bw = Files.newBufferedWriter(file,
					StandardOpenOption.TRUNCATE_EXISTING);
			int size = model.getSize();
			for(int i = 0; i < size; i++) {
				bw.write(getStringRepresentation(model.getObject(i)));
			}
			bw.close();
		} catch (IOException e) {
		}
		
	}

	/**
	 * This method creates the string representation of the specified
	 * {@link GeometricalObject} according to its type.
	 * 
	 * @param o
	 * 		the object for which the string representation is to be made.
	 * @return
	 * 		the string representation of the object.
	 */
	private static String getStringRepresentation(GeometricalObject o) {
		if (o instanceof Line) {
			Color c = ((Line) o).getColor();
			return String.format("LINE %d %d %d %d %d %d %d\n", ((Line) o).getxStart(),
					((Line) o).getyStart(), ((Line) o).getxEnd(), ((Line) o).getyEnd(),
					c.getRed(), c.getGreen(), c.getBlue());
		} else if (o instanceof Circle) {
			Color c = ((Circle) o).getColor();
			return String.format("CIRCLE %d %d %d %d %d %d\n", ((Circle) o).getCenterX(),
					((Circle) o).getCenterY(), ((Circle) o).getRadius(), c.getRed(),
					c.getGreen(), c.getBlue());
		} else if (o instanceof FilledCircle) {
			Color cOut = ((FilledCircle) o).getOutlineColor();
			Color cFill = ((FilledCircle) o).getFillColor();
			return String.format("FCIRCLE %d %d %d %d %d %d %d %d %d\n",
					((FilledCircle) o).getCenterX(), ((FilledCircle) o).getCenterY(),
					((FilledCircle) o).getRadius(), cOut.getRed(), cOut.getGreen(),
					cOut.getBlue(), cFill.getRed(), cFill.getGreen(), cFill.getBlue());
		}
		return null;
	}
}

