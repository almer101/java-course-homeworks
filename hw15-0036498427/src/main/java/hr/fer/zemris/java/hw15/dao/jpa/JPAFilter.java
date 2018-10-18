package hr.fer.zemris.java.hw15.dao.jpa;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Enables the chain to be executed but in the end closes the entity manager
 * if some was opened during the chain of events.
 * 
 * @author ivan
 *
 */
@WebFilter("/servleti/*")
public class JPAFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} finally {
			//close if someone opened it in the meantime
			JPAEMProvider.close();
		}
	}

	@Override
	public void destroy() {
	}
	
}