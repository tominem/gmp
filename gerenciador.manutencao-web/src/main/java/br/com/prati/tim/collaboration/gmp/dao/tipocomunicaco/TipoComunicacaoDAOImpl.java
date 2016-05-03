package br.com.prati.tim.collaboration.gmp.dao.tipocomunicaco;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.TipoComunicacao;

public class TipoComunicacaoDAOImpl extends AbstractJPADAO<TipoComunicacao> implements TipoComunicacaoDAO{

	public TipoComunicacaoDAOImpl() {
		super(TipoComunicacao.class);
	}

}
