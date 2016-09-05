package br.com.prati.tim.collaboration.gmp.mb.login;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.prati.tim.collaboration.gmp.filter.PaginaSistema;
import br.com.prati.tim.collaboration.gmp.filter.PaginaSistemaUtil;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;
import br.prati.tim.gmp.ws.usuario.ViewAcesso;

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

			HttpSession session = getSession();

			if (session != null && session.getAttribute("cracha") != null) {
				return Integer.parseInt(session.getAttribute("cracha").toString());
			}

		}

		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean temPermissaoGUM(ETipoAcessoGUM tipoAcesso){
		
    	List<PaginaSistema> 	paginas 	= PaginaSistemaUtil.getPaginasSistema();
    	HttpServletRequest 		request 	= getRequest();
    	String 					url 		= request.getRequestURL().toString();
    	HttpSession 			session 	= request.getSession(false);
        PaginaSistema			pagina		= null;
    	
        for (PaginaSistema paginaSistema : paginas) {
			if (url.contains(paginaSistema.getPagina())){
				pagina = paginaSistema;
				break;
			}
		}
        
        if (pagina == null){
        	return true;
        }else{
        	
        	List<ViewAcesso> acessosUsuario = (List<ViewAcesso>) session.getAttribute("acessosUsuario");
			
            for (ViewAcesso viewAcesso : acessosUsuario) {
            	if (viewAcesso.getNomeFuncao().equals(pagina.getFuncaoGUM())&& viewAcesso.getNomeSistema().equals("GMP") ){
            		if (viewAcesso.getTipoAcesso().equals(tipoAcesso.getTipoAcesso())){
            			return true;
            		}
            	}
			}
        }
    	
    	return false;
    }
	
	@SuppressWarnings("unchecked")
	public static boolean temPermissaoGUM(String funcao, ETipoAcessoGUM tipoAcesso){
		
		HttpServletRequest 		request 	= getRequest();
    	HttpSession 			session 	= request.getSession(false);
    	
		List<ViewAcesso> acessosUsuario = (List<ViewAcesso>) session.getAttribute("acessosUsuario");
		
        for (ViewAcesso viewAcesso : acessosUsuario) {
        	if (viewAcesso.getNomeFuncao().equals(funcao)&& viewAcesso.getNomeSistema().equals("GMP") ){
        		if (viewAcesso.getTipoAcesso().equals(tipoAcesso.getTipoAcesso())){
        			return true;
        		}
        	}
		}
        
        return false;
		
	}
    
}