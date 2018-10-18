package hr.fer.zemris.java.raytracer.model;

import java.util.Objects;

import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.RayIntersection;

/**
 * This class represents an intersection of the 
 * sphere and a ray of light.
 * 
 * @author ivan
 *
 */
public class SphereRayIntersection extends RayIntersection {

	//=============================Properties===============================
	
	/**
	 * The sphere intersection is on.
	 */
	private Sphere sphere;
	
	//============================Constructors==============================
	
	/**
	 * The constructor which gets initial values of three
	 * parameters.
	 * 
	 * @param point
	 * 		the point of intersection.
	 * 
	 * @param distance
	 * 		the distance from the start of the ray to 
	 * 		the point of intersection.
	 * 
	 * @param outer
	 * 		the indicator whether the intersection is inner or
	 * 		outer. If <code>true</code> the intersection is considered 
	 * 		outer, if <code>false</code> the intersection is 
	 * 		considered inner.
	 */
	protected SphereRayIntersection(Point3D point, double distance, 
			boolean outer) {
		super(point, distance, outer);
	}
	
	/**
	 * A constructor gets all relevant parameters for an intersection.
	 * And one additional which is the sphere the intersection is on.
	 * 
	 * @param point
	 * 		point where the intersection occurred.		
	 * 
	 * @param distance
	 * 		the distance from the start of the ray to the
	 * 		point of intersection. 
	 * 
	 * @param outer
	 * 		the indicator whether the intersection is outer or inner.
	 * 
	 * @param sphere
	 * 		the sphere, the intersection is on.
	 */
	public SphereRayIntersection(Point3D point, double distance, 
			boolean outer, Sphere sphere) {
		this(point, distance, outer);
		this.sphere = Objects.requireNonNull(sphere);
	}

	@Override
	public Point3D getNormal() {
		return getPoint().sub(sphere.center).modifyNormalize();
	}

	@Override
	public double getKdr() {
		return sphere.kdr;
	}

	@Override
	public double getKdg() {
		return sphere.kdg;
	}

	@Override
	public double getKdb() {
		return sphere.kdb;
	}

	@Override
	public double getKrr() {
		return sphere.krr;
	}

	@Override
	public double getKrg() {
		return sphere.krg;
	}

	@Override
	public double getKrb() {
		return sphere.krb;
	}

	@Override
	public double getKrn() {
		return sphere.krn;
	}
}
