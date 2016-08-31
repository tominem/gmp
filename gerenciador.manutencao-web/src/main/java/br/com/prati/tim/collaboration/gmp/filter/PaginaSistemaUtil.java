package br.com.prati.tim.collaboration.gmp.filter;

import java.util.ArrayList;
import java.util.List;

public class PaginaSistemaUtil {
	
	public static List<PaginaSistema> getPaginasSistema() {
		
		List<PaginaSistema> paginas = new ArrayList<PaginaSistema>();
	
		// Cadastros
		paginas.add(new PaginaSistema("cadastros/tipocomponente/formTipoComponente.xhtml", 		"CADASTRO_TIPO_COMPONENTE"));
		paginas.add(new PaginaSistema("cadastros/menuconfig/formMenuConfig.xhtml", 				"CADASTRO_MENUS_CONFIGURACAO"));
		paginas.add(new PaginaSistema("cadastros/itemconfig/formItemConfig.xhtml", 				"CADASTRO_ITEM_CONFIGURACAO"));
		paginas.add(new PaginaSistema("cadastros/fabricante/formFabricante.xhtml", 				"CADASTRO_FABRICANTE"));
		paginas.add(new PaginaSistema("cadastros/tipocodigo/formTipoCodigo.xhtml", 				"CADASTRO_TIPO_CODIGO"));
		paginas.add(new PaginaSistema("cadastros/equipamento/formEquipamento.xhtml", 			"CADASTRO_EQUIPAMENTO"));
		paginas.add(new PaginaSistema("cadastros/tipoinspecao/formTipoInspecao.xhtml", 			"CADASTRO_TIPO_INSPECAO"));
		paginas.add(new PaginaSistema("cadastros/tipocomunicacao/formTipoComunicacao.xhtml", 	"CADASTRO_TIPO_COMUNICACAO"));
		paginas.add(new PaginaSistema("cadastros/receita/formReceita.xhtml", 					"CADASTRO_RECEITA"));
		paginas.add(new PaginaSistema("cadastros/setor/formSetor.xhtml", 						"CADASTRO_SETOR"));
		paginas.add(new PaginaSistema("cadastros/sala/formSala.xhtml", 							"CADASTRO_SALA"));
		paginas.add(new PaginaSistema("cadastros/linha/formLinha.xhtml", 						"CADASTRO_LINHA"));
		paginas.add(new PaginaSistema("cadastros/maquina/formMaquina.xhtml", 					"CADASTRO_MAQUINA"));
		paginas.add(new PaginaSistema("cadastros/categoriaalarme/formCategoriaAlarme.xhtml", 	"CADASTRO_CATEGORIA_ALARME"));
		paginas.add(new PaginaSistema("cadastros/alarme/formAlarme.xhtml", 						"CADASTRO_ALARME"));
		paginas.add(new PaginaSistema("cadastros/produto/formProduto.xhtml", 					"CADASTRO_PRODUTO"));
		paginas.add(new PaginaSistema("cadastros/subproduto/formSubproduto.xhtml", 				"CADASTRO_SUBPRODUTO"));
		
		// Consultas
		paginas.add(new PaginaSistema("consultas/notas/notaspm.xhtml", 							"CONSULTA_NOTAS_PM"));
		paginas.add(new PaginaSistema("consultas/manutencao/manutencoes.xhtml", 				"CONSULTA_MANUTENCOES"));
		paginas.add(new PaginaSistema("consultas/historico/historicoproducao.xhtml", 			"CONSULTA_HISTORICO_PRODUCAO"));
		paginas.add(new PaginaSistema("consultas/notificacao/notificacaoconsulta.xhtml", 		"CONSULTA_NOTIFICACOES_ENVIADAS"));
		
		// Notificações
		paginas.add(new PaginaSistema("notificacoes/notificacao.xhtml", 						"ENVIO_NOTIFICACAO"));

		// Utilidades
		paginas.add(new PaginaSistema("utilidades/statuslote/statuslote.xhtml", 				"ALTERAR_STATUS_LOTE"));
		paginas.add(new PaginaSistema("utilidades/posselagem/imagens.xhtml", 					"VISUALIZAR_IMAGEM_POSSELAGEM"));

		// Configurações
		paginas.add(new PaginaSistema("configuracoes/variaveisclp/formVariaveisCLP.xhtml", 		"CONFIGURACAO_VARIAVEIS_CLP"));
		paginas.add(new PaginaSistema("configuracoes/receita/configuracaoreceita.xhtml", 		"CONFIGURACAO_VALOR_RECEITA"));
		paginas.add(new PaginaSistema("configuracoes/equipamento/configuracaoequipamento.xhtml","CONFIGURACAO_EQUIPAMENTO"));
		paginas.add(new PaginaSistema("configuracoes/geral/configuracao.xhtml",					"CONFIGURACAO_GERAL"));
		
		// Auditoria
		paginas.add(new PaginaSistema("auditoria/auditoria.xhtml", 								"AUDITORIA"));
		
		return paginas;
	}
}
