package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;



/**
 * Layout manager which manages the manages the layout
 * of the components which are spread across 5 rows and
 * 7 columns. The gap between the rows and columns is specified
 * in the constructor. 
 * 
 */
public class CalcLayout implements LayoutManager2 {
	
	//==============================Properties==================================
	
	/**
	 * The size of the gap between elements in rows
	 * and columns.
	 */
	private int gapSize;
	
	/**
	 * The preferred dimension of the component.
	 */
	private Dimension preferredDimension;
	
	/**
	 * The minimum dimension of the component.
	 */
	private Dimension minimumDimension;
	
	/**
	 * The maximum dimension of the component.
	 */
	private Dimension maximumDimension;
	
	//==============================Constants===================================
	
	/**
	 * The number of the rows in this layout.
	 */
	private static final int ROWS = 5;
	
	/**
	 * The number of columns in this layout.
	 */
	private static final int COLUMNS = 7;
	
	/**
	 * The default size of the gap between the elements
	 * in rows and columns.
	 */
	private static final int DEFAULT_GAP_SIZE = 0;
	
	/**
	 * The row of the first component.
	 */
	private static final int FIRST_COMPONENT_ROW = 1;
	
	/**
	 * The starting column of the first component.
	 */
	private static final int FIRST_COMPONENT_COLUMN_START = 1;
	
	/**
	 * The ending row of the first component.
	 */
	private static final int FIRST_COMPONENT_COLUMN_END = 5;
	
	/**
	 * The position of the first component.
	 */
	private static final RCPosition FIRST_COMPONENT_POSITION = 
			new RCPosition(1, 1);
	
	/**
	 * The matrix of component references.
	 */
	private Component components[][] = new Component[ROWS][COLUMNS];
	
	//=============================Constructors=================================
	
	/**
	 * The constructor which gets the size of the gap between the
	 * elements in one row and column.
	 * 
	 * @param gapSize
	 * 		the gap between the elements in one row and elements
	 * 		in one column.
	 */
	public CalcLayout(int gapSize) {
		super();
		if(gapSize < 0) {
			throw new IllegalArgumentException("The specified gap size"
					+ " can not be smaller than 0!");
		}
		this.gapSize = gapSize;
	}
	
	/**
	 * A default constructor which initializes the {@link #gapSize}
	 * property to the value of the {@link #DEFAULT_GAP_SIZE}.
	 */
	public CalcLayout() {
		this(DEFAULT_GAP_SIZE);
	}
	
	//==========================Method implementations==========================
	
	@Override
	public void addLayoutComponent(String name, Component component) {
		//this method is not directly called so does nothing.
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int maxWidth = parent.getWidth() - 
				(insets.left + insets.right);
		int maxHeight = parent.getHeight() - 
				(insets.bottom - insets.top);
		
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLUMNS; j++) {
				Component comp = components[i][j];
				if(comp == null) continue;
				setBoundsForComponent(
						comp, parent, insets, maxWidth, maxHeight, i, j);	
			}
		}
	}
	
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getDimension(minimumDimension);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getDimension(preferredDimension);
	}
	
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getDimension(maximumDimension);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		//this method is not directly called so does nothing.
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(constraints instanceof RCPosition) {
			addToPosition(comp, (RCPosition)constraints);
			
		} else if(constraints instanceof String) {
			addToPosition(comp, RCPosition.fromString((String)constraints));
			
		} else {
			throw new CalcLayoutException("The specified contstraint "
					+ "is not legal!");
		}
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return target.getAlignmentX();
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return target.getAlignmentY();
	}

	@Override
	public void invalidateLayout(Container target) {
		//method does not contain references to other 
		//containers so the implementation is empty.
	}

	/**
	 * This method sets the bounds for the specified component.
	 * The maximum width and height of the component are used to
	 * determine the size of one component which is to be located
	 * and resized. The <code>i</code> and <code>j</code> indexes
	 * are used for determining the location of the component in
	 * the {@link #components} matrix.
	 * 
	 * @param comp
	 * 		the component for which the bounds have to be set.
	 * 
	 * @param maxWidth
	 * 		maximum width of the layout.		
	 * 
	 * @param maxHeight
	 * 		maximum height of the layout.
	 * 
	 * @param i
	 * 		the row index of the component in the {@link #components}
	 * 		matrix		
	 * 
	 * @param j
	 * 		the column index of the component in the {@link #components}
	 * 		matrix
	 */
	private void setBoundsForComponent(Component comp, Container parent, 
			Insets insets, int maxWidth, int maxHeight, int i, int j) {
		int compWidth = 
				(int)((maxWidth - (COLUMNS - 1) * gapSize) / (double)COLUMNS);
		int compHeight =
				(int)((maxHeight - (ROWS - 1) * gapSize) / (double)ROWS);
		
		if(i == 0 && j == 0) {
			int firstCompLength = FIRST_COMPONENT_COLUMN_END - 
					FIRST_COMPONENT_COLUMN_START + 1;
			compWidth = compWidth * firstCompLength + 
					(firstCompLength - 1) * gapSize;
		}
		
		int x = j * (compWidth + gapSize) + insets.left;
		int y = i * (compHeight + gapSize) + insets.top;
		
		comp.setBounds(x, y, compWidth, compHeight);
		updateDimensions(comp, new RCPosition(i + 1, j + 1));
	}

	/**
	 * This method adds the specified component to the position
	 * specified by the parameter <code>constraints</code>.
	 * 
	 * @param comp
	 * 
	 * @param constraints
	 * 
	 * @throws CalcLayoutException
	 * 		if the element could not be added to the layout.
	 */
	private void addToPosition(Component comp, RCPosition constraints) 
			throws CalcLayoutException {
		
		if(!isValid(constraints)) {
			throw new CalcLayoutException("The desired position is "
					+ "invalid!");
		}
		int row = constraints.getRow();
		int column = constraints.getColumn();
		if(components[row - 1][column - 1] != null) {
			throw new CalcLayoutException("The component already exists"
					+ " on the desired location.");
		}
		components[row - 1][column - 1] = comp;
		updateDimensions(comp, constraints);
	}

	/**
	 * Every time one new component is added, the preferred,
	 * minimum and maximum dimensions are updated based
	 * on the specified component <code>comp</code>
	 * 
	 * @param comp
	 * 		the component which was added and the preferred,
	 * 		minimum and maximum sizes are going to be updated.		
	 * 
	 * @param constraints
	 * 		the position of the component.
	 */
	private void updateDimensions(Component comp, RCPosition constraints) {
		boolean isFirstComponent = constraints.equals(FIRST_COMPONENT_POSITION);
		update(comp, isFirstComponent);
	}

	/**
	 * This method updates the minimum, preferred and maximum 
	 * dimensions of on component. The specified component is 
	 * used for comparing.
	 * 
	 * @param comp
	 * 		component for which to check the dimensions.
	 * 
	 * @param isFirstComponent
	 * 		indicator whether the specified component is the 
	 * 		first and largest component in the layout.
	 */
	private void update(Component comp, boolean isFirstComponent) {
		updateMinimumDimension(comp, isFirstComponent);
		updatePreferredDimension(comp, isFirstComponent);
		updateMaximumDimension(comp, isFirstComponent);
	}

	/**
	 * This method updates the minimum dimensions of
	 * one component based on the specified width and height.
	 * 
	 * @param width
	 * 		the width of one component.
	 * 
	 * @param height
	 * 		the height of one component.
	 */
	private void updateMinimumDimension(Component comp, boolean isFirstComponent) {
		Dimension compDimension = comp.getMinimumSize();
		if(compDimension == null) return;
		int width = checkFirstComponent(compDimension.width, isFirstComponent);
		int height = compDimension.height;
		if(minimumDimension == null) {
			minimumDimension = new Dimension(width, height);
		}

		if(width > minimumDimension.width) {
			minimumDimension.width = width;
		}
		if(height > minimumDimension.height) {
			minimumDimension.height = height;
		}
	}

	/**
	 * This method updates the preferred dimensions of
	 * one component based on the specified width and height.
	 * 
	 * @param width
	 * 		the width of one component.
	 * 
	 * @param height
	 * 		the height of one component.
	 */
	private void updatePreferredDimension(Component comp, boolean isFirstComponent) {
		Dimension compDimension = comp.getPreferredSize();
		if(compDimension == null) return;
		int width = checkFirstComponent(compDimension.width, isFirstComponent);
		int height = compDimension.height;
		if(preferredDimension == null) {
			preferredDimension = new Dimension(width, height);
		}

		if(width > preferredDimension.width) {
			preferredDimension.width = width;
		}
		if(height > preferredDimension.height) {
			preferredDimension.height = height;
		}
	}

	/**
	 * This method updates the maximum dimensions of
	 * one component based on the specified width and height.
	 * 
	 * @param width
	 * 		the width of one component.
	 * 
	 * @param height
	 * 		the height of one component.
	 */
	private void updateMaximumDimension(Component comp, boolean isFirstComponent) {
		Dimension compDimension = comp.getMaximumSize();
		if(compDimension == null) return;
		int width = checkFirstComponent(compDimension.width, isFirstComponent);
		int height = compDimension.height;
		if(maximumDimension == null) {
			maximumDimension = new Dimension(width, height);
		}
		
		if(width < maximumDimension.width) {
			maximumDimension.width = width;
		}
		if(height < maximumDimension.height) {
			maximumDimension.height = height;
		}
		
	}
	
	/**
	 * This method checks the specified flag and if the flag is
	 * <code>true</code> than the specified width has to be split
	 * to the width of one component because the specified width is
	 * the width of the first (largest) component.
	 * 
	 * @param width
	 * 		the width of the component.
	 * 
	 * @param isFirstComponent
	 * 		a flag indicating that the specified width belongs
	 * 		to the first and largest component.
	 * 
	 * @return
	 * 		the split width if necessary, or just the <code>width</code>
	 * 		if the flag is <code>false</code>
	 */
	private int checkFirstComponent(int width, boolean isFirstComponent) {
		if(isFirstComponent) {
			int numOfGaps = FIRST_COMPONENT_COLUMN_END - FIRST_COMPONENT_COLUMN_START;
			return (width - (numOfGaps * gapSize)) / (numOfGaps + 1);
		}
		return width;
	}

	/**
	 * This method checks if the specified constraints are valid.
	 * 
	 * @param constraints
	 * 		constraints to check.
	 * @return
	 */
	private boolean isValid(RCPosition constraints) {
		int row = constraints.getRow();
		int column = constraints.getColumn();
		
		if(row < 1 || row > ROWS) return false;
		if(column < 1 || column > COLUMNS) return false;
		if(row == FIRST_COMPONENT_ROW && 
				column > FIRST_COMPONENT_COLUMN_START &&
				column <= FIRST_COMPONENT_COLUMN_END) return false;
		return true;
	}
	
	/**
	 * This method returns the dimension of the layout
	 * based on the specified dimension of one component.
	 * 
	 * @param dimension
	 * 		the dimension of one component.
	 * 
	 * @return
	 * 		the dimension of the whole layout.
	 */
	private Dimension getDimension(Dimension dimension) {
		int width = dimension.width * COLUMNS + 
				gapSize * (COLUMNS - 1);
		int height = dimension.height * ROWS + 
				gapSize * (ROWS - 1);
		return new Dimension(width, height);
	}
}
