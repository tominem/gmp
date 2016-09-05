package br.com.prati.tim.collaboration.gmp.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.prati.tim.gmp.ws.usuario.ViewAcesso;

public class FilterSecurity implements Filter{

    private FilterConfig filterConfig = null;

    @Override
    public void destroy() {
        
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        
        
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        resp.addHeader("X-UA-Compatible", "IE=9; IE=8; IE=7");
        
        boolean autorizado = false;
        boolean permissaoGUM = false;
        
        if (servletRequest instanceof HttpServletRequest) {
            
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            
            HttpSession session = httpServletRequest.getSession(false);
            
            if (session != null) {
                autorizado =  session.getAttribute("cracha") != null;
                
                if (autorizado){
            		permissaoGUM = temPermissaoGUM(httpServletRequest);
            	}
            }
        }
        
        if (autorizado) {
        	
        	if (!permissaoGUM){
        		String paginaPermissaoNegada = filterConfig.getInitParameter("paginaPermissaoNegada");
        		filterConfig.getServletContext().getRequestDispatcher(paginaPermissaoNegada).forward(servletRequest, servletResponse);
        	}else{
        		filterChain.doFilter(servletRequest, servletResponse);
        	}
            return;
            
        }  else if (filterChain != null) {

            String paginaInicial = filterConfig.getInitParameter("paginaInicial");

            if (paginaInicial != null && !"".equals(paginaInicial)) {
                
                filterConfig.getServletContext().getRequestDispatcher(paginaInicial).forward(servletRequest, servletResponse);
                
                return;
            }
            
        }

        throw new ServletException ("Acesso não autorizado, incapaz de redirecionar para a página de Login");

    }

    @SuppressWarnings("unchecked")
	public boolean temPermissaoGUM(HttpServletRequest httpServletRequest){
    	
    	List<PaginaSistema> paginas = PaginaSistemaUtil.getPaginasSistema();
    	String 				url 	= httpServletRequest.getRequestURL().toString();
    	HttpSession 		session = httpServletRequest.getSession(false);
        PaginaSistema		pagina	= null;
    	
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
            	if (viewAcesso.getNomeFuncao().equals(pagina.getFuncaoGUM()) && viewAcesso.getNomeSistema().equals("GMP")){
            		return true;
            	}
			}
        }
    	
    	return false;
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

}
