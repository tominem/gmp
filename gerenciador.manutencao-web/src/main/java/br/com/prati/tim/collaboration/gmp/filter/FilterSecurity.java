package br.com.prati.tim.collaboration.gmp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        
        
        if (servletRequest instanceof HttpServletRequest) {
            
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            
            HttpSession session = httpServletRequest.getSession(false);
            
            if (session != null) {
                autorizado =  session.getAttribute("cracha") != null;
            }
        }
        
        if (autorizado) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
            
        } else if (filterChain != null) {

            String paginaInicial = filterConfig.getInitParameter("paginaInicial");

            if (paginaInicial != null && !"".equals(paginaInicial)) {
                
                filterConfig.getServletContext().getRequestDispatcher(paginaInicial).forward(servletRequest, servletResponse);
                
                return;
            }
            
        }

        throw new ServletException ("Acesso não autorizado, incapaz de redirecionar para a página de Login");

    }

    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

}
