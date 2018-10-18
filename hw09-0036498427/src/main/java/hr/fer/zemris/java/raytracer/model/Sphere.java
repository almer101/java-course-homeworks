package hr.fer.zemris.java.raytracer.model;

import java.util.Objects;
import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;

import static java.lang.Math.sqrt;

/**
 * The implementation of the model of the sphere object in
 * the scene.
 * 
 * @author ivan
 *
 */
public class Sphere extends GraphicalObject {

	//================================Properties=====================================
	
	/**
	 * The center of the sphere.
	 */
	protected Point3D center;
	
	/**
	 * The radius of the sphere
	 */
	protected double radius;
	
	/**
	 * The diffusion coefficient of the red light.
	 */
	protected double kdr;
	
	/**
	 * The diffusion coefficient of the green light.
	 */
	protected double kdg;
	
	/**
	 * The diffusion coefficient of the blue light.
	 */
	protected double kdb;
	
	/**
	 * The reflection coefficient of the red light.
	 */
	protected double krr;
	
	/**
	 * The reflection coefficient of the green light.
	 */
	protected double krg;
	
	/**
	 * The reflection coefficient of the blue light.
	 */
	protected double krb;
	
	/**
	 * Coefficient describing the roughness of the surface.
	 */
	protected double krn;
	
	//=================================Constructor====================================
	
	/**
	 * Initializes all components of the sphere.
	 * 
	 * @param center the center of the sphere
	 * @param radius the radius of the sphere
	 * @param kdr the diffusion coefficient of the red component
	 * @param kdg the diffusion coefficient of the green component
	 * @param kdb the diffusion coefficient of the blue component
	 * @param krr the reflection coefficient of the red component
	 * @param krg the reflection coefficient of the green component
	 * @param krb the reflection coefficient of the blue component
	 * @param krn the coefficient describing the roughness of the surface.
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, 
			double kdb, double krr, double krg, double krb, double krn) {
		
		this.center = Objects.requireNonNull(center);
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}
	
	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D centerToSource = ray.start.sub(center);
		double b = ray.direction.scalarMultiply(2)
								.scalarProduct(centerToSource);
		double c = centerToSource.scalarProduct(centerToSource) - radius*radius;
		double determinant = b*b - 4*c;
		if(determinant < 0) return null;
		
		double lambda1 = -b/2 + sqrt(b*b - 4*c)/2; 
		double lambda2 = -b/2 - sqrt(b*b - 4*c)/2;
		if(determinant == 0) {
			// lambda1 == labda2
			if(lambda1 < 0) return null;
			Point3D point = getPoint(lambda1, lambda2, ray);
			return new SphereRayIntersection(point, lambda1, true, this);
		}
		if(lambda1 < 0 && lambda2 < 0) return null;
		
		Point3D point = getPoint(lambda1, lambda2, ray);
		
		if(lambda1 > 0 && lambda2 > 0) {
			return lambda1 < lambda2 ? new SphereRayIntersection(point, lambda1, true, this) :
										new SphereRayIntersection(point, lambda2, true, this);
		} else {	
			return lambda1 > lambda2 ? new SphereRayIntersection(point, lambda1, false, this) :
				new SphereRayIntersection(point, lambda2, false, this);
		}
	}

	/**
	 * This method returns the point of intersection based
	 * on the lambda1 and lambda2 parameters. The point
	 * which is behind the 
	 * 
	 * @param lambda1
	 * 		the first result of the quadratic equation.
	 * 
	 * @param lambda2
	 * 		the second result of the quadratic equation.
	 * 
	 * @param ray
	 * 		the ray for which intersections are searched.
	 * 
	 * @return
	 * 		the point of intersection.
	 */
	private Point3D getPoint(double lambda1, double lambda2, Ray ray) {
		if(lambda1 > 0 && lambda2 > 0) {
			lambda1 = lambda1 < lambda2 ? lambda1 : lambda2;
			return ray.start.add(ray.direction.scalarMultiply(lambda1));
			
		} else {
			//one root is greater than 0 and one is less than 0.
			lambda1 = lambda1 < lambda2 ? lambda2 : lambda1;
			return ray.start.add(ray.direction.scalarMultiply(lambda1));
		}
	}
}
