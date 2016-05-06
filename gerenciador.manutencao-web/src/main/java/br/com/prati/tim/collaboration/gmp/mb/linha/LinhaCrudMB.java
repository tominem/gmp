package br.com.prati.tim.collaboration.gmp.mb.linha;

import java.io.Serializable;

import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.linha.LinhaproducaoEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.prati.tim.collaboration.gp.jpa.Linhaproducao;

@Named("linhaMB")
@ViewScoped
public class LinhaCrudMB extends AbstractCrudMB<Linhaproducao, Long>	implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6966525816419190286L;
	

	@Inject
	private LinhaproducaoEJB ejb;

	
	//================ METHODS ========================//
	
	@Override
	public String getResourceDialogPath() {
		return "/cadastros/linha/searchLinha.xhtml";
	}

	@Override
	public Long getEntityId() {
		return entityBean.getIdLinhaproducao();
	}

	@Override
	public void setEntityId(Long entityId) {
		entityBean.setIdLinhaproducao(entityId);
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
	public CrudEJB<Linhaproducao> getCrudEJB() {
		return ejb;
	}

	@Override
	public void validate(ComponentSystemEvent event) {
		
//		if (entityBean.getIdLinhaproducao() != null) return;
//		
//		UIComponent components = event.getComponent();
//
//		// get descricao
//		UIInput uiInputDescricao = (UIInput) components.findComponent(DESCRICAO_INPUT_ID);
//		String descricao = uiInputDescricao.getSubmittedValue() != null ? ""
//				: uiInputDescricao.getLocalValue().toString();
//
//		HashMap<String, Object> params = new HashMap<String, Object>();
//		params.put("descricao", descricao);
//		
//		//validate if object is present in db
//		boolean exists = getCrudEJB().checkIfExists(params);
//		
//		if (exists) {
//			
//			//invalidate inputs
//			uiInputDescricao.setValid(false);
//			
//			//add validation message
//			addErrorMessage("Existe um Linhaproducao já cadastrado com a mesma descrição!");
//			
//		}
		
	}

	@Override
	public Class<Linhaproducao> getEntityClass() {
		return Linhaproducao.class;
	}

}
