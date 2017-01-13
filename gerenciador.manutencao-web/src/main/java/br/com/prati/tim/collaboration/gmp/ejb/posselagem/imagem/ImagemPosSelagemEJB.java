package br.com.prati.tim.collaboration.gmp.ejb.posselagem.imagem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
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

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.util.Base64;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.common.io.ByteStreams;

@Stateless
public class ImagemPosSelagemEJB implements Serializable{

	private static final long	serialVersionUID	= 	1L;
	private static String		DOMAIN				= 	"server-bolha";
	private static String		USER				= 	"desenvolvimento";
	private static String		PASS				= 	"BO2014des";
	private static String		SHARED_FOLDER		= 	"IMAGENS";
	private static String       SYSTEM_TAG          =   "IBL";
	public static Object		TEMP_SERVER			= 	System.getProperties().get("jboss.server.temp.dir");
	
	public ImagemPosSelagemEJB() {
		jcifs.Config.setProperty("encoding",        "UTF-8");
        jcifs.Config.setProperty("resolveOrder",    "DNS");
	}
	
	public List<String> getListFiles(String tagMaquina, String lote, int camera) throws Exception {
		
		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(DOMAIN, USER, PASS);
		
		String	raiz			= "smb://" + DOMAIN + "/" + SHARED_FOLDER;
		String  imagePath 		= SYSTEM_TAG + "/" + tagMaquina + "/" + lote  + "/0" + camera + "/";
		String	completePath 	= raiz + "/" + imagePath;
		
		List<String> files = null;
		
		SmbFile pathCam 	    = new SmbFile(completePath, auth);
		
		String[] listFiles = pathCam.list((dir, name) -> name.endsWith(".svg"));
		
		
		if (listFiles != null && listFiles.length > 0) {
			
			files = Arrays.asList(listFiles);
			files.sort((s1, s2) -> s1.compareTo(s2));
		}
		
		return files;
	}
	
	public String donwloadSource(String imgFile, String tagMaquina, String lote, Integer camera, String extensao) throws IOException {

		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(DOMAIN, USER, PASS);
		
		if (extensao.equals("jpg")) {
			imgFile = imgFile.replace(".svg", ".jpg");
		}
		
		String	raiz			= "smb://" + DOMAIN + "/" + SHARED_FOLDER;
		String  imagePath 		= SYSTEM_TAG + "/" + tagMaquina + "/" + lote  + "/0" + camera + "/";
		String	completePath 	= raiz + "/" + imagePath + "/" + imgFile;

		SmbFile file 			= new SmbFile(completePath, auth);
		
		String	targetFolder	= (TEMP_SERVER + "/PosSelagem/" + imagePath).replace("/", "\\");
		
		new File (targetFolder).mkdirs();
		
		File 	filteToCreate 	= new File(targetFolder += imgFile);
		
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