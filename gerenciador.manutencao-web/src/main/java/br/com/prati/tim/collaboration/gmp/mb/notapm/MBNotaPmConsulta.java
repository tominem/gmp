package br.com.prati.tim.collaboration.gmp.mb.notapm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.mb.AbstractBaseMB;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.NotaPmMaquina;

@Named("mbNotaPmConsluta")
@ViewScoped
public class MBNotaPmConsulta extends AbstractBaseMB {
	
	private static final long	serialVersionUID	= 1L;

	private Date				dataInical;

	private Date				dataFinal;

	private List<Maquina>		maquinas;

	private List<Maquina>		maquinasSelecionadas;

	private List<NotaPmMaquina>	notasPm;

	@PostConstruct
	public void init(){
		
		setMaquinasSelecionadas	(new ArrayList<Maquina>());
		setMaquinas				(new ArrayList<Maquina>());
		setNotasPm				(new ArrayList<NotaPmMaquina>());
		
	}
	
	public void consultar(){
		
	}
	
	@Override
	public boolean validate(ComponentSystemEvent event) {
		return false;
	}

	@Override
	public ValidateComponent[] getValidaComponents() {
		return null;
	}

	public Date getDataInical() {
		return dataInical;
	}

	public void setDataInical(Date dataInical) {
		this.dataInical = dataInical;
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

}
