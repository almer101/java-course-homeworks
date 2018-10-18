package hr.fer.zemris.java.hw16.trazilica.vector;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This is the implementation of the n-dimensional vector factory where n is the
 * size of the stop words list. It also provides the method which will produce
 * the vector of the specified file. This class has the property which is the
 * list of all the stop words which are necessary when creating the vector
 * representation of the document.
 * 
 * 
 * @author ivan
 *
 */
public class TFIDFVectorFactory implements FileVisitor<Path> {

	/**
	 * The set of stop words necessary when creating the vector representation of
	 * the document
	 */
	private List<String> stopWords = new ArrayList<>();

	/** The vocabulary of the documents. */
	private List<String> vocabulary = new ArrayList<>();

	/**
	 * This is the list of document paths used when user requests the vector
	 * representation of some document.
	 */
	private Map<Path, TFIDFVector> vectors = new HashMap<>();

	/**
	 * This is the list of document paths used when user requests the vector
	 * representation of some document.
	 */
	private Map<Path, List<String>> documents = new HashMap<>();

	/** This is the list of lists of document words the document contains. */
	private List<List<String>> documentsWords = new ArrayList<>();

	/** The time frequency matrix */
	private int[][] TF;

	/** The inverse document frequency matrix */
	private double[] IDF;

	/** This is the variable which */
	private boolean firstPass;

	// ================================Constructor=========================================

	/**
	 * This is the constructor which gets the path to the file which contains the
	 * stop words of some language and initializes the {@link #stopWords} list with
	 * it.
	 * 
	 * @param stopWordsPath
	 *            the path to the file which contains stop words of some language.
	 */
	public TFIDFVectorFactory(Path stopWordsPath) {
		try {
			List<String> lines = Files.readAllLines(stopWordsPath);
			for (String line : lines) {
				if (line.isEmpty())
					continue;
				stopWords.add(line.trim());
			}
		} catch (IOException e) {
		}
	}

	// ============================Method implementations==================================

	/**
	 * This method initializes the factory, i.e. the factory produces the
	 * vocabulary, vector representations of the files and is ready for user's
	 * requests.
	 * 
	 * @param articles
	 *            the path to the directory which contains all the files which the
	 *            vector representations are to be made for.
	 * @throws IOException
	 *             if walking the tree fails.
	 */
	public void initialize(Path articles) throws IOException {
		firstPass = true;
		Files.walkFileTree(articles, this);
		firstPass = false;
		Files.walkFileTree(articles, this);
	}

	/**
	 * This method calculates the values of the time frequency matrix using the data
	 * from the list of lists of documents words.
	 */
	private void calculateTF() {
		TF = new int[documentsWords.size()][vocabulary.size()];
		int numOfDocuments = documentsWords.size();
		int vocabularySize = vocabulary.size();
		for (int i = 0; i < numOfDocuments; i++) {
			for (int j = 0; j < vocabularySize; j++) {
				int count = timesRepeated(documentsWords.get(i), vocabulary.get(j));
				TF[i][j] = count;
			}
		}
	}

	/**
	 * This method calculates the values of the inverse document frequency matrix
	 * using the data from the list of lists of documents words.
	 */
	private void calculateIDF() {
		IDF = new double[vocabulary.size()];
		int numberOdDocs = documentsWords.size();
		for (int i = 0; i < vocabulary.size(); i++) {
			int count = 0;
			for (int j = 0; j < numberOdDocs; j++) {
				if (TF[j][i] > 0)
					count++;
			}
			IDF[i] = Math.log((double) numberOdDocs / (double) count);
		}
	}

	/**
	 * This method calculates the number of times the specified vocabulary word
	 * occurred in the specified list of words.
	 * 
	 * @param words
	 *            the list of words where the frequency of the vocabulary word is to
	 *            be measured.
	 * @param vocabularyWord
	 *            the vocabulary word whose frequency the method is measuring.
	 * @return the frequency of the specified vocabulary word.
	 */
	private int timesRepeated(List<String> words, String vocabularyWord) {
		int count = 0;
		for (String w : words) {
			if (w.equalsIgnoreCase(vocabularyWord)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * This method creates the list of all the words from the document.
	 * 
	 * @param lines
	 *            the lines of the document.
	 */
	private List<String> createListOfWords(List<String> lines) {
		List<String> words = new ArrayList<>();
		for (String line : lines) {
			String[] parts = line.split("\\s+|\\t+");
			addToWordList(parts, words);
		}
		return words;
	}

	/**
	 * This method adds all the words from the specified array to the specified word
	 * list.
	 * 
	 * @param parts
	 *            the array of words which is to be added to the words list.
	 * @param words
	 *            the list of words where the words are to be added.
	 */
	private void addToWordList(String[] parts, List<String> words) {
		for (String word : parts) {
			if (word.isEmpty())
				continue;
			addToList(word.trim().toLowerCase(), words);
		}
	}

	/**
	 * This method adds the components of the specified word to list. If the word
	 * consists only of alphabetic values it has only 1 component, but the word
	 * "hockey23player" is divided into 2 parts "hockey" and "player"
	 * 
	 * @param word
	 *            the word whose components to add to the word list.
	 * @param words
	 *            the list of words where to add the components of the word.
	 */
	private void addToList(String word, List<String> words) {
		char[] wordParts = word.toCharArray();
		char[] helper = new char[wordParts.length];
		int index = 0;
		for (int i = 0; i < wordParts.length; i++) {
			if (!Character.isAlphabetic(wordParts[i])) {
				if (index == 0)
					continue;
				String s = (new String(helper, 0, index)).toLowerCase();
				words.add(s);
				index = 0;
				continue;
			}
			helper[index++] = wordParts[i];
		}
		String s = (new String(helper, 0, index)).toLowerCase();
		if(s.isEmpty()) return;
		words.add(s);
	}

	/**
	 * This method add all the words from the specified list to the vocabulary but
	 * only if the stopWords set does not contain the word we want to put in the
	 * vocabulary.
	 * 
	 * @param words
	 *            the words to add to the vocabulary.
	 */
	private void addToVocabulary(List<String> words) {
		for (String word : words) {
			word = word.toLowerCase();
			if (!stopWords.contains(word) && !vocabulary.contains(word)) {
				vocabulary.add(word);
			}
		}
	}

	/**
	 * This method creates vector representations of all the documents in the
	 * {@link #documents} map and stores the corresponding vector to the
	 * {@link #vectors} map.
	 */
	private void createVectorRepresentations() {
		Set<Path> keys = documents.keySet();
		for (Path p : keys) {
			int index = documentsWords.indexOf(documents.get(p));
			double[] values = getValuesForDocumentAtIndex(index);
			TFIDFVector v = new TFIDFVector(values);
			vectors.put(p, v);
		}
	}

	/**
	 * This method actually calculates the values of the vector components for the
	 * document which has the word list at the specified index in the
	 * {@link #documentsWords} list.
	 * 
	 * @param index
	 *            the index of the document word list in the {@link #documentsWords}
	 *            list.
	 * @return the values of the vector components.
	 */
	private double[] getValuesForDocumentAtIndex(int index) {
		int size = vocabulary.size();
		double[] values = new double[size];
		for (int i = 0; i < size; i++) {
			values[i] = TF[index][i] * IDF[i];
		}
		return values;
	}

	/**
	 * This method returns the vector representation of the document with the
	 * specified path.
	 * 
	 * @param p
	 *            the path to the document which is to be represented by the vector.
	 * @return the vector representation of the document.
	 */
	public TFIDFVector getVectorRepresentation(Path p) {
		return vectors.get(p);
	}

	/**
	 * This method creates the {@link TFIDFVector} representation of the array of
	 * words specified. The method will trim and convert to lower case all the words
	 * for which that is necessary and such words will be processed.
	 * 
	 * @param words
	 *            the words for which to create a vector representation.
	 * @return the vector representation of the specified array of words.
	 */
	public TFIDFVector createTFIDFVectorForWords(String[] words) {
		int size = vocabulary.size();
		List<String> wordList = new ArrayList<>();
		addToList(words, wordList);
		double[] values = new double[size];

		for (int i = 0; i < size; i++) {
			int timeFreq = timesRepeated(wordList, vocabulary.get(i));
			values[i] = timeFreq * IDF[i];
		}
		return new TFIDFVector(values);
	}

	/**
	 * This method adds the trimmed and converted to lower case words from the array
	 * to the specified list of words.
	 * 
	 * @param words
	 *            the array of words which are to be added to the list.
	 * @param wordList
	 *            a list of words where to add the words.
	 */
	private void addToList(String[] words, List<String> wordList) {
		for (String s : words) {
			s = s.trim().toLowerCase();
			if (s.isEmpty())
				continue;
			wordList.add(s);
		}
	}

	/**
	 * This method returns unmodifiable map which maps path of the file to the
	 * vector representation of that file.
	 * 
	 * @return the unmodifiable map which maps path to the vector representation of
	 *         the file.
	 */
	public Map<Path, TFIDFVector> getVectors() {
		return Collections.unmodifiableMap(vectors);
	}

	// ============================File visitor
	// methods==================================

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
			throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		List<String> words = createListOfWords(Files.readAllLines(file));
		if (firstPass) {
			addToVocabulary(words);
		} else {
			documentsWords.add(words);
			documents.put(file, words);
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		if (!firstPass) {
			calculateTF();
			calculateIDF();
			System.out.println("The size of the vocabulary is : " + vocabulary.size());
			createVectorRepresentations();
		}
		return FileVisitResult.CONTINUE;
	}

}
