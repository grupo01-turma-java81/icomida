package com.generation.icomida.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import com.generation.icomida.model.Produto;
import com.generation.icomida.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public ResponseEntity<List<Produto>> listaTodosProdutos() {
	return ResponseEntity.ok(produtoRepository.findAll());
    }

    public ResponseEntity<Produto> buscarPorId(Long id) {
	return produtoRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
		.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<List<Produto>> buscarPorNome(@PathVariable String nome) {
	return ResponseEntity.ok(produtoRepository.findByNomeContainingIgnoreCase(nome));
    }

    public ResponseEntity<Produto> cadastrarProduto(Produto produto) {
	return ResponseEntity.status(HttpStatus.CREATED).body(produtoRepository.save(produto));
    }

    public ResponseEntity<Produto> atualizarProduto(Produto produto) {
	return produtoRepository.findById(produto.getId())
		.map(respoata -> ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produto)))
		.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public void deletarProduto(Long id) {
	Optional<Produto> produto = produtoRepository.findById(id);

	if (produto.isEmpty()) {
	    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
	}

	produtoRepository.deleteById(id);
    }

    public List<Produto> recomendacaoDeProdutoSaudavel(Boolean saudavel) {
	return produtoRepository.findByProdutoSaudavelContaining(saudavel);
    }

}
