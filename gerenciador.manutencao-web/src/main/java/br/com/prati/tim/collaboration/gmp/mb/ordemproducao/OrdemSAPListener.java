package br.com.prati.tim.collaboration.gmp.mb.ordemproducao;

import br.prati.tim.gmp.ws.ordemproducao.OrdemProducao;
import br.prati.tim.gmp.ws.ordemproducao.OrdemProducaoMateriais;

public interface OrdemSAPListener {
	
	void retrieveOrdem(OrdemProducao ordemProducao);
	
	void retrieveMaterialSelected(OrdemProducaoMateriais material);
	
}
