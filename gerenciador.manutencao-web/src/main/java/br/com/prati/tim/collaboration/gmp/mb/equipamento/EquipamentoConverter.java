package br.com.prati.tim.collaboration.gmp.mb.equipamento;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.MenuConfig;

@FacesConverter(forClass=MenuConfig.class, value="equipamentoConverter")
public class EquipamentoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if (value != null && !value.isEmpty()) {
            
        	Equipamento Equipamento = (Equipamento) component.getAttributes().get(value);
            
            return Equipamento;
        }
        
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,	Object value) {
		
		if (value instanceof Equipamento) {

			Equipamento entity = (Equipamento) value;

			if (entity != null && entity instanceof Equipamento && entity.getDescricao() != null) {

				component.getAttributes().put(entity.getDescricao(), entity);

				return entity.getDescricao();
			}

		}
		
		return null;
	}

}
