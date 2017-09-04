package br.com.prati.tim.collaboration.gmp.dao.replicador;

import javax.ejb.Stateless;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.ReplicatorParamsEntity;

@Stateless
public class ReplicatorParamsDAOImpl extends AbstractJPADAO<ReplicatorParamsEntity> implements ReplicatorParamsDAO{

	public ReplicatorParamsDAOImpl() {
		super(ReplicatorParamsEntity.class);
	}

}
