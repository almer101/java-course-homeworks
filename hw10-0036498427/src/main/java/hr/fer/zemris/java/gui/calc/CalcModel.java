package hr.fer.zemris.java.gui.calc;

import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;

/**
 * The classes which implement this interface will
 * implement these methods which are necessary for 
 * proper functioning of the calculator.
 * 
 * @author ivan
 *
 */
public interface CalcModel {
	
	/**
	 * Adds the specified listener to the list of 
	 * listeners.
	 * 
	 * @param l
	 * 		a listener to add.
	 */
	void addCalcValueListener(CalcValueListener l);
	
	/**
	 * Removes the specified listener from the list
	 * of listeners.
	 * 
	 * @param l
	 * 		a listener to remove.
	 */
	void removeCalcValueListener(CalcValueListener l);
	
	/**
	 * Returns the value of the calculator display if not
	 * <code>null</code> and returns the {@link String} "0"
	 * if the value of the calculator display is <code>null</code>
	 * 
	 * @return
	 * 		the value of the calculator display or "0" if
	 * 		the value is <code>null</code>.
	 */
	String toString();
	
	/**
	 * Returns the parsed value from the calculator display.
	 * 
	 * @return
	 * 		parsed value from the calculator display.
	 */
	double getValue();
	
	/**
	 * Sets the value of the calculator display to the 
	 * string representation of the specified value if the 
	 * value is not infinity and is not <code>Nan</code>.
	 * 
	 * @param value
	 * 		the value to set the calculator display to.
	 */
	void setValue(double value);
	
	/**
	 * Clears only the current value of the calculator display.
	 * 
	 */
	void clear();
	
	/**
	 * Clears the current value of the calculator display,
	 * active operand and pendingOperation.
	 * 
	 */
	void clearAll();
	
	/**
	 * Changes the sign of the current calculator value but only
	 * if at least one digit is entered.
	 * 
	 */
	void swapSign();
	
	/**
	 * Inserts the decimal point at the end of the current
	 * calculator display value.
	 * 
	 */
	void insertDecimalPoint();
	
	/**
	 * Inserts the specified digit to the end of the current
	 * calculator display value.
	 * 
	 * @param digit
	 * 		a value to insert.
	 */
	void insertDigit(int digit);
	
	/**
	 * Checks if the active operand is set and returns
	 * boolean value accordingly.
	 * 
	 * @return
	 * 		<code>true</code> if the active operand is set,
	 * 		<code>false</code> otherwise.
	 */
	boolean isActiveOperandSet();
	
	/**
	 * Returns the value of the active operand if one is set.
	 * If not set the method throws {@link IllegalStateException}.
	 * 
	 * @return
	 * 		the value of the active operand.
	 * 
	 * @throws IllegalStateException
	 * 		if the operand is not set.
	 */
	double getActiveOperand();
	
	/**
	 * Sets the value of the active operand to the specified
	 * value.
	 * 
	 * @param activeOperand
	 * 		the new value of the active operand.
	 */
	void setActiveOperand(double activeOperand);
	
	/**
	 * Clears the value of the active operand.
	 * 
	 */
	void clearActiveOperand();
	
	/**
	 * Returns the pending {@link BinaryOperator}.
	 * 
	 * @return
	 * 		the pending binary operation.
	 */
	DoubleBinaryOperator getPendingBinaryOperation();
	
	/**
	 * Sets the value of the current pending binary operation.
	 * 
	 * @param op
	 * 		the pending binary operation to set.
	 */
	void setPendingBinaryOperation(DoubleBinaryOperator op);
}