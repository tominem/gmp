package br.com.prati.tim.collaboration.gmp.dao.valorconfigequip;

import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createQueryCriteria;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.ConfigEquipamento;
import br.prati.tim.collaboration.gp.jpa.ValorConfigEquip;

public class ValorConfigEquipDAO  extends AbstractJPADAO<ValorConfigEquip> implements IValorConfigEquipDAO{

	public ValorConfigEquipDAO() {
		super(ValorConfigEquip.class);
	}

	@Override
	public ValorConfigEquip findByConfigEquipamento(ConfigEquipamento configEquipamento) {
		try{
			
		
		return createQueryCriteria(getEntityManager(), getEntityClass())
								.andEquals("configEquipamento", configEquipamento)
								.getSingleResult();
		
		}catch(Exception ex){
			return null;
		}
	}
	
	
	

}
