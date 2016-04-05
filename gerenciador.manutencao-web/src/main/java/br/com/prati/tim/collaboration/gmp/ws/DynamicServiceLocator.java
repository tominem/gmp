package br.com.prati.tim.collaboration.gmp.ws;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceClient;

import br.com.prati.tim.collaboration.gmp.ex.ws.WSException;

public class DynamicServiceLocator {

    private String ipServidor;
    private String serviceURL;

    public DynamicServiceLocator(String ipServidor) {
        this.ipServidor = "http://"+ ipServidor + "/";
    }
    
    public <T> T getPort(Class<T> portType, Class<? extends Service> serv) throws WSException {
        
        try {

            QName                           qname;
            Constructor<? extends Service>  constructor;
            Service                         servImpl;
            T                               port;
            BindingProvider                 bp;
            String                          endPoint;
            
            qname       = getQname(serv);
            
            constructor = serv.getConstructor(new Class[] {URL.class, QName.class});
            
            servImpl    = (Service) constructor.newInstance(new Object[] {getUrl(serv), qname});
            
            port        = servImpl.getPort(portType);
            
            bp          = (BindingProvider) port;
            
            endPoint    = (String) bp.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
            
            bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endPoint.replaceFirst("http://.[^/]*/", ipServidor));
            
            return port;
            
        } catch (Exception ex) {
        	
        	ex.printStackTrace();
        	
            throw new WSException("Erro de conexão com WebService, ip: " + ipServidor);
        }
    }
    
    private URL getUrl(Class<? extends Service> cl) throws MalformedURLException, WSException {

        WebServiceClient an = (WebServiceClient) cl.getAnnotation(WebServiceClient.class);

        if (an == null) {
            throw new RuntimeException("Anotation WebServiceClient não encontrada.");
        }

        StringBuilder newAddress = new StringBuilder();

        newAddress.append(ipServidor);
        
        if (!ipServidor.endsWith("/")) {
            newAddress.append('/');
        }

        String replaceFirst = an.wsdlLocation().replaceFirst("http://.[^/]*/", newAddress.toString());
        
        URL url = new URL(replaceFirst);
        
        serviceURL = url.toString();
        
        return urlIsAvailable(url);
    }
    
    public String getServiceURL() {
		return serviceURL;
	}

    private static URL urlIsAvailable(URL url) throws WSException {

    	HttpURLConnection http = null;
    	
		try {
			
			System.out.println(url);
			
			http = (HttpURLConnection) url.openConnection();
			
			http.setRequestMethod("GET");
			
			http.setConnectTimeout(2000); 
			
			if (http.getResponseCode() != HttpURLConnection.HTTP_OK) {
				
				throw new WSException("Falha ao tentar conectar-se a URL do WS");
				
			}
			
		} catch (IOException e) {
			
			throw new WSException("Falha ao tentar conectar-se a URL do WS"); 
			
		} finally{
			
			if (http != null) {
				http.disconnect();
			}
			
		}
    	
    	return url;
	}

	private QName getQname(Class<? extends Service> cl) {
        WebServiceClient an = (WebServiceClient) cl.getAnnotation(WebServiceClient.class);
        return new QName(an.targetNamespace(), an.name());
    }

}
