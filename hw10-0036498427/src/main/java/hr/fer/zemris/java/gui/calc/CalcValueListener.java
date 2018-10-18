package hr.fer.zemris.java.gui.calc;

/**
 * The interface which models all listeners which
 * are associated with the {@link CalcModel}.
 * 
 * @author ivan
 *
 */
public interface CalcValueListener {
	
	/**
	 * The method called when the value changes.
	 * 
	 * @param model
	 * 		the model whose value changed.
	 */
	void valueChanged(CalcModel model);
}