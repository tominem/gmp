package br.com.prati.tim.collaboration.gmp.utis;

import br.com.prati.tim.collaboration.gmp.mb.login.SessionUtil;

public final class Permit {
	
	public Permit() {
		// TODO Auto-generated constructor stub
	}
	
	public static boolean permit(String url)
	{
		return SessionUtil.temPermissaoGum(url);
	}

}
