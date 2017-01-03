package br.com.prati.tim.collaboration.gmp.mb.login;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.prati.tim.collaboration.gmp.configuracaogeral.ConfiguracaoGeralEJB;
import br.com.prati.tim.collaboration.gmp.mb.UtilsMessage;
import br.com.prati.tim.collaboration.gmp.mb.resources.MD5Gen;
import br.com.prati.tim.collaboration.gmp.ws.DynamicServiceLocator;
import br.prati.tim.collaboration.gp.jpa.ConfiguracaoGeral;
import br.prati.tim.gmp.ws.usuario.AlterarSenhaParams;
import br.prati.tim.gmp.ws.usuario.AutParams;
import br.prati.tim.gmp.ws.usuario.ETipoAcesso;
import br.prati.tim.gmp.ws.usuario.Papel;
import br.prati.tim.gmp.ws.usuario.RecuperacaoSenhaParams;
import br.prati.tim.gmp.ws.usuario.RetornoAutenticacao;
import br.prati.tim.gmp.ws.usuario.StatusAut;
import br.prati.tim.gmp.ws.usuario.UsuarioService;
import br.prati.tim.gmp.ws.usuario.UsuarioWS;
import br.prati.tim.gmp.ws.usuario.ViewAcesso;

@Named("mbLogin")
@ViewScoped
public class MBLogin implements Serializable{

	private static final long		serialVersionUID			= 1L;

	private boolean					showLogin					= true;
	private boolean					showEsqueciSenha			= false;
	private boolean					showRedefinirSenha			= false;
	private boolean					showRedefinirSenhaCodigo	= false;

	// Utilizado no panel Login
	private Long					cracha;
	private String					senha;

	// Utilizado no panel Redefinicao/Esqueci senha
	private Integer					crachaAjuda;
	private String					senhaAjuda;

	private String					novaSenha;
	private String					confirmacaoNovaSenha;
	private Integer					codigoSeguranca;

	private String					nomeUsuario;
	
	private List<ViewAcesso>		acessosUsuario;

	private String					msgAlerta;

	private RetornoAutenticacao     autenticarUsuario;


	@Inject
	private ConfiguracaoGeralEJB	configEJB;
	
	public String autenticar(){
		
		AutParams parametros = new AutParams();
		
		parametros.setAcao          (ETipoAcesso.AUTENTICAR);
		parametros.setNomeFuncao	("AUTENTICAR");
		parametros.setCrachaUsuario	(cracha);
		parametros.setNomeSistema	("GMP");
		parametros.setUsuarioSenha	(MD5Gen.getMD5(senha));
		
		UsuarioWS usuarioWS = null;
		
		try {
			
			usuarioWS =  getUsuarioService();
			
			autenticarUsuario = usuarioWS.autenticarUsuario(parametros);
			
			this.nomeUsuario = autenticarUsuario.getCadUsuario().getNome();
						
			if (autenticarUsuario.getStatusAutenticacao() == StatusAut.AUTORIZADO){

				SessionUtil.getSession().setAttribute("cracha", 	 	cracha);
				SessionUtil.getSession().setAttribute("nomeUsuario", 	nomeUsuario);
				SessionUtil.getSession().setAttribute("acessosUsuario",  autenticarUsuario.getAcessoList());

				return redirect();
			} else if (autenticarUsuario.getStatusAutenticacao() == StatusAut.ALTERAR_SENHA_PROXIMO_LOGIN){
				
				msgAlerta = autenticarUsuario.getMensagem();
				
				setShowLogin(false);
				setShowRedefinirSenha(true);
				
				RequestContext.getCurrentInstance().execute("PF('dlgRedefinicaoSenha').show();");
				
			} else if (autenticarUsuario.getStatusAutenticacao() == StatusAut.ALERTA_EXPIRAR_SENHA){
				
				msgAlerta = autenticarUsuario.getMensagem();
				
				RequestContext.getCurrentInstance().execute("PF('dlgAlerta').show();");
			}
			
		} catch (Exception e) {
		    
		    this.senha = null;
		    
			if (usuarioWS == null){
				UtilsMessage.showMessageInDialog("Falha ao efetuar tentativa de conexão com o WebService do Gerenciador de Usuários da Mecatrônica para autenticação. " +
												 e.getMessage(), 
												 FacesMessage.SEVERITY_ERROR);
			}else{
				UtilsMessage.showMessageInDialog(e.getMessage(), FacesMessage.SEVERITY_ERROR);
			}
		}
		
		return null;
	}

	public String redirect(){
		
		SessionUtil.getSession().setAttribute("cracha", cracha);
		return "/dashboard/resumo?faces-redirect=true";
		
	}
	
	public List<Papel> getPapeisSistema(String sistema){
		
		try {
			UsuarioWS usuarioWS =  getUsuarioService();
			return usuarioWS.retornaPapeisPorSistema(sistema);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private UsuarioWS getUsuarioService() throws Exception {
	    
	    ConfiguracaoGeral ipGUM = configEJB.getByChave("ip_ws_usuarios");
	    
        String ipServidorGUM = ipGUM == null || ipGUM.getValor().isEmpty() ? "localhost:8080" : ipGUM.getValor(); 
	    
		UsuarioWS usuarioWS = new DynamicServiceLocator(ipServidorGUM).getPort(UsuarioWS.class, UsuarioService.class);
		return usuarioWS;
	}
	
    public void logout() {
        
    	ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        
        ec.invalidateSession();
       
        try {
        	
            ec.redirect(ec.getRequestContextPath() + "/login.xhtml");
            
        } catch (Exception ex) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao sair do sistema. Erro: " + ex.getMessage(), null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        
    }

	public void salvarRedefinicaoSenha(){
		
		try {
			
			UsuarioWS usuarioWS = getUsuarioService();
			
			AlterarSenhaParams parametros = new AlterarSenhaParams();
			
			parametros.setCracha              (Long.parseLong (crachaAjuda.toString()));
			parametros.setSenhaAtual          (senhaAjuda);
			parametros.setNovaSenha           (novaSenha);
			parametros.setConfirmacaoSenha    (confirmacaoNovaSenha);
			
			usuarioWS.alterarSenhaSemCodigoRecuperacao(parametros);
			
			setShowRedefinirSenha(false);
			setShowLogin(true);
			
			addMessage("Redefinição de senha realizada com sucesso!", FacesMessage.SEVERITY_INFO);
			
			limparFields();
			
		} catch (Exception e) {
			UtilsMessage.showMessageInDialog(e.getMessage(), FacesMessage.SEVERITY_ERROR);
		}
		
	}
	
	public void enviarCodigo(){
		
		
		try {
			UsuarioWS usuarioWS = getUsuarioService();
			
			usuarioWS.solicitarCodigoRecuperacaoSenha(Long.parseLong(crachaAjuda.toString()));
			
			setShowEsqueciSenha(false);
			setShowRedefinirSenhaCodigo(true);
			
			addMessage("Solicitação de código efetuada com sucesso!", FacesMessage.SEVERITY_INFO);
			
		} catch (Exception e) {
			UtilsMessage.showMessageInDialog(e.getMessage(), FacesMessage.SEVERITY_ERROR);
		} 
		
	}
	
	public void salvarRedefinicaoSenhaCodigo(){
		
		
		
		try {

			UsuarioWS usuarioWS = getUsuarioService();
			
			RecuperacaoSenhaParams parametros = new RecuperacaoSenhaParams();
			
			parametros.setCodigoRecuperacao   (codigoSeguranca);
			parametros.setCracha              (Long.parseLong(crachaAjuda.toString()));
			parametros.setSenha               (novaSenha);
			parametros.setConfirmacaoSenha    (confirmacaoNovaSenha);
			
			usuarioWS.alterarSenhaComCodigoRecuperacao(parametros);
			
			setShowRedefinirSenhaCodigo(false);
			setShowLogin(true);
			
			addMessage("Senha alterada com sucesso!", FacesMessage.SEVERITY_INFO);
			
			limparFields();

		} catch (Exception e) {
			UtilsMessage.showMessageInDialog(e.getMessage(), FacesMessage.SEVERITY_ERROR);
		} 
		
	}
	
	public String getPanelsLogin(){
		return "panelLogin "
			 + "panelEsqueciSenha "
			 + "formLogin ";
	}
	
	public Long getCracha() {
		return cracha;
	}
	public void setCracha(Long cracha) {
		this.cracha = cracha;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmacaoNovaSenha() {
		return confirmacaoNovaSenha;
	}

	public void setConfirmacaoNovaSenha(String confirmacaoNovaSenha) {
		this.confirmacaoNovaSenha = confirmacaoNovaSenha;
	}

	public Integer getCodigoSeguranca() {
		return codigoSeguranca;
	}

	public void setCodigoSeguranca(Integer codigoSeguranca) {
		this.codigoSeguranca = codigoSeguranca;
	}

	public Integer getCrachaAjuda() {
		return crachaAjuda;
	}

	public void setCrachaAjuda(Integer crachaAjuda) {
		this.crachaAjuda = crachaAjuda;
	}

	public String getSenhaAjuda() {
		return senhaAjuda;
	}

	public void setSenhaAjuda(String senhaAjuda) {
		this.senhaAjuda = senhaAjuda;
	}

	private void addMessage(String errorMessage,FacesMessage.Severity severity) {
		FacesMessage message = new FacesMessage(errorMessage);
		message.setSeverity(severity);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public String getMsgAlerta() {
		return msgAlerta;
	}

	public void setMsgAlerta(String msgAlerta) {
		this.msgAlerta = msgAlerta;
	}

	public boolean isShowLogin() {
		return showLogin;
	}

	public void setShowLogin(boolean showLogin) {

		limparFields();

		this.showLogin = showLogin;
	}


	public boolean isShowRedefinirSenha() {
		return showRedefinirSenha;
	}

	public void setShowRedefinirSenha(boolean showRedefinirSenha) {
		limparFields();
		this.showRedefinirSenha = showRedefinirSenha;
	}

	public boolean isShowRedefinirSenhaCodigo() {
		return showRedefinirSenhaCodigo;
	}

	public void setShowRedefinirSenhaCodigo(boolean showRedefinirSenhaCodigo) {
		this.showRedefinirSenhaCodigo = showRedefinirSenhaCodigo;
	}

	public boolean isShowEsqueciSenha() {
		return showEsqueciSenha;
	}

	public void setShowEsqueciSenha(boolean showEsqueciSenha) {
		this.showEsqueciSenha = showEsqueciSenha;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public List<ViewAcesso> getAcessosUsuario() {
		return acessosUsuario;


	}

	public void setAcessosUsuario(List<ViewAcesso> acessosUsuario) {
		this.acessosUsuario = acessosUsuario;
		
	}
	
	public RetornoAutenticacao getAutenticarUsuario() {
		return autenticarUsuario;
	}

	public void setAutenticarUsuario(RetornoAutenticacao autenticarUsuario) {
		this.autenticarUsuario = autenticarUsuario;
	}

	private void limparFields() {
		cracha 			= null;
		crachaAjuda		= null;
		codigoSeguranca	= null;
	}

}

