package br.com.prati.tim.collaboration.gmp.mb;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

public class UtilsMessage {

	public static void addErrorMessage(String errorMessage) {
		addMessage(null, errorMessage, FacesMessage.SEVERITY_ERROR);
	}

	public static void addErrorMessage(String errorMessage, String componentId) {
		addMessage(componentId, errorMessage, FacesMessage.SEVERITY_ERROR);
	}

	public static void addInfoMessage(String infoMessage) {
		addMessage(null, infoMessage, FacesMessage.SEVERITY_INFO);
	}

	public static void addWarningMessage(String warningMessage) {
		addMessage(null, warningMessage, FacesMessage.SEVERITY_WARN);
	}

	private static void addMessage(String componentId, String errorMessage, FacesMessage.Severity severity) {

		FacesMessage message = new FacesMessage(errorMessage);
		message.setSeverity(severity);
		FacesContext.getCurrentInstance().addMessage(componentId, message);

	}
	
	public static void showMessageInDialog(String errorMessage) {

		FacesMessage message = new FacesMessage("Atenção", errorMessage);
		message.setSeverity(FacesMessage.SEVERITY_ERROR);

		RequestContext.getCurrentInstance().showMessageInDialog(message);
	}

	public static void showWarningInDialog(String errorMessage) {
		
		FacesMessage message = new FacesMessage("Atenção", errorMessage);
		message.setSeverity(FacesMessage.SEVERITY_WARN);
		
		RequestContext.getCurrentInstance().showMessageInDialog(message);
	}

	public static void showMessageInDialog(String errorMessage, FacesMessage.Severity severity) {
		
		FacesMessage message = new FacesMessage("Atenção", errorMessage);
		message.setSeverity(severity);
		
		RequestContext.getCurrentInstance().showMessageInDialog(message);
	}

}
