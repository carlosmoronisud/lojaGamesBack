package com.games.carlosgames.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.games.carlosgames.model.Categoria;



public interface CategoriaRepository {
	
	public List <Categoria> findAllByDescricaoContainingIgnoreCase(@Param("descricao") String descricao);

}
