package br.com.prati.tim.collaboration.gmp.mb.auditoria;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.prati.tim.collaboration.gmp.resources.PostgreConnection;

public class AuditoriaHelper implements Serializable{

	private static final long	serialVersionUID	= 1L;

	public static final String	COLUNA_USUARIO		= "Usuário";
	public static final String	COLUNA_ACAO			= "Ação";
	public static final String	COLUNA_TIMESTAMP	= "Data/Hora";
	public static final String	COLUNA_REVISAO		= "Revisão";
	
	public AuditoriaHelper(){
		
	}

	public List<String> retornarTabelasAuditaveis(){
		
		List<String> 	tabelas = new ArrayList<String>();
		StringBuffer  	sql 	= new StringBuffer();
		
		sql.append("	SELECT REPLACE(TABLENAME, '_aud', '') tabela ");
		sql.append("	  FROM PG_CATALOG.PG_TABLES ");
		sql.append("	 WHERE SCHEMANAME = 'audit' ");
		sql.append("	   AND TABLENAME LIKE'%_aud' ");
		sql.append("	 ORDER BY TABLENAME ");
		
        ResultSet           rs                  = null;
        PreparedStatement   ps                  = null;
        Connection          connection          = null;
        PostgreConnection	pgConnection        = new PostgreConnection();
        
        try {
        	
            connection          = pgConnection.getJNDIConnection();
            ps                  = connection.prepareStatement(sql.toString());
            rs          		= ps.executeQuery();
            
            while (rs.next()) {
                tabelas.add(rs.getString("tabela"));
            }
            
        } catch (SQLException e) {
			e.printStackTrace();
		} finally {

            if (rs != null) {
                try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }

            if (ps != null) {
                try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }

            if (connection != null) {
                try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }

        }
        
        return tabelas;
		
	}
	
	public List<String> retornarColunasTabela(String tabela, String schema){
		
		List<String> colunas = new ArrayList<String>();
		
		if (tabela == null || tabela.isEmpty()){
			return colunas;
		}
		
		StringBuffer  sql = new StringBuffer();
		
		sql.append("	SELECT COLUMN_NAME coluna ");
		sql.append("	  FROM INFORMATION_SCHEMA.COLUMNS ");
		sql.append("	  WHERE TABLE_SCHEMA = '").append(schema).append("' ");
		sql.append("	    AND TABLE_NAME   = '").append(tabela).append("' ");
		sql.append("	  ORDER BY ORDINAL_POSITION ");
				
        ResultSet           rs                  = null;
        PreparedStatement   ps                  = null;
        Connection          connection          = null;
        PostgreConnection	pgConnection        = new PostgreConnection();
        
        try {
        	
            connection          = pgConnection.getJNDIConnection();
            ps                  = connection.prepareStatement(sql.toString());
            rs          		= ps.executeQuery();
            
            while (rs.next()) {
                colunas.add(rs.getString("coluna"));
            }
            
        } catch (SQLException e) {
			e.printStackTrace();
		} finally {

            if (rs != null) {
                try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }

            if (ps != null) {
                try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }

            if (connection != null) {
                try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }

        }
        
        return colunas;
		
	}
	
	public String retornarColunaPK(String tabela){
		
		if (tabela == null || tabela.isEmpty()){
			return null;
		}
		
		StringBuffer  sql = new StringBuffer();
		
		sql.append("	SELECT pg_attribute.attname coluna");
		sql.append("	    FROM pg_index, pg_class, "); 
		sql.append("	         pg_attribute, "); 
		sql.append("	         pg_namespace "); 
		sql.append("	   WHERE pg_class.relname 		= '").append(tabela).append("' "); 
		sql.append("	     AND nspname 				= 'public' "); 
		sql.append("	     AND indrelid 				= pg_class.oid "); 
		sql.append("	     AND pg_class.relnamespace 	= pg_namespace.oid "); 
		sql.append("	     AND pg_attribute.attrelid 	= pg_class.oid "); 
		sql.append("	     AND pg_attribute.attnum 	= any(pg_index.indkey) ");
		sql.append("	     AND indisprimary ");
				
        ResultSet           rs                  = null;
        PreparedStatement   ps                  = null;
        Connection          connection          = null;
        PostgreConnection	pgConnection        = new PostgreConnection();
        
        try {
        	
            connection          = pgConnection.getJNDIConnection();
            ps                  = connection.prepareStatement(sql.toString());
            rs          		= ps.executeQuery();
            
            while (rs.next()) {
                return rs.getString("coluna");
            }
            
        } catch (SQLException e) {
			e.printStackTrace();
		} finally {

            if (rs != null) {
                try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }

            if (ps != null) {
                try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }

            if (connection != null) {
                try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }

        }
        
        return null;
		
	}
	
	public List<HashMap<String, String>> retornarRegistrosDetail(String tabela, String idEntidate, Date dataInicial, Date dataFinal){
		
		List<HashMap<String, String>> registros = new ArrayList<HashMap<String,String>>();
		
		if (tabela == null || tabela.isEmpty()){
			return registros;
		}
		
		StringBuffer  	sql 			= new StringBuffer();
		String 			idTabela 		= retornarColunaPK(tabela);
		String 			colunasTabela 	= "";
		
		for (String coluna : retornarColunasTabela(tabela + "_aud", "audit")) {
			if (coluna.equals("revtype")){
				colunasTabela += " CASE WHEN revtype = 0 THEN 'Inserção' "+
							     " 		WHEN revtype 	 = 1 THEN 'Alteração' "+
							     " 		ELSE 'Deleção'  "+ 
							     " END as \""+ COLUNA_ACAO + "\", ";
			}else if (coluna.equals("rev")){
				colunasTabela += "entidade." + coluna + " as \""+ COLUNA_REVISAO + "\", ";
			}else{
				colunasTabela += "entidade." + coluna + ", ";
			}
		}
		
		SimpleDateFormat 	formatas 		= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String 				dataInicialStr 	= formatas.format(dataInicial);
        String 				dataFinalStr 	= formatas.format(dataFinal);
		
		sql.append("	select ").append(colunasTabela); 
		sql.append("           to_char('1970-01-01 00:00:00 GMT'::timestamp AT TIME ZONE 'UTC' + ((revinfo.revtstmp/1000)::text)::interval , 'DD/MM/YYYY HH24:MI:SS') as \"").append(COLUNA_TIMESTAMP).append("\" , "); 
		sql.append("           revinfo.usuario as \"").append(COLUNA_USUARIO).append("\"  ");
		sql.append("      from audit.").append(tabela).append("_aud entidade, "); 
		sql.append("       	   audit.revinfo revinfo "); 
		sql.append("     where entidade.rev = revinfo.rev ");
		sql.append("       and entidade.").append(idTabela).append(" = ").append(idEntidate);
		sql.append("       and to_char('1970-01-01 00:00:00 GMT'::timestamp AT TIME ZONE 'UTC' + ((revinfo.revtstmp/1000)::text)::interval, 'YYYY-MM-DD HH24:MI:SS') ");
		sql.append("           between ('").append(dataInicialStr).append("') and ('").append(dataFinalStr).append("') ");
		sql.append("     order by revinfo.rev desc");
				
        ResultSet           rs                  = null;
        PreparedStatement   ps                  = null;
        Connection          connection          = null;
        PostgreConnection	pgConnection        = new PostgreConnection();
        
        try {
        	
            connection          = pgConnection.getJNDIConnection();
            ps                  = connection.prepareStatement(sql.toString());
            rs          		= ps.executeQuery();
            
            ResultSetMetaData columns = rs.getMetaData();
            
            while (rs.next()) {
            	HashMap<String, String> registro = new HashMap<String, String>();
            	for(int i = 1; i < columns.getColumnCount() + 1; i++){
            		
            		registro.put(columns.getColumnName(i), rs.getObject(i) != null ? rs.getObject(i).toString() : "");
            		
            	}
            	registros.add(registro);
            }
            
            
        } catch (SQLException e) {
			e.printStackTrace();
		} finally {

            if (rs != null) {
                try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }

            if (ps != null) {
                try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }

            if (connection != null) {
                try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }

        }
		
		return registros;
	}
	
	public List<HashMap<String, String>> retornaRegistrosMaster(String tabela, Date dataInicial, Date dataFinal){ 
		
		List<HashMap<String, String>> registros = new ArrayList<HashMap<String,String>>();
		
		if (tabela == null || tabela.isEmpty()){
			return registros;
		}
		
		String 			id 				= retornarColunaPK(tabela);
		StringBuffer  	sql 			= new StringBuffer();
		String 			colunasTabela 	= "";
		
		for (String coluna : retornarColunasTabela(tabela, "public")) {
				colunasTabela += "entidade." + coluna + ", ";
		}
		colunasTabela = colunasTabela.substring(0, colunasTabela.length() - 2);
		
		SimpleDateFormat 	formatas 		= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String 				dataInicialStr 	= formatas.format(dataInicial);
        String 				dataFinalStr 	= formatas.format(dataFinal);
		
		sql.append("	select ").append(colunasTabela); 
		sql.append("      from ").append(tabela).append(" entidade "); 
		sql.append("     where ").append(id).append(" in (select distinct ").append(id); 
		sql.append("       	      from audit.").append(tabela).append("_aud entidade_aud, audit.revinfo revinfo "); 
		sql.append("             where entidade_aud.rev = revinfo.rev ");
		sql.append("       and to_char('1970-01-01 00:00:00 GMT'::timestamp AT TIME ZONE 'UTC-02:00' + ((revinfo.revtstmp/1000)::text)::interval, 'YYYY-MM-DD HH24:MM:SS') ");
		sql.append("           between ('").append(dataInicialStr).append("') and ('").append(dataFinalStr).append("')) ");
		
		sql.append("     UNION ");
		/** Union para pegar os registros já excluídos do banco de dados. **/
		sql.append("	select ").append(colunasTabela); 
		sql.append("      from audit.").append(tabela).append("_aud entidade "); 
		sql.append("     where entidade.rev in ( SELECT (select rev ");
		sql.append("         					   from audit.").append(tabela).append("_aud ");
		sql.append("        					  where audit.").append(tabela).append("_aud.").append(id).append( " = entidade_aud.").append(id);
		sql.append("          						and revtype  <> 2 ");
		sql.append("        				   	  order by rev desc "); 
		sql.append("        					  limit 1) ");
		sql.append("       	      from audit.").append(tabela).append("_aud entidade_aud, audit.revinfo revinfo "); 
		sql.append("             where entidade_aud.rev = revinfo.rev ");
		sql.append("       and to_char('1970-01-01 00:00:00 GMT'::timestamp AT TIME ZONE 'UTC-02:00' + ((revinfo.revtstmp/1000)::text)::interval, 'YYYY-MM-DD HH24:MM:SS') ");
		sql.append("           between ('").append(dataInicialStr).append("') and ('").append(dataFinalStr).append("') ");
		sql.append(" 	   and entidade_aud.revtype = 2 ) ");
		
        ResultSet           rs                  = null;
        PreparedStatement   ps                  = null;
        Connection          connection          = null;
        PostgreConnection	pgConnection        = new PostgreConnection();
        
        try {
        	
            connection          = pgConnection.getJNDIConnection();
            ps                  = connection.prepareStatement(sql.toString());
            rs          		= ps.executeQuery();
            
            ResultSetMetaData columns = rs.getMetaData();
            
            while (rs.next()) {
            	
            	HashMap<String, String> registro = new HashMap<String, String>();
            	
            	for(int i = 1; i < columns.getColumnCount() + 1; i++){
            		
            		registro.put(columns.getColumnName(i), rs.getObject(i)!= null ? rs.getObject(i).toString() : "");
            		
            	}
            	registros.add(registro);
            }
            
            
        } catch (SQLException e) {
			e.printStackTrace();
		} finally {

            if (rs != null) {
                try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }

            if (ps != null) {
                try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }

            if (connection != null) {
                try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
            }
        }
		
		return registros;
	}
}
