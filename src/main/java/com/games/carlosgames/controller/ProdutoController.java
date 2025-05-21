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

import com.games.carlosgames.model.Produto;
import com.games.carlosgames.repository.CategoriaRepository;
import com.games.carlosgames.repository.ProdutoRepository;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<List<Produto>> getAll(){
		//SELECT * FROM tb_produto;
		return ResponseEntity.ok(produtoRepository.findAll());
	}
	
	@GetMapping("/preco/{preco}")
	public ResponseEntity<List<Produto>> getAllByPrecoMaior(@PathVariable Double preco) {
	    List<Produto> result = produtoRepository.findAllByPrecoGreaterThan(preco);
	    return ResponseEntity.ok(result);
	}
	
	@GetMapping("/preco/menor/{preco}")
	public ResponseEntity<List<Produto>> getAllByPrecoMenor(@PathVariable Double preco) {
	    List<Produto> result = produtoRepository.findAllByPrecoLessThan(preco);
	    return ResponseEntity.ok(result);
	}
	
	@PostMapping
	public ResponseEntity<Produto> post(@Valid @RequestBody Produto produto){
		if (categoriaRepository.existsById(produto.getId())) {
			
			return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));			
		}
		
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O tema não existe", null);		
		
	}
	
	@PutMapping
	public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto) {
	    return produtoRepository.findById(produto.getId())
	        .map(resposta -> ResponseEntity.ok().body(produtoRepository.save(produto)))
	        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		
		if(produto.isEmpty()) 
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			
		produtoRepository.deleteById(id);
		
	}
	
	

}
