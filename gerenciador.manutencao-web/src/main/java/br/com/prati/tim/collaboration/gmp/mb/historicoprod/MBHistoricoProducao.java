package br.com.prati.tim.collaboration.gmp.mb.historicoprod;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.historicoprod.HistoricoProducaoEJB;
import br.com.prati.tim.collaboration.gmp.ejb.historicoprod.RegistroProducao;
import br.com.prati.tim.collaboration.gmp.ejb.maquina.MaquinaEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractBaseMB;
import br.com.prati.tim.collaboration.gmp.mb.UtilsMessage;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.com.prati.tim.collaboration.gmp.mb.login.SessionUtil;
import br.prati.tim.collaboration.gp.jpa.Lote;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;

@Named("mbHistoricoProducao")
@ViewScoped
public class MBHistoricoProducao extends AbstractBaseMB {

	private static final long		serialVersionUID	= 1L;

	@Inject
	private HistoricoProducaoEJB	ejbHistorico;

	@Inject
	private MaquinaEJB				ejbMaquina;
	
	private Lote					lote;

	private Maquina					maquina;

	private Date					dataInicial;

	private Date					dataFinal;

	private List<RegistroProducao>	historico;
	
	private List<Maquina>			maquinas;

	@PostConstruct
	public void init() {
		
		setHistorico(new ArrayList<RegistroProducao>());
		setMaquinas(ejbMaquina.getCrudDAO().findActives());
	}
	
	public void find(){
		
		if (!isValid()){
			return;
		}
		
		try {
			historico = ejbHistorico.getHistoricoProducao(dataInicial, dataFinal, maquina, lote);
		} catch (SQLException e) {
			addErrorMessage("Erro ao consultar: " + e.getMessage());
			e.printStackTrace();
			return;
		}
		
		if (historico.isEmpty()){
			addInfoMessage("Registros não encontrados de acordo com os filtros informados.");
		}else{
			addInfoMessage("Consulta realizada com sucesso.");
		}
		
	}
	
	private boolean isValid(){
		
		if (!SessionUtil.temPermissaoGUM(ETipoAcessoGUM.CONSULTA)){
			UtilsMessage.addErrorMessage("Usuário sem permissão de " + ETipoAcessoGUM.CONSULTA.getDescricao() + ".");
			return false;
		}
		
		if (maquina == null){
			addErrorMessage("A máquina deve ser informada", "maquina");
			return false;
		}
		
		return true;
	}
	
	public void select(SelectEvent event){
		
		Object object = event.getObject();

		if (object != null && object.getClass().getName().equals(Maquina.class.getName())){
			
			Maquina maquinaSelecionada = (Maquina) object;
			
			if (maquinaSelecionada.equals(getMaquina())){
				return;
			}
			
			selectMaquina(maquinaSelecionada);		
			
		} else if (object != null && object.getClass().getName().equals(Lote.class.getName())){
			
			Lote loteSelecionado = (Lote) object;
			
			if (loteSelecionado.equals(getLote())){
				return;
			}
			
			selectLote(loteSelecionado);			
		}
	}

	private void selectMaquina(Maquina maquina){
		setMaquina(maquina);
	}
	
	private void selectLote(Lote lote){
		setLote(lote);
	}
	
	@Override
	public boolean validate(ComponentSystemEvent event) {
		return false;
	}

	@Override
	public ValidateComponent[] getValidaComponents() {
		return null;
	}

	public List<RegistroProducao> getHistorico() {
		return historico;
	}

	public void setHistorico(List<RegistroProducao> historico) {
		this.historico = historico;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public Maquina getMaquina() {
		return maquina;
	}

	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public List<Maquina> getMaquinas() {
		return maquinas;
	}

	public void setMaquinas(List<Maquina> maquinas) {
		this.maquinas = maquinas;
	}

}