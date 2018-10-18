package hr.fer.zemris.java.gui.layouts;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Test;

public class CalcLayoutTest {

	@Test (expected = CalcLayoutException.class)
	public void addComponentIllegalArgumentTest1() {
		JPanel p = new JPanel(new CalcLayout(3)); 
		p.add(new JLabel("x"), new RCPosition(1,2)); 
	}
	
	@Test (expected = CalcLayoutException.class)
	public void addComponentIllegalArgumentTest2() {
		JPanel p = new JPanel(new CalcLayout(3)); 
		p.add(new JLabel("x"), new RCPosition(1,-2)); 
	}
	
	@Test (expected = CalcLayoutException.class)
	public void addComponentIllegalArgumentTest3() {
		JPanel p = new JPanel(new CalcLayout(3)); 
		p.add(new JLabel("x"), new RCPosition(6,1)); 
	}
	
	@Test (expected = CalcLayoutException.class)
	public void addingComponentWhereComponentAlreadyExistsTest() {
		JPanel p = new JPanel(new CalcLayout(3)); 
		p.add(new JLabel("x"), new RCPosition(3,4));
		p.add(new JLabel("y"), new RCPosition(3,2));
		p.add(new JLabel("z"), new RCPosition(3,4));
	}
}
