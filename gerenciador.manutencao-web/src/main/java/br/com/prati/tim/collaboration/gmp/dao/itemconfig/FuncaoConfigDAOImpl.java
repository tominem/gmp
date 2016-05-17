package br.com.prati.tim.collaboration.gmp.dao.itemconfig;

import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createQueryCriteria;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.FuncaoConfig;
import br.prati.tim.collaboration.gp.jpa.MenuConfig;

public class FuncaoConfigDAOImpl extends AbstractJPADAO<FuncaoConfig> implements FuncaoConfigDAO {
	
	public FuncaoConfigDAOImpl() {
		super(FuncaoConfig.class);
	}

	@Override
	public List<FuncaoConfig> findByDescricaoAndMenuConfig(String descricao, MenuConfig menuConfig) {
		
		return createQueryCriteria(getEntityManager(), getEntityClass())
				.andEquals("descricao", descricao)
				.andEquals("menuConfig", menuConfig)
				.getResultList();
	}

	@Override
	public List<FuncaoConfig> findByComandoAndMenuConfig(String comando, MenuConfig menuConfig) {
		
		return createQueryCriteria(getEntityManager(), getEntityClass())
				.andEquals("comando", comando)
				.andEquals("menuConfig", menuConfig)
				.getResultList();
	}

}
