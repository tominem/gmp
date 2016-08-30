package br.com.prati.tim.collaboration.gmp.ejb.usuario;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;



@FacesConverter(forClass = PapelView.class, value = "papelViewConverter")
public class PapelViewConverter implements Converter{
	
	@Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
       
        if (value != null && !value.isEmpty()) {
            
        	PapelView papel = (PapelView) uiComponent.getAttributes().get(value);
            
            return papel;
        }
        
        return null;
        
    }

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {

		if (value instanceof PapelView) {

			PapelView entity = (PapelView) value;

			if (entity != null && entity instanceof PapelView && entity.getNome() != null) {

				String descricao = entity.getNome();
				
				uiComponent.getAttributes().put(descricao, entity);

				return descricao;

			}

		}
		
		return "";
	}

}
