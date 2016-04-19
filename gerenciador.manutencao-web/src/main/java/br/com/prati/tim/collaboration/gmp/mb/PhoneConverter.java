package br.com.prati.tim.collaboration.gmp.mb;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("phoneConverter")
public class PhoneConverter implements Converter{

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        String phoneNumber = (String) modelValue;
        StringBuilder formattedPhoneNumber = new StringBuilder();

        // ...

        return formattedPhoneNumber.toString();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        // Conversion is not necessary for now. However, if you ever intend to use 
        // it on input components, you probably want to implement it here.
        throw new UnsupportedOperationException("Not implemented yet");
    }

}