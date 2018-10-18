package hr.fer.zemris.java.gui.calc.buttons;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.CalcModel;

/**
 * This class models the button which has a digit on it
 * and which upon pushing the button adds the certain digit
 * to the calculator display.
 * 
 * @author ivan
 *
 */
public class DigitButton extends JButton {

	private static final long serialVersionUID = 1L;

	/**
	 * the digit which is going to be added to the 
	 * calculator display once the button is pressed.
	 * 
	 */
	private int digit;
	
	/**
	 * The model of the calculator
	 */
	private CalcModel model;
	
	//=======================Constructor=============================
	
	/**
	 * Constructor gets the value of the digit which is going
	 * to be added to the calculator display once the button is
	 * pressed.
	 * 
	 * @param digit
	 * 		the digit which is going to be added to the 
	 * 		calculator display.
	 * 
	 * @param model
	 * 		the model of the calculator.
	 */
	public DigitButton(int digit, CalcModel model) {
		super();
		this.digit = digit;
		this.model = model;
		setText(String.valueOf(digit));
		addActionListener(e -> {
			execute();
		});
	}
	
	//==========================Getter================================

	/**
	 * Returns the value of the {@link #digit}.
	 * 
	 * @return
	 * 		returns the value of the {@link #digit}.
	 */
	public int getDigit() {
		return digit;
	}
	
	/**
	 * The method executed once the button is pressed.
	 */
	private void execute() {
		model.insertDigit(getDigit());
	}

}
