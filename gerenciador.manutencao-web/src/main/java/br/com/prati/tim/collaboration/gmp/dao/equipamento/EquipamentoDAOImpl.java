package br.com.prati.tim.collaboration.gmp.dao.equipamento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.prati.tim.collaboration.gp.jpa.Equipamento;

public class EquipamentoDAOImpl extends AbstractJPADAO<Equipamento> implements EquipamentoDAO{

	public EquipamentoDAOImpl() {
		super(Equipamento.class);
	}
	
	@Override
	public List<Equipamento> findLikeOrNotLikeWithLimit(String pattern, Optional<Integer> limit, Optional<Boolean> active, FilterParam<?>... filterParams) {
		List<FilterParam<?>> collect = Arrays.asList(filterParams).stream().filter(fp -> !fp.getName().equals("Tipo")).collect(Collectors.toList());
		
		if(collect == null || collect.size() == 0) return new ArrayList<Equipamento>();
		
		filterParams = new FilterParam<?>[collect.size()];
		
		List<Equipamento> equipamentos = super.findLikeOrNotLikeWithLimit(pattern, limit, active, collect.toArray(filterParams));
		
		List<Equipamento> filterByTipo = equipamentos.stream()
				.filter(e -> e.getTipoEquipamento().toString().toLowerCase().contains(pattern.toLowerCase())).collect(Collectors.toList());
		
		return filterByTipo != null && filterByTipo.size() > 0 ? equipamentos : equipamentos;
	}

}
