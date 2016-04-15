package br.com.prati.tim.collaboration.gmp.mb.tipocomponente;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.prati.tim.collaboration.gp.jpa.enumerator.EComponentConverter;

public class EComponenteConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if (value != null && !value.isEmpty()) {
            
        	EComponentConverter menuConfig = (EComponentConverter) component.getAttributes().get(value);
            
            return menuConfig;
        }
        
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,	Object value) {
		
		if (value instanceof EComponentConverter) {

			EComponentConverter entity = (EComponentConverter) value;

			if (entity != null && entity instanceof EComponentConverter && entity.getClazzName() != null) {

				component.getAttributes().put(entity.getClazzName(), entity);

				return entity.getClazzName();
			}

		}
		
		return null;
	}

}
