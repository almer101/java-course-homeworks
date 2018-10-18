package hr.fer.zemris.java.gui.calc.buttons;

import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import hr.fer.zemris.java.gui.calc.CalcModel;

/**
 * This class models the button of the unary operation which
 * can be inverted or not.
 * 
 * @author ivan
 *
 */
public class UnaryOperationButton extends JButton {

	//===========================Properties=================================
	
	private static final long serialVersionUID = 1L;

	/**
	 * The normal unary operation
	 */
	private DoubleUnaryOperator normal;
	
	/**
	 * The inverted unary operation.
	 */
	private DoubleUnaryOperator inverted;
	
	/**
	 * Check box which indicates whether the normal
	 * or inverted operation will be executed.
	 */
	private JCheckBox inv;
	
	/**
	 * The model of the calculator.
	 */
	private CalcModel model;
	
	//===========================Constructor================================
	
	/**
	 * The constructor gets the values of normal and inverted unary
	 * operator, inverted check box and the model where all this is 
	 * happening.
	 * 
	 * @param normal
	 * 		the normal unary operation.
	 * 
	 * @param inverted
	 * 		the inverted unary operation.		
	 * 
	 * @param inv
	 * 		the inverted check box.		
	 * 
	 * @param model
	 * 		the model of the calculator.
	 * 
	 * @param text
	 * 		the text which will be written on the button. 
	 */
	public UnaryOperationButton(JCheckBox inv, CalcModel model, 
			DoubleUnaryOperator normal, DoubleUnaryOperator inverted, 
			String text) {
		super();
		this.normal = normal;
		this.inverted = inverted;
		this.inv = inv;
		this.model = model;
		addActionListener(e -> {
			executeOperation();
		});
		setText(text);
	}
	
	//===========================Execute method=============================
	
	/**
	 * This method executes the operation depending on the check
	 * box status. If the inverted check box is selected the
	 * inverted operation is performed and normal otherwise.
	 */
	public void executeOperation() {
		double value;
		if(inverted == null || !inv.isSelected()) {
			value = normal.applyAsDouble(model.getValue());
		} else {
			value = inverted.applyAsDouble(model.getValue());
		}
		model.setValue(value);
		//the active operand remains intact
	}
}
