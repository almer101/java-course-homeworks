package hr.fer.zemris.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ComplexPolynomialTest {

	@Test
	public void orderTest() {
		ComplexPolynomial p = new ComplexPolynomial(
				new Complex(2,3), 
				new Complex(1,0),
				Complex.ZERO,
				Complex.ZERO
		);
		assertEquals(1, p.order()); 
		//2 is expected because the factors next to the
		//z^3 and z^2 are zero so those members do not exist.
		
		p = new ComplexPolynomial(
				new Complex(2,3), 
				new Complex(0,0),
				new Complex(3,2),
				new Complex(0,-1)
		); 
		assertEquals(3, p.order());
		
		p = new ComplexPolynomial(
				Complex.ZERO, 
				Complex.ZERO,
				Complex.ZERO,
				Complex.ZERO
		);
		assertEquals(0, p.order());
	}
	
	@Test
	public void multiplyTest() {
		ComplexPolynomial p = new ComplexPolynomial(
				new Complex(2,3), 
				new Complex(1,0),
				Complex.ZERO,
				Complex.ZERO
		);
		ComplexPolynomial q = new ComplexPolynomial(
				new Complex(2,3),
				new Complex(0,2),
				new Complex(4,4)
		);	
		
		ComplexPolynomial result = p.multiply(q);
		System.out.println(result);
		assertEquals(3, result.order());
		
		p = new ComplexPolynomial(
					new Complex(2,3), 
					new Complex(1,0),
					new Complex(-2, -2),
					Complex.ZERO
		);
		q = new ComplexPolynomial(
					new Complex(2,3),
					new Complex(0,2),
					new Complex(4,4),
					new Complex(-1, -2),
					new Complex(3,4)
		);
		result = p.multiply(q);
		System.out.println(result);
		assertEquals(6, result.order());
	}
	
	@Test
	public void deriveTest() {
		ComplexPolynomial p = new ComplexPolynomial(
				new Complex(2,3), 
				new Complex(1,0),
				new Complex(4,5),
				Complex.ZERO
		);
		
		assertEquals(1, p.derive().order());
		
		p = new ComplexPolynomial(
				Complex.ZERO
		);
		assertEquals(0, p.derive().order());
		
		p = new ComplexPolynomial(
				Complex.ZERO,
				new Complex(2,3)
		);
		assertEquals(0, p.derive().order());
	}
}
