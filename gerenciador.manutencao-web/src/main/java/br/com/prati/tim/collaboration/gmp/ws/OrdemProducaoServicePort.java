package br.com.prati.tim.collaboration.gmp.ws;

import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.configuracaogeral.ConfiguracaoGeralEJB;
import br.com.prati.tim.collaboration.gmp.ex.WSException;
import br.prati.tim.collaboration.gp.jpa.ConfiguracaoGeral;
import br.prati.tim.collaboration.gp.jpa.enumerator.DefConfiguracaoGeralKeys;
import br.prati.tim.gmp.ws.ordemproducao.ErroConexaoSAPException_Exception;
import br.prati.tim.gmp.ws.ordemproducao.FuncaoNaoEncontradaException_Exception;
import br.prati.tim.gmp.ws.ordemproducao.OrdemProducao;
import br.prati.tim.gmp.ws.ordemproducao.OrdemProducaoException_Exception;
import br.prati.tim.gmp.ws.ordemproducao.OrdemProducaoService;
import br.prati.tim.gmp.ws.ordemproducao.OrdemProducaoWS;

public class OrdemProducaoServicePort {
	
	@Inject
	private ConfiguracaoGeralEJB	configEJB;

	public OrdemProducaoWS getOrdemProducaoWSPort() throws WSException {
		
		OrdemProducaoWS port;
		
		try {
			
			ConfiguracaoGeral ipGUM = configEJB.getByChave(DefConfiguracaoGeralKeys.IP_WS_INTEGRACAO_SAP.toString());
			
			String ipServerSap = ipGUM == null || ipGUM.getValor().isEmpty() ? "localhost:8080" : ipGUM.getValor(); 

			port = new DynamicServiceLocator(ipServerSap).getPort(OrdemProducaoWS.class, OrdemProducaoService.class);
			
		} catch (Exception e) {
			throw new WSException(e.getMessage());
		}
		
		return port;
    }
	
	public OrdemProducao consultaOrdem(Integer numeroOrdem) throws ErroConexaoSAPException_Exception, FuncaoNaoEncontradaException_Exception, OrdemProducaoException_Exception, WSException{
		
		return getOrdemProducaoWSPort().consultarOrdemProducaoMateriaisPorCodigo(numeroOrdem, "gmp", "gmp");
	}

}
