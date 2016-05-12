package br.com.prati.tim.collaboration.gmp.mb.alarme;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.alarme.AlarmeEJB;
import br.com.prati.tim.collaboration.gmp.ex.FacesValidateException;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.prati.tim.collaboration.gp.jpa.Alarme;
import br.prati.tim.collaboration.gp.jpa.Arquivo;
import br.prati.tim.collaboration.gp.jpa.CategoriaAlarme;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAlarme;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoNotificacaoAlarme;

@Named("alarmeMB")
@ViewScoped
public class AlarmeCrudMB extends AbstractCrudMB<Alarme, Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6966525816419190286L;
	
	private final String CODIGO_ALARME_INPUT_ID				= "formCad:codigoAlarme";
	
	private final String TAG_SISTEMA_INPUT_ID				= "formCad:tagSistema";

	@Inject
	private AlarmeEJB ejb;
	
	private boolean alarmeManual;

	private UploadedFile uploadedFile;

	private String nomeDoArquivo;

	//================ METHODS ========================//
	
	@PostConstruct
	@Override
	public void initObjects() {
		super.initObjects();
		
		load();
	}
	
	@Override
	public void clean() {
		
		super.clean();
		
		load();
	}
	
	public void load(){
		
		CategoriaAlarme categoriaAlarme = new CategoriaAlarme();
		categoriaAlarme.setDescricao("");
		
		entityBean.setCategoriaAlarme(categoriaAlarme);
		entityBean.setArquivo(new Arquivo());
		
		ejb.setBeforeFileContent(null);
		
	}
	
	@Override
	public String getResourceDialogPath() {
		return "/cadastros/alarme/searchAlarme.xhtml";
	}

	@Override
	public Long getEntityId() {
		return entityBean.getIdAlarme();
	}

	@Override
	public void setEntityId(Long entityId) {
		entityBean.setIdAlarme(entityId);
	}

	@Override
	public Boolean getEntityStatus() {
		return entityBean.getStatus();
	}

	@Override
	public void setEntityStatus(Boolean status) {
		entityBean.setStatus(status);
	}

	@Override
	public CrudEJB<Alarme> getCrudEJB() {
		return ejb;
	}

	@Override
	public Class<Alarme> getEntityClass() {
		return Alarme.class;
	}
	
	public void selectCategoria(SelectEvent event) {

		Object object = event.getObject();

		if (object != null	&& object.getClass().getName().equals(CategoriaAlarme.class.getName())) {
			
			entityBean.setCategoriaAlarme((CategoriaAlarme) object);
			
			addInfoMessage("Categoria de alarme informada com sucesso.");
		}
			
	}
	
	@Override
	public boolean validate(ComponentSystemEvent event) {
		
		if( isAlarmeManual() ) return true;

		UIComponent components = event.getComponent();

		UIInput uiCodigoAlarme = (UIInput) components.findComponent(CODIGO_ALARME_INPUT_ID);
		String alarmeStr = uiCodigoAlarme.getSubmittedValue() != null ? ""
				: uiCodigoAlarme.getLocalValue().toString();

		UIInput uiTagSistema = (UIInput) components.findComponent(TAG_SISTEMA_INPUT_ID);
		String tagSistema = uiTagSistema.getSubmittedValue() != null ? ""
				: uiTagSistema.getLocalValue().toString();

		Integer codigoAlarme = 0;
		
		try {
			
			try {
				codigoAlarme = Integer.valueOf(alarmeStr);
			} catch (NumberFormatException e) {
				throw new FacesValidateException("Código do alarme inváido!", uiCodigoAlarme);
			}
			
			List<Alarme> foundAlarms = ejb.findByCodigoAlarmeAndTagSistema(codigoAlarme, tagSistema.toUpperCase());
			
			//if on update mode
			if (getEntityId() != null) {
				for (Alarme alarme : foundAlarms) {
					if( !alarme.getIdAlarme().equals(getEntityId())) {
						throw new FacesValidateException("Já existe um alarme cadastrado com a mesma Tag de sistema e código do alarme!", uiCodigoAlarme, uiTagSistema);
					}
				}
			}
			
			else if( foundAlarms != null && foundAlarms.size() > 0 ){
				throw new FacesValidateException("Já existe um alarme cadastrado com a mesma Tag de sistema e código do alarme!", uiCodigoAlarme, uiTagSistema);
			}

		} catch (FacesValidateException e) {
			
			addErrorMessage(e.getMessage());
			
			return false;
			
		}
		
		return true;
	}
	
	public void onChangeTipoAlarme(){
		setAlarmeManual(entityBean.getTipoAlarme() == ETipoAlarme.MANUAL);
	}
	
	public void fazerUpload(FileUploadEvent fileUploadEvent) {
		
		uploadedFile 	= fileUploadEvent.getFile();
		
		entityBean.getArquivo().setNome(uploadedFile.getFileName());
		entityBean.getArquivo().setArquivo(uploadedFile.getContents());
		entityBean.getArquivo().setTamanho(new BigDecimal(uploadedFile.getSize()));
		entityBean.getArquivo().setExtensao(".pdf");
		
		ejb.setBeforeFileContent(uploadedFile.getContents());
		
	}

	public String getNomeDoArquivo() {
		return nomeDoArquivo;
	}
	
	public List<ETipoAlarme> getTipoAlarmes(){
		List<ETipoAlarme> tipoAlarmes = Arrays.asList(ETipoAlarme.values());
		Collections.sort(tipoAlarmes, (x1,x2) -> x1.getDescricao().compareTo(x2.getDescricao()));
		
		return tipoAlarmes;
	}
	
	public List<ETipoNotificacaoAlarme> getTipoNotificacoes(){
		List<ETipoNotificacaoAlarme> tpNotificacaoes = Arrays.asList(ETipoNotificacaoAlarme.values());
		Collections.sort(tpNotificacaoes, (x1,x2) -> x1.getDescricao().compareTo(x2.getDescricao()));
		
		return tpNotificacaoes;
	}

	public boolean isAlarmeManual() {
		return alarmeManual;
	}

	public void setAlarmeManual(boolean alarmeManual) {
		this.alarmeManual = alarmeManual;
	}

}
