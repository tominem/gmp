package br.com.prati.tim.collaboration.gmp.mb.login;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("senhaValidator")
public class SenhaValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        String senha = String.valueOf(value);
        
        if(!senha.isEmpty() && senha.length() < 4) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "A nova senha dever ter no mínimo 4 caracteres", null));
        }
        
    }

}