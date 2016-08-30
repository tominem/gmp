package br.com.prati.tim.collaboration.gmp.ejb.historicoprod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import br.com.prati.tim.collaboration.gmp.utis.ConnectionUtil;
import br.prati.tim.collaboration.gp.jpa.Lote;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.enumerator.EStatusMaquina;

@Stateless
public class HistoricoProducaoEJB {

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public List<RegistroProducao> getHistoricoProducao(	Date 	dataInicial, 
														Date 	dataFinal, 
														Maquina maquina, 
														Lote 	lote) throws SQLException{
		
		String dataInicialStr 	= df.format(dataInicial);
		String dataFinalStr 	= df.format(dataFinal);
		
		StringBuffer  sql = new StringBuffer();
		sql.append("SELECT CASE ");
		sql.append("           WHEN IDENTIFICADOR = 2 THEN 	'LOTE' ");
		sql.append("           WHEN IDENTIFICADOR = 3 THEN 	'SERVIÇO' ");
		sql.append("           ELSE 						'MÁQUINA' ");
		sql.append("        END AS IDENTIFICADOR, ");
		sql.append("        MAQUINA.TAG, ");
		sql.append("        LOTE.NUMERO_LOTE, ");
		sql.append("        EXTRATO.DATA_HORA, ");
		sql.append("        EXTRATO.CRACHA, ");
		sql.append("        EXTRATO.DESCRICAO_ACAO, ");
		sql.append("        EXTRATO.STATUS, ");
		sql.append("		NULL AS DESCRICAO_SERVICO  /*LOG MAQUINA*/");
		sql.append("  FROM ");
		sql.append("  	  (SELECT 	CASE ");
		sql.append("              		WHEN ID_LOTE IS NULL THEN 1 ");
		sql.append("              		ELSE 2 ");
		sql.append("          		END AS IDENTIFICADOR, ");
		sql.append("                LOG_MAQUINA.ID_MAQUINA, ");
		sql.append("                LOG_MAQUINA.ID_LOTE, ");
		sql.append("                LOG_MAQUINA.DATA_HORA, ");
		sql.append("                LOG_MAQUINA.CRACHA, ");
		sql.append("                LOG_MAQUINA.DESCRICAO_ACAO, ");
		sql.append("                LOG_MAQUINA.STATUS, ");
		sql.append("				NULL AS DESCRICAO_SERVICO");
		sql.append("   		   FROM LOG_MAQUINA ");
		sql.append("   		  WHERE LOG_MAQUINA.ID_MAQUINA =  ").append(maquina.getIdMaquina());
		sql.append("			AND LOG_MAQUINA.STATUS NOT IN (1, 2, 3, 7) ");
		sql.append("		    AND LOG_MAQUINA.DATA_HORA BETWEEN '").append(dataInicialStr).append("' AND '").append(dataFinalStr).append("' ");
		
		sql.append("   		UNION /*PARADAS*/ ");
		
		sql.append(			"SELECT PARADAS.IDENTIFICADOR, ");
		sql.append("                PARADAS.ID_MAQUINA, ");
		sql.append("                PARADAS.ID_LOTE, ");
		sql.append("                PARADAS.DATA_HORA, ");
		sql.append("                PARADAS.CRACHA, ");
		sql.append("                PARADAS.TITULO AS DESCRICAO_ACAO, ");
		sql.append("                2 AS STATUS, ");
		sql.append("				NULL AS DESCRICAO_SERVICO ");
		sql.append("   		 FROM ");
		sql.append("     		 ( SELECT CASE WHEN ID_LOTE IS NULL THEN 1 ELSE 2 END AS IDENTIFICADOR, ");
		sql.append("                      PARADA.ID_MAQUINA, ");
		sql.append("                      PARADA.ID_LOTE, ");
		sql.append("                      PARADA.DATA_HORAINICIO AS DATA_HORA, ");
		sql.append("                      PARADA.CRACHA, ");
		sql.append("                      'INICIO - '||ALARME.TITULO AS TITULO ");
		sql.append("      			 FROM PARADA, ");
		sql.append("           			  ALARME ");
		sql.append("      		    WHERE PARADA.ID_ALARME = ALARME.ID_ALARME ");
		sql.append("				AND   PARADA.DATA_HORAINICIO BETWEEN '").append(dataInicialStr).append("' AND '").append(dataFinalStr).append("' ");
		
		sql.append("      			UNION ");
		
		sql.append("      			SELECT CASE WHEN ID_LOTE IS NULL THEN 1 ELSE 2 END AS IDENTIFICADOR, ");
		sql.append("                       PARADA.ID_MAQUINA, ");
		sql.append("                       PARADA.ID_LOTE, ");
		sql.append("                       PARADA.DATA_HORAFIM AS DATA_HORA, ");
		sql.append("                       PARADA.CRACHA, ");
		sql.append("                       'FIM - '||ALARME.TITULO AS TITULO ");
		sql.append("      			  FROM PARADA, ");
		sql.append("           			   ALARME ");
		sql.append("      			 WHERE PARADA.ID_ALARME = ALARME.ID_ALARME ");
		sql.append("				  AND  PARADA.DATA_HORAFIM BETWEEN '").append(dataInicialStr).append("' AND '").append(dataFinalStr).append("') PARADAS /*SERVICOS*/ ");
		sql.append("   			UNION ");
		sql.append("   				SELECT  3 AS IDENTIFICADOR, ");
		sql.append("                     	SERVICOS.ID_MAQUINA, ");
		sql.append("                     	SERVICOS.ID_LOTE, ");
		sql.append("                     	SERVICOS.DATA_HORA, ");
		sql.append("                     	SERVICOS.CRACHA, ");
		sql.append("                     	SERVICOS.TITULO AS DESCRICAO_ACAO, ");
		sql.append("                        5 AS STATUS, ");
		sql.append("						SERVICOS.DESCRICAO_SERVICO ");
		sql.append("   				   FROM ");
		sql.append("     					( SELECT LOG_SERVICO.ID_MAQUINA, ");
		sql.append("              					 LOG_SERVICO.ID_LOTE, ");
		sql.append("              					 LOG_SERVICO.DATA_HORAINICIO AS DATA_HORA, ");
		sql.append("                                 LOG_SERVICO.CRACHA, ");
		sql.append("                                 'INICIO - '||SERVICOS.DESCRICAO AS TITULO, ");
		sql.append("								 SERVICOS.DESCRICAO AS DESCRICAO_SERVICO ");
		sql.append("      						FROM LOG_SERVICO, ");
		sql.append("           						 SERVICOS ");
		sql.append("      					   WHERE LOG_SERVICO.ID_SERVICO = SERVICOS.ID_SERVICO ");
		sql.append("                           AND   LOG_SERVICO.DATA_HORAINICIO BETWEEN '").append(dataInicialStr).append("' AND '").append(dataFinalStr).append("' ");
		sql.append("      				UNION ");
		sql.append("      					  SELECT LOG_SERVICO.ID_MAQUINA, ");
		sql.append("                   				 LOG_SERVICO.ID_LOTE, ");
		sql.append("                   				 LOG_SERVICO.DATA_HORAFIM AS DATA_HORA, ");
		sql.append("                                 LOG_SERVICO.CRACHA, ");
		sql.append("                                 'FIM - '||SERVICOS.DESCRICAO AS TITULO, ");
		sql.append("								 SERVICOS.DESCRICAO AS DESCRICAO_SERVICO ");
		sql.append("      						FROM LOG_SERVICO, ");
		sql.append("           						 SERVICOS ");
		sql.append("      					   WHERE LOG_SERVICO.ID_SERVICO = SERVICOS.ID_SERVICO ");
		sql.append("                           AND   LOG_SERVICO.DATA_HORAFIM BETWEEN '").append(dataInicialStr).append("' AND '").append(dataFinalStr).append("') SERVICOS) EXTRATO ");
		sql.append("		LEFT JOIN LOTE ON LOTE.ID_LOTE = EXTRATO.ID_LOTE ");
		sql.append("		LEFT JOIN MAQUINA ON MAQUINA.ID_MAQUINA = EXTRATO.ID_MAQUINA ");
		sql.append("        WHERE MAQUINA.TAG = '").append(maquina.getTag()).append("' ");
		if (lote !=	null){
			sql.append("		 AND  NUMERO_LOTE = '").append(lote.getNumeroLote()).append("' ");
		}
		sql.append("	ORDER BY EXTRATO.DATA_HORA desc ");
		
		
		Connection      connection          = 	ConnectionUtil.getJNDIConnectionGestaoProducao();
		
		PreparedStatement 		ps 			= 	null;
        ResultSet         	  	rs 			= 	null;
        List<RegistroProducao> 	registros 	=	new ArrayList<RegistroProducao>();
        
        try {
            
        	ps = connection.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            
            while(rs.next()) {
            	
            	RegistroProducao registro = new RegistroProducao();
            	
            	String 		numeroLote 	= 	rs.getString	("numero_lote");
            	String 		descServico	= 	rs.getString	("descricao_servico");
            	Integer 	status		= 	rs.getInt		("status"); 
            	
            	registro.setDescricao	(descServico == null || descServico.isEmpty() ? numeroLote : descServico);
            	
            	registro.setAcao			(rs.getString	("descricao_acao"));
            	registro.setCracha			(rs.getInt		("cracha"));
            	registro.setDataHora		(rs.getTimestamp("data_hora"));
            	registro.setIdentificador	(rs.getString	("identificador"));
            	
            	if (status != null){
            		registro.setStatusMaquina(EStatusMaquina.parse(status));
            	}
            	
            	registros.add(registro);
            }

        } finally {

            if (rs != null) {
                rs.close();
            }

            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return registros;
		
	}
	
}
