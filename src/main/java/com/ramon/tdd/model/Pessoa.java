package com.ramon.tdd.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 80, nullable = false)
	private String nome;
	@Column(length = 15, nullable = false)
	private String cpf;
	
	@OneToMany(mappedBy = "pessoa")
	private List<Endereco> enderecos;
	
	@OneToMany(mappedBy = "pessoa")
	private List<Telefone> telefones;
	
}
