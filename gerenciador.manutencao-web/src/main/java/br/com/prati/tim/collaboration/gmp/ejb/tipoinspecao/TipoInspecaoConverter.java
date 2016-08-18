package br.com.prati.tim.collaboration.gmp.ejb.tipoinspecao;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.prati.tim.collaboration.gp.jpa.TipoInspecao;;

@FacesConverter(forClass=TipoInspecao.class, value="tipoInspecaoConverter")
public class TipoInspecaoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if (value != null && !value.isEmpty()) {
            
			TipoInspecao tipoInspecao = (TipoInspecao) component.getAttributes().get(value);
            
            return tipoInspecao;
        }
        
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,	Object value) {
		
		if (value instanceof TipoInspecao) {

			TipoInspecao entity = (TipoInspecao) value;

			if (entity != null && entity instanceof TipoInspecao && entity.getDescricao() != null) {

				component.getAttributes().put(entity.getDescricao(), entity);

				return entity.getDescricao();
			}

		}
		
		return null;
	}

}
