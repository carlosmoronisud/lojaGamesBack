package com.games.carlosgames.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import com.games.carlosgames.model.Produto;



public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	public List <Produto> findAllByTituloContainingIgnoreCase(@Param("titulo") String titulo);
	List<Produto> findAllByPrecoGreaterThan(Double preco);
	List<Produto> findAllByPrecoLessThan(Double preco);

}

