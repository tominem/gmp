package br.com.prati.tim.collaboration.gmp.ejb.alarme;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.alarme.AlarmeDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.com.prati.tim.collaboration.gmp.utis.FileUtis;
import br.prati.tim.collaboration.gp.jpa.Alarme;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAlarme;

@Stateless
public class AlarmeEJBImpl extends AbstractCrudEJB<Alarme> implements AlarmeEJB{

	@Inject
	private TimeZone defaultTimeZone;

	@Inject
	private AlarmeDAO alarmeDAO;
	
	private byte[] beforeFileContent = null;
	
	@Override
	public Alarme save(Alarme entityBean) throws Exception {
		if (entityBean.getIdAlarme() == null) {
			
			entityBean.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
			entityBean.setStatus(Boolean.TRUE);
			entityBean.setTagSistema(entityBean.getTagSistema().toUpperCase());
			
			if (entityBean.getTipoAlarme() == ETipoAlarme.MANUAL) {
				entityBean.setCodigoAlarme(null);
				entityBean.setCodigoReset(null);
			}
			
			if (entityBean.getArquivo().getNome() == null || entityBean.getArquivo().getNome().isEmpty()) {
				entityBean.setArquivo(null);
			}else{
				entityBean.getArquivo().setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
			}
			
		}
		
		//compacta apenas quando o arquivo selecionado for outro
		if (entityBean.getArquivo() != null && 
				getBeforeFileContent() != null && 
					Arrays.equals(getBeforeFileContent(), entityBean.getArquivo().getArquivo())) {
			
			byte[] content = entityBean.getArquivo().getArquivo();
			entityBean.getArquivo().setArquivo(FileUtis.compress(content));
			
		}
		
		return alarmeDAO.update(entityBean);
	}

	@Override
	public GenericDAO<Alarme> getCrudDAO() {
		return alarmeDAO;
	}

	@Override
	public List<Alarme> findByCodigoAlarmeAndTagSistema(Integer codigoAlarme, String tagSistema) {
		return alarmeDAO.findByCodigoAlarmeAndTagSistema(codigoAlarme, tagSistema);
	}

	@Override
	public byte[] getBeforeFileContent() {
		return beforeFileContent;
	}

	@Override
	public void setBeforeFileContent(byte[] beforeFileContent) {
		this.beforeFileContent = beforeFileContent;
	}


}
