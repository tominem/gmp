package br.com.prati.tim.collaboration.gmp.ejb.ordemproducao;

import br.prati.tim.gmp.ws.ordemproducao.OrdemProducao;

public interface OrdemProducaoEJB {

	OrdemProducao carregarOrdemProducao(Integer numeroOrdem) throws Exception;

}
