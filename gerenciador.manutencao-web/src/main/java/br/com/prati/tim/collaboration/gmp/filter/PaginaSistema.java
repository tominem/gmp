package br.com.prati.tim.collaboration.gmp.filter;

public class PaginaSistema {
	
	private String	pagina;

	private String	funcaoGUM;

	public PaginaSistema() {

	}

	public PaginaSistema(String pagina, String funcaoGUM) {
		this.pagina = pagina;
		this.funcaoGUM = funcaoGUM;
	}

	public String getPagina() {
		return pagina;
	}

	public void setPagina(String pagina) {
		this.pagina = pagina;
	}

	public String getFuncaoGUM() {
		return funcaoGUM;
	}

	public void setFuncaoGUM(String funcaoGUM) {
		this.funcaoGUM = funcaoGUM;
	}
}
