package br.com.prati.tim.collaboration.gmp.utis;

import java.util.Arrays;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public class FacesUtis {

	public static UIInput getUIComponentById(String id) {

		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		
		UIInput component = (UIInput) viewRoot.findComponent(id);

		return component;
	}
	
	public static void setValidComponents(List<UIComponent> children, boolean valid) {
		
		for (UIComponent uiComponent : children) {
			
			if (uiComponent instanceof UIInput) {
				((UIInput) uiComponent).setValid(valid);
			}

			else if (uiComponent.getChildren() != null && uiComponent.getChildren().isEmpty()) {

				if (uiComponent instanceof UIInput) {
					((UIInput) uiComponent).setValid(valid);
				}

			} else {
				setValidComponents(uiComponent.getChildren(), valid);
			}

		}
	}

	public static void setValidComponent(String componentId, boolean valid) {
		setValidComponents(Arrays.asList(getUIComponentById(componentId)), valid);
	}
	
	public static String translateMessage(String message){
		message = message.replace("Detail", "Detalhe");
		message = message.replace("Key", "A chave");
		message = message.replace("is still referenced from table", "ainda está referenciado na tabela");
		
		return message;
	}

}
