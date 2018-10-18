package hr.fer.zemris.math;

import static org.junit.Assert.assertEquals;

import java.util.Vector;

import static java.lang.Math.sqrt;

import org.junit.Test;

public class Vector3Test {

	private static final double EPSILON = 1E-06;
	
	@Test
	public void normTest() {
		Vector3 v = new Vector3(4, 0, 0);
		assertEquals(4.0, v.norm(), EPSILON);
		
		v = new Vector3(2, -2, 2);
		assertEquals(sqrt(12), v.norm(), EPSILON);
		
		v = new Vector3(-3, 0, 4);
		assertEquals(5, v.norm(), EPSILON);
	}
	
	@Test
	public void normalizedTest() {
		Vector3 v = new Vector3(100, 2, -54);
		v = v.normalized();
		assertEquals(1, v.norm(), EPSILON);
		
		v = new Vector3(3.4, -5.6, 10.8);
		assertEquals(1, v.normalized().norm(), EPSILON);
		
		v = new Vector3(0, 0, 0);
		assertEquals(0, v.normalized().norm(), EPSILON);
		
		v = new Vector3(0.5, 0.5, 0.3);
		assertEquals(1, v.normalized().norm(), EPSILON);
	}
	
	@Test
	public void addTest() {
		Vector3 v1 = new Vector3(100, 2, -54);
		Vector3 v2 = new Vector3(-100, -2, 54);
		assertEquals(new Vector3(0, 0, 0), v1.add(v2));
		
		v1 = new Vector3(4.5, -3.4, 4);
		v2 = new Vector3(2, 2, 2);
		assertEquals(new Vector3(6.5, -1.4, 6), v1.add(v2));
		
		v1 = new Vector3(0, -3, 4);
		v2 = new Vector3(-5, 3, 3.5);
		assertEquals(new Vector3(-5, 0, 7.5), v1.add(v2));
	}
	
	@Test
	public void subTest() {
		Vector3 v1 = new Vector3(100, 2, -54);
		Vector3 v2 = new Vector3(100, 2, -54);
		assertEquals(new Vector3(0, 0, 0), v1.sub(v2));
		
		v1 = new Vector3(4.5, -3.4, 4);
		v2 = new Vector3(2, 2, 2);
		assertEquals(new Vector3(2.5, -5.4, 2), v1.sub(v2));
		
		v1 = new Vector3(0.05, 5.6, 10);
		v2 = new Vector3(0.4, 3, -20);
		assertEquals(new Vector3(0.35, -2.6, -30), v2.sub(v1));
	}
	
	@Test
	public void dotTest() {
		Vector3 v1 = new Vector3(1, 1, 0);
		Vector3 v2 = new Vector3(0, 0, 4);
		assertEquals(0, v1.dot(v2), EPSILON);
		
		v1 = new Vector3(2, 0, 4);
		v2 = new Vector3(2, 3, 2);
		assertEquals(12, v1.dot(v2), EPSILON);
		
		v1 = new Vector3(2, -2, 2);
		v2 = new Vector3(2, 2, -2);
		assertEquals(-4, v1.dot(v2), EPSILON);
	}
	
	@Test
	public void crossTest() {
		Vector3 v1 = new Vector3(1, 0, 0);
		Vector3 v2 = new Vector3(0, 1, 0);
		assertEquals(new Vector3(0, 0, 1), v1.cross(v2));
		
		v1 = new Vector3(1, 0, 0);
		v2 = new Vector3(0, 0, -1);
		assertEquals(new Vector3(0, 1, 0), v1.cross(v2));
		
		v1 = new Vector3(2, 0, 2);
		v2 = new Vector3(0, 1, 0);
		assertEquals(new Vector3(-2, 0, 2), v1.cross(v2));
	}
	
	@Test
	public void scaleTest() {
		Vector3 v = new Vector3(1, 0, 0);
		assertEquals(new Vector3(3.5, 0, 0), v.scale(3.5));
		
		v = new Vector3(-4, -2, -1);
		assertEquals(new Vector3(8, 4, 2), v.scale(-2));
		
		v = new Vector3(2.5, -0.5, 1.5);
		assertEquals(new Vector3(10, -2, 6), v.scale(4.0));
	}
	
	@Test
	public void cosAngle() {
		Vector3 v1 = new Vector3(1, 0, 0);
		Vector3 v2 = new Vector3(0, 1, 0);
		assertEquals(0, v1.cosAngle(v2), EPSILON);
		
		v2 = new Vector3(4, 0, 0);
		v1 = new Vector3(2.4, 0, 2.4);
		assertEquals(sqrt(2)/2, v1.cosAngle(v2), EPSILON);
		
		v1 = new Vector3(0, 0, 0);
		v2 = new Vector3(1, 3, 5.6);
		assertEquals(0, v1.cosAngle(v2), EPSILON);
	}
}
