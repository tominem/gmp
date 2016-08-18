package br.com.prati.tim.collaboration.gmp.mb;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.context.RequestContext;

public abstract class AbstractBaseMB implements BaseMB, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5009617321552805539L;
	
	
	@Override
	public void clean() {
		getRequestContext().reset(getFormName());
	}	
	
	@Override
	public String getFormName(){
		return "formCad";
	}
	
	@Override
	public String getContentPane(){
		return "contentPane";
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
	
	public Map<String, Object> getParamsDialogPesquisa() {
		
		Map<String,Object> options = new HashMap<String, Object>();

        options.put("modal"			,  	true);
        options.put("resizable"		,  	false);
        options.put("width"			,	780);
        options.put("contentWidth"	, 	738);
        options.put("responsive"	, 	true);
        options.put("height"		, 	"450");
        
		return options;
		
	}
	
	public void openDialog(String path){
		
		openDialog(path, false);
		
	}
	
	/**
	 * Open a DialogFramework
	 * 
	 * @param path outcome for dialogview
	 * @param onlyActives flag that informs if search results should only be actives or not 
	 */
	public void openDialog(String path, boolean onlyActives){
		
		Map<String, Object> options = getParamsDialogPesquisa();
		
		Map<String, List<String>> params = null;
		
		if (onlyActives) {
			
			params = new HashMap<String, List<String>>();
			
			params.put("onlyActives", Arrays.asList("true"));
			
		}
		
		RequestContext.getCurrentInstance().openDialog(path, options, params);
		
	}
	
	protected PersistenceException isConstraintViolationException(Exception e) throws PersistenceException{
		Throwable t = e.getCause();
		while ((t != null) && !(t instanceof ConstraintViolationException)) {
		    t = t.getCause();
		}
		if (t instanceof ConstraintViolationException) {
			return new PersistenceException(t.getCause().getMessage());
		}
		else return null;
	}

}
