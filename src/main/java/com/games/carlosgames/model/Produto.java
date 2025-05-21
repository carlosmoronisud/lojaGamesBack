package com.games.carlosgames.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "tb_produtos") //CREATE TABLE tb_produtos();
public class Produto {
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Categoria categoria;

	@Id // Primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
	private Long id;
	
	@Column(length = 100)
	@NotBlank(message = "O atributo título é obrigatório!")
	@Size(min = 5, max = 100, message =  "O atributo título de ter no minimo 5 e no maximo 100 caracteres!")
	private String titulo;
	
	@Column(length = 1000)
	@NotBlank(message = "O atributo descrção é obrigatório!")
	@Size(min = 10, max = 1000, message =  "O atributo descrição de ter no minimo 5 e no maximo 100 caracteres!")
	private String descricao;
	
	@Column(length = 1000)
	@NotBlank(message = "O atributo descrção é obrigatório!")
	@Size(min = 10, max = 1000, message =  "O atributo descrição de ter no minimo 5 e no maximo 100 caracteres!")
	private BigDecimal preco;
	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	
}
