package com.games.carlosgames.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional; // Importar Optional

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.repository.query.Param; // Não é mais necessário

import com.games.carlosgames.model.Jogo;

public interface JogoRepository extends JpaRepository<Jogo, Long> {

    // OK: Busca jogos pelo nome (parcial e ignorando caixa)
    public List<Jogo> findAllByNomeContainingIgnoreCase(String nome);

    // Métodos para buscar por preço
    public List<Jogo> findByPrecoLessThanEqual(BigDecimal preco); // Busca jogos com preço menor ou igual ao informado
    public List<Jogo> findByPrecoGreaterThanEqual(BigDecimal preco); // Busca jogos com preço maior ou igual ao informado
    public List<Jogo> findByPrecoBetween(BigDecimal precoInicial, BigDecimal precoFinal); // Busca jogos com preço entre dois valores

    // Optional: Se quiser um método para buscar apenas um jogo pelo ID (o método findById já existe na JpaRepository)
    // Optional<Jogo> findById(Long id); // Já é fornecido pela JpaRepository
}