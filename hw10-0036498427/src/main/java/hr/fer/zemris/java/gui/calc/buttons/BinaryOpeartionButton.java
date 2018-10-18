package hr.fer.zemris.java.gui.calc.buttons;

import java.util.function.DoubleBinaryOperator;
import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.CalcModel;

/**
 * The button which executes the operation on the 
 * active operand and the current value on the display.
 * 
 * @author ivan
 *
 */
public class BinaryOpeartionButton extends JButton {

	private static final long serialVersionUID = 1L;

	/**
	 * Operation to be executed.
	 */
	private DoubleBinaryOperator op;
	
	/**
	 * The model of the calculator.
	 */
	private CalcModel model;
	
	/**
	 * The constructor which gets the model of
	 * the calculator on which the multiplication has to be executed.
	 * 
	 * @param op
	 * 		the operator which applies the operation to
	 * 		the operands. 
	 * 
	 * @param model 
	 * 		the model of the calculator.	
	 * 
	 * @param text
	 * 		the text to be written on the button. 
	 * 
	 */
	public BinaryOpeartionButton(String text, CalcModel model,DoubleBinaryOperator op) {
		setText(text);
		this.model = model;
		this.op = op;
		addActionListener(e -> {
			executeOperation();
		});
	}
	
	/**
	 * This method sets the value of the binary operator
	 * to the specified value
	 * 
	 * @param op
	 * 		the new value of the operator.
	 */
	protected void setOp(DoubleBinaryOperator op) {
		this.op = op;
	}
	
	/**
	 * This method executes the binary operation.
	 */
	public void executeOperation() {
		if(!model.isActiveOperandSet()) {
			double value = model.getValue();
			model.setActiveOperand(value);
			model.setPendingBinaryOperation(op);
			
		} else {
			double left = model.getActiveOperand();
			double right = model.getValue();
			double result = model.getPendingBinaryOperation().applyAsDouble(left, right);
			model.setActiveOperand(result);
			model.setPendingBinaryOperation(op);
		}
		model.clear();
	}
}
