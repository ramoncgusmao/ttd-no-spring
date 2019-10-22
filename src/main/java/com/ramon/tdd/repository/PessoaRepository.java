package com.ramon.tdd.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import com.ramon.tdd.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{


	Optional<Pessoa> findByCpf(String cpf);

	@Query(value = "select p from Pessoa p")
	Optional<Pessoa> buscarPorTelefoneEDdd(String ddd, String numero);

}
