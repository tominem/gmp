package br.com.prati.tim.collaboration.gmp.mb.historico.manutencao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.dao.historico.manutencao.HistoricoManutencaoEjb;
import br.com.prati.tim.collaboration.gmp.ejb.equipamento.EquipamentoEJB;
import br.com.prati.tim.collaboration.gmp.ejb.maquina.MaquinaEJB;
import br.com.prati.tim.collaboration.gmp.mb.UtilsMessage;
import br.com.prati.tim.collaboration.gmp.mb.login.SessionUtil;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.HistManutencao;
import br.prati.tim.collaboration.gp.jpa.HistManutencaoDetail;
import br.prati.tim.collaboration.gp.jpa.Maquina;

@Named("historicoManutencaoBean")
@ViewScoped
public class HistoricoManutencaoBean implements Serializable {

	/**
	 * 
	 */
	private static final long		serialVersionUID	= 1L;

	private static final Integer	EM_ANDAMENTO		= 1;
	private static final Integer	FINALIZADO			= 2;

	@Inject
	private MaquinaEJB					ejbMaquina;

	@Inject
	private EquipamentoEJB				equipamentoEJB;
	
	@Inject
	private HistoricoManutencaoEjb		historicoManutencaoEjb;

	private HistManutencao				histManutencao;

	private HistManutencaoDetail		histManutencaoDetail;

	public HistoricoManutencaoBean() {
	}

	@PostConstruct
	public void initObjects() {
		limpar();
	}

	public void limpar() {
		this.histManutencao = new HistManutencao();
		this.histManutencaoDetail = new HistManutencaoDetail();
	}

	public void selectObjectAfterSearch(SelectEvent event) {

	}

	public String getResourceDialogPath() {
		return "/historico/atendimento/searchhistoricomanutencao.xhtml";
	}

	public void save() {
		
		if (histManutencao.getDatahoraFim() != null) {
			histManutencao.setStatus(FINALIZADO);
		} else {
			histManutencao.setStatus(EM_ANDAMENTO);
		}
		
		histManutencao = historicoManutencaoEjb.salvar(histManutencao);
		UtilsMessage.showMessageInDialog("Histórico salvo com sucesso.");
	}

	public void clear() {

	}

	public List<Maquina> maquinaComplete(String maquina) {

		List<Maquina> maquinas = ejbMaquina.getWithQueryLike(maquina, null);

		return maquinas;
	}

	public void processarPesquisaMaquina(SelectEvent event) {

		Maquina maquina = (Maquina) event.getObject();

		if (!maquina.getStatus()) {
			this.histManutencao.setMaquina(null);
			UtilsMessage.showMessageInDialog("Máquina inativa.");
			return;
		}

	}

	public void processarPesquisaEquipamento(SelectEvent event) {

		Equipamento equipamento = (Equipamento) event.getObject();
		
		this.histManutencao.setEquipamento(equipamento);
		

	}
	
	public void pesquisar() {
		
	}
	
	public void addHistorico() {
		
		this.histManutencaoDetail.setCracha(SessionUtil.getCracha().longValue());
		
		this.histManutencaoDetail.setHistManutencao(histManutencao);

		this.histManutencao.getHistManutencaoDetailList().add(histManutencaoDetail);

		this.histManutencaoDetail = new HistManutencaoDetail();

	}

	public List<Equipamento> equipamentoComplete(String tag) {
		return equipamentoEJB.getWithQueryLike(tag, null);
	}

	public HistManutencao getHistManutencao() {
		return histManutencao;
	}

	public void setHistManutencao(HistManutencao histManutencao) {
		this.histManutencao = histManutencao;
	}

	public HistManutencaoDetail getHistManutencaoDetail() {
		return histManutencaoDetail;
	}
	
	public void setHistManutencaoDetail(HistManutencaoDetail histManutencaoDetail) {
		this.histManutencaoDetail = histManutencaoDetail;
	}

	
	public List<HistManutencaoDetail> getHistManutencaoDetailList() {
		return this.histManutencao.getHistManutencaoDetailList();
	}

}
