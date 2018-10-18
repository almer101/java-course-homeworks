package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * This is the producer which is used to produce the data
 * needed for drawing the picture.
 * 
 * @author ivan
 *
 */
public class MyProducer implements IFractalProducer {
	
	//================================Properties=======================================
	
	/**
	 * The pool of threads.
	 */
	private ExecutorService pool;
	
	/**
	 * The polynomial for which the picture has to be drawn.
	 */
	private ComplexRootedPolynomial polynom;

	//===============================Constructor=======================================
	
	/**
	 * This constructor gets the polynomial for which the 
	 * picture has to be drawn.
	 * 
	 * @param polynom
	 * 		the polynomial for which the picture has to be drawn.
	 */
	public MyProducer(ComplexRootedPolynomial polynom) {
		pool = Executors.newFixedThreadPool(
				Runtime.getRuntime().availableProcessors(),
				new MyThreadFactory()
		);
		this.polynom = polynom;
	}
	
	//==============================Thread factory=====================================
	
	/**
	 * This is my implementation of the thread factory.
	 * The factory produces threads which have daemon flag
	 * set to <code>true</code>
	 * 
	 * @author ivan
	 *
	 */
	private static class MyThreadFactory implements ThreadFactory {

		@Override
		public Thread newThread(Runnable r) {
			Thread worker = new Thread(r);
			worker.setDaemon(true);
			return worker;
		}
	}
	
	//===========================Method implementations=================================
	
	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax,
			int width, int height, long requestNo, IFractalResultObserver observer) {
		
		System.out.println("Starting the calculation...");
		short[] data = new short[width * height];
		int m = 16*16*16;
		final int numOfStrips = 8 * Runtime.getRuntime().availableProcessors();
		int numberOfYPerStrip = height / numOfStrips;
		
		List<Future<Void>> info = new ArrayList<>();
		
		for(int i = 0; i < numOfStrips; i++) {
			int yMin = i*numberOfYPerStrip;
			int yMax = (i+1)*numberOfYPerStrip - 1;
			if(i == numOfStrips - 1) {
				yMax = height - 1;
			}
			CalculationJob job = new CalculationJob(reMin, reMax, imMin, imMax, 
					width, height, yMin, yMax, m, data, polynom);
			info.add(pool.submit(job));
		}
		for(Future<Void> job : info) {
			try {
				job.get();
			} catch (InterruptedException | ExecutionException e) {
			}
		}
		
		System.out.println("The calculation has finished!");
		observer.acceptResult(data, (short)(polynom.toComplexPolynom().order() + 1), requestNo);
	}
}
