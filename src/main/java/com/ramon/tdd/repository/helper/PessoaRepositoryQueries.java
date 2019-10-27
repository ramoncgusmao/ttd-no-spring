package com.ramon.tdd.repository.helper;

import java.util.List;

import com.ramon.tdd.model.Pessoa;
import com.ramon.tdd.repository.filtro.PessoaFiltro;

public interface PessoaRepositoryQueries {

	List<Pessoa>  filter(PessoaFiltro filtro);
}
