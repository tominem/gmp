package br.com.prati.tim.collaboration.gmp.mb.equipamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.equipamento.EquipamentoEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.com.prati.tim.collaboration.gmp.mb.UtilsMessage;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.Fabricante;
import br.prati.tim.collaboration.gp.jpa.FuncaoConfig;
import br.prati.tim.collaboration.gp.jpa.IoEquipamento;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoEquipamento;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoIoEquipamento;

@Named("equipamentoMB")
@ViewScoped
public class EquipamentoCrudMB extends AbstractCrudMB<Equipamento, Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6966525816419190286L;
	
	@Inject
	private EquipamentoEJB ejb;
	
	private List<ETipoEquipamento> tiposEquipamentos;

	private IoEquipamento ioEquipamentoInserted;
	
	private List<IoEquipamento> ioEquipamentos;
	
	private List<FuncaoConfig> itensConfig;
	

	//========================= METHODS ============================//
	
	@Override
	@PostConstruct
	public void initObjects() {
		
		load();
		
	}
	
	public void load() {
		
		this.entityBean = new Equipamento();
		
		tiposEquipamentos = Arrays.asList(ETipoEquipamento.values());
		
		ioEquipamentos = new ArrayList<IoEquipamento>();
		
		ioEquipamentoInserted = new IoEquipamento();
		
		itensConfig = ejb.findAllFuncaoConfig();
		
		itensConfig = itensConfig.stream().limit(10).collect(Collectors.toList());
		
	}
	
	@Override
	public void clean() {
		
		load();

		super.clean();

	}
	
	public List<FuncaoConfig> getItensConfig() {
		return itensConfig;
	}

	public void setItensConfig(List<FuncaoConfig> itensConfig) {
		this.itensConfig = itensConfig;
	}

	public List<ETipoIoEquipamento> getTiposIoEquipamentos(){
		return Arrays.asList(ETipoIoEquipamento.values());
	}
	
	public List<IoEquipamento> getIoEquipamentos() {
		return ioEquipamentos;
	}

	public void setIoEquipamentos(List<IoEquipamento> ioEquipamentos) {
		this.ioEquipamentos = ioEquipamentos;
	}
	
	public IoEquipamento getIoEquipamentoInserted() {
		return ioEquipamentoInserted;
	}

	public void setIoEquipamentoInserted(IoEquipamento ioEquipamentoInserted) {
		this.ioEquipamentoInserted = ioEquipamentoInserted;
	}

	public List<ETipoEquipamento> getTiposEquipamentos() {
		return tiposEquipamentos;
	}

	public void setTiposEquipamentos(List<ETipoEquipamento> tiposEquipamentos) {
		this.tiposEquipamentos = tiposEquipamentos;
	}

	@Override
	public String getResourceDialogPath() {
		return "/cadastros/tipocodigo/searchEquipamento.xhtml";
	}

	@Override
	public Long getEntityId() {
		return entityBean.getIdEquipamento();
	}

	@Override
	public void setEntityId(Long entityId) {
		entityBean.setIdEquipamento(entityId);
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
	public CrudEJB<Equipamento> getCrudEJB() {
		return ejb;
	}

	@Override
	public void validate(ComponentSystemEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<Equipamento> getEntityClass() {
		return Equipamento.class;
	}
	
	public void openFabricanteDialog(){
		
		Map<String, Object> params = getParamsDialogPesquisa();
		
		String searchFabPath = "/cadastros/fabricante/searchFabricante.xhtml";
		
		RequestContext.getCurrentInstance().openDialog(searchFabPath, params, null);
		
	}

	public void selectFabricante(SelectEvent event) {
		
		Object object = event.getObject();

		if (object != null && object.getClass().getName().equals(Fabricante.class.getName())) {
			this.entityBean.setFabricante((Fabricante) object);
			
			UtilsMessage.addInfoMessage("Fabricante informado com sucesso.");
		}
		
	}
	
	public void addIOIntoTable(){
		
		ioEquipamentos.add(ioEquipamentoInserted);
		
		ioEquipamentoInserted = new IoEquipamento();
		
	}
	
	public void removeIoIntoTable(IoEquipamento removedIoEquipamento){
		
		ioEquipamentos.remove(removedIoEquipamento);
		
	}

}
