package br.com.prati.tim.collaboration.gmp.mb.configuracaogeral;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.configuracaogeral.ConfiguracaoGeralEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractBaseMB;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.prati.tim.collaboration.gp.jpa.ConfiguracaoGeral;
import br.prati.tim.collaboration.gp.jpa.Maquina;


@Named("mbConfiguracoesGerais")
@ViewScoped
public class MBConfiguracoesGerais extends AbstractBaseMB implements Serializable{

	private static final long		serialVersionUID		= 1L;

	private ConfiguracaoGeral		configuracao;

	@EJB
	private ConfiguracaoGeralEJB	configEJB;
	
	private Maquina					maquina;
	
	private String					tagSistema;

	private List<ConfiguracaoGeral>	configuracoes			= new ArrayList<ConfiguracaoGeral>();

	private List<ConfiguracaoGeral>	configuracoesRemover	= new ArrayList<ConfiguracaoGeral>();

	@PostConstruct
	public void init() throws Exception{
		
		configuracoes = configEJB.getByMaquinaAndSistema(maquina, tagSistema);
		
		limpar();
	}
		
	public void select(SelectEvent event){
			
		Object object = event.getObject();

		if (object != null	&& object.getClass().getName().equals(Maquina.class.getName())) {
			
			Maquina maquinaSelecionada = (Maquina) object;
			
			if (maquinaSelecionada.equals(getMaquina())) return;
			
			selectConfiguracoes(maquinaSelecionada);
			
		} 
	}
	
	public void selectSistema(AjaxBehaviorEvent event)  {
		
		selectConfiguracoes(maquina);
		
	}
	
	private void selectConfiguracoes(Maquina maquina){
		
		setMaquina(maquina);
		
		try {
			configuracoes = configEJB.getByMaquinaAndSistema(maquina, tagSistema);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void limpar(){
		configuracao 	= new ConfiguracaoGeral();
		maquina			= new Maquina();
		tagSistema		= null;		
	}
	
	public void limparEdicao(){
		configuracao 	= new ConfiguracaoGeral();
	}
	
	public void limparMaquina() throws Exception{
		setMaquina(null);
		
		configuracoes = configEJB.getByMaquinaAndSistema(maquina, tagSistema);
	}
	
	public void salvar(){
		
		configEJB.salvar(configuracoes, configuracoesRemover);
		
		configuracoes = configEJB.listActives();
		
		FacesContext.getCurrentInstance().getViewRoot().getViewMap().clear();
		
		addInfoMessage("Configurações Gerais salvas com sucesso!");
		
	}
	
	public void adicionar(){
		
		if (!isConfiguracaoValida(false)){
			return;
		}
		
		if (tagSistema != null && !tagSistema.isEmpty()){
			configuracao.setTagSistema(tagSistema);
		}
		if (maquina != null){
			configuracao.setMaquina(maquina);
		}
		
		configuracao.setDataRegistro(new Timestamp(new Date().getTime()));
		
		configuracoes.add(configuracao);
		
		limparEdicao();
		
	}
	
	public void salvarEdicao(){
		
		if (!isConfiguracaoValida(true)){
			return;
		}
		
		for (ConfiguracaoGeral configuracaoGeral : configuracoes) {
			if(configuracaoGeral.getChaveConfig().equals(configuracao.getChaveConfig())){
				configuracaoGeral.setChaveConfig(configuracao.getChaveConfig());
				configuracaoGeral.setValor(configuracao.getValor());
				configuracaoGeral.setDataRegistro(new Timestamp(new Date().getTime()));
			}
		}
		
		RequestContext.getCurrentInstance().execute("PF('configuracaoDialogWidget').hide()");
		
		limparEdicao();
		
	}
	
	private boolean isConfiguracaoValida(boolean edicao){
		
		if (configuracao.getChaveConfig() == null || configuracao.getChaveConfig().isEmpty()){
			addErrorMessage("A chave deve ser informada", getNomeFormulario()+":chave");
			return false;
		}
		
		if (configuracao.getValor() == null || configuracao.getValor().isEmpty()){
			addErrorMessage("O valor deve ser informado", getNomeFormulario()+":chave");
			return false;
		}
		
		if (!edicao){
			for (ConfiguracaoGeral configuracaoGeral : configuracoes) {
				if (configuracaoGeral.getChaveConfig().equals(configuracao.getChaveConfig())){
					addErrorMessage("A chave informada já existe.", getNomeFormulario()+":chave");
					return false;	
				}
			}
		}
		
		return true;
	}
	
	public void remover(){
	
		if (configuracao.getIdConfiguracao() != null){
			configuracoesRemover.add(configuracao);
		}
		
		configuracoes.remove(configuracao);
		
		limpar();
		
	}
	
	public void editar(){
		
		RequestContext.getCurrentInstance().execute("PF('configuracaoDialogWidget').show()");
		
	}
	
	public void clearConfig(){
		setConfiguracao(new ConfiguracaoGeral());
	}
	
	@Override
	public boolean validate(ComponentSystemEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ValidateComponent[] getValidaComponents() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ConfiguracaoGeral getConfiguracao() {
		return configuracao;
	}

	public void setConfiguracao(ConfiguracaoGeral configuracao) {
		this.configuracao = configuracao;
	}

	public List<ConfiguracaoGeral> getConfiguracoes() {
		return configuracoes;
	}

	public void setConfiguracoes(List<ConfiguracaoGeral> configuracoes) {
		this.configuracoes = configuracoes;
	}
	
	public String getNomeFormulario(){
		return "configuracoesForm";
	}

	public List<ConfiguracaoGeral> getConfiguracoesRemover() {
		return configuracoesRemover;
	}

	public void setConfiguracoesRemover(List<ConfiguracaoGeral> configuracoesRemover) {
		this.configuracoesRemover = configuracoesRemover;
	}

	public Maquina getMaquina() {
		return maquina;
	}

	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}

	public String getTagSistema() {
		return tagSistema;
	}

	public void setTagSistema(String tagSistema) {
		this.tagSistema = tagSistema;
	}

	

}
