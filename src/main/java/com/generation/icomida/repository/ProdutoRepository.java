package com.generation.icomida.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.icomida.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
    public List<Produto> findByNomeContainingIgnoreCase(@Param("nome") String nome);
    
    public List<Produto> findByProdutoSaudavelContainingIgnoreCase(@Param("saudavel") String saudavel);
}
