package br.com.prati.tim.collaboration.gmp.ejb.produto;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.prati.tim.collaboration.gp.jpa.Produto;
 
@FacesConverter(forClass = Produto.class, value = "produtoConverter")
public class ProdutoConverter implements Converter {
	 
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
       
        if (value != null && !value.isEmpty()) {
            
        	Produto produto = (Produto) uiComponent.getAttributes().get(value);
            
            return produto;
        }
        
        return null;
        
    }

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {

		if (value instanceof Produto) {

			Produto entity = (Produto) value;

			if (entity != null && entity instanceof Produto && entity.getDescricao() != null) {

				String descricao = entity.getCodigoSap().toString() + " - " + entity.getDescricao();
				
				uiComponent.getAttributes().put(descricao, entity);

				return descricao;

			}

		}
		
		return "";
	}
} 