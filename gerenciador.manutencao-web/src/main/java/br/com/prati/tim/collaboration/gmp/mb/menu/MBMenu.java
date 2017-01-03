package br.com.prati.tim.collaboration.gmp.mb.menu;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.prati.tim.collaboration.gmp.mb.login.SessionUtil;



@ManagedBean(name="mbMenu")
@SessionScoped
public class MBMenu implements Serializable {

	/**
	 * 
	 */
	private static final long			serialVersionUID	= 1L;

	private String						nomeUsuario;

	private Long						crachaUsuario;
	
	public MBMenu() {
	
	}
	
	@PostConstruct
	private void setUser() {
		this.crachaUsuario 	= (Long) 	SessionUtil.getSession().getAttribute("cracha");
		this.nomeUsuario 	= (String)	SessionUtil.getSession().getAttribute("nomeUsuario");
	}
		
	public String getNomeUsuario() {
		return nomeUsuario;
	}
	
	public Long getCrachaUsuario() {
		return crachaUsuario;
	}

	
}
