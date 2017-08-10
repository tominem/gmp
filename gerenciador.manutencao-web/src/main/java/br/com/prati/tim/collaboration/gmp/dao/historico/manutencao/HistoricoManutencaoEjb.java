package br.com.prati.tim.collaboration.gmp.dao.historico.manutencao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.prati.tim.collaboration.gp.jpa.HistManutencao;

@Stateless
public class HistoricoManutencaoEjb {

	@Inject
	private EntityManager entityManager;
	
	public HistManutencao salvar(HistManutencao historicoManutencao) {
		return entityManager.merge(historicoManutencao);
	}
}
