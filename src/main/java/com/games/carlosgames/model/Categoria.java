
package com.games.carlosgames.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType; // Importe o CascadeType
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List; // Importe List

@Entity
@Table(name = "tb_categorias")
public class Categoria {

    // Uma categoria pode ter MUITOS jogos
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.REMOVE) // Mapeado pelo atributo "categoria" em Jogo
    @JsonIgnoreProperties("categoria") // Evita recursão infinita ao serializar
    private List<Jogo> jogos; // Deve ser uma lista de Jogos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O atributo tipo é obrigatório!")
    @Size(min = 3, max = 100, message = "O atributo tipo deve ter no mínimo 3 e no máximo 50 caracteres")
    private String tipo;

    // Getters e Setters para a lista de jogos
    public List<Jogo> getJogos() {
        return jogos;
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogos = jogos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}