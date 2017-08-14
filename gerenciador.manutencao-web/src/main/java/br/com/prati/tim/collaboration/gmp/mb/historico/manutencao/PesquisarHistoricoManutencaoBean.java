package br.com.prati.tim.collaboration.gmp.mb.historico.manutencao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.dao.historico.manutencao.HistoricoManutencaoEjb;
import br.com.prati.tim.collaboration.gmp.ejb.equipamento.EquipamentoEJB;
import br.com.prati.tim.collaboration.gmp.ejb.maquina.MaquinaEJB;
import br.com.prati.tim.collaboration.gmp.mb.UtilsMessage;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.HistManutencao;
import br.prati.tim.collaboration.gp.jpa.Maquina;

@ViewScoped
@Named("pesquisarHistoricoManutencaoBean")
public class PesquisarHistoricoManutencaoBean implements Serializable {

	/**
	 * 
	 */
	private static final long		serialVersionUID	= 1L;

	@Inject
	private MaquinaEJB				ejbMaquina;

	@Inject
	private EquipamentoEJB			equipamentoEJB;

	@Inject
	private HistoricoManutencaoEjb	historicoManutencaoEjb;

	private List<HistManutencao>	histManutencaoList;

	private HistManutencao			histManutencao;

	private HistoricoPesquisaParams	params;

	public PesquisarHistoricoManutencaoBean() {
		params = new HistoricoPesquisaParams();
	}
	public List<Maquina> maquinaComplete(String maquina) {

		List<Maquina> maquinas = ejbMaquina.getWithQueryLike(maquina, null);

		return maquinas;
	}

	public void processarPesquisaMaquina(SelectEvent event) {
		params.setMaquina((Maquina) event.getObject());
	}

	public void processarPesquisaEquipamento(SelectEvent event) {
		params.setEquipamento((Equipamento) event.getObject());
	}

	public List<Equipamento> equipamentoComplete(String tag) {
		return equipamentoEJB.getWithQueryLike(tag, null);
	}

	public void pesquisar() {
		
		try {
			histManutencaoList = historicoManutencaoEjb.pesquisar(params);
		} catch (Exception e) {
			UtilsMessage.showMessageInDialog(e.getMessage());
		}
	}

	public String getNotaPm() {
		return params.getNotaPm();
	}

	public void setNotaPm(String notaPm) {
		params.setNotaPm(notaPm);
	}

	public Maquina getMaquina() {
		return params.getMaquina();
	}

	public void setMaquina(Maquina maquina) {
		params.setMaquina(maquina);
	}

	public Equipamento getEquipamento() {
		return params.getEquipamento();
	}

	public void setEquipamento(Equipamento equipamento) {
		params.setEquipamento(equipamento);
	}

	public Date getDatahoraInicio() {
		return params.getDatahoraInicio();
	}

	public void setDatahoraInicio(Date datahoraInicio) {
		params.setDatahoraInicio(datahoraInicio);
	}

	public Date getDatahoraFim() {
		return params.getDatahoraFim();
	}

	public void setDatahoraFim(Date datahoraFim) {
		params.setDatahoraFim(datahoraFim);
	}

	public String getDescricao() {
		return params.getDescricao();
	}

	public void setDescricao(String descricao) {
		params.setDescricao(descricao);
	}

	public HistManutencao getHistManutencao() {
		return histManutencao;
	}

	public void setHistManutencao(HistManutencao histManutencao) {
		this.histManutencao = histManutencao;
	}

	public List<HistManutencao> getHistManutencaoList() {
		return histManutencaoList;
	}
	
	public void setarObjetoSelecionado() {
		RequestContext.getCurrentInstance().closeDialog(histManutencao);
	}
}
