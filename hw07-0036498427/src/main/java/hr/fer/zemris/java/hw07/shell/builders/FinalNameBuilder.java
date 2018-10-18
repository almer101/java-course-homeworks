package hr.fer.zemris.java.hw07.shell.builders;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hr.fer.zemris.java.hw07.shell.NameBuilder;
import hr.fer.zemris.java.hw07.shell.NameBuilderInfo;

/**
 * This class stores references to all other {@link NameBuilder}s
 * and in the method execute, executes each one of them.
 * 
 * @author ivan
 *
 */
public class FinalNameBuilder implements NameBuilder {
	
	//============================Property================================
	
	private List<NameBuilder> builders;
	
	//==========================Constructor===============================

	/**
	 * This constructor gets a reference to the collection of 
	 * builders and copies the content of the specified list
	 * to the property list.
	 * 
	 * @param builders
	 * 		the list of builders.
	 */
	public FinalNameBuilder(List<NameBuilder> builders) {
		super();
		Objects.requireNonNull(builders);
		this.builders = new ArrayList<>(builders);
	}
	
	//=====================Method implementations=========================
	
	@Override
	public void execute(NameBuilderInfo info) {
		builders.forEach(b -> b.execute(info));
	}
}
