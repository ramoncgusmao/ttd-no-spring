package com.ramon.tdd.repository.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ramon.tdd.model.Pessoa;
import com.ramon.tdd.repository.filtro.PessoaFiltro;

@Component
public class PessoaRepositoryImpl implements PessoaRepositoryQueries{

	@PersistenceContext
	private EntityManager manager;
	@Override
	public List<Pessoa> filter(PessoaFiltro filtro) {
		final StringBuilder sb = new StringBuilder();
		final Map<String, Object> params = new HashMap<>();

		sb.append("SELECT p FROM Pessoa p JOIN p.telefones tele where p.id is not null  ");
		
		carregarValorSeNecessario(filtro.getNome(),"nome", sb, params);
		carregarValorSeNecessario(filtro.getCpf(),"cpf", sb, params);
		carregarValorDeTelefonesSeNecessario(filtro.getDdd(),"ddd", sb, params);
		carregarValorDeTelefonesSeNecessario(filtro.getTelefone(),"numero", sb, params);
		
		
		Query query = manager.createQuery(sb.toString(), Pessoa.class);
		preencherParametrosDaQuery(params, query);
		return query.getResultList();
	}


	private void carregarValorSeNecessario(String consulta, String parametro, final StringBuilder sb,
			final Map<String, Object> params) {
		if( consulta != null && !consulta.isEmpty()) {
			sb.append(" AND p."+parametro+" LIKE :" + parametro +" ");
			params.put(parametro, "%" + consulta + "%");
		}
	}
	
	private void carregarValorDeTelefonesSeNecessario(String consulta, String parametro, final StringBuilder sb,
			final Map<String, Object> params) {
		if( consulta != null && !consulta.isEmpty()) {
			sb.append(" AND tele."+parametro+" LIKE :" + parametro +" ");
			params.put(parametro, "%" + consulta + "%");
		}
	}

	private void preencherParametrosDaQuery(final Map<String, Object> params, Query query) {
		for(Entry<String, Object> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
			
		}
	}

}
