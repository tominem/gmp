package br.com.prati.tim.collaboration.gmp.dao.alarme;

import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createQueryCriteria;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.Alarme;

public class AlarmeDAOImpl extends AbstractJPADAO<Alarme> implements AlarmeDAO{

	public AlarmeDAOImpl() {
		super(Alarme.class);
	}

	@Override
	public List<Alarme> findByCodigoAlarmeAndTagSistema(Integer codigoAlarme, String tagSistema) {
		
		return createQueryCriteria(getEntityManager(), getEntityClass())
				.andEquals("codigoAlarme", codigoAlarme)
				.andEquals("tagSistema", tagSistema)
				
				.getResultList();
	}

}
