package br.com.prati.tim.collaboration.gmp.mb.tipocomponente;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.tipocomponente.CadTipoComponenteEJB;
import br.com.prati.tim.collaboration.gmp.ex.tipocomponente.VinculoTipoComponenteEhFuncaoConfigException;
import br.com.prati.tim.collaboration.gmp.mb.ABaseMBean;
import br.prati.tim.collaboration.gp.jpa.TipoComponente;

@Named("mbTpComponente")
@ViewScoped
public class MBTipoComponente extends ABaseMBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private CadTipoComponenteEJB tpComponenteEjb;

	private TipoComponente cadTipoComponente;

	@Override
	public void salvar() {

		this.cadTipoComponente = tpComponenteEjb.salvar(cadTipoComponente);

		limpar();

		showMensagemSucessoSalvar();

	}

	@Override
	public void excluir() {

		try {

			this.tpComponenteEjb.exluir(this.cadTipoComponente);

			limpar();

			showMensagemExclusaoSucesso();

		} catch (VinculoTipoComponenteEhFuncaoConfigException e) {
			showMessageInDialog("Não é possível excluir uma TipoComponente que possua vínculo à FuncaoConfig.");
		}

	}

	@Override
	public void inativarAtivar() {

		this.cadTipoComponente = this.tpComponenteEjb.alterarSituacao(this.cadTipoComponente);

		showMensagemAlterarSituacaoSucesso();
	}

	@Override
	public void limpar() {

		getRequestContext().reset(getNomeFormulario());

		this.cadTipoComponente = new TipoComponente();

	}

	@Override
	public void selecionarObjetoPosPesquisa(SelectEvent event) {

		Object object = event.getObject();

		if (object != null && object instanceof TipoComponente) {
			this.cadTipoComponente = (TipoComponente) object;
			showMensagemSucessoConsulta();
		}

	}

	@Override
	public String getCaminhoDialogPesquisar() {
		return "/cadastros/tipocomponente/pesquisarTipoComponente.xhtml";
	}

	@Override
	public String getNomeFormulario() {
		return "formCadTpComponente";
	}

	@Override
	@PostConstruct
	public void initObjects() {
		limpar();
	}
	
	public void validate(ComponentSystemEvent event) {
		
		FacesContext fc = FacesContext.getCurrentInstance();

		UIComponent components = event.getComponent();

		// get descricao
		UIInput uiInputDescricao = (UIInput) components.findComponent("descricao");
		String descricao = uiInputDescricao.getSubmittedValue() != null ? ""
				: uiInputDescricao.getLocalValue().toString();
		String descricaoId = uiInputDescricao.getClientId();

		// get nome
		UIInput uiInputNome = (UIInput) components.findComponent("nomeComponente");
		String nome = uiInputNome.getSubmittedValue() != null ? ""
				: uiInputNome.getLocalValue().toString();
		
		Map<String, Object> params = putParams(descricao, nome);
		
		//validate if object is present in db
		boolean exists = tpComponenteEjb.validaCadastroExistente(params);
		
		if (exists) {
			
			//invalidate inputs
			uiInputDescricao.setValid(false);
			uiInputNome.setValid(false);
			
			//add validation message
			FacesMessage msg = new FacesMessage("Tipo Componente já está cadastrado, "
						+ "com a mesma descrição, nome ou ambos");
			
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(descricaoId, msg);
			fc.renderResponse();
			
		}
		
	}

	private Map<String, Object> putParams(String descricao, String nomeComponente) {
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("descricao", descricao);
		params.put("nomeComponente", nomeComponente);
		
		return params;
	}

	public TipoComponente getCadTipoComponente() {
		return cadTipoComponente;
	}

	public void setCadTipoComponente(TipoComponente cadTipoComponente) {
		this.cadTipoComponente = cadTipoComponente;
	}

}