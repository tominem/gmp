package br.com.prati.tim.collaboration.gmp.mb.tipocomponente;

import javax.annotation.PostConstruct;
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
	private CadTipoComponenteEJB	tpComponenteEjb;
	private TipoComponente			cadTipoComponente;

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
		return "/cadastros/faca/pesquisarfaca.xhtml";
	}

	@Override
	public String getNomeFormulario() {
		return "formCadTpComponente";
	}
	
	@Override
	@PostConstruct
	public void initObjects() {
		limpar();
		cadTipoComponente.setDescricao("TEXTFIELD");
	}
	
	public TipoComponente getCadTipoComponente() {
		return cadTipoComponente;
	}

	public void setCadFaca(TipoComponente cadTpComponente) {
		this.cadTipoComponente = cadTpComponente;
	}

	
}