package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;


/**
 * This class model the position of the components
 * in the layout. It has two read-only properties
 * the {@link #row} and {@link #column} which describe
 * in which row and column the component is located.
 * 
 * @author ivan
 *
 */
public class RCPosition {

	//==========================Properties=============================
	
	/**
	 * A read-only property which indicates in which row the
	 * component is located.
	 */
	private int row;
	
	/**
	 * The read-only property which indicates in which 
	 * column the component is located.
	 */
	private int column;
	
	//==========================Constructor============================
	
	/**
	 * The constructor which gets the parameters for the
	 * position of the component and initializes the
	 * properties with the specified values.
	 * 
	 * @param row
	 * 		the row in which the component is going to
	 * 		be located.
	 * 
	 * @param column
	 * 		the column in which the component is going to
	 * 		be located.
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	//============================Getters==============================
	
	/**
	 * This method returns the value of the
	 * row in which the component is located.
	 * 
	 * @return
	 * 		the row in which the component 
	 * 		is located
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * This method returns the value of the 
	 * column in which the component is located.
	 * 
	 * @return
	 * 		the column in which the component
	 * 		is located.
	 */
	public int getColumn() {
		return column;
	}
	
	//======================Method implementations=====================
	
	/**
	 * This method parses the specified string and returns 
	 * the instance of {@link RCPosition}.
	 * 
	 * @param s 
	 * 		a string to parse to a position
	 * @return
	 * 		the parse instance of {@link RCPosition}.
	 * 
	 * @throws IllegalArgumentException 
	 * 		if the {@link String} could not be parsed.
	 */
	public static RCPosition fromString(String s) 
			throws IllegalArgumentException {
		
		String parts[] = s.trim().split(",");
		if(parts.length != 2) {
			throw new IllegalArgumentException("Illegal format"
					+ " of the string constraints!");
		}
		
		try {
			int row = Integer.parseInt(parts[0].trim());
			int column = Integer.parseInt(parts[1].trim());
			return new RCPosition(row, column);
			
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Illegal format"
					+ " of the string constraints!");
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		Objects.requireNonNull(obj);
		RCPosition other = (RCPosition)obj;
		return row == other.row && column == other.column; 
	}
}
