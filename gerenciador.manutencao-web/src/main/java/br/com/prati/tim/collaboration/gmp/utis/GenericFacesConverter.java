package br.com.prati.tim.collaboration.gmp.utis;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.prati.tim.collaboration.gp.jpa.converters.FacesUIConverter;


/**
 * Conversor genérico dos Componentes JSF
 * 
 * @author ewerton.costa
 *
 */
@FacesConverter(forClass = FacesUIConverter.class)
public class GenericFacesConverter implements Converter {
   
	@SuppressWarnings("rawtypes")
	@Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (FacesUIConverter) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
	@Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof FacesUIConverter) {
        	FacesUIConverter entity= (FacesUIConverter) value;
            if (entity != null && entity instanceof FacesUIConverter && entity.getId() != null) {
                uiComponent.getAttributes().put( entity.getId().toString(), entity);
                return entity.getId().toString();
            }
        }
        return "";
    }
}