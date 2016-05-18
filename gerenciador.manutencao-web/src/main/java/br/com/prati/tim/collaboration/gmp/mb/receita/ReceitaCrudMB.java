package br.com.prati.tim.collaboration.gmp.mb.receita;

import static br.com.prati.tim.collaboration.gmp.utis.FacesUtis.setValidComponent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleSelectEvent;
import org.primefaces.event.UnselectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.receita.ReceitaEJB;
import br.com.prati.tim.collaboration.gmp.ex.FacesValidateException;
import br.com.prati.tim.collaboration.gmp.mb.AbstractBaseMB;
import br.com.prati.tim.collaboration.gmp.mb.UtilsMessage;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.prati.tim.collaboration.gp.jpa.ConfigEquipamento;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.EquipamentoMaquina;
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

	private final String COMBOBOX_EQUIPAMENTO_ID 	= "formCad:equipamento";

	private final String INPUT_TIPO_INSPECAO_ID 	= "formCad:tipoInspecao";

	private final String MAQUINA_COMPONENT_ID 		= "formCad:maquina";

	@Inject
	private ReceitaEJB ejb;

	private Maquina maquina;
	
	private Equipamento equipamento;

	private TipoInspecao tipoInspecao;

	private List<Equipamento> equipamentos;

	private List<Receita> selectedReceitas;

	private List<Receita> receitas;

	private List<EquipamentoMaquina> filteredEquipamentoMaquinas;

	/**
	 * Receitas to delete 
	 */
	private List<Receita> receitasDel;



	// =============== METHODS ===========================//

	@PostConstruct
	public void initObjects() {

		load();

	}
	
	@Override
	public void clean() {
		super.clean();
		load();
	}

	private void load() {
		
		maquina = null;
		
		equipamento = null;
		
		tipoInspecao = null;
		
		filteredEquipamentoMaquinas = null;
		
		equipamentos = null;
		
		receitas = new ArrayList<Receita>();
		
		selectedReceitas = new ArrayList<Receita>();
		
		receitasDel = new ArrayList<Receita>();
		
	}
	
	public void save(){
		
		try {
			
			if (maquina == null || maquina.getIdMaquina() == null) {
				
				throw new FacesValidateException("Máquina requerida!", MAQUINA_COMPONENT_ID);
			}
			
			if (selectedReceitas != null && selectedReceitas.size() > 0) {
				
				for (Receita receita : selectedReceitas) {
					ejb.save(receita);
				}
				
				for (Receita receita : receitasDel) {
					ejb.remove(receita);
				}

				clean();
				
				addInfoMessage("Receitas cadastradas com sucesso");
				
			}
			
			
		} catch (Exception e) {
			
			addErrorMessage(e.getMessage());
			
		}
		
	}
	
	private void populateTable() {
		
		try {
			
			List<ConfigEquipamento> configEquipamentos = ejb.findConfigEquipamentoFetchByEquipamento(getEquipamento());
			
			configEquipamentos.forEach(ce -> {
				
				Receita receita = new Receita(ce);
				receita.setTipoInspecao(getTipoInspecao());
				receita.setMaquina(getMaquina());
				
				receitas.add(receita);
				
			});
			
		} catch (Exception e) {
			
			if (e instanceof NoResultException) {
				addErrorMessage("Não existem itens de configuração cadastrados para o equipamento: " + getEquipamento().getNome());
			}
			else{
				addErrorMessage(e.getMessage());
			}
		}
		
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
			
			receitas = new ArrayList<Receita>();
			
			if (equipamento != null && equipamento.getIdEquipamento() != null) {
				
				Optional<EquipamentoMaquina> findFirst = filteredEquipamentoMaquinas.stream().filter(em -> em.getEquipamento().equals(equipamento)).findFirst();
					
				if (findFirst.isPresent()) {
					
					setTipoInspecao(findFirst.get().getTipoInspecao());
					
					if (getTipoInspecao() == null) throw new FacesValidateException("Tipo de Inspeção não vinculada ao equipamento selecionado!", INPUT_TIPO_INSPECAO_ID);
					
					populateTable();

					loadItensReceita();
				
					setValidComponent(INPUT_TIPO_INSPECAO_ID, true);
					
				}
				
			}
			
		} catch (FacesValidateException e) {
			
			addErrorMessage(e.getMessage());
			
		}
		
	}

	private void loadItensReceita() {
		
		List<Receita> fromDBReceitas = ejb.findReceitasFetchByMaquinaAndEquipamentoAndTipoInspecao(maquina, equipamento, tipoInspecao);
		
		if (fromDBReceitas != null && receitas.size() > 0) {
			
			ArrayList<Receita> buffer = new ArrayList<Receita>(receitas);
			
			for (int j = 0; j < buffer.size(); j++) {
				
				for (int i = 0; i < fromDBReceitas.size(); i++) {
					
					Receita curr = fromDBReceitas.get(i);
					Receita buff = buffer.get(j);
					
					Long bufIdFuncaoConfig 	= buff.getConfigEquipamento().getFuncaoConfig().getIdFuncaoConfig();
					Long currIdFuncaoConfig = curr.getConfigEquipamento().getFuncaoConfig().getIdFuncaoConfig();
					
					if (bufIdFuncaoConfig.equals(currIdFuncaoConfig)) {
						receitas.set(j, curr);
					}
					
				}
			}
			
			ordenaTableReceita();

			selectedReceitas = fromDBReceitas;
			
		}
		
	}

	private void ordenaTableReceita() {
		ordenaReceitasPorOrdem();
		
		ordenaReceitasPorSelecao();
	}
	
	private void ordenaReceitasPorOrdem(){
		Collections.sort(receitas, (x1, x2) -> {
			
			if (x1.getOrdem() != null && x2.getOrdem() != null) {
				return Integer.compare(x1.getOrdem(), x2.getOrdem());
			}
			
			else if(x2.getOrdem() == null) 
				return -1;

			else return 1;
			
		});
	}

	private void ordenaReceitasPorSelecao() {
		
		Collections.sort(receitas, (x1, x2) -> {
			
			if (x1.getIdReceita() != null && x2.getIdReceita() != null) 
				return 0;
			else if (x1.getIdReceita() != null)
				return -1;
			else
				return 1;
			
		});
		
	}
	
	public void onRowSelectReceita(SelectEvent event) {
		Receita receita = (Receita) event.getObject();
		
		if (receita.getIdReceita() != null) {
			receitasDel.remove(receita);
		} 
		
	}
 
    public void onRowUnselectReceita(UnselectEvent event) {
    	Receita receita = (Receita) event.getObject();

    	if (receita.getIdReceita() != null) {
			receitasDel.add(receita);
		}
    }

    public void onToggleSelect(ToggleSelectEvent event){
    	receitasDel.clear();
    	
    	if (!event.isSelected()) {
    		receitasDel = receitas.stream().filter(r -> r.getIdReceita() != null).collect(Collectors.toList());
    		
    		if (receitasDel == null) {
				receitasDel = new ArrayList<Receita>();
			}
		}
    }
    
	@Override
	public ValidateComponent[] getValidaComponents() {
		return null;
	}

	@Override
	public boolean validate(ComponentSystemEvent event) {
		return false;
	}

}
