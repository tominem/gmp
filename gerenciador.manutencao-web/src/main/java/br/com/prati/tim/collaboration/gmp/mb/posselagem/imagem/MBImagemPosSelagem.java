package br.com.prati.tim.collaboration.gmp.mb.posselagem.imagem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.xml.sax.SAXException;

import br.com.prati.tim.collaboration.gmp.ejb.posselagem.imagem.ImagemPosSelagemEJB;
import br.com.prati.tim.collaboration.gmp.ejb.posselagem.imagem.InspecaoBlister;
import br.com.prati.tim.collaboration.gmp.mb.AbstractBaseMB;
import br.com.prati.tim.collaboration.gmp.mb.UtilsMessage;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.com.prati.tim.collaboration.gmp.mb.login.SessionUtil;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;

@Named("mbImagemPosSelagem")
@ViewScoped
public class MBImagemPosSelagem extends AbstractBaseMB {

	private static final long		serialVersionUID	= 1L;

	@Inject
	private ImagemPosSelagemEJB		ejbImagem;

	private List<InspecaoBlister>	inspecoesCamera1;
	
	private List<InspecaoBlister>	inspecoesCamera2;
	
	private String					urlTargetImage;
	
	private String					lote;
	
	private InputStream				image;
	
	@PostConstruct
	public void init(){
		setInspecoesCamera1(new ArrayList<InspecaoBlister>());
		setInspecoesCamera2(new ArrayList<InspecaoBlister>());
	}

	@Override
	public boolean validate(ComponentSystemEvent event) {
		return false;
	}

	@Override
	public ValidateComponent[] getValidaComponents() {
		return null;
	}
	
	public void find() throws SQLException{
		
		if (!SessionUtil.temPermissaoGUM(ETipoAcessoGUM.CONSULTA)){
			UtilsMessage.addErrorMessage("Usuário sem permissão de " + ETipoAcessoGUM.CONSULTA.getDescricao() + ".");
			return;
		}
		
		inspecoesCamera1 = ejbImagem.getInspecaoBlisterList(lote, 1);
		inspecoesCamera2 = ejbImagem.getInspecaoBlisterList(lote, 2);
		
	}
	
	@SuppressWarnings("static-access")
	public void close() throws IOException{
		
		if (image != null){
			image.close();
		}
		
		File directory = new File(ejbImagem.TEMP_SERVER + "\\PosSelagem");
		
		deleteDirectory(directory);
		
	}
	
	private boolean deleteDirectory(File directory) {
	    
		if(directory.exists()){
	        File[] files = directory.listFiles();
	        if(null!=files){
	            for(int i=0; i<files.length; i++) {
	                if(files[i].isDirectory()) {
	                    deleteDirectory(files[i]);
	                }
	                else {
	                    files[i].delete();
	                }
	            }
	        }
	    }
		
	    return(directory.delete());
	}
	
	public void openImageCamera(InspecaoBlister inspecaoCamera, int camera) {
		try {
			openImage(inspecaoCamera.getCodigoLote(), inspecaoCamera.getIdInspecao(), camera);
		} catch (XPathExpressionException e) {
			addErrorMessage("Erro[0]: " + e.getMessage());
		} catch (IOException e) {
			addErrorMessage("Erro[1]: " + e.getMessage());
		} catch (SAXException e) {
			addErrorMessage("Erro[2]: " + e.getMessage());
		} catch (ParserConfigurationException e) {
			addErrorMessage("Erro[3]: " + e.getMessage());
		} catch (TransformerException e) {
			addErrorMessage("Erro[4]: " + e.getMessage());
		}
	}

	public void openImage(String lote, Integer idInspecao, Integer camera) throws IOException, 
																				  XPathExpressionException, 
																				  SAXException, 
																				  ParserConfigurationException, 
																				  TransformerException {
		setImage(null);
		
		String svg 			= ejbImagem.donwloadSource(lote, idInspecao, camera, "svg");
		String jpg 			= ejbImagem.donwloadSource(lote, idInspecao, camera, "jpg");
		String targetFile	= ejbImagem.getTargetFile(svg, jpg);
		
		
		setUrlTargetImage(targetFile);
		
		setImage(getImageInspecao());
		
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("PF('imageDialogWidget').show();");
		
		
	}
	
	private InputStream getImageInspecao() throws IOException {
		
		if (urlTargetImage != null && !urlTargetImage.isEmpty()){
			FileInputStream fileInputStream = new FileInputStream(urlTargetImage);
			DefaultStreamedContent defaultStreamedContent = new DefaultStreamedContent(fileInputStream, "image/svg+xml");
			return defaultStreamedContent.getStream();
		}
		
		return null;
    }

	public List<InspecaoBlister> getInspecoesCamera1() {
		return inspecoesCamera1;
	}

	public void setInspecoesCamera1(List<InspecaoBlister> inspecoesCamera1) {
		this.inspecoesCamera1 = inspecoesCamera1;
	}

	public List<InspecaoBlister> getInspecoesCamera2() {
		return inspecoesCamera2;
	}

	public void setInspecoesCamera2(List<InspecaoBlister> inspecoesCamera2) {
		this.inspecoesCamera2 = inspecoesCamera2;
	}

	public String getUrlTargetImage() {
		return urlTargetImage;
	}

	public void setUrlTargetImage(String urlTargetImage) {
		this.urlTargetImage = urlTargetImage;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public InputStream getImage() {
		return image;
	}

	public void setImage(InputStream image) {
		this.image = image;
	}	
	
}
