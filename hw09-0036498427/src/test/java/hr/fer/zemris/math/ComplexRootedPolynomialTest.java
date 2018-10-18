package hr.fer.zemris.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ComplexRootedPolynomialTest {

	@Test
	public void transformationTest() {
		ComplexRootedPolynomial p = new ComplexRootedPolynomial(
				new Complex(2,0), 
				new Complex(-1,0),
				new Complex(3,0)
		);
		//(z-2)*(z+1)*(z-3) ----> 	z^3 - 4*z^2 + z + 6
		ComplexPolynomial q = p.toComplexPolynom();
		assertEquals(3, q.order());
		System.out.println(q); //expected: z^3 - 4*z^2 + z + 6
		
		p = new ComplexRootedPolynomial(
				new Complex(-1,0), 
				new Complex(1,0),
				new Complex(0,-1),
				new Complex(0,1)
		);
		//(z+1)*(z-1)*(z+i)*(z-i)	-->		z^4 - 1
		q = p.toComplexPolynom();
		assertEquals(q.order(), 4);
		System.out.println(q); //expected: z^4 + 0*z^3 + 0*z^2 + 0*z - 1
		
		ComplexPolynomial derived = q.derive();
		assertEquals(derived.order(), 3);
		System.out.println(q.derive()); //expected: 4*z^3 + 0*z^2 + 0*z^1 + 0
	}
	
	@Test
	public void applyTestRealRoots() {
		ComplexRootedPolynomial p = new ComplexRootedPolynomial(
				new Complex(2,0), 
				new Complex(-1,0),
				new Complex(3,0)
		);
		//(z-2)*(z+1)*(z-3) 
		
		Complex result = p.apply(Complex.ONE_NEG);
		assertEquals(Complex.ZERO, result);
		
		result = p.apply(new Complex(3,0));
		assertEquals(Complex.ZERO, result);
		
		result = p.apply(new Complex(2,2));
		assertEquals(new Complex(-8, -14), result);
	}
	
	@Test
	public void applyTestComplexRoots() {
		ComplexRootedPolynomial p = new ComplexRootedPolynomial(
				new Complex(2,1), 
				new Complex(-1,3),
				new Complex(-1,0),
				new Complex(0, 1)
		);
		//(z-(2+i))*(z-(-1+3i))*(z+1))*(z-i)
		
		Complex result = p.apply(new Complex(-1, 3));
		assertEquals(Complex.ZERO, result);
		
		result = p.apply(new Complex(2,-1));
		assertEquals(new Complex(-80, 40), result);
	}
	
	@Test
	public void indexOfTheClosestRootTest() {
		ComplexRootedPolynomial p = new ComplexRootedPolynomial(
				new Complex(2,1), 
				new Complex(-1,3),
				new Complex(-1,0),
				new Complex(0, 1)
		);
		int index = p.indexOfClosestRootFor(new Complex(2.00001, 0.9999999), 1E-03);
		assertEquals(0, index);
		
		index = p.indexOfClosestRootFor(new Complex(3, 0), 1E-03);
		assertEquals(-1, index);
		
		index = p.indexOfClosestRootFor(new Complex(0.00002, 1.0003), 1E-03);
		assertEquals(3, index);
		
		p = new ComplexRootedPolynomial(
				new Complex(-5,1), 
				new Complex(-3,3),
				new Complex(-1,0),
				new Complex(0, 1)
		);
		index = p.indexOfClosestRootFor(new Complex(-4.99, 1), 1E-03);
		assertEquals(-1, index);
		
		index = p.indexOfClosestRootFor(new Complex(-4.99999, 1), 1E-03);
		assertEquals(0, index);
		
		index = p.indexOfClosestRootFor(new Complex(-2.99999, 3.00009), 1E-03);
		assertEquals(1, index);
	}
}
