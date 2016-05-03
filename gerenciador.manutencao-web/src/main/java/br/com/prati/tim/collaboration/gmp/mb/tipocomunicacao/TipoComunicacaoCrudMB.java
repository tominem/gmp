package br.com.prati.tim.collaboration.gmp.mb.tipocomunicacao;

import java.io.Serializable;

import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.tipocomunicaco.TipoComunicacaoEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.prati.tim.collaboration.gp.jpa.TipoComunicacao;

@Named("tipoComunicacaoMB")
@ViewScoped
public class TipoComunicacaoCrudMB extends AbstractCrudMB<TipoComunicacao, Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5144587074129076687L;

	
	@Inject
	private TipoComunicacaoEJB ejb;
	
	@Override
	public String getResourceDialogPath() {
		return "/cadastros/tipocomunicacao/searchTipoComunicacao.xhtml";
	}

	@Override
	public Long getEntityId() {
		return entityBean.getIdTipoComunicacao();
	}

	@Override
	public void setEntityId(Long entityId) {
		entityBean.setIdTipoComunicacao(entityId);
	}

	@Override
	public Boolean getEntityStatus() {
		return entityBean.getStatus();
	}

	@Override
	public void setEntityStatus(Boolean status) {
		entityBean.setStatus(status);
	}

	@Override
	public CrudEJB<TipoComunicacao> getCrudEJB() {
		return ejb;
	}

	@Override
	public void validate(ComponentSystemEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<TipoComunicacao> getEntityClass() {
		return TipoComunicacao.class;
	}
	
}
