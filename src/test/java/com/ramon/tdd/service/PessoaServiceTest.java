package com.ramon.tdd.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.ramon.tdd.model.Pessoa;
import com.ramon.tdd.model.Telefone;
import com.ramon.tdd.repository.PessoaRepository;
import com.ramon.tdd.service.impl.PessoaServiceImpl;
import com.ramon.tdd.service.impl.UnicidadeCpfException;

@RunWith(SpringRunner.class)
public class PessoaServiceTest {

	private static final String NUMERO = "98888888";
	private static final String DDD = "098";
	private static final String NOME = "Ramon";
	private static final String CPF = "055397";

	private PessoaService sut;

	@MockBean
	private PessoaRepository repository;

	private Pessoa pessoa;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	
	private Telefone telefone;

	@Before
	public void setUp() throws Exception {
		sut = new PessoaServiceImpl(repository);
		pessoa = new Pessoa();
		pessoa.setCpf(CPF);
		pessoa.setNome(NOME);

		telefone = new Telefone();
		telefone.setDdd(DDD);
		telefone.setNumero(NUMERO);
		pessoa.setTelefones(new ArrayList());
		pessoa.getTelefones().add(telefone);
		when(repository.findByCpf(CPF)).thenReturn(Optional.empty());
		when(repository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.empty());
	}

	@Test
	public void deveSalvarPessoaNoRepositorio() throws Exception {
		sut.salvar(pessoa);

		verify(repository).save(pessoa);
	}

	@Test
	public void naoDeveSalvarDuasPessoasComOMesmoCpf() throws Exception {
		when(repository.findByCpf(CPF)).thenReturn(Optional.of(pessoa));
		expectedException.expect(UnicidadeCpfException.class);
		expectedException.expectMessage("Já existe pessoa cadastrada com o CPF '"+ pessoa.getCpf() + "'");
		sut.salvar(pessoa);
	}

	@Test(expected = UnicidadeTelefoneException.class)
	public void naoDeveSalvarDuasPessoasComOMesmoTelefoneNumero() throws Exception {
		when(repository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));

		sut.salvar(pessoa);

	}

	@Test
	public void deveProcurarPessoaPorDDDENumeroDoTelefone() throws Exception {

		when(repository.findByTelefoneDddAndTelefoneNumero(DDD, NUMERO)).thenReturn(Optional.of(pessoa));

		Pessoa pessoateste = sut.buscarPorTelefone(telefone);

		verify(repository).findByTelefoneDddAndTelefoneNumero(DDD, NUMERO);

		assertThat(pessoateste).isNotNull();
		assertThat(pessoateste.getNome()).isEqualTo(NOME);
		assertThat(pessoateste.getCpf()).isEqualTo(CPF);
	}

	@Test(expected = TelefoneNaoEncontradoException.class)
	public void deveRetornarExcecaoDeNaoEncontradoQuandoNaoExistirDDDENumero() throws Exception {

		Pessoa pessoateste = sut.buscarPorTelefone(telefone);

	}

}
