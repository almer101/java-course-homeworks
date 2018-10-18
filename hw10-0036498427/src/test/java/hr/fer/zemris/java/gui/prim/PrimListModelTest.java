package hr.fer.zemris.java.gui.prim;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PrimListModelTest {

	@Test
	public void nextSizeTest() {
		PrimListModel model = new PrimListModel();
		assertEquals(1, model.getSize());
		
		for(int i = 0; i < 12; i++) {
			model.next();
		}
		assertEquals(13, model.getSize());
		
		model.next();
		model.next();
		assertEquals(15, model.getSize());
	}
	
	@Test
	public void nextTestSmallNumbers() {
		PrimListModel model = new PrimListModel();
		assertEquals(Integer.valueOf(1), (Integer)model.getElementAt(0));
		
		for(int i = 0; i < 50; i++) {
			model.next();
		}
		
		assertEquals(Integer.valueOf(7), (Integer)model.getElementAt(4));
		assertEquals(Integer.valueOf(5), (Integer)model.getElementAt(3));
		
		assertEquals(Integer.valueOf(23), (Integer)model.getElementAt(9));
		assertEquals(Integer.valueOf(41), (Integer)model.getElementAt(13));
		
		assertEquals(Integer.valueOf(229), model.getElementAt(50));
	}
	
	@Test
	public void nextTestBigNumbers() {
		PrimListModel model = new PrimListModel();
		
		for(int i = 0; i < 10000; i++) {
			model.next();
		}
		
		assertEquals(Integer.valueOf(104_729), model.getElementAt(10_000));
		
		for(int i = 0; i < 20000; i++) {
			model.next();
		}
		
		assertEquals(Integer.valueOf(350_377), model.getElementAt(30_000));
		assertEquals(Integer.valueOf(224_737), model.getElementAt(20_000));
	}
}
