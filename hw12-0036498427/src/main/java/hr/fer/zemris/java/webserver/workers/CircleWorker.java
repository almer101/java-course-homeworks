package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This class represents the worker which creates
 * a 200x200 image with a single filled circle.
 * 
 * @author ivan
 *
 */
public class CircleWorker implements IWebWorker {

	/**The width of the image.*/
	private int IMAGE_WIDTH = 200;
	
	/**The height of the image.*/
	private int IMAGE_HEIGHT = 200;
	
	@Override
	public void processRequest(RequestContext context) throws Exception {
		BufferedImage bim = new BufferedImage(
				IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
		
		context.setMimeType("png");
		Graphics2D g2d = bim.createGraphics();
		g2d.setColor(Color.ORANGE);
		g2d.fillOval(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
		g2d.dispose();
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bim, "png", bos);
			context.write(bos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
