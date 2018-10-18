package hr.fer.zemris.math;

import static org.junit.Assert.assertEquals;

import java.util.List;

import static java.lang.Math.sqrt;
import org.junit.Test;

public class ComplexTest {

	private static final double EPSILON = 1E-06;
	
	@Test
	public void moduleTest() {
		Complex z = new Complex();
		assertEquals(0, z.module(), EPSILON);
		
		z = new Complex(3, -2);
		assertEquals(sqrt(13), z.module(), EPSILON);
		
		z = new Complex(0.5, 0.5);
		assertEquals(sqrt(2)/2, z.module(), EPSILON);
	}
	
	@Test
	public void multiplyTest() {
		Complex z1 = new Complex(1,0);
		Complex z2 = new Complex(-sqrt(2)/2,sqrt(2)/2);
		assertEquals(1, z1.multiply(z2).module(), EPSILON);
		assertEquals(z2, z1.multiply(z2));
		
		z1 = new Complex(1,1);
		z2 = new Complex(-1, 1);
		assertEquals(new Complex(-2,0), z1.multiply(z2));

		z1 = new Complex(0,0);
		z2 = new Complex(-1, 1);
		assertEquals(new Complex(0,0), z1.multiply(z2));
	}
	
	@Test
	public void divide() {
		Complex z1 = new Complex(1,0);
		Complex z2 = new Complex(-1, 1);
		assertEquals(new Complex(-0.5,-0.5), z1.divide(z2));
		
		z1 = new Complex(1,-1);
		z2 = new Complex(-1, 1);
		assertEquals(new Complex(-1,0), z1.divide(z2));
	}
	
	@Test
	public void addTest() {
		Complex z1 = new Complex(2.4, -3.6);
		Complex z2 = new Complex(-3, 2);
		assertEquals(new Complex(-0.6, -1.6), z1.add(z2));
		
		z1 = new Complex(3.9,-0.006);
		z2 = new Complex(2.3, 0.006);
		assertEquals(new Complex(6.2,0), z1.add(z2));
	}
	
	@Test
	public void subTest() {
		Complex z1 = new Complex(5,4);
		Complex z2 = new Complex(5,4);	
		assertEquals(Complex.ZERO, z1.sub(z2));
		
		z1 = new Complex(3.9,-0.006);
		z2 = new Complex(2.3, 0.006);
		assertEquals(new Complex(1.6,-0.012), z1.sub(z2));
	}
	
	@Test
	public void negateTest() {
		Complex z = new Complex(3.2, 2.5);
		assertEquals(new Complex(-3.2, -2.5), z.negate());
		
		z = Complex.ZERO;
		assertEquals(Complex.ZERO, z.negate());
	}
	
	@Test
	public void powerTest() {
		Complex z = new Complex(2, 0);
		assertEquals(new Complex(16,0), z.power(4));
		
		z = new Complex(3,-3);
		assertEquals(new Complex(-18*18, 0), z.power(4));
		
		z = new Complex(-1, -1);
		assertEquals(new Complex(0,2), z.power(2));
		
	}
	
	@Test
	public void rootTest() {
		Complex z = new Complex(2, 0);
		List<Complex> roots = z.root(4);
		assertEquals(new Complex(sqrt(sqrt(2)), 0), roots.get(0));
		assertEquals(new Complex(0, sqrt(sqrt(2))), roots.get(1));
		assertEquals(new Complex(-sqrt(sqrt(2)), 0), roots.get(2));
		assertEquals(new Complex(0, -sqrt(sqrt(2))), roots.get(3));
		
		z = new Complex(0, 1);
		roots = z.root(3);
		assertEquals(new Complex(sqrt(3)/2, 1.0/2), roots.get(0));
		assertEquals(new Complex(-sqrt(3)/2, 1.0/2), roots.get(1));
		assertEquals(new Complex(0, -1), roots.get(2));
	}
}


