package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * This is the program which renders the 3D picture of
 * the scene and the objects within.
 * 
 * @author ivan
 *
 */
public class RayCaster {

	/**
	 * This is the method called when the program is run.
	 * 
	 * @param args
	 * 		command line arguments.
	 */
	public static void main(String[] args) { 
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10,0,0),
			new Point3D(0,0,0),
			new Point3D(0,0,10),
			20, 20);
	}
	
	/**
	 * This method creates a new {@link IRayTracerProducer} and 
	 * returns it. The returned producer knows how to draw
	 * a picture.
	 * 
	 * @return
	 * 		the new {@link IRayTracerProducer}.
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
							double horizontal, double vertical, int width, int height, 
							long requestNo, IRayTracerResultObserver observer) {
				
				System.out.println("Starting the calculations."); 
				short[] red = new short[width*height]; 
				short[] green = new short[width*height]; 
				short[] blue = new short[width*height];
				
				Point3D og = view.sub(eye).normalize();
				Point3D vuv = viewUp.normalize();
				Point3D yAxis = vuv.sub(og.scalarMultiply(og.scalarProduct(vuv))).normalize();
				Point3D xAxis = og.vectorProduct(yAxis).normalize();
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal/2))
											.add(yAxis.scalarMultiply(vertical/2));
				Scene scene = RayTracerViewer.createPredefinedScene();
				short[] rgb = new short[3];
				int offset = 0;
				for(int y = 0; y < height; y++) {
					for(int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner
								.add(xAxis.scalarMultiply((double)x/(width - 1) * horizontal))
								.sub(yAxis.scalarMultiply((double)y/(height - 1) * vertical));
						Ray ray = Ray.fromPoints(eye, screenPoint);
						Util.tracer(scene, ray, rgb);
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++; 
					}
				}
				System.out.println("Calculations are done!"); 
				observer.acceptResult(red, green, blue, requestNo); 
				System.out.println("Informed the observer!");
			}
		};
	}
}
