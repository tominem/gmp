package br.com.prati.tim.collaboration.gmp.utis;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@SessionScoped
@Named("userAccess")
public class Permit implements Serializable {

	private static final long serialVersionUID = 1L;

	public Permit() {
	}

	public boolean permit(String url) {
		return true;
//		return SessionUtil.temPermissaoGum(url);
	}

}
