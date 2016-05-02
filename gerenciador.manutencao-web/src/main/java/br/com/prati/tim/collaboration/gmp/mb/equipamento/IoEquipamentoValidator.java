package br.com.prati.tim.collaboration.gmp.mb.equipamento;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("ioEquipamentoValidator")
public class IoEquipamentoValidator implements Validator {


	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		if(value == null) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tipo de equipamento requerido", null));
		}
		
	}

}
