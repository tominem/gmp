package br.com.prati.tim.collaboration.gmp.ejb.notapm;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.notapm.NotaPmMaquinaDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.NotaPmMaquina;
import br.prati.tim.collaboration.gp.jpa.enumerator.EStatusNotaPm;

@Stateless
public class NotaPmMaquinaEJBImpl extends AbstractCrudEJB<NotaPmMaquina> implements NotaPmMaquinaEJB{

	@Inject
	private NotaPmMaquinaDAO notaPmMaquinaDAO;
	
	@Override
	public NotaPmMaquina save(NotaPmMaquina entityBean) throws Exception {
		entityBean.setDataRegistro(new Date());
		return notaPmMaquinaDAO.update(entityBean);
	}

	@Override
	public GenericDAO<NotaPmMaquina> getCrudDAO() {
		return notaPmMaquinaDAO;
	}

	@Override
	public List<NotaPmMaquina> findByStatus(EStatusNotaPm status) {
		return notaPmMaquinaDAO.findByStatus(status);
	}

	@Override
	public void updateStatusNota(NotaPmMaquina nota, EStatusNotaPm status) throws Exception {
		nota.setStatus(status);
		notaPmMaquinaDAO.update(nota);
	}

	@Override
	public List<NotaPmMaquina> findByPeriodoAndMaquinas(Date dataInicial, Date dataFinal, List<Maquina> maquinas) {
		return notaPmMaquinaDAO.findByPeriodoAndMaquinas(dataInicial, dataFinal, maquinas);
	}

}
