package br.com.prati.tim.collaboration.gmp.ejb.programacaomaquina;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.configuracaogeral.ConfiguracaoGeralEJB;
import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.programacaomaquina.ProgramacaoMaquinaDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.ConfiguracaoGeral;
import br.prati.tim.collaboration.gp.jpa.NotaPmMaquina;
import br.prati.tim.collaboration.gp.jpa.ProgramacaoMaquina;
import br.prati.tim.collaboration.gp.jpa.enumerator.DefConfiguracaoGeralKeys;
import br.prati.tim.collaboration.gp.jpa.enumerator.EStatusNotaPm;

@Stateless
public class ProgramacaoMaquinaEJBImpl extends AbstractCrudEJB<ProgramacaoMaquina> implements ProgramacaoMaquinaEJB {

	@Inject
	private ProgramacaoMaquinaDAO	programacaoMaquinaDAO;

	@Inject
	private ConfiguracaoGeralEJB	configuracaoEJB;

	@Override
	public ProgramacaoMaquina save(ProgramacaoMaquina entityBean) throws Exception {
		return programacaoMaquinaDAO.update(entityBean);
	}

	@Override
	public GenericDAO<ProgramacaoMaquina> getCrudDAO() {
		return programacaoMaquinaDAO;
	}

	@Override
	public List<ProgramacaoMaquina> findByServicosPendentes() {
		
		ConfiguracaoGeral configServicos;
		
		try {
			configServicos = configuracaoEJB.getByChave(DefConfiguracaoGeralKeys.SERVICOS_MANUTENCAO.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
			return new ArrayList<ProgramacaoMaquina>();
		}
		
		if (configServicos == null){
			return new ArrayList<ProgramacaoMaquina>();
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
		
		return programacaoMaquinaDAO.findByServicosPendentes(idServicos);
	}
	
}
