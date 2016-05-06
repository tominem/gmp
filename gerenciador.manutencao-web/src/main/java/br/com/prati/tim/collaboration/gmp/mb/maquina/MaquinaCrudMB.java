package br.com.prati.tim.collaboration.gmp.mb.maquina;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.maquina.MaquinaEJB;
import br.com.prati.tim.collaboration.gmp.ex.FacesValidateException;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.EquipamentoMaquina;
import br.prati.tim.collaboration.gp.jpa.Linhaproducao;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.Sala;
import br.prati.tim.collaboration.gp.jpa.TipoComunicacao;
import br.prati.tim.collaboration.gp.jpa.TipoInspecao;

@Named("maquinaMB")
@ViewScoped
public class MaquinaCrudMB extends AbstractCrudMB<Maquina, Long>	implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6966525816419190286L;

	private final String INPUT_EQUIPAMENTO_ID = "formCad:equipamento";

	private final String INPUT_TIPOINSPECAO_ID = "formCad:tipoInspecao";

	private final String INPUT_TIPOCOMUNICACAO_ID = "formCad:tipoComunicacao";
	

	@Inject
	private MaquinaEJB ejb;
	
	private Equipamento equipamentoSelected;

	private TipoInspecao tipoInspecaoSelected;

	private TipoComunicacao tipoComunicacaoSelected;
	
	private List<EquipamentoMaquina> equipamentoMaquinas;
	
	private List<EquipamentoMaquina> selectedEquipamentoMaquinas;
	
	private EquipamentoMaquina equipamentoMaquinaSelected;
	
	//================ METHODS ========================//
	
	@Override
	public String getResourceDialogPath() {
		return "/cadastros/maquina/searchMaquina.xhtml";
	}
	
	@Override
	public void clean() {
		
		super.clean();
		
		cleanEquipamentoForm();
		
		equipamentoMaquinas = new ArrayList<EquipamentoMaquina>();
		
	}

	@Override
	public Long getEntityId() {
		return entityBean.getIdMaquina();
	}

	@Override
	public void setEntityId(Long entityId) {
		entityBean.setIdMaquina(entityId);
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
	public CrudEJB<Maquina> getCrudEJB() {
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
//			addErrorMessage("Existe um Maquina já cadastrado com a mesma descrição!");
//			
//		}
		
	}

	@Override
	public Class<Maquina> getEntityClass() {
		return Maquina.class;
	}
	
	@Override
	public void selectObjectAfterSearch(SelectEvent event) {
		super.selectObjectAfterSearch(event);
		
		if (entityBean.getIdMaquina() != null) {
			equipamentoMaquinas = ejb.findEquipamentoMaquinaByMaquina(entityBean);
		}
	}
	
	public void selectLinha(SelectEvent event) {

		Object object = event.getObject();

		if (object != null	&& object.getClass().getName().equals(Linhaproducao.class.getName())) {
			entityBean.setLinhaproducao((Linhaproducao) object);

			addInfoMessage("Linha informada com sucesso.");
		}
			
	}

	public void selectSala(SelectEvent event) {
		
		Object object = event.getObject();
		
		if (object != null	&& object.getClass().getName().equals(Sala.class.getName())) {
			entityBean.setSala((Sala) object);
			
			addInfoMessage("Sala informada com sucesso.");
		}
		
	}

	public void selectEquipamento(SelectEvent event) {
		
		Object object = event.getObject();
		
		if (object != null	&& object.getClass().getName().equals(Equipamento.class.getName())) {
			setEquipamentoSelected((Equipamento) object);
			
			addInfoMessage("Equipamento informado com sucesso.");
		}
		
	}

	public void selectTipoInspecao(SelectEvent event) {
		
		Object object = event.getObject();
		
		if (object != null	&& object.getClass().getName().equals(TipoInspecao.class.getName())) {
			setTipoInspecaoSelected((TipoInspecao) object);
			
			addInfoMessage("Tipo de Inspeção informada com sucesso.");
		}
		
	}

	public void selectTipoComunicacao(SelectEvent event) {
		
		Object object = event.getObject();
		
		if (object != null	&& object.getClass().getName().equals(TipoComunicacao.class.getName())) {
			setTipoComunicacaoSelected((TipoComunicacao) object);
			
			addInfoMessage("Tipo de Comunicação informada com sucesso.");
		}
		
	}
	
	public void addEquipamento(){
		
		try {
			
			validateAddEquipamento();
			
			equipamentoMaquinas.add(new EquipamentoMaquina(getEquipamentoSelected(), getEntityBean(), getTipoInspecaoSelected(), getTipoComunicacaoSelected()));
			
			cleanEquipamentoForm();
			
		} catch (FacesValidateException e) {
			
			addErrorMessage(e.getMessage());
			
		}
		
	}

	private void cleanEquipamentoForm() {
		
		equipamentoSelected 	= null;
		
		tipoInspecaoSelected 	= null;
		
		tipoComunicacaoSelected = null;
		
	}

	private void validateAddEquipamento() throws FacesValidateException {
		
		if (equipamentoSelected == null)
			throw new FacesValidateException("", INPUT_EQUIPAMENTO_ID);

		if (tipoInspecaoSelected == null)
			throw new FacesValidateException("", INPUT_TIPOINSPECAO_ID);

		if (tipoComunicacaoSelected == null)
			throw new FacesValidateException("", INPUT_TIPOCOMUNICACAO_ID);
		
	}

	public Equipamento getEquipamentoSelected() {
		return equipamentoSelected;
	}

	public void setEquipamentoSelected(Equipamento equipamentoSelected) {
		this.equipamentoSelected = equipamentoSelected;
	}

	public TipoComunicacao getTipoComunicacaoSelected() {
		return tipoComunicacaoSelected;
	}

	public void setTipoComunicacaoSelected(TipoComunicacao tipoComunicacaoSelected) {
		this.tipoComunicacaoSelected = tipoComunicacaoSelected;
	}

	public TipoInspecao getTipoInspecaoSelected() {
		return tipoInspecaoSelected;
	}

	public void setTipoInspecaoSelected(TipoInspecao tipoInspecaoSelected) {
		this.tipoInspecaoSelected = tipoInspecaoSelected;
	}

	public List<EquipamentoMaquina> getEquipamentoMaquinas() {
		return equipamentoMaquinas;
	}

	public void setEquipamentoMaquinas(List<EquipamentoMaquina> equipamentoMaquinas) {
		this.equipamentoMaquinas = equipamentoMaquinas;
	}

	public List<EquipamentoMaquina> getSelectedEquipamentoMaquinas() {
		return selectedEquipamentoMaquinas;
	}

	public void setSelectedEquipamentoMaquinas(
			List<EquipamentoMaquina> selectedEquipamentoMaquinas) {
		this.selectedEquipamentoMaquinas = selectedEquipamentoMaquinas;
	}

	public EquipamentoMaquina getEquipamentoMaquinaSelected() {
		return equipamentoMaquinaSelected;
	}

	public void setEquipamentoMaquinaSelected(EquipamentoMaquina equipamentoMaquinaSelected) {
		this.equipamentoMaquinaSelected = equipamentoMaquinaSelected;
	}

}
