package br.com.prati.tim.collaboration.gmp.utis;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionUtil {

	public static Connection getJNDIConnectionPosSelagem() {
		
		return getJNDIConnection("java:jboss/datasources/PosSelagemXADS");
		
	}
	
	public static Connection getJNDIConnectionGestaoProducao() {
		
		return getJNDIConnection("java:jboss/datasources/GestaoProducaoXADS");
		
	}
		
	private static Connection getJNDIConnection(String dataSourceContext){
		
		Connection result = null;
        try {

            Context initialContext = new InitialContext();
            DataSource datasource = (DataSource) initialContext.lookup(dataSourceContext);
            
            if (datasource != null) {
                result = datasource.getConnection();
            } else {
                System.out.println("Failed to lookup datasource.");
            }
            
        } catch (NamingException ex) {
            System.out.println("Cannot get connection: " + ex);
        } catch (SQLException ex) {
            System.out.println("Cannot get connection: " + ex);
        }
        return result;
    
	}
	
}
