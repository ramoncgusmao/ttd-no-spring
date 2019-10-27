package com.ramon.tdd.Resource;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ramon.tdd.model.Pessoa;
import com.ramon.tdd.model.Telefone;
import com.ramon.tdd.service.PessoaService;
import com.ramon.tdd.service.TelefoneNaoEncontradoException;
import com.ramon.tdd.service.UnicidadeTelefoneException;
import com.ramon.tdd.service.impl.UnicidadeCpfException;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaService pessoaService;
	
	
	@GetMapping("/{ddd}/{numero}")
	public ResponseEntity<Pessoa> buscarPorDddENumeroDoTelefone(@PathVariable("ddd") String ddd, @PathVariable("numero") String numero) throws TelefoneNaoEncontradoException{
		
		final Telefone telefone = new Telefone();
		telefone.setDdd(ddd);
		telefone.setNumero(numero);
		
		final Pessoa pessoa = pessoaService.buscarPorTelefone(telefone);
		
		return new ResponseEntity<Pessoa>(pessoa,HttpStatus.OK);
	}
	
	
	
	@PostMapping
	public ResponseEntity<Pessoa> salvarNova(@RequestBody Pessoa pessoa, HttpServletResponse response) throws UnicidadeCpfException, UnicidadeTelefoneException{
		final Pessoa pessoaSalva = pessoaService.salvar(pessoa);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{ddd}/{numero}")
				.buildAndExpand(pessoa.getTelefones().get(0).getDdd(), pessoa.getTelefones().get(0).getNumero()).toUri();
		
		response.setHeader("Location", uri.toASCIIString());
				
		return new ResponseEntity<Pessoa>(pessoaSalva,HttpStatus.CREATED);
	}
	
	
	@ExceptionHandler({UnicidadeCpfException.class})
	public ResponseEntity<Erro> handleUnicidadeCpfException(UnicidadeCpfException e){
		return new ResponseEntity<Erro> (new Erro(e.getMessage()), HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler({TelefoneNaoEncontradoException.class})
	public ResponseEntity<Erro> handleTelefoneNaoEncontrado(TelefoneNaoEncontradoException e){
		return new ResponseEntity<Erro> (new Erro(e.getMessage()), HttpStatus.NOT_FOUND);
		
	}
	class Erro{
		private final String erro;
		
		public Erro(String erro) {
			this.erro = erro;
		}
		
		public String getErro() {
			return erro;
		}
	}
}
