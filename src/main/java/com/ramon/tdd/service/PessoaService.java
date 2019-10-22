package com.ramon.tdd.service;

import com.ramon.tdd.model.Pessoa;
import com.ramon.tdd.model.Telefone;
import com.ramon.tdd.service.impl.UnicidadeCpfException;

public interface PessoaService {

	Pessoa salvar(Pessoa pessoa) throws UnicidadeCpfException, UnicidadeTelefoneException;

	Pessoa buscarPorTelefone(Telefone telefone) throws TelefoneNaoEncontradoException;

}
