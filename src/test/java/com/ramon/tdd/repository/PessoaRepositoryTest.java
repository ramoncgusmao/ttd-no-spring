package com.ramon.tdd.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.ramon.tdd.model.Pessoa;

@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
public class PessoaRepositoryTest {

	@Autowired
	private PessoaRepository sut;
	
	@Test
	public void deveProcurarPessoaPeloCpf() throws Exception {
		Optional<Pessoa> optional = sut.findByCpf("86730543540");
		
		assertThat(optional.isPresent()).isTrue();
		
		Pessoa pessoa = optional.get();
		assertThat(pessoa.getId()).isEqualTo(1);
		assertThat(pessoa.getNome()).isEqualTo("Iago");
		assertThat(pessoa.getCpf()).isEqualTo("86730543540");
		
	}
	
	
	@Test
	public void naoDeveEncontrarCPFInexistente() throws Exception {
		Optional<Pessoa> optional = sut.findByCpf("05539748310");
		
		assertThat(optional.isPresent()).isFalse();
		
	}
}
