package br.com.prati.tim.collaboration.gmp.cdi.producer;

import java.util.TimeZone;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * This class uses CDI to alias Java EE resources, such as the persistence
 * context, to CDI beans
 * 
 * <p>
 * Example injection on a managed bean field:
 * </p>
 * 
 * <pre>
 * &#064;Inject
 * private EntityManager em;
 * </pre>
 */
public class EntityManagerProducer {

	@Produces
	@PersistenceContext()
	private EntityManager em;

	@Produces
	public TimeZone getDefaultTimeZone() {
		return TimeZone.getTimeZone("America/Sao_Paulo");
	}
	
}