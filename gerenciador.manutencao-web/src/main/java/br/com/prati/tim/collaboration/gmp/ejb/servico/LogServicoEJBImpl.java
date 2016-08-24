package br.com.prati.tim.collaboration.gmp.ejb.servico;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.configuracaogeral.ConfiguracaoGeralEJB;
import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.servico.LogServicoDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.ConfiguracaoGeral;
import br.prati.tim.collaboration.gp.jpa.LogServico;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.ProgramacaoMaquina;
import br.prati.tim.collaboration.gp.jpa.enumerator.DefConfiguracaoGeralKeys;

@Stateless
public class LogServicoEJBImpl extends AbstractCrudEJB<LogServico> implements LogServicoEJB {

	@Inject
	private LogServicoDAO logServicoDAO;

	@Inject
	private ConfiguracaoGeralEJB	configuracaoEJB;
	
	@Override
	public LogServico save(LogServico entityBean) throws Exception {
		return logServicoDAO.update(entityBean);
	}

	@Override
	public GenericDAO<LogServico> getCrudDAO() {
		return logServicoDAO;
	}

	@Override
	public List<LogServico> findByPeriodoMaquinasAndServicos(Date dataInicial, Date dataFinal, List<Maquina> maquinas) {
		
		ConfiguracaoGeral configServicos;
		
		try {
			configServicos = configuracaoEJB.getByChave(DefConfiguracaoGeralKeys.SERVICOS_MANUTENCAO.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
			return new ArrayList<LogServico>();
		}
		
		if (configServicos == null){
			return new ArrayList<LogServico>();
		}
		
		String[] servicosArray = configServicos.getValor().split(",");
		
		List<Long> idServicos = new ArrayList<>();
		
		for (String servico : servicosArray) {
			
			try{
			
				Long idServico = Long.parseLong(servico.trim());
				idServicos.add(idServico);
				
			} catch (Exception e){
				
			}
		}
		
		return logServicoDAO.findByPeriodoMaquinasAndServicos(dataInicial, dataFinal, maquinas, idServicos);
	}

}
