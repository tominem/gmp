package br.com.prati.tim.collaboration.gmp.mb.receita;

import static br.com.prati.tim.collaboration.gmp.utis.FacesUtis.setValidComponent;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import javax.annotation.PostConstruct;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;


import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;


import br.com.prati.tim.collaboration.gmp.ejb.receita.ReceitaEJB;
import br.com.prati.tim.collaboration.gmp.ex.FacesValidateException;
import br.com.prati.tim.collaboration.gmp.mb.AbstractBaseMB;
import br.com.prati.tim.collaboration.gmp.mb.UtilsMessage;
import br.prati.tim.collaboration.gp.jpa.ConfigEquipamento;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.EquipamentoMaquina;
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

	private final String COMBOBOX_EQUIPAMENTO_ID = "formCad:equipamento";

	private final String INPUT_TIPO_INSPECAO_ID = "formCad:tipoInspecao";

	@Inject
	private ReceitaEJB ejb;

	private Maquina maquina;
	
	private Equipamento equipamento;

	private TipoInspecao tipoInspecao;

	private List<Equipamento> equipamentos;

	private List<Receita> selectedReceitas;

	private List<Receita> receitas;

	private List<EquipamentoMaquina> filteredEquipamentoMaquinas;


	// =============== METHODS ===========================//

	@PostConstruct
	public void initObjects() {

		load();

	}

	private void load() {
		
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

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
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

		RequestContext.getCurrentInstance().openDialog(searchFabPath, params, null);

	}

	public void selectMaquina(SelectEvent event) {

		try {
			
			Object object = event.getObject();

			if (object != null	&& object.getClass().getName().equals(Maquina.class.getName())) {
				setMaquina((Maquina) object);
				
				loadParametersByMaquina();

				UtilsMessage.addInfoMessage("Máquina informada com sucesso.");
			}
			
		} catch (FacesValidateException e) {
			
			addErrorMessage(e.getMessage());
			
		}

	}

	private void loadParametersByMaquina() throws FacesValidateException {
		
		equipamento = null;
		
		equipamentos = new ArrayList<Equipamento>();
		
		tipoInspecao = new TipoInspecao();
		
		filteredEquipamentoMaquinas = ejb.findEquipamentoMaquinaByMaquina(getMaquina());
		
		if (filteredEquipamentoMaquinas != null) {
			
			filteredEquipamentoMaquinas.forEach(em -> {
				
				Equipamento equipamento = em.getEquipamento();
				
				if (equipamento != null){
					equipamentos.add(equipamento);
				}
				
			});
			
			if (equipamentos.size() == 0) 
				throw new FacesValidateException("Máquina não possui Equipamentos vinculados a ela!", COMBOBOX_EQUIPAMENTO_ID);
			
			ordenaEquipamentos();
		}
		
		
	}

	private void ordenaEquipamentos() {
		if (equipamentos.size() > 0) {
			
			Collections.sort(equipamentos, (x1, x2) -> x1.getDescricao().compareTo(x2.getDescricao()));
			
		}
	}
	
	public void onChangeEquipamento() {
		
		try {
			
			if (equipamento != null) {
				
				Optional<EquipamentoMaquina> findFirst = filteredEquipamentoMaquinas.stream().filter(em -> em.getEquipamento().equals(equipamento)).findFirst();
					
				if (findFirst.isPresent()) {
					
					setTipoInspecao(findFirst.get().getTipoInspecao());
					
					if (getTipoInspecao() == null) throw new FacesValidateException("Tipo de Inspeção não vinculada ao equipamento selecionado!", INPUT_TIPO_INSPECAO_ID);
					
					loadItensReceita();
				
				}
				
				setValidComponent(INPUT_TIPO_INSPECAO_ID, true);
				
			}
			
		} catch (FacesValidateException e) {
			
			addErrorMessage(e.getMessage());
			
		}
		
	}

	private void loadItensReceita() {
		
		List<Receita> fromDBReceitas = ejb.findReceitasFetchByMaquinaAndEquipamentoAndTipoInspecao(maquina, equipamento, tipoInspecao);
		
		if (fromDBReceitas != null) {
			
			ArrayList<Receita> buffer = new ArrayList<Receita>();
			
			buffer.forEach(r -> {
				
				for (int i = 0; i < fromDBReceitas.size(); i++) {
					
					Receita curr = fromDBReceitas.get(i);
					
					Long bufIdFuncaoConfig = r.getConfigEquipamento().getFuncaoConfig().getIdFuncaoConfig();
					Long currIdFuncaoConfig = curr.getConfigEquipamento().getFuncaoConfig().getIdFuncaoConfig();
					
					
				}
				
			});
			
		}
		
	}

}
