package hr.fer.zemris.java.hw07.shell.builders;

import java.util.regex.Matcher;

import hr.fer.zemris.java.hw07.shell.NameBuilderInfo;

/**
 * This is an implementation of the
 * 
 * @author ivan
 *
 */
public class NameBuilderInfoImpl implements NameBuilderInfo {

	//===========================Properties==========================
	
	/**
	 * A matcher which groups strings into groups.
	 */
	private Matcher matcher;
	
	/**
	 * A {@link StringBuilder} used for building names of files.
	 */
	private StringBuilder sb;
	
	//==========================Constructor==========================
	
	/**
	 * This constructor gets a matcher as a parameter. The
	 * matcher is used when the getGroup method is called.
	 * 
	 * @param matcher
	 * 		a matcher which groups inputs.
	 */
	public NameBuilderInfoImpl(Matcher matcher) {
		super();
		this.matcher = matcher;
		this.sb = new StringBuilder();
	}

	@Override
	public StringBuilder getStringBuilder() {
		return sb;
	}

	@Override
	public String getGroup(int index) {
		if(matcher.matches()) {
			return matcher.group(index);
		}
		return null;
	}
}
