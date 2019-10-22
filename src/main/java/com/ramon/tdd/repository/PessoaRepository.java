package com.ramon.tdd.repository;

import java.util.Optional;

import com.ramon.tdd.model.Pessoa;

public interface PessoaRepository {

	Pessoa save(Pessoa pessoa);

	Optional<Pessoa> findByCpf(String cpf);

	Optional<Pessoa> findByTelefoneDddAndTelefoneNumero(String ddd, String numero);

}
