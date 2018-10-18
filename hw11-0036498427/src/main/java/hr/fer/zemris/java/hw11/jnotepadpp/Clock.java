package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * This is the clock which gets the label through
 * the constructor and periodically changes the 
 * text of the label to the current time.
 * 
 * @author ivan
 *
 */
public class Clock {

	//==========================Properties==============================
	
	/**
	 * The label to update periodically.
	 */
	private JLabel label;
	
	/**
	 * The frame where the label is. It is required because
	 * the EDT will not stop while the clock is doing its job
	 * so the listener must be registered to the frame to stop
	 * the job with the {@link #stopRequested} flag.
	 */
	private JFrame frame;
	
	/**
	 * The formatter for formating the time correctly.
	 */
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
			"yyyy/MM/dd HH:mm:ss");

	/**
	 * The flag indicating that the stop is requested.
	 */
	private boolean stopRequested = false;
	
	//==========================Constructor==============================
	
	/**
	 * The constructor which gets the label parameter
	 * and initializes the {@link #label} with 
	 * that parameter.
	 * 
	 * @param label
	 * 		the label which shows time and will be
	 * 		updated periodically.
	 * 
	 * @param frame
	 * 		the frame this label is in.
	 */
	public Clock(JLabel label, JFrame frame) {
		this.label = label;
		this.frame = frame;
		updateTime();
		this.frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				stopRequested = true;
			}
		});
		
		Thread t = new Thread(() ->  {
			while(true) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {}
				if(stopRequested) break;
				SwingUtilities.invokeLater(() -> {
					updateTime();
				});
			}
		});
		t.setDaemon(true);
		t.start();
	}

	/**
	 * This method updates the time and sets the
	 * current time as the {@link #label} text.
	 */
	private void updateTime() {
		label.setText(formatter.format(LocalDateTime.now()));
	}

}
