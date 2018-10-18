package hr.fer.zemris.java.hw02;

import static org.junit.Assert.assertEquals;
import static java.lang.Math.PI;
import static java.lang.Math.sqrt;
import static java.lang.Math.atan;

import org.junit.Test;

import hr.fer.zemris.java.hw02.ComplexNumber;

public class ComplexNumberTest {

	private static final double EPSILON = 1E-06;
	@Test
	public void parseAndImaginaryRealTest() {
		ComplexNumber z = ComplexNumber.parse("- 2 + 3i");
		assertEquals(-2, z.getReal(), EPSILON);
		ComplexNumber z1 = ComplexNumber.parse("2");
		assertEquals(0, z1.getImaginary(), EPSILON);
		ComplexNumber z2 = ComplexNumber.parse("-3i + 5.7");
		assertEquals(-3, z2.getImaginary(), EPSILON);
		assertEquals(5.7, z2.getReal(), EPSILON);
		ComplexNumber z3 = ComplexNumber.parse("-i");
		assertEquals(0, z3.getReal(), EPSILON);
		assertEquals(-1, z3.getImaginary(), EPSILON);
	}
	
	@Test
	public void fromMagnitudeAndAngle() {
		ComplexNumber z = ComplexNumber.fromMagnitudeAndAngle(0, 5.7);
		assertEquals(0, z.getReal(), EPSILON);
		assertEquals(0, z.getImaginary(), EPSILON);
		ComplexNumber z1 = ComplexNumber.fromMagnitudeAndAngle(1, PI/3);
		assertEquals(0.5, z1.getReal(), EPSILON);
		assertEquals(sqrt(3)/2 , z1.getImaginary(), EPSILON);
	}
	
	@Test
	public void fromRealFromImaginaryTest() {
		ComplexNumber z1 = ComplexNumber.fromImaginary(0.56);
		assertEquals(0, z1.getReal(), EPSILON);
		assertEquals(0.56, z1.getImaginary(), EPSILON);
		ComplexNumber z2 = ComplexNumber.fromReal(3.567);
		assertEquals(3.567, z2.getReal(), EPSILON);
		assertEquals(0, z2.getImaginary(), EPSILON);
	}
	
	@Test 
	public void getMagnitudeGetAngleTest() {
		ComplexNumber z1 = ComplexNumber.parse("-5i+4");
		assertEquals(sqrt(41), z1.getMagnitude(), EPSILON);
		assertEquals(atan(-5/(double)4) + 2*PI, z1.getAngle(), EPSILON);
		ComplexNumber z2 = ComplexNumber.parse("4i");
		assertEquals(PI/2, z2.getAngle(), EPSILON);
		ComplexNumber z3 = ComplexNumber.parse("-i");
		assertEquals(3*PI/2, z3.getAngle(), EPSILON);
		ComplexNumber z4 = ComplexNumber.parse("0");
		assertEquals(0, z4.getMagnitude(), EPSILON);
		assertEquals(0, z4.getAngle(), EPSILON);
	}
	
	@Test
	public void addAndSubTest() {
		ComplexNumber z1 = ComplexNumber.parse(" 2.5 - 5i");
		ComplexNumber z2 = ComplexNumber.parse("-2.5 + i");
		ComplexNumber z3 = z1.add(z2);
		assertEquals(0, z3.getReal(), EPSILON);
		assertEquals(-4, z3.getImaginary(), EPSILON);
		z3 = z3.sub(z1); //this should be equal to z2
		assertEquals(-2.5, z3.getReal(), EPSILON);
		
	}
	
	@Test
	public void mulAndDivTest() {
		ComplexNumber z1 = ComplexNumber.parse("3.5i -5");
		ComplexNumber z2 = ComplexNumber.parse("-2i");
		ComplexNumber z3 = z1.div(z2);
		assertEquals(-1.75, z3.getReal(), EPSILON);
		assertEquals(-2.5, z3.getImaginary(), EPSILON);
	}
	
	@Test
	public void powTest() {
		ComplexNumber z1 = ComplexNumber.parse("-3 + 6i");
		ComplexNumber z2 = ComplexNumber.parse("i");
		z1 = z1.pow(5);
		z2 = z2.pow(3);
		assertEquals(-9963, z1.getReal(), EPSILON);
		assertEquals(-9234, z1.getImaginary(), EPSILON);
		assertEquals(-1, z2.getImaginary(), EPSILON);
		ComplexNumber z3 = ComplexNumber.parse("0");
		z3 = z3.pow(10);
		assertEquals(0, z3.getMagnitude(), EPSILON);
		assertEquals(0, z3.getImaginary(), EPSILON);
		assertEquals(0, z3.getReal(), EPSILON);
	}
	
	@Test
	public void rootTest() {
		ComplexNumber z1 = ComplexNumber.parse("2i");
		ComplexNumber results1[] = z1.root(2);
		assertEquals(1, results1[0].getReal(), EPSILON);
		assertEquals(1, results1[0].getImaginary(), EPSILON);
		assertEquals(-1, results1[1].getReal(), EPSILON);
		assertEquals(-1, results1[1].getImaginary(), EPSILON);
		ComplexNumber z2 = ComplexNumber.fromMagnitudeAndAngle(1, 0);
		ComplexNumber results2[] = z2.root(3); 
		// the roots are: 1, -0.5 + (sqrt(3)/2)i, -0.5 - (sqrt(3)/2)i
		assertEquals(-0.5, results2[1].getReal(), EPSILON);
		assertEquals(-0.5, results2[2].getReal(), EPSILON);
	}
	
}
