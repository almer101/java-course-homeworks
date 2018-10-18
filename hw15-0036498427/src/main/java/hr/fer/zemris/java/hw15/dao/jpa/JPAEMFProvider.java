package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * This is the entity manager factory provider. The entity manager factory is
 * object which can produce entity managers.
 * 
 * @author ivan
 *
 */
public class JPAEMFProvider {

	/** The singleton object which produces entity managers. */
	public static EntityManagerFactory emf;

	/**
	 * This is the method which returns one instance of the entity manager factory.
	 * 
	 * @return the instance of entitiy manager factory.
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * This method sets the {@link #emf} property to the specified value.
	 * 
	 * @param emf
	 * 		the new value of the {@link #emf}	property
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}