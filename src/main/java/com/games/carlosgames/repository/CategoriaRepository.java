package com.games.carlosgames.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.games.carlosgames.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // OK: Busca categorias pelo tipo (parcial e ignorando caixa)
    public List<Categoria> findAllByTipoContainingIgnoreCase(String tipo);
}