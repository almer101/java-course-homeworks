package hr.fer.zemris.java.hw16.trazilica;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import hr.fer.zemris.java.hw16.trazilica.vector.TFIDFVector;
import hr.fer.zemris.java.hw16.trazilica.vector.TFIDFVectorFactory;

/**
 * This program enables user to interact with custom shell which can process
 * queries and find the best results for the query. The user simply writes the
 * words which he is interested in in the document and the program finds the
 * best fits for the user.
 * 
 * @author ivan
 *
 */
public class Konzola {

	/** The exit keyword */
	private static final String EXIT = "exit";

	/** The exit keyword */
	private static final String RESULTS = "results";

	/** The exit keyword */
	private static final String TYPE = "type";

	/** The exit keyword */
	private static final String QUERY = "query";

	/** Limit how many results to print out to the user. */
	private static final int LIMIT = 10;

	/**
	 * The threshold for the similarity of the files in order for files to be
	 * displayed to user
	 */
	private static final double EPSILON = 1E-07;
	
	/**The variable which counts how many results were printed out, since not
	 * all results have to have similarity greater than the specified threshold.
	 */
	private static int printedCount;

	/**
	 * This is the method called when the program is run.
	 * 
	 * @param args
	 *            the command line arguments.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException(
					"The path to the directory with the" + " files was expected.");
		}
		Path stopWordsPath = Paths.get("./src/main/resources/hrvatski_stoprijeci.txt");
		Path articles = Paths.get(args[0]);
		TFIDFVectorFactory factory = new TFIDFVectorFactory(stopWordsPath);

		try {
			factory.initialize(articles);
		} catch (IOException e) {
			System.out.println("The exception while initializing the factory occurred."
					+ "The program will now quit.");
			System.exit(0);
		}

		Scanner sc = new Scanner(System.in);
		System.out.print("Enter command > ");
		List<Result> results = new ArrayList<>();

		while (sc.hasNext()) {
			String input = sc.nextLine().trim().toLowerCase();
			if (input.isEmpty()) {
				System.out.print("Enter command > ");
				continue;
			}
			String[] parts = input.split("\\s+");
			if (parts.length == 1) {
				if (parts[0].trim().equalsIgnoreCase(EXIT)) {
					System.out.println("Exiting the console...");
					break;
				} else if (parts[0].trim().equalsIgnoreCase(RESULTS)) {
					printResults(results, LIMIT);
					System.out.print("Enter command > ");
					continue;
				}
				System.out.println("Unrecognized command.");
				System.out.print("Enter command > ");
				continue;
			}
			String keyword = parts[0].trim();
			switch (keyword) {
			case TYPE:
				processType(parts, results);
				break;
			case QUERY:
				processQuery(parts, results, factory);
				break;
			default:
				System.out.println("Unrecognized command.");
				break;
			}
			System.out.print("Enter command > ");
		}
		sc.close();
	}

	/**
	 * This method processes the query command, i.e. finds the best fit document for
	 * the query.
	 * 
	 * @param parts
	 *            the parts of the command.
	 * @param results
	 *            the results list where to put the best results.
	 * @param factory
	 *            the vector factory which knows how to create the vector
	 *            representation of the file.
	 */
	private static void processQuery(String[] parts, List<Result> results,
			TFIDFVectorFactory factory) {
		parts = Arrays.copyOfRange(parts, 1, parts.length);
		printQuery(parts);
		TFIDFVector v = factory.createTFIDFVectorForWords(parts);
		results.clear();
		Map<Path, TFIDFVector> vectors = factory.getVectors();

		Set<Path> keys = vectors.keySet();
		for (Path p : keys) {
			double similarity = vectors.get(p).calculateSimilarity(v);
			results.add(new Result(p, similarity));
		}
		Collections.sort(results, Result.DESC);
		printResults(results, LIMIT);
	}

	/**
	 * This method prints out the components of the query in shape of array.
	 * 
	 * @param parts
	 *            the parts of the command.
	 */
	private static void printQuery(String[] parts) {
		StringBuilder sb = new StringBuilder();
		sb.append("Query is : [");
		for (int i = 0; i < parts.length; i++) {
			if (i != 0) {
				sb.append(", ");
			}
			sb.append(parts[i]);
		}
		sb.append("]\n");
		System.out.println(sb.toString());
	}

	/**
	 * This method processes the TYPE command entered by the user. It prints out the
	 * whole document from the results with the index specified by the command.
	 * 
	 * @param parts
	 *            the parts of the command.
	 * @param results
	 *            the the results from where the document is to be printed.
	 */
	private static void processType(String[] parts, List<Result> results) {
		if (parts.length != 2) {
			System.out.println("Unrecognized command - type expects one argument. Had "
					+ (parts.length - 1));
			return;
		}
		if (results == null || results.size() == 0) {
			System.out.println("The results are not ready yet!");
			return;
		}
		try {
			int index = Integer.parseInt(parts[1].trim());
			if (index >= printedCount) {
				System.out.format("The results with indexes [%d,%d] can be printed out.\n", 0,
						printedCount - 1);
				return;
			}
			Path p = results.get(index).getPath();
			printDocument(p);

		} catch (NumberFormatException | IOException | IndexOutOfBoundsException e) {
			System.out.println(
					"Error while fetching the result, please check the inputed index.");
			return;
		}
	}

	/**
	 * This method prints the content of the specified file to the standard output.
	 * Also the path of the file itself is written out.
	 * 
	 * @param p
	 *            the path to the file to print out.
	 */
	private static void printDocument(Path p) throws IOException {
		String text = new String(Files.readAllBytes(p), StandardCharsets.UTF_8);
		System.out.println("---------------------------------------------------\n");
		System.out.println("Document : " + p.toAbsolutePath().normalize().toString());
		System.out.println("---------------------------------------------------\n");
		System.out.println(text);
		System.out.println("\n\n---------------------------------------------------\n");
	}

	/**
	 * This method prints out the results gotten from the query.
	 * 
	 * @param limit
	 *            number of results to print out. (first <code>limit</code>
	 *            results.)
	 * @param results
	 *            the results to print out.
	 */
	private static void printResults(List<Result> results, int limit) {
		int size = results.size();
		if (size == 0) {
			System.out.println("There are no results yet.");
			return;
		}
		int count = 0;
		for (int i = 0; i < size && i < limit; i++) {
			Result r = results.get(i);
			if (Math.abs(r.getSimilarity()) < EPSILON || Double.isNaN(r.getSimilarity()))
				break;
			System.out.format("[%d] (%6.4f) %s\n", i, r.getSimilarity(), r.getPath());
			count++;
		}
		printedCount = count;
		System.out.println("\n");
	}
}
