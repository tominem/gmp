package br.com.prati.tim.collaboration.gmp.mb;

import javax.faces.event.ComponentSystemEvent;

public interface BaseMB {
	
	String getFormName();
	
	boolean validate(ComponentSystemEvent event);
	
	void clean();

	String getContentPane();

	ValidateComponent[] getValidaComponents();

}
