
package com.games.carlosgames.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.games.carlosgames.model.Categoria;
// Certifique-se de que este import está correto
import com.games.carlosgames.model.Jogo;
import com.games.carlosgames.repository.CategoriaRepository;
import com.games.carlosgames.repository.JogoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/jogos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class JogoController {

    @Autowired
    private JogoRepository jogoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<Jogo>> getAll(){
        return ResponseEntity.ok(jogoRepository.findAll());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Jogo>> findByNome(@PathVariable String nome){
        return ResponseEntity.ok(jogoRepository.findAllByNomeContainingIgnoreCase(nome));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jogo> getById(@PathVariable Long id){
        return jogoRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Jogo> post(@Valid @RequestBody Jogo jogo){
        // Verifica se a categoria existe no banco de dados
        // O ID da categoria virá dentro do objeto 'jogo'
        Optional<Categoria> categoriaExistente = categoriaRepository.findById(jogo.getCategoria().getId());

        if (categoriaExistente.isPresent()) {
            // Se a categoria existir, salva o novo jogo e retorna 201 Created
            return ResponseEntity.status(HttpStatus.CREATED).body(jogoRepository.save(jogo));
        } else {
            // Se a categoria não existir, retorna 404 Not Found ou 400 Bad Request
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada!", null);
            // Ou, se preferir um erro 400, pode ser:
            // throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A categoria do jogo não existe ou não foi informada!", null);
        }
    }    	
    			
    @PutMapping("/atualizar/{id}") // Use o ID da URL para buscar o recurso a ser atualizado
    public ResponseEntity<Jogo> put(@Valid @RequestBody Jogo jogo, @PathVariable Long id){ // Adicionado @PathVariable id
        // 1. Verifica se o Jogo com o ID da URL existe
        Optional<Jogo> jogoExistente = jogoRepository.findById(id);

        // 2. Verifica se a Categoria informada no corpo da requisição existe
        Optional<Categoria> categoriaExistente = categoriaRepository.findById(jogo.getCategoria().getId());

        // Se ambos existirem, procede com a atualização
        if (jogoExistente.isPresent() && categoriaExistente.isPresent()) {
            // Garante que o ID do jogo a ser atualizado é o ID da URL
            jogo.setId(id);
            return ResponseEntity.status(HttpStatus.OK).body(jogoRepository.save(jogo));
        } else {
            // Se o jogo não existir ou a categoria não existir, retorna um erro apropriado
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Jogo ou Categoria não encontrados para atualização!",
                null
            );
        }        
        
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Jogo> jogo = jogoRepository.findById(id);
		
		if(jogo.isEmpty()) 
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			
		jogoRepository.deleteById(id);
		
	}

    // FALTAM O MÉTODO DELETE (APAGAR)
    // Adicionar métodos para DELETE é crucial para uma API REST completa.
}