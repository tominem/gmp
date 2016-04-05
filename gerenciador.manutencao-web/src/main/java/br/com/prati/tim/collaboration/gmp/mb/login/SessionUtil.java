package br.com.prati.tim.collaboration.gmp.mb.login;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Named("mbSession")
@ViewScoped 
public class SessionUtil implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

	public static Integer getCracha() {

		if (FacesContext.getCurrentInstance() != null) {

			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

			if (session != null && session.getAttribute("cracha") != null) {
				return Integer.parseInt(session.getAttribute("cracha").toString());
			}

		}

		return null;
	}
    
}