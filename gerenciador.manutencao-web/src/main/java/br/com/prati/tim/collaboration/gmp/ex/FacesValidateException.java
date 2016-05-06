package br.com.prati.tim.collaboration.gmp.ex;

import static br.com.prati.tim.collaboration.gmp.utis.FacesUtis.getUIComponentById;
import static br.com.prati.tim.collaboration.gmp.utis.FacesUtis.setValidComponents;

import java.util.Arrays;

import javax.faces.component.UIInput;


public class FacesValidateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1023080187062097474L;
	

	public FacesValidateException() {
	}

	public FacesValidateException(String message) {
		super(message);
	}

	public FacesValidateException(Throwable cause) {
		super(cause);
	}

	public FacesValidateException(String message, Throwable cause) {
		super(message, cause);
	}

	public FacesValidateException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	public FacesValidateException(String message, UIInput ... inputs) {
		
		super( message = inputs != null && inputs.length == 1 ? 
				( inputs[0].getRequiredMessage() != null && !inputs[0].getRequiredMessage().isEmpty() ?
						inputs[0].getRequiredMessage() : message )
			: message);
		
		if (inputs != null) {
			setValidComponents(Arrays.asList(inputs), false);
		}
		
	}

	public FacesValidateException(String message, String componentId) {
		this(message, getUIComponentById(componentId));
	}

}
