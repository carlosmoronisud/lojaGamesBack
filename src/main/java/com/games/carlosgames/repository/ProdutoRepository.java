package com.games.carlosgames.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.games.carlosgames.model.Produto;



public interface ProdutoRepository extends  JpaRepository<Produto, Long>{
	
	public List <ProdutoRepository> findAllByTituloContainingIgnoreCase(@Param("titulo") String titulo);
	public List <ProdutoRepository> findAllByPrecoContainingIgnoreCase(@Param("preco") BigDecimal preco );

}
