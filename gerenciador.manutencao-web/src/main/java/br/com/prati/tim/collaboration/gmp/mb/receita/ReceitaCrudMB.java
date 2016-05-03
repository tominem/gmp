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

import br.com.prati.tim.collaboration.gmp.ejb.receita.ReceitaEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractBaseMB;
import br.com.prati.tim.collaboration.gmp.mb.UtilsMessage;
import br.prati.tim.collaboration.gp.jpa.ConfigEquipamento;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.FuncaoConfig;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.Receita;
import br.prati.tim.collaboration.gp.jpa.TipoInspecao;

@Named("receitaMB")
@ViewScoped
public class ReceitaCrudMB extends AbstractBaseMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6966525816419190286L;

	@Inject
	private ReceitaEJB ejb;

	private Maquina maquina;

	private ConfigEquipamento configEquipamento;
	
	private TipoInspecao tipoInspecao;

	private List<Equipamento> equipamentos;

	private List<TipoInspecao> tiposInspecao;
	
	private List<Receita> selectedReceitas;

	private List<Receita> receitas;

	// =============== METHODS ===========================//

	@PostConstruct
	public void initObjects() {

		load();

	}

	private void load() {
		
		configEquipamento = new ConfigEquipamento();
		
		equipamentos = new ArrayList<Equipamento>();
		
		receitas = new ArrayList<Receita>();
		
		selectedReceitas = new ArrayList<Receita>();
		
		populateItens();
		
	}
	
	private void populateItens() {
		
		List<FuncaoConfig> funcaoConfigs = ejb.findAllFuncaoConfig();
		
		funcaoConfigs.forEach(fc -> {
			
			ConfigEquipamento ce = new ConfigEquipamento(fc);
			Receita receita = new Receita(ce);
			receitas.add(receita);
			
		});
		
	}

	public TipoInspecao getTipoInspecao() {
		return tipoInspecao;
	}

	public void setTipoInspecao(TipoInspecao tipoInspecao) {
		this.tipoInspecao = tipoInspecao;
	}

	public Maquina getMaquina() {
		return maquina;
	}

	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}
	
	public List<Receita> getSelectedReceitas() {
		return selectedReceitas;
	}

	public void setSelectedReceitas(List<Receita> selectedReceitas) {
		this.selectedReceitas = selectedReceitas;
	}

	public List<Receita> getReceitas() {
		return receitas;
	}

	public void setReceitas(List<Receita> receitas) {
		this.receitas = receitas;
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
	public void validate(ComponentSystemEvent event) {
		// TODO Auto-generated method stub

	}

	public void openMaquinaDialog() {

		Map<String, Object> params = getParamsDialogPesquisa();

		String searchFabPath = "/cadastros/maquina/searchMaquina.xhtml";

		RequestContext.getCurrentInstance().openDialog(searchFabPath, params,
				null);

	}

	public void selectMaquina(SelectEvent event) {

		Object object = event.getObject();

		if (object != null	&& object.getClass().getName().equals(Maquina.class.getName())) {
			setMaquina(maquina);

			UtilsMessage.addInfoMessage("Máquina informada com sucesso.");
		}

	}

}
