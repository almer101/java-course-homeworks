package hr.fer.zemris.java.gui.calc;

import java.awt.Container;
import java.awt.Dimension;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.buttons.BinaryOpeartionButton;
import hr.fer.zemris.java.gui.calc.buttons.DigitButton;
import hr.fer.zemris.java.gui.calc.buttons.PowerNButton;
import hr.fer.zemris.java.gui.calc.buttons.UnaryOperationButton;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;
import static hr.fer.zemris.java.gui.calc.buttons.Util.*;

/**
 * This program creates a calculator which can perform
 * both binary and unary operations.
 * 
 * @author ivan
 *
 */
public class Calculator extends JFrame {

	//===========================Constants================================
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The starting x-axis position of the digit buttons.
	 */
	private static final int DIGITS_X_START = 3;
	
	/**
	 * The starting y-axis position of the digit buttons.
	 */
	private static final int DIGITS_Y_START = 2;
	
	/**
	 * The number of digits per row.
	 */
	private static final int NUMBERS_PER_ROW = 3;
	
	/**
	 * The position of the zero digit.
	 */
	private RCPosition ZERO_POS = new RCPosition(5, 3);
	
	/**
	 * The string value of the number 0.
	 */
	private static final String ZERO_STRING = "0";
	
	/**
	 * The model of the calculator.
	 */
	private CalcModelImpl model;
	
	/**
	 * The stack for pushing the numbers to.
	 */
	private Stack<Double> stack;
	
	//=========================Constructor================================
	
	/**
	 * This constructor initializes the window and the GUI.
	 * 
	 */
	public Calculator() {
		model = new CalcModelImpl();
		stack = new Stack<>();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(200, 200);
		
		initGUI();
	}
	
	//====================Constructor helper methods=======================
	
	/**
	 * This method initializes the GUI.
	 * 
	 */
	private void initGUI() {
		Container c = getContentPane();
		c.setLayout(new CalcLayout());

		CalcDisplay display = new CalcDisplay();
		model.addCalcValueListener(display);
		c.add(display, new RCPosition(1, 1));
		
		addDigitButtons(c);
		
		JCheckBox inv = new JCheckBox("Inv");
		c.add(inv, new RCPosition(5, 7));
		
		addBinaryOperationButtons(inv, c);
		addUnaryOperationButtons(inv,c);
		addOtherButtons(c);
		
		setSize(getPreferredSize());
	}

	/**
	 * This method adds the other buttons which are left
	 * after the binary operator and unary operator buttons 
	 * are added to the container.
	 * 
	 * @param c
	 * 		the container to add the buttons to.
	 */
	private void addOtherButtons(Container c) {
		JButton equalsButton = new JButton("=");
		equalsButton.setPreferredSize(new Dimension(40, 40));
		equalsButton.addActionListener(e -> {
			equalsCommand();
		});
		JButton clr = new JButton("clr");
		clr.addActionListener(e -> {
			model.clear();
		});
		JButton res = new JButton("res");
		res.addActionListener(e -> {
			model.clearAll();
		});
		JButton push = new JButton("push");
		push.addActionListener(e -> {
			stack.push(model.getValue());
		});
		JButton pop = new JButton("pop");
		pop.addActionListener(e -> {
			popStack();
		});
		JButton swap = new JButton("+/-");
		swap.addActionListener(e -> {
			model.swapSign();
		});
		JButton decimalDot = new JButton(".");
		decimalDot.addActionListener(e -> {
			model.insertDecimalPoint();
		});
		c.add(equalsButton, new RCPosition(1, 6));
		c.add(clr, new RCPosition(1, 7));
		c.add(res, new RCPosition(2, 7));
		c.add(push, new RCPosition(3, 7));
		c.add(pop, new RCPosition(4, 7));
		c.add(swap, new RCPosition(5, 4));
		c.add(decimalDot, new RCPosition(5, 5));
	}

	/**
	 * This method adds the unary operation buttons to the specified
	 * container.
	 * 
	 * @param inv
	 * 		the inverted button needed for proper functioning of the
	 * 		unary operations.		
	 * 
	 * @param c
	 * 		the container to put the buttons in.
	 */
	private void addUnaryOperationButtons(JCheckBox inv, Container c) {
		UnaryOperationButton reciprocalButton = 
				new UnaryOperationButton(inv, model, 
						reciprocal(), null, "1/x");
		
		UnaryOperationButton ln = 
				new UnaryOperationButton(inv, model, 
						ln(), powerE(), "ln");
		
		UnaryOperationButton log = 
				new UnaryOperationButton(inv, model, 
						log10(), power10(), "log");
		
		UnaryOperationButton sin = 
				new UnaryOperationButton(inv, model, 
						sin(), asin(), "sin");
		
		UnaryOperationButton cos = 
				new UnaryOperationButton(inv, model, 
						cos(), acos(), "cos");

		
		UnaryOperationButton tan = 
				new UnaryOperationButton(inv, model, 
						tan(), atan(), "tan");

		UnaryOperationButton ctg = 
				new UnaryOperationButton(inv, model, 
						ctg(), actg(), "ctg");
		
		c.add(reciprocalButton, new RCPosition(2, 1));
		c.add(log, new RCPosition(3, 1));
		c.add(ln, new RCPosition(4, 1));
		c.add(sin, new RCPosition(2, 2));
		c.add(cos, new RCPosition(3, 2));
		c.add(tan, new RCPosition(4, 2));
		c.add(ctg, new RCPosition(5, 2));
	}

	/**
	 * This method adds the binary operation buttons to the
	 * specified container.
	 * 
	 * @param c
	 * 		the container to add the buttons to.
	 * 
	 * @param inv
	 * 		the inverted check box indicating whether
	 *		the {@link PowerNButton} should execute normal
	 *		or inverted operation.
	 */
	private void addBinaryOperationButtons(JCheckBox inv, Container c) {
		BinaryOpeartionButton add = 
				new BinaryOpeartionButton(
						"+", model, 
						(a,b) -> a + b 
				);
		
		BinaryOpeartionButton div = 
				new BinaryOpeartionButton(
						"/", model, 
						(a,b) -> a / b
				);
		
		BinaryOpeartionButton mul = 
				new BinaryOpeartionButton(
						"*", model, 
						(a,b) -> a * b
				);
		
		BinaryOpeartionButton sub = 
				new BinaryOpeartionButton(
						"-", model, 
						(a,b) -> a - b
				);
		BinaryOpeartionButton powerN = 
				new PowerNButton(inv, model);
		
		c.add(div, new RCPosition(2, 6));
		c.add(mul, new RCPosition(3, 6));
		c.add(sub, new RCPosition(4, 6));
		c.add(add, new RCPosition(5, 6));
		c.add(powerN, new RCPosition(5, 1));
	}

	/**
	 * This is the method called when the equals
	 * button is pressed.
	 * 
	 */
	private void equalsCommand() {
		if(model.isActiveOperandSet()) {
			double res = model.getPendingBinaryOperation()
					.applyAsDouble(model.getActiveOperand(), model.getValue());
			model.setValue(res);
			model.clearActiveOperand();
		}	
	}

	/**
	 * This is the method called when the pop button is pressed.
	 * 
	 */
	private void popStack() {
		if(stack.isEmpty()) {
			showDialog("The stack is empty.");
		} else {
			model.setValue(stack.pop());
		}
	}

	/**
	 * This method creates the dialog with the specified
	 * message.
	 * 
	 * @param text
	 * 		the text to display in the dialog.
	 */
	private void showDialog(String text) {
		JOptionPane.showMessageDialog(
				this, 
				text, 
				"Warning", 
				JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * This method adds the digit buttons to the calculator.
	 * The digits can be from 0 to 9.
	 * 
	 * @param c
	 * 		container where to put the buttons.
	 */
	private void addDigitButtons(Container c) {
		int digit = 7;
		for(int i = DIGITS_Y_START; i < DIGITS_Y_START + NUMBERS_PER_ROW; i++) {
			for(int j = DIGITS_X_START; j < DIGITS_X_START + NUMBERS_PER_ROW; j++) {
				DigitButton button = new DigitButton(digit, model);
				c.add(button, new RCPosition(i, j));
				digit++;
			}
			digit -= 6;
			if(digit < 0) {
				DigitButton button = new DigitButton(0, model);
				button.setText(String.valueOf(ZERO_STRING));
				c.add(button, ZERO_POS);
			}
		}
	}

	/**
	 * The method called when the program is run.
	 * 
	 * @param args
	 * 		command line arguments.
	 */
	public static void main(String[] args) {
		Calculator calc = new Calculator();
		SwingUtilities.invokeLater(() -> {
			calc.setVisible(true);
		});
	}
}
