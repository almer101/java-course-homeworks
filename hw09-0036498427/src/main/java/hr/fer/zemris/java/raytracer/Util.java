package hr.fer.zemris.java.raytracer;

import static java.lang.Math.pow;

import java.util.List;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;

/**
 * This class provides utility methods used
 * in calculations for the rendering.
 * 
 * @author ivan
 *
 */
public class Util {

	//=================================Constants=========================================
	
	/**
	 * The ambient component of the red light.
	 */
	private static final short AMBIENT_RED_COMPONENT = 15;
	
	/**
	 * The ambient component of the green light.
	 */
	private static final short AMBIENT_GREEN_COMPONENT = 15;
	
	/**
	 * The ambient component of the blue light.
	 */
	private static final short AMBIENT_BLUE_COMPONENT = 15;
	
	/**
	 * The interval used for comparing double numbers.
	 */
	private static final double EPSILON = 1E-04;
	
	//=============================Method implementations================================
	
	/**
	 * This method sets the rbg array to either <code>BLACK</code>
	 * of <code>WHITE</code> color. The white color is for those
	 * parts of scene where the ray from the eye passes through the 
	 * object.
	 * 
	 * @param scene
	 * 		the scene which contains the objects.		
	 * 
	 * @param ray
	 * 		the ray from the eye and the pixel on the screen.
	 * 
	 * @param rgb
	 * 		the array which represents a color which is a 
	 * 		combination of red, green and blue.
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		rgb[0] = 0;
        rgb[1] = 0;
        rgb[2] = 0;
        RayIntersection closest = findClosestIntersection(scene, ray);
        if(closest == null) { 
        	return;
        }
        short[] helper = determineColor(closest, scene, ray);
        rgb[0] = helper[0];
        rgb[1] = helper[1];
        rgb[2] = helper[2];
	}

	/**
	 * This method determines the color for the specified
	 * intersection and returns and rgb array.
	 * 
	 * @param s
	 * 		the intersection whose color has to be determined.
	 * 
	 * @param view
	 * 		the ray from the eye to the intersection.
	 * 
	 * @return
	 * 		the rgb array.
	 */
	private static short[] determineColor(RayIntersection s, Scene scene, Ray view) {
		short rgb[] = new short[] {AMBIENT_RED_COMPONENT, 
									AMBIENT_GREEN_COMPONENT, 
									AMBIENT_BLUE_COMPONENT};
		List<LightSource> sources = scene.getLights();
		int size = sources.size();
		
		for(int i = 0; i < size; i++) {
			LightSource ls = sources.get(i);
			Ray ray = Ray.fromPoints(ls.getPoint(), s.getPoint());
			RayIntersection closest = findClosestIntersection(scene, ray);
			
			if(closest != null && 
					s.getPoint().sub(closest.getPoint()).norm() > EPSILON) {
				continue;
			}
			Point3D normal = s.getNormal();
			Point3D l = normal.difference(ls.getPoint(), s.getPoint()).normalize();
			Point3D r = getReflectedRayDirection(s.getNormal(), ray.direction.negate());
			Point3D v = view.direction.negate();
			
			rgb[0] += (short)(ls.getR()*(s.getKdr()*normal.scalarProduct(l) + 
								s.getKrr()*pow(r.scalarProduct(v), s.getKrn())));
			rgb[1] += (short)(ls.getG()*(s.getKdg()*normal.scalarProduct(l) + 
								s.getKrg()*pow(r.scalarProduct(v), s.getKrn())));
			rgb[2] += (short)(ls.getB()*(s.getKdb()*normal.scalarProduct(l) + 
								s.getKrb()*pow(r.scalarProduct(v), s.getKrn())));
		}
		return rgb;
	}

	/**
	 * This method calculates and returns the direction of the
	 * reflected vector r.
	 * 
	 * @param normal
	 * 		the normal to the surface.
	 * 
	 * @param l
	 * 		the vector direction vector from the point of
	 * 		intersection to the light source.
	 * 
	 * @return
	 * 		the direction unit vector of the reflected ray. 
	 */
	private static Point3D getReflectedRayDirection(Point3D normal, Point3D l) {
		return normal.scalarMultiply(2)
					.scalarMultiply(l.scalarProduct(normal)).sub(l)
					.normalize();
	}

	/**
	 * This method finds and returns the closest intersection of the ray
	 * with the object in the scene. The ray is given as a parameter.
	 * 
	 * @param scene
	 * 		the scene which contains objects.
	 * 
	 * @param ray
	 * 		the ray for which the closest intersection is to be calculated.
	 * 		
	 * @return
	 * 		the closes ray intersection with the object in the scene and
	 * 		<code>null</code> otherwise.
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		List<GraphicalObject> objects = scene.getObjects();
		RayIntersection closest = objects.get(0).findClosestRayIntersection(ray);
		int size = objects.size();
		
		for(int i = 1; i < size; i++) {
			RayIntersection intersection = objects.get(i).findClosestRayIntersection(ray);
			if(closest == null) {
				closest = intersection;
				
			} else if(intersection != null && 
					(intersection.getDistance() < closest.getDistance())){
				closest = intersection;
			}
			
		}
		return closest;
	}
		
	protected static void printInfo(int x, int y, Point3D screenPoint, Ray ray, short[] rgb) {
		System.out.format("Informacije za tocku x = %d, y = %d\n", x, y);
		System.out.format("Screen-point: (%f, %f, %f)\n", 
				screenPoint.x, screenPoint.y, screenPoint.z);
		System.out.format("Ray: start=(%f, %f, %f), direction=(%f, %f, %f)\n", ray.start.x, 
				ray.start.y, ray.start.z, ray.direction.x, ray.direction.y, ray.direction.z);
		System.out.format("RGB = [%d, %d, %d]\n\n", rgb[0], rgb[1], rgb[2]);
		
	}

}
