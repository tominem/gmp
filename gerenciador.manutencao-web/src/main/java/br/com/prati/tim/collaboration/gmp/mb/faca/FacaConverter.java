package br.com.prati.tim.collaboration.gmp.mb.faca;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.prati.tim.collaboration.gp.jpa.Faca;

@FacesConverter(forClass=Faca.class, value="facaConverter")
public class FacaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if (value != null && !value.isEmpty()) {
            
        	Faca faca = (Faca) component.getAttributes().get(value);
            
            return faca;
        }
        
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,	Object value) {
		
		if (value instanceof Faca) {

			Faca entity = (Faca) value;

			if (entity != null && entity instanceof Faca && entity.getDescricao() != null) {

				component.getAttributes().put(entity.getDescricao(), entity);

				return entity.getDescricao();
			}

		}
		
		return null;
	}

}