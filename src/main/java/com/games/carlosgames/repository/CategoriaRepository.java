package com.games.carlosgames.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import com.games.carlosgames.model.Categoria;




public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	
	public List <Categoria> findAllByDescricaoContainingIgnoreCase(@Param("descricao") String descricao);

}
