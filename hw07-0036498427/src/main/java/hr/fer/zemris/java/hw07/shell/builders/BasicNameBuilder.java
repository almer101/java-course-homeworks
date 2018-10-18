package hr.fer.zemris.java.hw07.shell.builders;

import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.NameBuilder;
import hr.fer.zemris.java.hw07.shell.NameBuilderInfo;

/**
 * This class represents an object which writes a constant
 * string to the {@link StringBuilder} in the which is 
 * contained within the {@link NameBuilderInfo} object.
 * 
 * @author ivan
 *
 */
public class BasicNameBuilder implements NameBuilder {
	
	//==============================Property=================================
	
	/**
	 * The {@link String} to write in the {@link StringBuilder}.
	 */
	private String value;
	
	//==========================Constructor===================================
	
	/**
	 * This constructor initializes the value property of 
	 * the object.
	 * 
	 * @param value
	 * 		the initial value of the <code>vaule</code> 
	 * 		property.
	 */
	public BasicNameBuilder(String value) {
		Objects.requireNonNull(value);
		this.value = value;
	}
	
	//========================Method implementations==========================

	@Override
	public void execute(NameBuilderInfo info) {
		info.getStringBuilder().append(value);
	}

}
