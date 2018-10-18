package hr.fer.zemris.java.hw07.shell.builders;

import java.util.regex.Matcher;
import hr.fer.zemris.java.hw07.shell.NameBuilder;
import hr.fer.zemris.java.hw07.shell.NameBuilderInfo;

/**
 * This class represents an object which writes the
 * value of the certain group from the {@link Matcher}
 * with possible, but not mandatory, minimal width of
 * the String to write to the {@link StringBuilder}
 * which is in the {@link NameBuilderInfo} object.
 * 
 * @author ivan
 *
 */
public class GroupNameBuilder implements NameBuilder {

	//=========================Properties=======================================
	
	/**
	 * The index of the group from the matcher.
	 */
	private int groupIndex;
	
	/**
	 * The minimal width of the string to write to the
	 * {@link StringBuilder}. If 
	 * <code>WIDTH_NOT_SPECIFIED</code> than
	 * the minimal width is not specified and is not considered
	 * when writing to the {@link StringBuilder}.
	 */
	private int minimalWidth;
	
	/**
	 * The value of the minimal width if it is not specified.
	 */
	private static final int WIDTH_NOT_SPECIFIED = -1;
	
	/**
	 * The char with which to fill the group to have 
	 * the minimal width. The default value is SPACE
	 */
	private static char FILL_CHAR = ' ';
	
	private static final char ZERO = '0';
	private static final char SPACE = ' ';
	
	//===========================Constructor====================================
	
	/**
	 * The constructor which initializes the value of the
	 * group index and the minimal width.
	 * 
	 * @param groupIndex
	 * 		the index of the group
	 * 
	 * @param minimalWidth
	 * 		the minimal width of the string to be written to
	 * 		the console.
	 * 
	 * @param fillZero 
	 * 		the indicator whether to fill the necessary chars
	 * 		with spaces or zeros. The chars are filled 
	 * 		for those strings which have the width less than 
	 * 		<code>minimalWidth</code>.
	 */
	public GroupNameBuilder(int groupIndex, int minimalWidth, boolean fillZero) {
		super();
		if(groupIndex < 0 || 
				(minimalWidth != WIDTH_NOT_SPECIFIED && minimalWidth < 0)) {
			throw new IllegalArgumentException("The specified group "
					+ "index and minimal width must not be less "
					+ "than zero!");
		}
		this.groupIndex = groupIndex;
		this.minimalWidth = minimalWidth;
		FILL_CHAR = fillZero ? ZERO : SPACE;
	}
	
	/**
	 * The constructor which gets only a group index,
	 * because the width is not specified.
	 * 
	 * @param groupIndex
	 * 		the index of the group from the {@link Matcher}.
	 */
	public GroupNameBuilder(int groupIndex) {
		this(groupIndex, WIDTH_NOT_SPECIFIED, false);
	}
	
	//======================Method implementations===============================
	
	@Override
	public void execute(NameBuilderInfo info) {
		String group = info.getGroup(groupIndex);
		
		if(minimalWidth == WIDTH_NOT_SPECIFIED || 
						group.length() >= minimalWidth) {
			info.getStringBuilder().append(group);
			
		} else {
			int numberOfFills = minimalWidth - group.length();
			StringBuilder sb = info.getStringBuilder();
			
			for(int i = 0; i < numberOfFills; i++) {
				sb.append(FILL_CHAR);
			}
			sb.append(group);
		}	
	}
}
