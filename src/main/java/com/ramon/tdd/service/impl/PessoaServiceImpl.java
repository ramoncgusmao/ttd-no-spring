package com.ramon.tdd.service.impl;

import java.util.Optional;

import com.ramon.tdd.model.Pessoa;
import com.ramon.tdd.model.Telefone;
import com.ramon.tdd.repository.PessoaRepository;
import com.ramon.tdd.service.PessoaService;
import com.ramon.tdd.service.TelefoneNaoEncontradoException;
import com.ramon.tdd.service.UnicidadeTelefoneException;

public class PessoaServiceImpl implements PessoaService {

	private PessoaRepository repository;

	public PessoaServiceImpl(PessoaRepository repository) {
		this.repository = repository;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException, UnicidadeTelefoneException {
		// TODO Auto-generated method stub
		Optional<Pessoa> optional =  repository.findByCpf(pessoa.getCpf());
		
		if( optional.isPresent()) {
			throw new UnicidadeCpfException();
		}
		
		String ddd = pessoa.getTelefones().get(0).getDdd();
		String numero = pessoa.getTelefones().get(0).getNumero();
		
		optional = repository.findByTelefoneDddAndTelefoneNumero(ddd, numero);
		
		if( optional.isPresent()) {
				throw new UnicidadeTelefoneException();
		}
		return repository.save(pessoa);
	}

	@Override
	public Pessoa buscarPorTelefone(Telefone telefone) throws TelefoneNaoEncontradoException {
		
		Optional<Pessoa> pessoa = repository.findByTelefoneDddAndTelefoneNumero(telefone.getDdd(), telefone.getNumero());
		return pessoa.orElseThrow(() ->  new TelefoneNaoEncontradoException());
	}
	

}
