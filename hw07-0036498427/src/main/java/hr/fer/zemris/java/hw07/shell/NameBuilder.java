package hr.fer.zemris.java.hw07.shell;

/**
 * The objects of this type generate pieces of
 * name by writing in the {@link StringBuilder}
 * which is in the {@link NameBuilderInfo} object
 * given in the execute() method.
 * 
 * @author ivan
 *
 */
public interface NameBuilder {
	
	/**
	 * This method generates a piece of name
	 * by writing in the {@link StringBuilder} which
	 * is in the {@link NameBuilderInfo} object.
	 * 
	 * @param info
	 * 		the object which has the {@link StringBuilder}
	 * 		to write pieces of name into.
	 */
	void execute(NameBuilderInfo info);
}
