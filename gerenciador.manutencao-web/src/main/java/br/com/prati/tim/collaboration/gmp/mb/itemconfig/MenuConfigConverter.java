package br.com.prati.tim.collaboration.gmp.mb.itemconfig;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.prati.tim.collaboration.gp.jpa.MenuConfig;

@FacesConverter(forClass=MenuConfig.class, value="menuConfigConverter")
public class MenuConfigConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if (value != null && !value.isEmpty()) {
            
        	MenuConfig menuConfig = (MenuConfig) component.getAttributes().get(value);
            
            return menuConfig;
        }
        
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,	Object value) {
		
		if (value instanceof MenuConfig) {

			MenuConfig entity = (MenuConfig) value;

			if (entity != null && entity instanceof MenuConfig && entity.getDescricao() != null) {

				component.getAttributes().put(entity.getDescricao(), entity);

				return entity.getDescricao();
			}

		}
		
		return null;
	}

}
