package br.com.prati.tim.collaboration.gmp.mb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

public abstract class ABaseMBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 201119042011L;

	public abstract String getCaminhoDialogPesquisar();

	/**
	 * 
	 * Retorna o nome o do formulário da View
	 * @return 
	 */
	public abstract String getNomeFormulario();

	public abstract void initObjects();
	
	public abstract void salvar();

	public abstract void excluir();

	public abstract void inativarAtivar();

	public abstract void limpar();

	public abstract void selecionarObjetoPosPesquisa(SelectEvent event);

	
	public void showMensagemSucessoSalvar() {
		UtilsMessage.addInfoMessage("Cadastro salvo com sucesso.");
	}

	public void showMensagemSucessoConsulta() {
		UtilsMessage.addInfoMessage("Consulta realizada com sucesso.");
	}

	public void showMensagemAlterarSituacaoSucesso() {
		UtilsMessage.addInfoMessage("Situação alterada com sucesso.");
	}

	public void showMensagemExclusaoSucesso() {
		UtilsMessage.addInfoMessage("Exclusão realizada com sucesso.");
	}
	
	public static void addErrorMessage(String errorMessage) {
		UtilsMessage.addErrorMessage(errorMessage);
	}

	public static void addErrorMessage(String errorMessage, String componentId) {
		UtilsMessage.addErrorMessage(errorMessage, componentId);
	}

	public static void addInfoMessage(String infoMessage) {
		UtilsMessage.addInfoMessage(infoMessage);
	}

	public static void addWarningMessage(String warningMessage) {
		UtilsMessage.addWarningMessage(warningMessage);
	}

	public void showMessageInDialog(String errorMessage) {
		UtilsMessage.showMessageInDialog(errorMessage);
	}

	public RequestContext getRequestContext() {
		return RequestContext.getCurrentInstance();
	}
	
	/**
	 * Retorna o caminho relativo do endereço do dialog de pesquisa
	 */
    public void pesquisar() {
        
        limpar();

        Map<String, Object> options = getParamsDialogPesquisa();
        
        RequestContext.getCurrentInstance().openDialog(getCaminhoDialogPesquisar(), options, null);
        
    }

	public static Map<String, Object> getParamsDialogPesquisa() {
		
		Map<String,Object> options = new HashMap<String, Object>();

        options.put("modal",        true);
        options.put("resizable",    false);
        options.put("width", 		780);
        options.put("contentWidth", 738);
        options.put("responsive", 	true);
        
		return options;
		
	}

	public UIInput getUIComponentById(String id) {

		UIInput component = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent(id);

		return component;
	}
	
	public static void  setValidComponents(List<UIComponent> children) {
		
		for (UIComponent uiComponent : children) {

			if (uiComponent.getChildren().isEmpty()) {

				if (uiComponent instanceof UIInput) {
					((UIInput) uiComponent).setValid(true);
				}

			} else {
				setValidComponents(uiComponent.getChildren());
			}

		}
	}

}