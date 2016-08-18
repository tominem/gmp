package br.com.prati.tim.collaboration.gmp.resources;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class PostgreConnection {
    
    public Connection getJNDIConnection(){
        
    	String DATASOURCE_CONTEXT = "java:jboss/datasources/GestaoProducaoXADS";

        Connection result = null;
        try {

            Context initialContext = new InitialContext();
            DataSource datasource = (DataSource) initialContext.lookup(DATASOURCE_CONTEXT);
            
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
