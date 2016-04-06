package br.com.prati.tim.collaboration.gmp.mb;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

public class ViewExpiredExceptionExceptionHandler extends ExceptionHandlerWrapper {

    private ExceptionHandler wrapped;

    public ViewExpiredExceptionExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    @Override
    public void handle() throws FacesException {
        
        FacesContext facesContext = FacesContext.getCurrentInstance();

        for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator(); i.hasNext();) {
            
            ExceptionQueuedEvent        event   = i.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
            
            Throwable t = context.getException();
            
            if (t instanceof ViewExpiredException) {

                try {

                    final ExternalContext externalContext = facesContext.getExternalContext();

                    facesContext.setViewRoot(facesContext.getApplication().getViewHandler().createView(facesContext, "/login.xhtml"));     
                    externalContext.redirect("login.xhtml");             
                    facesContext.getPartialViewContext().setRenderAll(true);
                    facesContext.renderResponse();

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    i.remove();
                }
                    
                
            }
        }
        
        getWrapped().handle();

    }
}
