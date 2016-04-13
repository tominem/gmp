package br.com.prati.tim.collaboration.gmp.mb.tipocomponente;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.BOTH_LIKE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterOrder.ASC;

import java.io.Serializable;

import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.tipocomponente.TipoComponenteEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.prati.tim.collaboration.gp.jpa.TipoComponente;

@Named("tipoComponenteMB")
@ViewScoped
public class TipoComponenteCrudMB extends AbstractCrudMB<TipoComponente, Long> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5144587074129076687L;
	
	@Inject
	private TipoComponenteEJB ejb;

//	@Override
//	@PostConstruct
//	public void initObjects() {
//		clean();
//	}
	
	@Override
	public FilterParam<?>[] getFilterParams() {
		return new FilterParam<?>[]{
			
			new FilterParam<String>("Descrição", "descricao", BOTH_LIKE, ASC), 
			new FilterParam<String>("Nome", "nomeComponente", BOTH_LIKE)
			
		};
	}

	@Override
	public String getFormName() {
		return "formCadTpComponente";
	}
	
	@Override
	public String getSearchMB() {
		return "tipoComponenteSearch";
	}

	@Override
	public CrudEJB<TipoComponente> getCrudEJB() {
		return ejb;
	}

	@Override
	public Class<TipoComponente> getEntityClass() {
		return TipoComponente.class;
	}

	@Override
	public Long getEntityId() {
		return entityBean.getIdTipoComponente();
	}
	
	@Override
	public void setEntityId(Long entityId) {
		entityBean.setIdTipoComponente(entityId);
	}
	
	@Override
	public void validate(ComponentSystemEvent event) {
		
//		FacesContext fc = FacesContext.getCurrentInstance();
//
//		UIComponent components = event.getComponent();
//
//		// get descricao
//		UIInput uiInputDescricao = (UIInput) components.findComponent("descricao");
//		String descricao = uiInputDescricao.getSubmittedValue() != null ? ""
//				: uiInputDescricao.getLocalValue().toString();
//		String descricaoId = uiInputDescricao.getClientId();
//
//		// get nome
//		UIInput uiInputNome = (UIInput) components.findComponent("nomeComponente");
//		String nome = uiInputNome.getSubmittedValue() != null ? ""
//				: uiInputNome.getLocalValue().toString();
//		
//		Map<String, Object> params = putParams(descricao, nome);
//		
//		//validate if object is present in db
//		boolean exists = getCrudEJB().validaCadastroExistente(params);
//		
//		if (exists) {
//			
//			//invalidate inputs
//			uiInputDescricao.setValid(false);
//			uiInputNome.setValid(false);
//			
//			//add validation message
//			FacesMessage msg = new FacesMessage("Tipo Componente já está cadastrado, "
//						+ "com a mesma descrição, nome ou ambos");
//			
//			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//			fc.addMessage(descricaoId, msg);
//			fc.renderResponse();
//			
//		}
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
	public String getResourceDialogPath() {
		return "/cadastros/tipocomponente/searchTipoComponente.xhtml";
	}

}
