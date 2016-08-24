package br.com.prati.tim.collaboration.gmp.mb.manutencao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;
import org.joda.time.Period;

import br.com.prati.tim.collaboration.gmp.ejb.maquina.MaquinaEJB;
import br.com.prati.tim.collaboration.gmp.ejb.servico.LogServicoEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractBaseMB;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.prati.tim.collaboration.gp.jpa.LogServico;
import br.prati.tim.collaboration.gp.jpa.Maquina;

@Named("mbManutencaoConsulta")
@ViewScoped
public class MBManutencaoConsulta extends AbstractBaseMB {

	private static final long	serialVersionUID	= 1L;

	@Inject
	private LogServicoEJB		ejbLogServico;

	@Inject
	private MaquinaEJB			ejbMaquina;

	private Date				dataInicial;

	private Date				dataFinal;

	private List<Maquina>		maquinas;

	private List<Maquina>		maquinasSelecionadas;

	private List<LogServico>	logServicos;
	
	@PostConstruct
	public void Init(){
		
		setMaquinasSelecionadas	(new ArrayList<Maquina>());
		setLogServicos			(new ArrayList<LogServico>());
		setMaquinas				(ejbMaquina.getCrudDAO().findActives());
	}
	
	public void find(){
		
		logServicos = ejbLogServico.findByPeriodoMaquinasAndServicos(dataInicial, dataFinal, maquinasSelecionadas);
		
		if (logServicos.isEmpty()){
			addInfoMessage("Registros não encontrados de acordo com os filtros informados.");
		}else{
			addInfoMessage("Consulta realizada com sucesso.");
		}
		
	}
	
	public String getDuracaoServico(Date dataHoraInicio, Date dataHoraFim){
		
		if (dataHoraInicio == null || dataHoraFim == null){
			return "-";
		}
		
		DateTime 	startTime 	= 	new DateTime(dataHoraInicio), endTime = new DateTime(dataHoraFim);
		Period 		p 			=	new Period(startTime, endTime);
		
		long		days		= 	p.getDays();
		long 		hours 		= 	p.getHours();
		long 		minutes 	= 	p.getMinutes();
		
		String 		horas 		=	String.format("%2s",  String.valueOf(hours  )).replace(' ', '0');
		String 		minutos 	= 	String.format("%2s",  String.valueOf(minutes)).replace(' ', '0');
		
		String duracaoStr = horas + "h " + minutos + "m";
		
		if (days > 0){
			duracaoStr = days + "d " + duracaoStr; 
		}
		
		return duracaoStr;
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

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
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

	public List<LogServico> getLogServicos() {
		return logServicos;
	}

	public void setLogServicos(List<LogServico> logServicos) {
		this.logServicos = logServicos;
	}

}
