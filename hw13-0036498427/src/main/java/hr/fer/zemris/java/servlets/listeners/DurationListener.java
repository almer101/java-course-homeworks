package hr.fer.zemris.java.servlets.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * This is the listener which, at the moment the web application is initialized,
 * stores the current time and it is used to show the information about how long
 * the application has been running
 * 
 * @author ivan
 *
 */
public class DurationListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		long currentTime = System.currentTimeMillis();
		sce.getServletContext().setAttribute("time", currentTime);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
