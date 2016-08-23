package br.com.prati.tim.collaboration.gmp.ejb.maquina;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.prati.tim.collaboration.gp.jpa.Maquina;

@FacesConverter(forClass = Maquina.class, value = "maquinaConverter")
public class MaquinaConverter implements Converter{
	
	@Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
       
        if (value != null && !value.isEmpty()) {
            
        	Maquina maquina = (Maquina) uiComponent.getAttributes().get(value);
            
            return maquina;
        }
        
        return null;
        
    }

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {

		if (value instanceof Maquina) {

			Maquina entity = (Maquina) value;

			if (entity != null && entity instanceof Maquina && entity.getTag() != null) {

				String descricao = entity.getTag();
				
				uiComponent.getAttributes().put(descricao, entity);

				return descricao;

			}

		}
		
		return "";
	}

}
