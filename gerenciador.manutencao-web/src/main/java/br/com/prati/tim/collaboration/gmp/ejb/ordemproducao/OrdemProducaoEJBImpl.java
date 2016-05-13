package br.com.prati.tim.collaboration.gmp.ejb.ordemproducao;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.ws.OrdemProducaoServicePort;
import br.prati.tim.gmp.ws.ordemproducao.ErroConexaoSAPException_Exception;
import br.prati.tim.gmp.ws.ordemproducao.FuncaoNaoEncontradaException_Exception;
import br.prati.tim.gmp.ws.ordemproducao.OrdemProducao;
import br.prati.tim.gmp.ws.ordemproducao.OrdemProducaoException_Exception;

@Stateless
public class OrdemProducaoEJBImpl implements OrdemProducaoEJB{

	@Inject
	private OrdemProducaoServicePort opPort;
	
	@Override
	public OrdemProducao carregarOrdemProducao(Integer numeroOrdem) throws Exception {
			
		try {

			return opPort.consultaOrdem(numeroOrdem);

		} catch (ErroConexaoSAPException_Exception e) {

			e.printStackTrace();
			throw new Exception("Erro de conexão com serviço SAP");

		} catch (FuncaoNaoEncontradaException_Exception e) {

			e.printStackTrace();
			throw new Exception("Função Não encontrada no serviço SAP");

		} catch (OrdemProducaoException_Exception e) {

			e.printStackTrace();
			throw new Exception("Ordem não encontrada ou inválida");

		} catch (Exception e){

			e.printStackTrace();
			throw new Exception("Falha de conexão com serviço SAP");

		}
	}
	
}
