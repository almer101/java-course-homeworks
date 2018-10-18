package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

/**
 * This is the implementation of the {@link CalcModel}.
 * The model stores the current value of the calculator display,
 * the active operand, and the pending operation. Every time the value
 * is changed the listeners of this model are informed. 
 * 
 * @author ivan
 *
 */
public class CalcModelImpl implements CalcModel {
	
	//============================Properties===============================
	
	/**
	 * The current value of the calculator. (the value on 
	 * the display.)
	 */
	private String value = null;
	
	/**
	 * The active operand.
	 */
	private double activeOperand;
	
	/**
	 * The flag indicating that the active operand is set.
	 */
	private boolean activeOperandSet = false;
	
	/**
	 * The operation which is to be executed.
	 */
	private DoubleBinaryOperator pendingOperation;
	
	/**
	 * The list of all the listeners of this model.
	 */
	private List<CalcValueListener> listeners = new ArrayList<>();
	
	/**
	 * The string of the number zero.
	 */
	private static final String ZERO_STRING = "0";
	
	/**
	 * The double value of the number zero.
	 */
	private static final double ZERO = 0.0;
	
	/**
	 * The decimal point.
	 */
	private static final String DOT = ".";
	
	/**
	 * The integer value of zero
	 */
	private static final int ZERO_INT = 0;
	
	/**
	 * The epsilon used for comparing double numbers.
	 */
	private static final double EPSILON = 1E-08;
	
	/**
	 * The minus sign
	 */
	private static final String MINUS = "-";
	
	//=======================Method implementations=========================

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		Objects.requireNonNull(l, "The listener to be added "
				+ "can not be null!");
		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		Objects.requireNonNull(l, "The listener to be removed "
				+ "can not be null!");
		listeners.remove(l);
	}

	@Override
	public double getValue() {
		if(value == null) return ZERO;
		return Double.parseDouble(value);
	}

	@Override
	public void setValue(double value) {
		if(Double.isFinite(value) && !Double.isNaN(value)) {
			this.value = String.valueOf(value);
			informListeners();
		}
	}

	@Override
	public void clear() {
		value = null;
		informListeners();
	}

	@Override
	public void clearAll() {
		value = null;
		clearActiveOperand();
		pendingOperation = null;
		informListeners();
	}

	@Override
	public void swapSign() {
		if(value == null) return;
		double currentValue = getValue();
		
		if(Math.abs(currentValue) < EPSILON) {
			swapSignOnZero();
			return;
		}
		currentValue = -currentValue;
		setValue(currentValue);
	}

	/**
	 * This method swaps the sign if the value is zero.
	 * so we can get "-0" and also "0".
	 * 
	 */
	private void swapSignOnZero() {
		if(value.contains(MINUS)) {
			value = value.substring(1);
		} else {
			value = MINUS + value;
		}
	}

	@Override
	public void insertDecimalPoint() {
		if(value == null) {
			value = ZERO_STRING + DOT;
			return;
		}
		if(!value.contains(DOT)) {
			value = value + DOT;
			informListeners();
		}
	}

	@Override
	public void insertDigit(int digit) {
		if(value == null) {
			value = String.valueOf(digit);
		} else {
			if(!areMultipleZeros(digit)) {
				addDigit(digit);
			}
		}
		informListeners();
	}

	/**
	 * Checks if the multiple zeros might occur. The method
	 * returns the boolean value accordingly.
	 * 
	 * @param digit
	 * 		the digit to check
	 * 
	 * @return
	 * 		<code>false</code> if multiple zeros will not
	 * 		occur after adding the specified digit.
	 * 		<code>true</code> otherwise.
	 */
	private boolean areMultipleZeros(int digit) {
		if(digit != ZERO_INT) return false;
		if(value.contains(DOT)) return false;
		if(Math.abs(getValue()) > EPSILON) return false;
		return true;
	}
	
	/**
	 * This method inserts the specified digit to the value
	 * but taking in account that the leading zeros have to be
	 * removed.
	 * 
	 * @param digit
	 * 		the digit to insert.
	 */
	private void addDigit(int digit) {
		if(isTooBig(digit)) return;
		String digitString = String.valueOf(digit);
		if(Math.abs(getValue()) < EPSILON && 
				!value.contains(DOT)) {
			value = digitString;
		} else {
			value += digitString;
		}
	}

	/**
	 * This method checks if the number will be too big if
	 * the specified digit is added.
	 * 
	 * @param digit
	 * 		the digit to be added.
	 * 
	 * @return
	 * 		<code>true</code> if the number will be to big
	 * 		after the digit is inserted. <code>false</code>
	 * 		otherwise.
	 */
	private boolean isTooBig(int digit) {
		return !Double.isFinite(Double.parseDouble(value + digit));
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperandSet;
	}

	@Override
	public double getActiveOperand() {
		if(!isActiveOperandSet()) {
			throw new IllegalStateException("The active operand is "
					+ "not set!");
		}
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		activeOperandSet = true;
	}

	@Override
	public void clearActiveOperand() {
		activeOperandSet = false;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		Objects.requireNonNull(op);
		this.pendingOperation = op;
	}

	@Override
	public String toString() {
		if(value == null) return ZERO_STRING;
		return value;
	}

	/**
	 * This method informs all the listeners that the value stored
	 * in the model changed.
	 */
	private void informListeners() {
		listeners.forEach(l -> l.valueChanged(this));	
	}
}
