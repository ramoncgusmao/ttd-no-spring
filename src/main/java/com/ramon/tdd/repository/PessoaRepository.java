package com.ramon.tdd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ramon.tdd.model.Pessoa;
import com.ramon.tdd.repository.filtro.PessoaFiltro;
import com.ramon.tdd.repository.helper.PessoaRepositoryQueries;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer>, PessoaRepositoryQueries{


	Optional<Pessoa> findByCpf(String cpf);

	@Query(value = "select DISTINCT p from Pessoa p INNER JOIN FETCH p.telefones  tele   where tele.ddd = :ddd AND tele.numero = :numero")
	Optional<Pessoa> findByTelefoneDddAndTelefoneNumero(@Param("ddd") String ddd, @Param("numero") String numero);

}
