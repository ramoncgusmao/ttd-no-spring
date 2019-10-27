package com.ramon.tdd.resources;

import static org.hamcrest.CoreMatchers.equalTo;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import com.ramon.tdd.SpringBootComTddApplicationTests;
import com.ramon.tdd.model.Pessoa;
import com.ramon.tdd.model.Telefone;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class PessoaResourceTest extends SpringBootComTddApplicationTests{


	private static final String CPF = "85748574851";
	private static final String LORENZO = "Lorenzo";


	@Test
	public void deveProcurarPessoaPeloDddENumeroDoTelefone() throws Exception {
		given()
		.pathParam("ddd", "86")
		.pathParam("numero", "35006330")
		.get("/pessoas/{ddd}/{numero}")
		.then()
		.log().body().and()
		.statusCode(HttpStatus.OK.value())
		.body("nome", equalTo("Cauê"), "cpf", equalTo("38767897100"));
				
	}
	
	
	@Test
	public void naoEncontrarPessoaPeloDddENumeroDoTelefone() throws Exception {
		given()
		.pathParam("ddd", "99")
		.pathParam("numero", "123456789")
		.get("/pessoas/{ddd}/{numero}")
		.then()
		.log().body().and()
		.statusCode(HttpStatus.NOT_FOUND.value())
		.body("erro", equalTo("Não existe pessoa com o telefone (99) 123456789"));
				
	}
	
	
	@Test
	public void deveSalvarUmaNovaPessoaNoSistema() throws Exception {
		Pessoa pessoa = new Pessoa();
		pessoa.setNome(LORENZO);
		pessoa.setCpf(CPF);
		Telefone telefone = new Telefone();
		telefone.setDdd("79");
		telefone.setNumero("36977168");
		
		pessoa.setTelefones(Arrays.asList(telefone));
		
		given()
		.request()
			.header("Accept", ContentType.ANY)
			.header("Content-type", ContentType.JSON)
			.body(pessoa)
		.when()
		.post("/pessoas")
		.then()
			.log().headers()
			.and()
			.log().body()
			.and()
			.statusCode(HttpStatus.CREATED.value())
			.header("Location", equalTo("http://localhost:"+porta+"/pessoas/79/36977168"))
			.body("nome",equalTo(LORENZO), 
					"cpf", equalTo(CPF));
		
	}
	
	
	@Test
	public void naoDeveSalvarDuasPessoasComOMesmoCPF() {
		Pessoa pessoa = new Pessoa();
		pessoa.setNome(LORENZO);
		pessoa.setCpf("86730543540");
		Telefone telefone = new Telefone();
		telefone.setDdd("79");
		telefone.setNumero("36977168");
		
		pessoa.setTelefones(Arrays.asList(telefone));
		
		given()
		.request()
			.header("Accept", ContentType.ANY)
			.header("Content-type", ContentType.JSON)
			.body(pessoa)
		.when()
		.post("/pessoas")
		.then()
			.log().body()
			.and()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("erro", equalTo("Já existe pessoa cadastrada com o CPF '86730543540'"));
			
		
	}
}

