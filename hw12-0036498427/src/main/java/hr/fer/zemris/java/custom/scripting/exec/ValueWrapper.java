package hr.fer.zemris.java.custom.scripting.exec;

/**
 * This class represents a wrapper for values
 * stored in the {@link ObjectMultistack}.
 * 
 * @author ivan
 *
 */
public class ValueWrapper {

	//=============================Property===================================
	
	/**
	 * The actual value which can be of any type.
	 */
	private Object value;
	
	/**
	 * This is the integer value returned if the certain
	 * value is null. Then it is treated as an integer with
	 * value 0.
	 */
	private static final Integer VALUE_IF_NULL = Integer.valueOf(0);
	
	/**
	 * This is the threshold used for comparison of numbers. (i.e.
	 * two double numbers are equal if abs(first-second) < EPSILON)
	 */
	private static final double EPSILON = 1E-08;

	//===========================Constructor===================================

	/**
	 * This constructor gets one argument <code>value</code>
	 * which is the initial value of this class' property
	 * <code>value</code>. The given argument can even be
	 * <code>null</code>.
	 * 
	 * @param value
	 * 		the initial value of this class' property
	 * 		<code>value</code>.
	 */
	public ValueWrapper(Object value) {
		super();
		this.value = value;
	}
	
	//==========================Getters and setters==============================
	
	/**
	 * This method returns the value of the property
	 * <code>value</code>.
	 * 
	 * @return
	 * 		the value of the property <code>value</code>.
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * This method sets the value of the <code>value</code>
	 * property to the specified value.
	 * 
	 * @param value
	 * 		the value to set the value of the property 
	 * 		<code>value</code> to.
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	//=====================Method implementations===============================
	
	/**
	 * This method increments the value of the <code>value</code> 
	 * property for the value of the <code>incValue</code> 
	 * argument. If either property <code>value</code> or 
	 * the given argument are not {@link Integer}, 
	 * {@link Double}, {@link String} or <code>null</code> 
	 * throws an exception.
	 * 
	 * @param incValue
	 * 		increment value.
	 */
	public void add(Object incValue) {
		chechArgument(incValue);
		checkProperty();
		Number value1 = getNumberValue(value);
		Number value2 = getNumberValue(incValue);
		
		if(value1 instanceof Double || value2 instanceof Double) {
			value = (Double)(value1.doubleValue() + value2.doubleValue());
		} else {
			value = (Integer)(value1.intValue() + value2.intValue());
		}
	}

	/**
	 * This method decrements the value of the <code>value</code> 
	 * property for the value of the <code>decValue</code> 
	 * argument. If either property <code>value</code> or 
	 * the given argument are not {@link Integer}, 
	 * {@link Double}, {@link String} or <code>null</code> 
	 * throws an exception.
	 * 
	 * @param decValue
	 * 		decrement value.
	 */
	public void subtract(Object decValue) {
		chechArgument(decValue);
		checkProperty();
		Number value1 = getNumberValue(value);
		Number value2 = getNumberValue(decValue);
		
		if(value1 instanceof Double || value2 instanceof Double) {
			value = (Double)(value1.doubleValue() - value2.doubleValue());
		} else {
			value = (Integer)(value1.intValue() - value2.intValue());
		}
	}
	
	/**
	 * This method multipliers the value of the <code>value</code> 
	 * property with the value of the <code>mulValue</code> 
	 * argument. If either property <code>value</code> or 
	 * the given argument are not {@link Integer}, 
	 * {@link Double}, {@link String} or <code>null</code> 
	 * throws an exception.
	 * 
	 * @param mulValue
	 * 		multiply value.
	 */
	public void multiply(Object mulValue) {
		chechArgument(mulValue);
		checkProperty();
		Number value1 = getNumberValue(value);
		Number value2 = getNumberValue(mulValue);
		
		if(value1 instanceof Double || value2 instanceof Double) {
			value = (Double)(value1.doubleValue() * value2.doubleValue());
		} else {
			value = (Integer)(value1.intValue() * value2.intValue());
		}
	}
	
	/**
	 * This method divides the value of the <code>value</code> 
	 * property with the value of the <code>divValue</code> 
	 * argument. If either property <code>value</code> or 
	 * the given argument are not {@link Integer}, 
	 * {@link Double}, {@link String} or <code>null</code> 
	 * throws an exception.
	 * 
	 * @param divValue
	 * 		divide value.
	 */
	public void divide(Object divValue) {
		chechArgument(divValue);
		checkProperty();
		Number value1 = getNumberValue(value);
		Number value2 = getNumberValue(divValue);
		
		try {
			if(value1 instanceof Double || value2 instanceof Double) {
				value = (Double)(value1.doubleValue() / value2.doubleValue());
			} else {
				value = (Integer)(value1.intValue() / value2.intValue());
			}
		} catch (ArithmeticException e) {
			throw new IllegalArgumentException("The number to divide with, must "
					+ "not be zero!");
		}
		
	}

	/**
	 * This method compares the value of the <code>value</code> 
	 * property with the value of the <code>withValue</code> 
	 * argument. If either property <code>value</code> or 
	 * the given argument are not {@link Integer}, 
	 * {@link Double}, {@link String} or <code>null</code> 
	 * throws an exception.
	 * 
	 * @param withValue
	 * 		value to compare to.
	 * 
	 * @return
	 * 		the result of comparison. Less than 0 if
	 * 		the <code>value</code> is less than 
	 * 		<code>withValue</code>, greater than 0 if
	 * 		greater and 0 if equal.
	 */
	public int numCompare(Object withValue) {
		if(value == null && withValue == null) return 0;
		chechArgument(withValue);
		checkProperty();
		Number value1 = getNumberValue(value);
		Number value2 = getNumberValue(withValue);
		
		double result = value1.doubleValue() - value2.doubleValue();
		
		if(Math.abs(result) < EPSILON) return 0;
		return result > 0 ? 1 : -1;
	}
	
	/**
	 * This method checks if the given argument is valid
	 * for arithmetic operations.
	 * 
	 * @param value
	 * 		value to be checked.
	 * 
	 * @throws IllegalArgumentException
	 * 		if the argument <code>value</code> 
	 * 		is not valid for arithmetic operations.
	 */
	private void chechArgument(Object value) {
		if(!(value instanceof Integer) && 
			!(value instanceof Double) &&
			!(value instanceof String) &&
			value != null) {
			throw new IllegalArgumentException("The given argument "
					+ "for arithmetic operations must be of type "
					+ "Integer, Double, String or null!");
		}
		
	}
	
	/**
	 * This method checks if the property 
	 * <code>value</code> is valid for arithmetic
	 * operations. Throws an exception if not.
	 * 
	 * @throws IllegalArgumentException
	 * 		if the <code>value</code> property
	 * 		is not valid for arithmetic operations.
	 * 
	 */
	private void checkProperty() {
		if(!(value instanceof Integer) && 
			!(value instanceof Double) &&
			!(value instanceof String) &&
			value != null) {
				throw new IllegalArgumentException("The property "
						+ "value must be of type Integer, Double, "
						+ "String or null to perform arithmetic "
						+ "operations.");
			}
	}

	/**
	 * This method determines the value of the specified
	 * argument. If it is a String parses it and returns
	 * a Number value. If it is null then returns 
	 * <code>VALUE_IF_NULL</code> constant of this class.
	 * And if none of the above than returns the specified
	 * argument casted to {@link Number}.
	 * 
	 * @param value
	 * 		value to be checked.
	 * @return
	 * 		the determined Number value.
	 */
	private Number getNumberValue(Object value) {
		Object helpValue = value;
		if(value == null) return VALUE_IF_NULL;
		if(helpValue instanceof String) {
			return parseString((String)helpValue);
		}
		
		return (Number)helpValue;
	}

	/**
	 * This method parses the given {@link String} to 
	 * Double or Integer, depending whether it contains
	 * a decimal dot or "E" in it.
	 * 
	 * @param s
	 * 		a value to be parsed
	 * @return
	 * 		a parsed Number.
	 * 
	 * @throws IllegalArgumentException
	 * 		if the parsing fails than clearly the
	 * 		given argument was illegal because
	 * 		the {@link Number} was expected to 
	 * 		be returned.
	 */
	private Number parseString(String s) {
		if(isDecimal(s)) {
			return parseDecimal(s);
		} else {
			return parseInteger(s);
		}
	}

	/**
	 * This method parses the given {@link String} s.
	 * 
	 * @param s
	 * 		a {@link String} to be parsed.
	 * @return
	 * 		parsed number.
	 * 
	 * @throws IllegalArgumentException
	 * 		if the given string was illegal and
	 * 		parsing failed.
	 */
	private Number parseInteger(String s) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("The integer "
					+ "number could not be parsed!");
		}	
	}

	/**
	 * This method parses the given {@link String} s.
	 * 
	 * @param s
	 * 		a {@link String} to be parsed.
	 * @return
	 * 		parsed number.
	 * 
	 * @throws IllegalArgumentException
	 * 		if the given string was illegal and
	 * 		parsing failed.
	 */
	private Number parseDecimal(String s) {
		try {
			return Double.parseDouble(s);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("The decimal "
					+ "number could not be parsed!");
		}	
	}

	/**
	 * This method checks if the given string is
	 * a string representation of decimal number.
	 * 
	 * @param s
	 * 		a {@link String} to be checked.
	 * @return
	 * 		<code>true</code> if is decimal.
	 * 		<code>false</code> otherwise.
	 */
	private boolean isDecimal(String s) {
		return s.contains(".") || s.contains("E");
	}

}
