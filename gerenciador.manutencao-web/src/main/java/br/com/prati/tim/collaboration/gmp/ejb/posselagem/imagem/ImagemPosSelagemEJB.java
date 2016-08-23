package br.com.prati.tim.collaboration.gmp.ejb.posselagem.imagem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.common.io.ByteStreams;

import br.com.prati.tim.collaboration.gmp.utis.ConnectionUtil;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.util.Base64;

@Stateless
public class ImagemPosSelagemEJB implements Serializable{

	private static final long	serialVersionUID	= 	1L;
	private static String		DOMAIN				= 	"server-bolha";
	private static String		USER				= 	"desenvolvimento";
	private static String		PASS				= 	"BO2014des";
	private static String		SHARED_FOLDER		= 	"IMAGENS";
	public static Object		TEMP_SERVER			= 	System.getProperties().get("jboss.server.temp.dir");
	
	public ImagemPosSelagemEJB() {
		jcifs.Config.setProperty("encoding",        "UTF-8");
        jcifs.Config.setProperty("resolveOrder",    "DNS");
	}
	
	public List<InspecaoBlister> getInspecaoBlisterList(String lote, int camera) throws SQLException {
		
		StringBuffer  sb = new StringBuffer();
		sb.append("SELECT id_inspecao, ");
		sb.append("       data_hora, ");
		sb.append("       retorno").append(camera);
		sb.append("  FROM inspecao_blisteres_cam").append(camera);
		sb.append(" WHERE codigo_lote 	= '").append(lote).append("' ");
		sb.append("   AND retorno").append(camera).append(" LIKE '%2%'");
		sb.append(" ORDER BY data_hora  ASC");
		
		Connection      connection          = 	ConnectionUtil.getJNDIConnectionPosSelagem();
		
		PreparedStatement 		ps 			= 	null;
        ResultSet         	  	rs 			= 	null;
        List<InspecaoBlister> 	inspecoes 	=	new ArrayList<InspecaoBlister>();
        
        try {
            
        	ps = connection.prepareStatement(sb.toString());
            rs = ps.executeQuery();
            
            while(rs.next()) {
            	
            	InspecaoBlister inspecao = new InspecaoBlister();
            	
            	inspecao.setCodigoLote		(lote);
            	inspecao.setDataHoraRegistro(rs.getTimestamp("data_hora"));
            	inspecao.setIdInspecao		(rs.getInt	 	("id_inspecao"));
            	inspecao.setRetorno			(rs.getString	("retorno" + camera));
            	
            	inspecoes.add(inspecao);
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

        return inspecoes;
	}
	
	public String donwloadSource(String lote, Integer idInspecao, Integer camera, String extensao) throws IOException {

		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(DOMAIN, USER, PASS);
		
		String	raiz			= "smb://" + DOMAIN + "/" + SHARED_FOLDER;
		String  imagePath 		= lote + "/0" + camera + "/";
		String  imageName		= idInspecao + "_imagem" + camera + "0." + extensao;
		String	completePath 	= raiz + "/" + imagePath + imageName; 

		SmbFile file 			= new SmbFile(completePath, auth);
		
		String	targetFolder	= (TEMP_SERVER + "/PosSelagem/" + imagePath).replace("/", "\\");
		
		new File (targetFolder).mkdirs();
		
		File 	filteToCreate 	= new File(targetFolder += imageName);
		
		try {
			
			if (!filteToCreate.exists()) {
				filteToCreate.createNewFile();
			}
			
			FileOutputStream 	fileOutputStream 	=	new FileOutputStream(filteToCreate);
			InputStream 		fileInputStream 	= 	file.getInputStream();
			
			byte[]	 buf 	= 	new byte[16 * 1024 * 1024];
			int 	 len;
			
            while ((len = fileInputStream.read(buf)) > 0) {
                fileOutputStream.write(buf, 0, len);
            }
            
            fileInputStream.close();
            fileOutputStream.close();
            
            return targetFolder;
            
        } catch (SmbException e) {
            throw e;
        } catch (FileNotFoundException e) {
        	throw e;
        } catch (IOException e) {
        	throw e;
        }
		
	}
	
	public String getTargetFile(String svgPath, String jpgPath) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException, TransformerException {
		
		String 		source 	= 	svgPath;
		String 		target 	= 	svgPath.replace(".svg", "_target.svg");
		
		Document 	doc 	= 	DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(source));
		XPath 		xpath 	= 	XPathFactory.newInstance().newXPath();
		
		NodeList 	nodes 	= 	(NodeList)xpath.evaluate("//*[contains(@href, 'imagem')]", doc, XPathConstants.NODESET);
		
		for (int idx = 0; idx < nodes.getLength(); idx++) {
		    
			Node value = nodes.item(idx).getAttributes().getNamedItem("xlink:href");
		    
		    FileInputStream fileInputStream = new FileInputStream(jpgPath);
		    
		    byte[] byteArray = ByteStreams.toByteArray(fileInputStream);
		    String base64String = "data:image/jpeg;base64," + Base64.encode(byteArray);
			 
		    String val = value.getNodeValue();
		    
		    value.setNodeValue(val.replaceAll(val, base64String));
		    
		    fileInputStream.close();
		}
		
		Transformer xformer = TransformerFactory.newInstance().newTransformer();
		xformer.transform(new DOMSource(doc), new StreamResult(new File(target)));
		
		return target;
	}
	
}