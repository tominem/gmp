package br.com.prati.tim.collaboration.gmp.mb.receita;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.receita.ReceitaEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.com.prati.tim.collaboration.gmp.mb.UtilsMessage;
import br.prati.tim.collaboration.gp.jpa.ConfigEquipamento;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.Receita;
import br.prati.tim.collaboration.gp.jpa.TipoInspecao;

@Named("receitaMB")
@ViewScoped
public class ReceitaCrudMB extends AbstractCrudMB<Receita, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6966525816419190286L;

	@Inject
	private ReceitaEJB ejb;

	private ConfigEquipamento configEquipamento;

	private List<Equipamento> equipamentos;

	private List<TipoInspecao> tiposInspecao;
	
	private List<Receita> receitas;

	// =============== METHODS ===========================//

	@PostConstruct
	@Override
	public void initObjects() {

		super.initObjects();

		configEquipamento = new ConfigEquipamento();

		equipamentos = new ArrayList<Equipamento>();

	}

	@Override
	public String getResourceDialogPath() {
		return "/cadastros/receita/searchReceita.xhtml";
	}

	public List<TipoInspecao> getTiposInspecao() {
		return tiposInspecao;
	}

	public void setTiposInspecao(List<TipoInspecao> tiposInspecao) {
		this.tiposInspecao = tiposInspecao;
	}

	public ConfigEquipamento getConfigEquipamento() {
		return configEquipamento;
	}

	public void setConfigEquipamento(ConfigEquipamento configEquipamento) {
		this.configEquipamento = configEquipamento;
	}

	public List<Equipamento> getEquipamentos() {
		return equipamentos;
	}

	public void setEquipamentos(List<Equipamento> equipamentos) {
		this.equipamentos = equipamentos;
	}

	@Override
	public Long getEntityId() {
		return null;
	}

	@Override
	public void setEntityId(Long entityId) {
		//do nothing
	}

	@Override
	public Boolean getEntityStatus() {
		return null;
	}

	@Override
	public void setEntityStatus(Boolean status) {
		// do nothing
	}

	@Override
	public CrudEJB<Receita> getCrudEJB() {
		return ejb;
	}

	@Override
	public void validate(ComponentSystemEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public Class<Receita> getEntityClass() {
		return Receita.class;
	}

	public void openMaquinaDialog() {

		Map<String, Object> params = getParamsDialogPesquisa();

		String searchFabPath = "/cadastros/maquina/searchMaquina.xhtml";

		RequestContext.getCurrentInstance().openDialog(searchFabPath, params,
				null);

	}

	public void selectMaquina(SelectEvent event) {

		Object object = event.getObject();

		if (object != null
				&& object.getClass().getName().equals(Maquina.class.getName())) {
			this.entityBean.setMaquina((Maquina) object);

			UtilsMessage.addInfoMessage("Máquina informada com sucesso.");
		}

	}

}
