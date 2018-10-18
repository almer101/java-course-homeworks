package hr.fer.zemris.java.math;

import static org.junit.Assert.assertTrue;

import java.nio.channels.Pipe;

import static java.lang.Math.abs;
import static java.lang.Math.PI;
import static java.lang.Math.sqrt;

import org.junit.Test;

public class Vector2DTest {

	public static final double EPSILON = 1E-06;
	
	@Test
	public void getTest() {
		Vector2D v = new Vector2D(3, -4);
		assertTrue(abs(v.getX() - 3) < EPSILON);
		assertTrue(abs(v.getY() - (-4)) < EPSILON);
		
		Vector2D v2 = new Vector2D(3.4567, -2.999);
		assertTrue(abs(v2.getX() - 3.4567) < EPSILON);
		assertTrue(abs(v2.getY() - (-2.999)) < EPSILON);
	}
	
	@Test
	public void copyTest() {
		Vector2D v1 = new Vector2D(2, 0);
		Vector2D v2 = v1.copy();
		assertTrue(abs(v1.getX() - v2.getX()) < EPSILON);
		assertTrue(abs(v1.getY() - v2.getY()) < EPSILON);
	}
	
	@Test
	public void translationTest() {
		Vector2D v1 = new Vector2D(-2.5, 3.88);
		Vector2D v2 = v1.copy();
		
		assertTrue(abs(v2.getX() - v1.getX()) < EPSILON);
		assertTrue(abs(v2.getY() - v1.getY()) < EPSILON);
		
		Vector2D offset = new Vector2D(3, 4.5);
		v1.translate(offset);
		
		assertTrue(abs(v1.getX() - 0.5) < EPSILON);
		assertTrue(abs(v1.getY() - 8.38) < EPSILON);
		
		//now we translate the vector for the same amount but in the opposite
		//direction so it should be the same as the vector v2
		offset = new Vector2D(-3, -4.5);
		v1.translate(offset);
		
		assertTrue(abs(v1.getX() - v2.getX()) < EPSILON);
		assertTrue(abs(v1.getY() - v2.getY()) < EPSILON);
	
		v1 = new Vector2D(2.0, 3.0);
		v2 = v1.copy();
		offset = new Vector2D(2, -3);
		
		v2.translate(offset);
		Vector2D v3 = v1.translated(offset);
		
		assertTrue(abs(v2.getX() - v3.getX()) < EPSILON);
		assertTrue(abs(v2.getY() - v3.getY()) < EPSILON);
		
		v3.translate(new Vector2D(0, 0));
		assertTrue(abs(v2.getX() - v3.getX()) < EPSILON);
		assertTrue(abs(v2.getX() - v3.getX()) < EPSILON);
	}
	
	@Test
	public void rotationTest() {
		Vector2D v1 = new Vector2D(4, 0);
		
		v1.rotate(PI);
		assertTrue(abs(v1.getX() - (-4)) < EPSILON);
		assertTrue(abs(v1.getY() - 0) < EPSILON);
		
		Vector2D v2 = new Vector2D(-2, 2);
		v2.rotate(3*PI/4);
		assertTrue(abs(v2.getX() - 0) < EPSILON);
		assertTrue(abs(v2.getY() - (-sqrt(8))) < EPSILON);
		
		Vector2D v3 = new Vector2D(0, 0);
		v3.rotate(PI/2);
		assertTrue(abs(v3.getX() - 0) < EPSILON);
		assertTrue(abs(v3.getY() - 0) < EPSILON);
		
		v2 = new Vector2D(-sqrt(8), 0);
		v3 = v2.rotated(3*PI/4);
		assertTrue(abs(v3.getX() - 2) < EPSILON);
		assertTrue(abs(v3.getY() - (-2)) < EPSILON);
		
		v2 = new Vector2D(-3.456, 2.445);
		v2.rotate(4*PI);
		assertTrue(abs(v2.getX() - (-3.456)) < EPSILON);
		assertTrue(abs(v2.getY() - 2.445) < EPSILON);
		
		v2 = new Vector2D(-2, 2);
		v2.rotate(-PI/4);
		assertTrue(abs(v2.getX() - 0) < EPSILON);
		assertTrue(abs(v2.getY() - sqrt(8)) < EPSILON);
	}
	
	@Test
	public void scalingTest() {
		Vector2D v1 = new Vector2D(3, 4);
		
		//neutral element for scaling is 1.
		v1.scale(1);
		assertTrue(abs(v1.getX() - 3) < EPSILON);
		assertTrue(abs(v1.getY() - 4) < EPSILON);
		
		Vector2D v2 = new Vector2D(5.6, 12.2);
		v2.scale(-2.5);
		assertTrue(abs(v2.getX() - (-14)) < EPSILON);
		assertTrue(abs(v2.getY() - (-30.5)) < EPSILON);
		
		Vector2D v3 = new  Vector2D(-45, -45);
		v3.scale(-0.2);
		assertTrue(abs(v3.getX() - 9) < EPSILON);
		assertTrue(abs(v3.getY() - 9) < EPSILON);
		
		
	}
}
