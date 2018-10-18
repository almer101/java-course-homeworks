package hr.fer.zemris.java.hw07.shell.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.NameBuilder;
import hr.fer.zemris.java.hw07.shell.builders.BasicNameBuilder;
import hr.fer.zemris.java.hw07.shell.builders.FinalNameBuilder;
import hr.fer.zemris.java.hw07.shell.builders.GroupNameBuilder;

/**
 * This is the parser for the expressions which
 * represent a naming scheme for the files. 
 * The parser throws {@link NameBuilderParserException}
 * if an error occurs during parsing.
 * 
 * @author ivan
 *
 */
public class NameBuilderParser {

	//=========================Properties==================================
	
	/**
	 * The char array of the input line 
	 * (i.e. expression to parse).
	 */
	private char data[];
	
	/**
	 * The current index in the array.
	 */
	private int currentIndex = 0;
	
	/**
	 * A builder containing references to all other builders.
	 * This builder is returned when the method getNameBilder
	 * is called.
	 */
	private NameBuilder finalBuilder;
	
	//==========================Constants==================================
	
	private static final char DOLLAR = '$';
	private static final char OPENED_PARANTHESES = '{';
	private static final char CLOSED_PARANTHESES = '}';
	private static final String TAG_SPLIT = ",";
	
	//==========================Constructors===============================
	
	/**
	 * This constructor gets an expression and if the expression
	 * is not <code>null</code> than makes a char array out of it
	 * and stores that array to the object's property 
	 * <code>data</code>
	 * 
	 * @param expression
	 * 		the expression to parse.
	 */
	public NameBuilderParser(String expression) {
		Objects.requireNonNull(expression);
		expression = expression.trim();
		data = expression.toCharArray();
		parse();
	}
	
	/**
	 * This method returns a final builder which is got after
	 * parsing the expression specified in the constructor.
	 * 
	 * @return
	 * 		the final {@link NameBuilder}.
	 */
	public NameBuilder getNameBuilder() {
		return finalBuilder;
	}

	/**
	 * This method parses a {@link String} specified in the
	 * constructor, creates as many {@link NameBuilder} 
	 * objects as needed and at the end creates one name builder
	 * which stores references to all other name builders.
	 * 
	 */
	private void parse() {
		StringBuilder sb = new StringBuilder();
		List<NameBuilder> builders = new ArrayList<>();
		
		while(currentIndex < data.length ) {
			if(isOpenedTag()) {
				if(sb.length() != 0) {
					builders.add(new BasicNameBuilder(sb.toString()));
				}
				builders.add(parseBracket());
				sb.setLength(0);
				
			} else {
				sb.append(data[currentIndex]);
			}
			currentIndex++;
		}
		if(sb.length() != 0) {
			builders.add(new BasicNameBuilder(sb.toString()));
		}
		finalBuilder = new FinalNameBuilder(builders);
	}

	/**
	 * This method checks if the tag opening is ahead
	 * the opening looks like ${
	 * 
	 * @return
	 * 		return <code>true</code> if the tag is
	 * 		opening. <code>false</code> otherwise.
	 */
	private boolean isOpenedTag() {
		int i = currentIndex;
		return data[i] == DOLLAR && i + 1 < data.length &&
				data[i + 1] == OPENED_PARANTHESES;
	}

	/**
	 * This method parses a bracket of type ${something}
	 * in the bracket there can be either one integer
	 * number or 2 integer numbers divided by a comma.
	 * The method creates a {@link GroupNameBuilder} object.
	 * 
	 */
	private GroupNameBuilder parseBracket() {
		//increment currentIndex by 2 because we checked that the
		//tag is opened with ${
		currentIndex += 2;
		StringBuilder sb = new StringBuilder();
		
		while(currentIndex < data.length && 
				data[currentIndex] != CLOSED_PARANTHESES) {
			sb.append(data[currentIndex++]);
		}
		if(currentIndex >= data.length) {
			throw new NameBuilderParserException("The parentheses must be closed!");
		}
		return parseTagContent(sb.toString());	
	}

	/**
	 * This method parses the content which was inside the tag
	 * and returns the appropriate {@link GroupNameBuilder}.
	 * 
	 * @param value
	 * 		value to parse.
	 * 
	 * @return
	 * 		a {@link GroupNameBuilder} object with the 
	 * 		specification given by the parameter string.
	 */
	private GroupNameBuilder parseTagContent(String value) {
		String parts[] = value.split(TAG_SPLIT);
		
		switch (parts.length) {
		case 1:
			return getGroupNameBuilder(parts[0].trim(), null);
			
		case 2:
			return getGroupNameBuilder(parts[0].trim(), parts[1].trim());

		default:
			throw new NameBuilderParserException("One or two arguments "
					+ "are expected in the tag.");
		}
	}

	/**
	 * This method returns the {@link GroupNameBuilder} with 
	 * specifications as given by the parameters
	 * 
	 * @param groupIndexString
	 * 		the string value of the group index.
	 * 
	 * @param widthString
	 * 		the string value of the minimum width.
	 * 
	 * @return
	 * 		a {@link GroupNameBuilder} object.
	 */
	private GroupNameBuilder getGroupNameBuilder(String groupIndexString, 
										String widthString) {
		int groupIndex = parseNumber(groupIndexString);	
		
		if(widthString == null) {
			return new GroupNameBuilder(groupIndex);
			
		} else {
			boolean fillZeros = widthString.trim().startsWith("0");
			int width = parseNumber(widthString);
			return new GroupNameBuilder(groupIndex, width, fillZeros);
		}
	}

	/**
	 * This method parses an integer number and returns the value of it.
	 * 
	 * @param value
	 * 		value to parse.
	 * 
	 * @return
	 * 		the parsed value.
	 */
	private int parseNumber(String value) {
		try {
			return Integer.parseInt(value);
			
		} catch (NumberFormatException e) {
			throw new NameBuilderParserException("The number inside the brackets is "
					+ "not an integer.");
		}
	}	
}
