package br.com.prati.tim.collaboration.gmp.ejb.subproduto;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.prati.tim.collaboration.gp.jpa.Subproduto;

@FacesConverter(forClass=Subproduto.class, value="subprodutoConverter")
public class SubprodutoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if (value != null && !value.isEmpty()) {
            
			Subproduto subproduto = (Subproduto) component.getAttributes().get(value);
            
            return subproduto;
        }
        
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,	Object value) {
		
		if (value instanceof Subproduto) {

			Subproduto entity = (Subproduto) value;

			if (entity != null && entity instanceof Subproduto && entity.getDescricao() != null) {

				component.getAttributes().put(entity.getDescricao(), entity);

				return entity.getDescricao();
			}

		}
		
		return null;
	}

}
