package br.com.prati.tim.collaboration.gmp.mb.maquina;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.maquina.MaquinaEJB;
import br.com.prati.tim.collaboration.gmp.ex.FacesValidateException;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
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

	private final String INPUT_TIPOCOMUNICACAO_ID = "formCad:tipoComunicacao";

	private final String INPUT_DESCRICAO_ID = "formCad:descricao";

	private final String INPUT_TAG_ID = "formCad:tag";
	

	@Inject
	private MaquinaEJB ejb;
	
	@Inject
	private TimeZone defaultTimeZone;
	
	private Equipamento equipamentoSelected;

	private TipoInspecao tipoInspecaoSelected;

	private TipoComunicacao tipoComunicacaoSelected;
	
	private List<EquipamentoMaquina> equipamentoMaquinas;
	
	private List<EquipamentoMaquina> selectedEquipamentoMaquinas;
	
	private EquipamentoMaquina equipamentoMaquinaSelected;
	
	//================ METHODS ========================//
	
	@PostConstruct
	@Override
	public void initObjects() {
		super.initObjects();
		
		load();
	}
	
	@Override
	public String getResourceDialogPath() {
		return "/cadastros/maquina/searchMaquina.xhtml";
	}
	
	@Override
	public void clean() {
		
		super.clean();
		
		load();
		
	}

	private void load() {
		
		entityBean.setLinhaproducao(new Linhaproducao());
		entityBean.setSala(new Sala());
		
		equipamentoMaquinas = new ArrayList<EquipamentoMaquina>();

		cleanEquipamentoForm();
		
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
	public ValidateComponent[] getValidaComponents() {
		return new ValidateComponent[] {
			
			new ValidateComponent(INPUT_TAG_ID, "Tag", "tag"),
			new ValidateComponent(INPUT_DESCRICAO_ID, "Descrição", "descricao")
			
		};
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
			
			EquipamentoMaquina em = new EquipamentoMaquina(getEquipamentoSelected(), getEntityBean(), getTipoInspecaoSelected(), getTipoComunicacaoSelected());
			em.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
			
			equipamentoMaquinas.add(em);
			
			cleanEquipamentoForm();
			
		} catch (FacesValidateException e) {
			
			addErrorMessage(e.getMessage());
			
		}
		
	}
	
	public void removeEquipamento(EquipamentoMaquina em){
		equipamentoMaquinas.remove(em);
	}

	private void cleanEquipamentoForm() {
		
		equipamentoSelected 	= null;
		
		tipoInspecaoSelected 	= null;
		
		tipoComunicacaoSelected = null;
		
	}

	private void validateAddEquipamento() throws FacesValidateException {
		
		if (equipamentoSelected == null)
			throw new FacesValidateException("", INPUT_EQUIPAMENTO_ID);

		if (tipoComunicacaoSelected == null)
			throw new FacesValidateException("", INPUT_TIPOCOMUNICACAO_ID);

		//	it can't exist more than one equipamento in equipamento_maquina 
		if (equipamentoMaquinas.stream().filter(em -> em.getEquipamento().hashCode() == equipamentoSelected.hashCode()).findFirst().isPresent()){
			throw new FacesValidateException("Equipamento já vinculado à maquina", INPUT_EQUIPAMENTO_ID);
		}
		
	}
	
	@Override
	public void save() {
		
		entityBean.setEquipamentoMaquinas(new ArrayList<EquipamentoMaquina>());
		entityBean.setEquipamentoMaquinas(equipamentoMaquinas);
		
		super.save();
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
