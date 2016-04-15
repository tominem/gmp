package br.com.prati.tim.collaboration.gmp.dao.tipocomponente;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.prati.tim.collaboration.gp.jpa.TipoComponente;

@FacesConverter(forClass=TipoComponente.class, value="tipoComponenteConverter")
public class TipoComponenteConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if (value != null && !value.isEmpty()) {
            
        	TipoComponente menuConfig = (TipoComponente) component.getAttributes().get(value);
            
            return menuConfig;
        }
        
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,	Object value) {
		
		if (value instanceof TipoComponente) {

			TipoComponente entity = (TipoComponente) value;

			if (entity != null && entity instanceof TipoComponente && entity.getDescricao() != null) {

				component.getAttributes().put(entity.getDescricao(), entity);

				return entity.getDescricao();
			}

		}
		
		return null;
	}

}
