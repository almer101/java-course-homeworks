package hr.fer.zemris.java.raytracer;

import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * This is the program which renders the picture 
 * using more threads for faster and more efficient
 * rendering.
 * 
 * @author ivan
 *
 */
public class RayCasterParallel {

	/**
	 * This is the method called when the program 
	 * is run.
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
	 * This is the calculation job which has to be done.
	 * The job delegates the parts of the job to new 
	 * instances of {@link CalculationJob}.
	 * 
	 * @author ivan
	 *
	 */
	private static class CalculationJob extends RecursiveAction {
		private static final long serialVersionUID = 1L;
		
		/**The position of the eye*/
		private Point3D eye;
		
		/**The origin of the coordinate system*/
		private Point3D view;
		
		/**The viewUp vector*/
		private Point3D viewUp;
		
		/**The horizontal parameter of the coordinate system*/
		private double horizontal;
		
		/**The vertical parameter of the coordinate system*/
		private double vertical;
		
		/**The width of the frame*/
		private int width;
		
		/**The height of the frame*/
		private int height;
		
		/**The minimum x value*/
		private int xMin;
		
		/**The maximum x value*/
		private int xMax;
		
		/**The minimum y value*/
		private int yMin;
		
		/**The maximum y value*/
		private int yMax;
		
		/**The array of the red color values*/
		private short[]red;
		
		/**The values of the green color values.*/
		private short[]green;
		
		/**The values of the blue color values.*/
		private short[]blue;
		
		/**The scene where the objects are located.*/
		private Scene scene;
		
		/**
		 * The threshold for determining the minimum size of
		 * the job for one thread.
		 */
		private static final int threshold = 16;
		
		/**
		 * The constructor which initializes all the properties
		 * of this class
		 * 
		 * @param eye @see {@link #eye}
		 * @param view @see {@link #view}
		 * @param viewUp @see {@link #viewUp}
		 * @param horizontal @see {@link #horizontal}
		 * @param vertical @see {@link #vertical}
		 * @param width @see {@link #width}
		 * @param height @see {@link #height}
		 * @param xMin @see {@link #xMin}
		 * @param xMax @see {@link #xMax}
		 * @param yMin @see {@link #yMin}
		 * @param yMax @see {@link #yMax}
		 * @param red @see {@link #red}
		 * @param green @see {@link #green}
		 * @param blue @see {@link #blue}
		 * @param scene @see {@link #scene}
		 */
		public CalculationJob(Point3D eye, Point3D view, Point3D viewUp,
				double horizontal, double vertical, int width, int height, 
				int xMin, int xMax, int yMin, int yMax, short[]red, short[]green, 
				short[]blue, Scene scene) {
			this.eye = Objects.requireNonNull(eye);
			this.view = Objects.requireNonNull(view);
			this.viewUp = Objects.requireNonNull(viewUp);
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.width = width;
			this.height = height;
			this.xMin = xMin;
			this.xMax = xMax;
			this.yMin = yMin;
			this.yMax = yMax;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.scene = scene;
		}
		
		
		@Override
		protected void compute() {
			if(yMax - yMin + 1 <= threshold) {
				computeDirect();
				return;
			}
			invokeAll(
					new CalculationJob(eye, view, viewUp, horizontal, vertical, 
					width, height, xMin, xMax, yMin, yMin+(yMax-yMin)/2, red, 
							green, blue, scene),
					new CalculationJob(eye, view, viewUp, horizontal, vertical, 
							width, height, xMin, xMax, yMin+(yMax-yMin)/2 + 1, yMax, red, 
							green, blue, scene)
			);
		}

		/**
		 * This method computes directly the color values for each pixel.
		 * Method is called when the y axis part of the job is smaller
		 * than threshold.
		 */
		private void computeDirect() {
			Point3D og = view.sub(eye).normalize();
			Point3D vuv = viewUp.normalize();
			Point3D yAxis = vuv.sub(og.scalarMultiply(og.scalarProduct(vuv))).normalize();
			Point3D xAxis = og.vectorProduct(yAxis).normalize();
			Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal/2))
										.add(yAxis.scalarMultiply(vertical/2));
			short[] rgb = new short[3];
			int offset = yMin * width;
			
			for(int y = yMin; y <= yMax; y++) {
				for(int x = xMin; x <= xMax; x++) {
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
		}
	}
	
	/**
	 * This method returns the producer which makes
	 * calculations needed for rendering the picture.
	 * 
	 * @return
	 * 		the {@link IRayTracerProducer} which performs
	 * 		calculations needed for the rendering of the 
	 * 		picture.
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
				
				Scene scene = RayTracerViewer.createPredefinedScene();
				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new CalculationJob(eye, view, viewUp, horizontal, vertical, 
						width, height, 0, width - 1, 0, height - 1, red, green, blue, scene));
				pool.shutdown();
				
				System.out.println("Calculations are done!"); 
				observer.acceptResult(red, green, blue, requestNo); 
				System.out.println("Informing the observer done!");
			}
		};
	}
}
