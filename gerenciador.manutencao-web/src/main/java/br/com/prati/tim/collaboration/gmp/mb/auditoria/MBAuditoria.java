package br.com.prati.tim.collaboration.gmp.mb.auditoria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.mb.UtilsMessage;
import br.com.prati.tim.collaboration.gmp.mb.login.SessionUtil;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;

@Named("mbAuditoria")
@ViewScoped
public class MBAuditoria implements Serializable{
	
	private static final long				serialVersionUID		= 1L;

	private boolean							exibirFiltros			= true;

	private boolean							exibirResultadoPesquisa	= true;

	private boolean							exibirDetalhes			= false;

	private List<String>					tabelasAuditaveis		= new ArrayList<String>();

	private List<String>					colunasTabela			= new ArrayList<String>();

	private String							chaveTabela;

	private List<String>					colunasTabelaAuditada	= new ArrayList<String>();

	private String							idEntidadeSelecionada;

	private List<HashMap<String, String>>	registros				= new ArrayList<HashMap<String, String>>();

	private List<HashMap<String, String>>	detalhamentoRegistros	= new ArrayList<HashMap<String, String>>();

	private AuditoriaHelper					auditoriaHelper			= new AuditoriaHelper();

	private String							tabelaSelecionada;

	private Date							dataInicial;

	private Date							dataFinal;
	
	@PostConstruct
	public void init(){
		
		if (tabelaSelecionada != null){
			consultar();
		}
			
	}
	public void consultar(){
		
		if (!SessionUtil.temPermissaoGUM(ETipoAcessoGUM.CONSULTA)){
			UtilsMessage.addErrorMessage("Usuário sem permissão de " + ETipoAcessoGUM.CONSULTA.getDescricao() + ".");
			return;
		}
		
		chaveTabela = auditoriaHelper.retornarColunaPK(tabelaSelecionada);
		registros = auditoriaHelper.retornaRegistrosMaster(tabelaSelecionada, dataInicial, dataFinal);
		exibicaoPanels(true, true, false);
	}
	
	public void consultarDetalhes(){
		detalhamentoRegistros = auditoriaHelper.retornarRegistrosDetail(tabelaSelecionada, idEntidadeSelecionada, dataInicial, dataFinal);
		exibicaoPanels(false, false, true);
	}
	
	public void resetarConsulta(){
		registros = new ArrayList<HashMap<String,String>>();
		tabelaSelecionada = "";
		chaveTabela = "";
		setColunasTabela(new ArrayList<String>());
		colunasTabelaAuditada = new ArrayList<String>();
	}
	
	public void resetarDetalhes(){
		idEntidadeSelecionada = "";
		detalhamentoRegistros = new ArrayList<HashMap<String,String>>();
		exibicaoPanels(true, true, false);
	}
	
	public Set<String> getColumnNames(){
        return (registros.size() > 0 ? registros.get(0).keySet() : new HashSet<String>());
    }
	
	public List<String> getColumnNamesDetail(){
		
		if (detalhamentoRegistros.size() > 0 && detalhamentoRegistros.get(0).keySet().size() > 0){
			
			List<String> colunasOrdenadas  = new ArrayList<String>();
			
			colunasOrdenadas.add(AuditoriaHelper.COLUNA_ACAO); 
			colunasOrdenadas.add(AuditoriaHelper.COLUNA_TIMESTAMP);
			colunasOrdenadas.add(AuditoriaHelper.COLUNA_USUARIO	);
			colunasOrdenadas.add(AuditoriaHelper.COLUNA_REVISAO); 

			for (String s :  detalhamentoRegistros.get(0).keySet()) {
				if (!s.equals(AuditoriaHelper.COLUNA_ACAO) &&
						!s.equals(AuditoriaHelper.COLUNA_TIMESTAMP) &&
						!s.equals(AuditoriaHelper.COLUNA_USUARIO) &&
						!s.equals(AuditoriaHelper.COLUNA_REVISAO)){
					colunasOrdenadas.add(s);
				}
			}
	        return colunasOrdenadas;
		} else {
			return new ArrayList<String>();
		}
		
    }
	
	public List<String> getColunasTabela() {
		colunasTabela = auditoriaHelper.retornarColunasTabela(tabelaSelecionada, "public");
		return colunasTabela;
	}
	
	public void setColunasTabela(List<String> colunasTabela) {
		this.colunasTabela = colunasTabela;
	}
	
	public String getTabelaSelecionada() {
		return tabelaSelecionada;
	}

	public void setTabelaSelecionada(String tabela_selecionada) {
		this.tabelaSelecionada = tabela_selecionada;
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

	public List<HashMap<String, String>> getRegistros() {
		return registros;
	}

	public void setRegistros(List<HashMap<String, String>> registros) {
		this.registros = registros;
	}
	
	public List<String> getTabelasAuditaveis() {
		if(tabelasAuditaveis.size() == 0)
			return tabelasAuditaveis = auditoriaHelper.retornarTabelasAuditaveis();
		else
			return tabelasAuditaveis;
	}
	public void setTabelasAuditaveis(List<String> tabelasAuditaveis) {
		this.tabelasAuditaveis = tabelasAuditaveis;
	}
	public String getChaveTabela() {
		chaveTabela = auditoriaHelper.retornarColunaPK(tabelaSelecionada);
		return chaveTabela;
	}
	public void setChaveTabela(String chaveTabela) {
		this.chaveTabela = chaveTabela;
	}
	public List<String> getColunasTabelaAuditada() {
		return colunasTabelaAuditada;
	}
	public void setColunasTabelaAuditada(List<String> colunasTabelaAuditada) {
		this.colunasTabelaAuditada = colunasTabelaAuditada;
	}
	public List<HashMap<String, String>> getDetalhamentoRegistros() {
		return detalhamentoRegistros;
	}
	public void setDetalhamentoRegistros(
			List<HashMap<String, String>> detalhamentoRegistros) {
		this.detalhamentoRegistros = detalhamentoRegistros;
	}
	public String getIdEntidadeSelecionada() {
		return idEntidadeSelecionada;
	}
	public void setIdEntidadeSelecionada(String idEntidadeSelecionada) {
		this.idEntidadeSelecionada = idEntidadeSelecionada;
	}
	
	private void exibicaoPanels(boolean filtros, boolean resultadoPesquisa, boolean detalhes){
		setExibirFiltros(filtros);
		setExibirResultadoPesquisa(resultadoPesquisa);
		setExibirDetalhes(detalhes);
	}
	
	public boolean isExibirFiltros() {
		return exibirFiltros;
	}
	
	public void setExibirFiltros(boolean exibirFiltros) {
		this.exibirFiltros = exibirFiltros;
	}
	
	public boolean isExibirResultadoPesquisa() {
		return exibirResultadoPesquisa;
	}
	
	public void setExibirResultadoPesquisa(boolean exibirResultadoPesquisa) {
		this.exibirResultadoPesquisa = exibirResultadoPesquisa;
	}
	
	public boolean isExibirDetalhes() {
		return exibirDetalhes;
	}
	
	public void setExibirDetalhes(boolean exibirDetalhes) {
		this.exibirDetalhes = exibirDetalhes;
	}
	
}
