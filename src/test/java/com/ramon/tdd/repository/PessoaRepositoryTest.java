package com.ramon.tdd.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.ramon.tdd.model.Pessoa;
import com.ramon.tdd.repository.filtro.PessoaFiltro;

@Sql(scripts = "classpath:load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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

		assertThat(pessoa.getNome()).isEqualTo("Iago");
		assertThat(pessoa.getCpf()).isEqualTo("86730543540");
		
	}
	
	
	@Test
	public void naoDeveEncontrarCPFInexistente() throws Exception {
		Optional<Pessoa> optional = sut.findByCpf("05539748310");
		
		assertThat(optional.isPresent()).isFalse();
		
	}
	
	@Test
	public void deveEncontrarPessoaPeloDddENumero() {
		
		Optional<Pessoa> optional = sut.findByTelefoneDddAndTelefoneNumero("86", "35006330");

		Pessoa pessoa = optional.get();

		assertThat(pessoa.getNome()).isEqualTo("Cauê");
		assertThat(pessoa.getCpf()).isEqualTo("38767897100");
	}
	
	
	@Test
	public void NaodeveEncontrarPessoaPeloDddENumero() {
		
		Optional<Pessoa> optional = sut.findByTelefoneDddAndTelefoneNumero("10", "34006330");

		assertThat(optional.isPresent()).isFalse();
		
	}
	
	@Test
	public void deveFiltrarPorParteDoNome() {
		
		PessoaFiltro filtro = new PessoaFiltro();
		filtro.setNome("a");
		
		List<Pessoa> lista = sut.filter(filtro);
		
		assertThat(lista.size()).isEqualTo(3);
		
	}
	
	@Test
	public void deveFiltrarPorParteDoCPF() {
		
		PessoaFiltro filtro = new PessoaFiltro();
		filtro.setCpf("78");
		
		List<Pessoa> lista = sut.filter(filtro);
		
		assertThat(lista.size()).isEqualTo(3);
		
	}
	
	@Test
	public void deveFiltrarPorParteDoCPFeParteDoNome() {
		
		PessoaFiltro filtro = new PessoaFiltro();
		filtro.setCpf("78");
		filtro.setNome("a");
		List<Pessoa> lista = sut.filter(filtro);
		
		assertThat(lista.size()).isEqualTo(2);
		
	}
	
	
	@Test
	public void deveFiltrarPorParteDoDdd() {
		
		PessoaFiltro filtro = new PessoaFiltro();
		filtro.setDdd("21");
		
		List<Pessoa> lista = sut.filter(filtro);
		
		assertThat(lista.size()).isEqualTo(1);
		
	}
	
	
	@Test
	public void deveFiltrarPorParteDoTelefone() {
		
		PessoaFiltro filtro = new PessoaFiltro();
		filtro.setTelefone("9975");
		
		List<Pessoa> lista = sut.filter(filtro);
		
		assertThat(lista.size()).isEqualTo(1);
		
	}
}
