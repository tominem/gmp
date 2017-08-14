package br.com.prati.tim.collaboration.gmp.dao.historico.manutencao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.prati.tim.collaboration.gmp.mb.historico.manutencao.HistoricoPesquisaParams;
import br.prati.tim.collaboration.gp.jpa.HistManutencao;
import br.prati.tim.collaboration.gp.jpa.HistManutencaoDetail;

@Stateless
public class HistoricoManutencaoEjb {

	@Inject
	private EntityManager entityManager;
	
	public HistManutencao salvar(HistManutencao historicoManutencao) {
		
		List<HistManutencaoDetail> detailList = new ArrayList<>(historicoManutencao.getHistManutencaoDetailList());
		
		historicoManutencao.getHistManutencaoDetailList().clear();
		
		historicoManutencao.setHistManutencaoDetailList(detailList);
		
		return entityManager.merge(historicoManutencao);
	}

	@SuppressWarnings("unchecked")
	public List<HistManutencao> pesquisar(HistoricoPesquisaParams params) throws Exception {
		
		StringBuilder sb = new StringBuilder();

		StringBuilder sbParams = new StringBuilder();

		Map<String, Object> mapParams = new HashMap<>();
		
		sb.append("SELECT * ");
		sb.append("FROM hist_manutencao  ");
		sb.append("WHERE ");
		
		if (params.getDatahoraInicio() != null && params.getDatahoraFim() != null) {
			
			sbParams.append(":dataInicial <= datahora_fim AND datahora_inicio <= :dataFinal");
			
			mapParams.put("dataInicial", params.getDatahoraInicio());
			mapParams.put("dataFinal", params.getDatahoraFim());
			
		} else if (params.getDatahoraInicio() != null) {
			
			sbParams.append(" date_trunc('minute', datahora_inicio) >= :dataInicial ");
			mapParams.put("dataInicial", params.getDatahoraInicio());
			
			
		} else if (params.getDatahoraFim() != null) {
			
			sbParams.append(" date_trunc('minute', datahora_fim) <= :dataFinal ");
			mapParams.put("dataFinal", params.getDatahoraFim());
			
		}

		if (params.getDescricao() != null && !params.getDescricao().replaceAll(" ", "").isEmpty()) {

			if (!mapParams.isEmpty()) {
				sbParams.append(" AND ");
			}

			sbParams.append(" descricao LIKE :descricao ");
			mapParams.put("descricao", "%" + params.getDescricao() + "%");
		}

		if (params.getEquipamento() != null) {

			if (!mapParams.isEmpty()) {
				sbParams.append(" AND ");
			}

			sbParams.append(" id_equipamento = :idEquipamento ");

			mapParams.put("idEquipamento", params.getEquipamento().getIdEquipamento());

		}

		if (params.getMaquina() != null) {

			if (!mapParams.isEmpty()) {
				sbParams.append(" AND ");
			}

			sbParams.append(" id_maquina = :idMaquina ");

			mapParams.put("idMaquina", params.getMaquina().getIdMaquina());
		}

		if (params.getNotaPm() != null && !params.getNotaPm().replaceAll(" ", "").isEmpty()) {

			if (!mapParams.isEmpty()) {
				sbParams.append(" AND ");
			}

			sbParams.append(" nota_pm LIKE :notaPM ");

			mapParams.put("notaPM", "%" + params.getNotaPm() + "%");

		}

		if (mapParams.isEmpty()) {
			throw new Exception("Informe pelo menos um parâmetro de pesquisa");
		}
		
		sb.append(sbParams);
		
		Query query = entityManager.createNativeQuery(sb.toString(), HistManutencao.class);
		
		mapParams.forEach(query::setParameter);
		
		return query.getResultList();
	}
}
