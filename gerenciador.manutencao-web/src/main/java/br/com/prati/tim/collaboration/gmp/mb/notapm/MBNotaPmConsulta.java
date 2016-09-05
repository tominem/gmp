package br.com.prati.tim.collaboration.gmp.mb.notapm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.ejb.maquina.MaquinaEJB;
import br.com.prati.tim.collaboration.gmp.ejb.notapm.NotaPmMaquinaEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractBaseMB;
import br.com.prati.tim.collaboration.gmp.mb.UtilsMessage;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.com.prati.tim.collaboration.gmp.mb.login.SessionUtil;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.NotaPmMaquina;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;

@Named("mbNotaPmConsluta")
@ViewScoped
public class MBNotaPmConsulta extends AbstractBaseMB {
	
	private static final long	serialVersionUID	= 1L;

	@Inject
	private NotaPmMaquinaEJB	ejbNotaPm;
	
	@Inject
	private MaquinaEJB			ejbMaquina;
	
	private Date				dataInicial;

	private Date				dataFinal;

	private List<Maquina>		maquinas;

	private List<Maquina>		maquinasSelecionadas;

	private List<NotaPmMaquina>	notasPm;
	
	private List<NotaPmMaquina>	notasPmFiltradas;

	@PostConstruct
	public void init(){
		
		setMaquinasSelecionadas	(new ArrayList<Maquina>());
		setMaquinas				(ejbMaquina.getCrudDAO().findActives());
		setNotasPm				(new ArrayList<NotaPmMaquina>());
		
	}
	
	public void consultar(){
		
		if (!SessionUtil.temPermissaoGUM(ETipoAcessoGUM.CONSULTA)){
			UtilsMessage.addErrorMessage("Usuário sem permissão de " + ETipoAcessoGUM.CONSULTA.getDescricao() + ".");
			return;
		}
		
		notasPm = ejbNotaPm.findByPeriodoAndMaquinas(dataInicial, dataFinal, maquinasSelecionadas);
		
		if (notasPm.isEmpty()){
			addInfoMessage("Registros não encontrados de acordo com os filtros informados.");
		}else{
			addInfoMessage("Consulta realizada com sucesso.");
		}
	}
	
	@Override
	public boolean validate(ComponentSystemEvent event) {
		return false;
	}

	@Override
	public ValidateComponent[] getValidaComponents() {
		return null;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInical) {
		this.dataInicial = dataInical;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public List<Maquina> getMaquinas() {
		return maquinas;
	}

	public void setMaquinas(List<Maquina> maquinas) {
		this.maquinas = maquinas;
	}

	public List<Maquina> getMaquinasSelecionadas() {
		return maquinasSelecionadas;
	}

	public void setMaquinasSelecionadas(List<Maquina> maquinasSelecionadas) {
		this.maquinasSelecionadas = maquinasSelecionadas;
	}

	public List<NotaPmMaquina> getNotasPm() {
		return notasPm;
	}

	public void setNotasPm(List<NotaPmMaquina> notasPm) {
		this.notasPm = notasPm;
	}

	public List<NotaPmMaquina> getNotasPmFiltradas() {
		return notasPmFiltradas;
	}

	public void setNotasPmFiltradas(List<NotaPmMaquina> notasPmFiltradas) {
		this.notasPmFiltradas = notasPmFiltradas;
	}

}
