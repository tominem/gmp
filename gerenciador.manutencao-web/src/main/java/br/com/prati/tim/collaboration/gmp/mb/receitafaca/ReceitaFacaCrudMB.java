package br.com.prati.tim.collaboration.gmp.mb.receitafaca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleSelectEvent;
import org.primefaces.event.UnselectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.receitafaca.ReceitaFacaEJB;
import br.com.prati.tim.collaboration.gmp.ex.FacesValidateException;
import br.com.prati.tim.collaboration.gmp.mb.AbstractBaseMB;
import br.com.prati.tim.collaboration.gmp.mb.UtilsMessage;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.prati.tim.collaboration.gp.jpa.ConfigEquipamento;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.EquipamentoMaquina;
import br.prati.tim.collaboration.gp.jpa.Faca;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.ReceitaFaca;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;

@Named("receitaFacaMB")
@ViewScoped
public class ReceitaFacaCrudMB extends AbstractBaseMB implements Serializable {

	/**
	 * 
	 */
	private static final long			serialVersionUID		= -6966525816419190286L;

	private final String				COMBOBOX_EQUIPAMENTO_ID	= "formCad:equipamento";

	private final String				INPUT_FACA_ID	        = "formCad:faca";

	private final String				MAQUINA_COMPONENT_ID	= "formCad:maquina";

	@Inject
	private ReceitaFacaEJB				ejb;

	private Maquina						maquina;

	private Equipamento					equipamento;

	private Faca				        faca;
	
	private List<Faca>                  facas;

	private List<Equipamento>			equipamentos;

	private List<ReceitaFaca>			selectedReceitas;

	private List<ReceitaFaca>			receitas;

	private List<EquipamentoMaquina>	filteredEquipamentoMaquinas;

	/**
	 * Receitas to delete
	 */
	private List<ReceitaFaca>				receitasDel;



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
		
		faca = null;
		
		filteredEquipamentoMaquinas = null;
		
		equipamentos = null;
		
		facas = new ArrayList<Faca>();
		
		receitas = new ArrayList<ReceitaFaca>();
		
		selectedReceitas = new ArrayList<ReceitaFaca>();
		
		receitasDel = new ArrayList<ReceitaFaca>();
		
	}
	
	public void save(){
		
		try {
			
			if (maquina == null || maquina.getIdMaquina() == null) {
				
				throw new FacesValidateException("Máquina requerida!", MAQUINA_COMPONENT_ID);
			}
			
			if (selectedReceitas != null && selectedReceitas.size() > 0) {
				
				for (ReceitaFaca receitaFaca : selectedReceitas) {
					ejb.save(receitaFaca);
				}
				
				for (ReceitaFaca receitaFaca : receitasDel) {
					ejb.remove(receitaFaca);
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
				
				ReceitaFaca receita = new ReceitaFaca();
				receita.setConfigEquipamento(ce);
				receita.setFaca(getFaca());
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

	public Faca getFaca() {
		return faca;
	}

	public void setFaca(Faca faca) {
		this.faca = faca;
	}

	public Maquina getMaquina() {
		return maquina;
	}

	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}
	
	public List<ReceitaFaca> getSelectedReceitas() {
		return selectedReceitas;
	}

	public void setSelectedReceitas(List<ReceitaFaca> selectedReceitas) {
		this.selectedReceitas = selectedReceitas;
	}

	public List<ReceitaFaca> getReceitas() {
		return receitas;
	}

	public void setReceitas(List<ReceitaFaca> receitas) {
		this.receitas = receitas;
	}

	public List<Equipamento> getEquipamentos() {
		return equipamentos;
	}

	public void setEquipamentos(List<Equipamento> equipamentos) {
		this.equipamentos = equipamentos;
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
		
		faca = new Faca();
		faca.setDescricao("");
		
		filteredEquipamentoMaquinas = ejb.findEquipamentoMaquinaByMaquina(getMaquina());
		
		if (filteredEquipamentoMaquinas != null) {
			
			filteredEquipamentoMaquinas.forEach(em -> {
				
				Equipamento equipamento = em.getEquipamento();
				
				if (equipamento != null && equipamento.getStatus()){
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
			validatePermission(ETipoAcessoGUM.CONSULTA);
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			return;
		}
		
		try {
			
			receitas = new ArrayList<ReceitaFaca>();
			
			if (equipamento != null && equipamento.getIdEquipamento() != null) {
				
				Optional<EquipamentoMaquina> findFirst = filteredEquipamentoMaquinas.stream().filter(em -> em.getEquipamento().equals(equipamento)).findFirst();
					
				if (findFirst.isPresent()) {
					
					loadFacas();
					
					setFaca(facas.get(0));
					
					populateTable();

					loadItensReceita();
				
				}
				
			}
			
		} catch (FacesValidateException e) {
			
			addErrorMessage(e.getMessage());
			
		}			
				
	}

	private void loadFacas() throws FacesValidateException {
		
		facas = ejb.findAllFacas();
		
		if(facas == null || facas.size() == 0)
		{
			throw new FacesValidateException("Não existem Facas cadastradas no sistema", INPUT_FACA_ID);
		}
	}
	
	public void onChangeFaca(){
		
		try {
			validatePermission(ETipoAcessoGUM.CONSULTA);
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			return;
		}
		
		receitas = new ArrayList<ReceitaFaca>();
		
		populateTable();
		
		loadItensReceita();
		
	}

	private void loadItensReceita() {
		
		List<ReceitaFaca> fromDBReceitas = ejb.findReceitaFacasFetchByMaquinaAndEquipamentoAndFaca(maquina, equipamento, faca);
		
		if (fromDBReceitas != null && receitas.size() > 0) {
			
			ArrayList<ReceitaFaca> buffer = new ArrayList<ReceitaFaca>(receitas);
			
			for (int j = 0; j < buffer.size(); j++) {
				
				for (int i = 0; i < fromDBReceitas.size(); i++) {
					
					ReceitaFaca curr = fromDBReceitas.get(i);
					ReceitaFaca buff = buffer.get(j);
					
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
			
			if (x1.getIdReceitaFaca() != null && x2.getIdReceitaFaca() != null) 
				return 0;
			else if (x1.getIdReceitaFaca() != null)
				return -1;
			else
				return 1;
			
		});
		
	}
	
	public void onRowSelectReceita(SelectEvent event) {
		ReceitaFaca receita = (ReceitaFaca) event.getObject();
		
		if (receita.getIdReceitaFaca() != null) {
			receitasDel.remove(receita);
		} 
		
	}
 
    public void onRowUnselectReceita(UnselectEvent event) {
    	ReceitaFaca receita = (ReceitaFaca) event.getObject();

    	if (receita.getIdReceitaFaca() != null) {
			receitasDel.add(receita);
		}
    }

    public void onToggleSelect(ToggleSelectEvent event){
    	receitasDel.clear();
    	
    	if (!event.isSelected()) {
    		receitasDel = receitas.stream().filter(r -> r.getIdReceitaFaca() != null).collect(Collectors.toList());
    		
    		if (receitasDel == null) {
				receitasDel = new ArrayList<ReceitaFaca>();
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

	public List<Faca> getFacas() {
		return facas;
	}

	public void setFacas(List<Faca> facas) {
		this.facas = facas;
	}

}
