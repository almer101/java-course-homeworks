package hr.fer.zemris.java.gui.calc.buttons;

import java.util.function.DoubleBinaryOperator;
import javax.swing.JCheckBox;
import hr.fer.zemris.java.gui.calc.CalcModel;

/**
 * This button models the button which raises the current
 * value of the calculator display to the power of 
 * 
 * @author ivan
 *
 */
public class PowerNButton extends BinaryOpeartionButton {

	//==========================Properties==============================
	
	private static final long serialVersionUID = 1L;

	/**
	 * The x^n binary operator
	 */
	private DoubleBinaryOperator xPowerN;
	
	/**
	 * The nth root binary operator.
	 */
	private DoubleBinaryOperator nthRoot;
	
	/**
	 * Check box which indicates whether normal or inverted
	 * function should be applied.
	 */
	private JCheckBox inv;
	
	//==========================Constructor=============================
	
	/**
	 * Constructor which gets the model and the check box.
	 * See the description above.
	 * 
	 * @param inv	
	 * 		the inverted-check box
	 * 
	 * @param model
	 * 		the model of the calculator.
	 */
	public PowerNButton(JCheckBox inv, CalcModel model) {
		super("x^n", model, null);
		this.inv = inv;
		this.xPowerN = Util.xPowerN();
		this.nthRoot = Util.nthRoot();
	}
	
	@Override
	public void executeOperation() {
		if(inv.isSelected()) {
			setOp(nthRoot);
		} else {
			setOp(xPowerN);
		}
		super.executeOperation();
	}
}
