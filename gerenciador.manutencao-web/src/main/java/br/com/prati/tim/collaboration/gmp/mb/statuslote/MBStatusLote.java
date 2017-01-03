package br.com.prati.tim.collaboration.gmp.mb.statuslote;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.lote.LoteEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractBaseMB;
import br.com.prati.tim.collaboration.gmp.mb.UtilsMessage;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.com.prati.tim.collaboration.gmp.mb.login.SessionUtil;
import br.prati.tim.collaboration.gp.jpa.Lote;
import br.prati.tim.collaboration.gp.jpa.enumerator.EStatusLote;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;

@Named("mbStatusLote")
@ViewScoped
public class MBStatusLote extends AbstractBaseMB implements Serializable {

	private static final long	serialVersionUID	= 1L;
	
	@Inject
	private LoteEJB				ejbLote;
	
	private Lote				lote;

	private EStatusLote			novoStatusLote;

	private String				justificativa;
	
	private Long				crachaUsuario;
	
	@PostConstruct
	public void init(){
		
		setLote				(new Lote());
		setJustificativa	("");
		setCrachaUsuario	(Long.parseLong(SessionUtil.getCracha().toString()));
		
	}
	
	public void save(){
		
		if (!SessionUtil.temPermissaoGUM(ETipoAcessoGUM.ALTERACAO)){
			UtilsMessage.addErrorMessage("Usuário sem permissão de " + ETipoAcessoGUM.ALTERACAO.getDescricao() + ".");
			return;
		}
		
		lote.setStatus(novoStatusLote);
		
		try {
			ejbLote.save(lote);
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage("[Lote] Erro ao salvar status: " + e.getMessage());
			return;
		}
		
		cleanForm();
		
		addInfoMessage("Status alterado com sucesso.");
		
	}
	
	public void select(SelectEvent event){
		
		Object object = event.getObject();

		if (object != null && object.getClass().getName().equals(Lote.class.getName())){
			
			Lote loteSelecionado = (Lote) object;
			
			if (loteSelecionado.equals(getLote())){
				return;
			}
			
			selectLote(loteSelecionado);
		}
		
	}
	
	private void selectLote(Lote lote){
		setLote(lote);
	}
	
	public List<EStatusLote> getStatusLotes() {
		
		List<EStatusLote> list = new ArrayList<EStatusLote>();
		
		for (EStatusLote eStatusLote : Arrays.asList(EStatusLote.values())){
			if (lote.getStatus() != null && lote.getStatus() == eStatusLote){
				continue;
			} else{
				list.add(eStatusLote);
			}
		}
		
		Collections.sort(list, (x1, x2) -> x1.getDesc().compareTo(x2.getDesc()));
		
		return list;
	}
	
	@Override
	public boolean validate(ComponentSystemEvent event) {
		return false;
	}

	@Override
	public ValidateComponent[] getValidaComponents() {
		return null;
	}

	public EStatusLote getNovoStatusLote() {
		return novoStatusLote;
	}

	public void setNovoStatusLote(EStatusLote novoStatusLote) {
		this.novoStatusLote = novoStatusLote;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public Long getCrachaUsuario() {
		return crachaUsuario;
	}

	public void setCrachaUsuario(Long crachaUsuario) {
		this.crachaUsuario = crachaUsuario;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

}
